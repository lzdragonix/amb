package com.scxrh.amb.presenter;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.scxrh.amb.Const;
import com.scxrh.amb.net.http.HttpClient;
import com.scxrh.amb.net.http.IHttpResponse;
import com.scxrh.amb.view.iview.RegView;

import org.json.JSONObject;

import javax.inject.Inject;

public class RegPresenter
{
    private static final String TAG = RegPresenter.class.getSimpleName();
    private RegView view;
    private HttpClient client;
    private Activity activity;

    @Inject
    public RegPresenter(RegView view, HttpClient client, Activity activity)
    {
        this.view = view;
        this.client = client;
        this.activity = activity;
    }

    public void reg(String user, String pwd)
    {
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pwd))
        {
            view.showError(Const.TOAST_EMPTY_USER_OR_PASSWORD);
        }
        RequestParams params = new RequestParams();
        params.put("telephone", user);
        params.put("verifyNo", "1111");
        params.put("password", pwd);
        client.post(activity, Const.URL_USER_REGISTER, params, new IHttpResponse()
        {
            @Override
            public void onHttpStart()
            {
                view.showReging();
            }

            @Override
            public void onHttpSuccess(JSONObject response)
            {
                Log.i(TAG, "onHttpSuccess");
            }

            @Override
            public void onHttpFailure(JSONObject response)
            {
                Log.i(TAG, "onHttpFailure");
            }

            @Override
            public void onHttpFinish()
            {
                Log.i(TAG, "onHttpFinish");
                view.showError(Const.TOAST_EMPTY_USER_OR_PASSWORD);
            }
        });
    }

    public void getSMS(String moblie)
    {
        if (TextUtils.isEmpty(moblie))
        {
            view.showError(Const.TOAST_EMPTY_USER_OR_PASSWORD);
        }
    }
}
