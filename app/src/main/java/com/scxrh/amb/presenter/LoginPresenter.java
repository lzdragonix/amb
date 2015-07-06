package com.scxrh.amb.presenter;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.scxrh.amb.Const;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.net.http.HttpClient;
import com.scxrh.amb.net.http.IHttpResponse;
import com.scxrh.amb.view.iview.LoginView;

import org.json.JSONObject;

import javax.inject.Inject;

public class LoginPresenter
{
    private static final String TAG = LoginPresenter.class.getSimpleName();
    @Inject
    MessageManager messager;
    @Inject
    HttpClient client;
    @Inject
    LoginView view;
    @Inject
    Activity activity;

    @Inject
    public LoginPresenter() { }

    public void login(String user, String pwd)
    {
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pwd))
        {
            view.showError(messager.getMessage(Const.MSG_EMPTY_USER_OR_PASSWORD));
        }
        RequestParams params = new RequestParams();
        params.put("userKey", user);
        params.put("password", pwd);
        client.post(activity, Const.URL_LOGIN, params, new IHttpResponse()
        {
            @Override
            public void onHttpStart()
            {
                Log.i(TAG, "onHttpStart");
                view.showProgress(messager.getMessage(Const.MSG_LOGIN));
            }

            @Override
            public void onHttpSuccess(JSONObject response)
            {
                Log.i(TAG, "onHttpSuccess");
                view.showMain();
            }

            @Override
            public void onHttpFailure(JSONObject response)
            {
                Log.i(TAG, "onHttpFailure");
                view.showError(messager.getMessage(Const.MSG_SERVER_ERROR));
            }

            @Override
            public void onHttpFinish()
            {
                Log.i(TAG, "onHttpFinish");
                view.loginFinished();
            }
        });
    }
}
