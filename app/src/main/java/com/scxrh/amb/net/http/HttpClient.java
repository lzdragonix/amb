package com.scxrh.amb.net.http;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;
import java.util.List;


public class HttpClient
{
    /**
     * 成功
     */
    public static final String RETURNCODE_0000 = "0000";
    /**
     * 未登录
     */
    public static final String RETURNCODE_0001 = "0001";
    /**
     * 接口返回错误代码键
     */
    public static final String KEY_RETURNCODE = "code";
    /**
     * 登录账号
     */
    public static final String KEY_ACCOUNT = "KEY_ACCOUNT";
    /**
     * 登录密码
     */
    public static final String KEY_PASSWORD = "KEY_PASSWORD";
    /**
     * 登录服务器地址 baseUrl
     */
    public static final String KEY_SERVER = "KEY_SERVER";
    /**
     * 登录web服务器接口
     */
    public static final String URL_LOGIN = "/interface/logincheck";
    /**
     * 数据源接口
     */
    public static final String URL_DATAACCESS = "/interface/dataaccess";
    private static HttpClient sHttpClient;
    private Context mContext;
    private SyncHttpClient sClient;
    private AsyncHttpClient aClient;
    private Handler sHandler;
    private volatile boolean isLogin;
    private CookieStore mCookieStore;
    private List<String> mWithoutLoginUrl = new ArrayList<>();

    public HttpClient(Context context)
    {
        mContext = context;
        sClient = new SyncHttpClient(true, 80, 443);
        aClient = new AsyncHttpClient(true, 80, 443);
        HandlerThread ht = new HandlerThread("http-task");
        ht.start();
        sHandler = new Handler(ht.getLooper());
    }

    public void login(Context requester, String url, RequestParams params, IHttpResponse response)
    {
        HttpTask task = new HttpTask(url, params, requester);
        task.mWithoutLogin = mWithoutLoginUrl.contains(url);
        task.mResponse = response;
        task.mClient = this;
        task.mLoginTask = true;
        post(task);
    }

    public void post(Context requester, String url)
    {
        post(requester, url, new RequestParams(), null);
    }

    public void post(Context requester, String url, RequestParams params)
    {
        post(requester, url, params, null);
    }

    public void post(Context requester, String url, RequestParams params, IHttpResponse response)
    {
        if (response == null && !(requester instanceof IHttpResponse))
        {
            throw new IllegalArgumentException("both response is null and requester not implements IHttpResponse.");
        }
        HttpTask task = new HttpTask(url, params, requester);
        task.mWithoutLogin = mWithoutLoginUrl.contains(task.url);
        task.mResponse = response;
        task.mClient = this;
        post(task);
    }

    public boolean isLogin()
    {
        return isLogin;
    }

    void setLogin(final boolean b)
    {
        sHandler.postAtFrontOfQueue(new Runnable()
        {
            @Override
            public void run()
            {
                isLogin = b;
                if (b)
                {
                    //保存cookie
                    mCookieStore = ((DefaultHttpClient)sClient.getHttpClient()).getCookieStore();
                }
            }
        });
    }

    public SyncHttpClient getSyncClient()
    {
        if (mCookieStore != null) { sClient.setCookieStore(mCookieStore); }
        return sClient;
    }

    public AsyncHttpClient getAsynClient()
    {
        if (mCookieStore != null) { aClient.setCookieStore(mCookieStore); }
        return aClient;
    }

    public void cancelRequests(Context requester)
    {
        aClient.cancelRequests(requester, true);
        sClient.cancelRequests(requester, true);
    }

    public void registerWithoutLoginUrl(String url)
    {
        mWithoutLoginUrl.add(url);
    }

    public CookieStore getCookie()
    {
        return mCookieStore;
    }

    void post(HttpTask task)
    {
        sHandler.post(task);
    }

    /**
     * 如果未登录web服务器，先登录，再执行后续http task
     *
     * @param task 未登录时执行的http task
     */
    void postWithReLogin(HttpTask task)
    {
        if (isLogin) { post(task); }
        else
        {
//            SettingsManager instance = SettingsManager.getInstance(mContext);
//            String usr = instance.getString(KEY_ACCOUNT);
//            String pwd = instance.getString(KEY_PASSWORD);
//            pwd = DES.decrypt(pwd, DES.getKey());
//            if (TextUtils.isEmpty(usr) || TextUtils.isEmpty(pwd))
//            {
//                throw new IllegalArgumentException("user account or password is null.");
//            }
//            String baseUrl = instance.getString(KEY_SERVER);
//            String url = Utils.getAbsoluteUrl(baseUrl, URL_LOGIN);
//            RequestParams params = new RequestParams();
//            params.put("login_account", usr);
//            params.put("password", pwd);
//            params.put("comefrom", "01");
//            params.put("datascope", "all");
//            HttpTask loginTask = new HttpTask(url, params, mContext);
//            loginTask.mWithoutLogin = mWithoutLoginUrl.contains(url);
//            loginTask.mClient = this;
//            loginTask.mLoginTask = true;
//            loginTask.mRunTask = task;
//            post(loginTask);
        }
    }
}
