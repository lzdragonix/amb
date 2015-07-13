package com.scxrh.amb.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.loopj.android.http.RequestParams;
import com.scxrh.amb.Const;
import com.scxrh.amb.common.Utils;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.net.http.HttpClient;
import com.scxrh.amb.net.http.IHttpResponse;
import com.scxrh.amb.rest.RestRepository;
import com.scxrh.amb.views.view.RetView;

import org.json.JSONObject;

import javax.inject.Inject;

public class RetPresenter
{
    private static final String TAG = RetPresenter.class.getSimpleName();
    @Inject
    MessageManager message;
    @Inject
    RetView view;
    @Inject
    HttpClient client;
    @Inject
    Activity activity;
    @Inject
    RestRepository rest;

    @Inject
    public RetPresenter() { }

    public void retrieve(String user, String pwd, String pwd1, String verify, boolean agree)
    {
        if (!Utils.regex(user, Const.REGEX_MOBILE))
        {
            view.showError(message.getMessage(Const.MSG_MOBILE_ILLEGAL));
            view.finish();
            return;
        }
        if (TextUtils.isEmpty(verify))
        {
            view.showError(message.getMessage(Const.MSG_INPUT_VERIFY_CODE));
            view.finish();
            return;
        }
        if (pwd == null || pwd.length() < 6)
        {
            view.showError(message.getMessage(Const.MSG_PWD_IS_SHORT));
            view.finish();
            return;
        }
        if (!agree)
        {
            view.showError(message.getMessage(Const.MSG_AGREE_REG_PROTOCOL));
            view.finish();
            return;
        }

        RequestParams params = new RequestParams();
        params.put("telephone", user);
        params.put("verifyNo", verify);
        params.put("password", pwd);
        client.post(activity, Const.URL_USER_REGISTER, params, new IHttpResponse()
        {
            @Override
            public void onHttpStart()
            {
                view.showProgress(message.getMessage(Const.MSG_SUBMITTING));
            }

            @Override
            public void onHttpSuccess(JSONObject response)
            {
                view.regSuccess();
            }

            @Override
            public void onHttpFailure(JSONObject response)
            {
                view.showError(message.getMessage(Const.MSG_REG_FAILED));
            }

            @Override
            public void onHttpFinish()
            {
                view.finish();
            }
        });
    }
}
