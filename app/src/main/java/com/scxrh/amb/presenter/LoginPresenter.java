package com.scxrh.amb.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.scxrh.amb.Const;
import com.scxrh.amb.common.DES;
import com.scxrh.amb.common.Utils;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.manager.SettingsManager;
import com.scxrh.amb.net.http.HttpClient;
import com.scxrh.amb.rest.RestRepository;
import com.scxrh.amb.views.view.LoginView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

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
    SettingsManager settings;
    @Inject
    RestRepository rest;

    @Inject
    public LoginPresenter() { }

    public void initialize()
    {
        String user = settings.getString(Const.KEY_ACCOUNT);
        if (TextUtils.isEmpty(user)) { return; }
        view.initUser(user);
    }

    public void login(final String user, final String pwd)
    {
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pwd))
        {
            view.showError(messager.getMessage(Const.MSG_EMPTY_USER_OR_PASSWORD));
            view.finish();
            return;
        }
        if (!Utils.regex(user, Const.REGEX_MOBILE))
        {
            view.showError(messager.getMessage(Const.MSG_MOBILE_ILLEGAL));
            view.finish();
            return;
        }
        view.showProgress(messager.getMessage(Const.MSG_LOGIN));
        rest.login(user, pwd).observeOn(AndroidSchedulers.mainThread()).subscribe(response -> {
            settings.setValue(Const.KEY_ACCOUNT, user);
            settings.setValue(Const.KEY_PASSWORD, DES.encrypt(pwd, DES.getKey()));
            view.finish();
            view.showMain();
        }, e -> {
            view.showError(messager.getMessage(Const.MSG_SERVER_ERROR));
            view.finish();
        });
    }

    public void inputUser(String user)
    {
        view.initUser(user);
    }

    public void inputPwd(String pwd)
    {
        view.initPwd(pwd);
    }
}
