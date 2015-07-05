package com.scxrh.amb.net.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;


public class HttpTask implements Runnable
{
    private static final String TAG = HttpTask.class.getSimpleName();
    protected volatile boolean cancel = false;
    String url;
    RequestParams params;
    Context mRequester;
    Handler mHandler;
    HttpClient mClient;
    //HttpClient#post方法传入的参数，优先使用，若为空则使用mContext实现的IHttpResponse接口
    IHttpResponse mResponse;
    //当登录出错时，存放出错的HttpTask
    HttpTask mRunTask;
    //是否是不需要登录的url
    boolean mWithoutLogin = false;
    //是否登录任务
    boolean mLoginTask = false;

    HttpTask(String url, RequestParams params, Context requester)
    {
        this.url = url;
        this.params = params;
        this.mRequester = requester;
        mHandler = new Handler(Looper.getMainLooper());
    }

    //获取IHttpResponse接口，可能来至于参数或mContext
    private IHttpResponse getResponse()
    {
        return mResponse == null ? (IHttpResponse)mRequester : mResponse;
    }

    @Override
    public void run()
    {
        if (cancel) { return; }
        if (mClient.isLogin() || mWithoutLogin)
        {
            Log.i(TAG, "is login or without login task, call async method.");
            if (mLoginTask)
            {
                Log.i(TAG, "login success, ignore this login task.");
            }
            else { mClient.getAsynClient().post(mRequester, url, params, new HttpHandler()); }
        }
        else
        {
            Log.i(TAG, "is not login, call sync method");
            mClient.getSyncClient().post(mRequester, url, params, new HttpHandler());
        }
    }

    //login task或重登task 执行成功
    private void onLoginSuccess(String rc, final JSONObject response)
    {
        if (HttpClient.RETURNCODE_0000.equals(rc))
        {
            mClient.setLogin(true);
            if (mRunTask != null)
            {
                Log.i(TAG, "login success, then call task.");
                mClient.post(mRunTask);
            }
            else
            {
                //在主线程中执行子类的success(JSONObject response)方法
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        getResponse().onHttpSuccess(response);
                    }
                });
            }
        }
        else
        {
            if (mRunTask != null) { return; }
            //返回非0000，执行失败回调
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    getResponse().onHttpFailure(response);
                }
            });
        }
    }

    //非login task执行成功
    private void onTaskSuccess(String rc, final JSONObject response)
    {
        switch (rc)
        {
            case HttpClient.RETURNCODE_0000:
                //在主线程中执行子类的success(JSONObject response)方法
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        getResponse().onHttpSuccess(response);
                    }
                });
                break;
            case HttpClient.RETURNCODE_0001:
                //未登录，重新登录
                Log.i(TAG, "is not login, login again.");
                mClient.setLogin(false);
                mClient.postWithReLogin(HttpTask.this);
                break;
            default:
                if (mRunTask == null)
                {
                    //其他错误
                    mHandler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            getResponse().onHttpFailure(response);
                        }
                    });
                }
                break;
        }
    }

    //基于android-async-http库的JsonHttpResponseHandler实现的http请求回调类
    private class HttpHandler extends JsonHttpResponseHandler
    {
        @Override
        public void onStart()
        {
            //如果是重新执行登录失败时的http task，则跳过
            if (mRunTask != null) { return; }
            //在主线程中执行start()方法
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    getResponse().onHttpStart();
                }
            });
        }

        @Override
        public void onFinish()
        {
            //如果是重新执行登录失败时的http task，则跳过
            if (mRunTask != null) { return; }
            //在主线程中执行finish()方法
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    getResponse().onHttpFinish();
                }
            });
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, final JSONObject response)
        {
            String rc = response.optString(HttpClient.KEY_RETURNCODE);
            if (mLoginTask) { onLoginSuccess(rc, response); }
            else { onTaskSuccess(rc, response); }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, final JSONObject errorResponse)
        {
            //如果是重新执行登录失败时的http task，则跳过
            if (mRunTask != null) { return; }
            Log.e(TAG, "http task failure, statusCode:" + statusCode, throwable);
            //在主线程中执行failure()方法
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    getResponse().onHttpFailure(errorResponse);
                }
            });
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
        {
            //如果是重新执行登录失败时的http task，则跳过
            if (mRunTask != null) { return; }
            Log.e(TAG, "http task failure, statusCode:" + statusCode + "responseString:" + responseString,
                        throwable);
            //在主线程中执行failure()方法
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    getResponse().onHttpFailure(null);
                }
            });
        }
    }
}
