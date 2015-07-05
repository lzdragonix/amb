package com.scxrh.amb.presenter.impl;

import android.text.TextUtils;

import com.scxrh.amb.Const;
import com.scxrh.amb.feature.ToastHelper;
import com.scxrh.amb.presenter.MvpBasePresenter;
import com.scxrh.amb.view.LoginView;

import javax.inject.Inject;

public class LoginPresenterImpl extends MvpBasePresenter<LoginView>
{
    @Inject
    ToastHelper mToastHelper;
    public void login(String user, String pwd)
    {
        mToastHelper.test();
        if (!isViewAttached()) { return; }
        getView().showLogining();
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pwd))
        {
            getView().showError(Const.TOAST_EMPTY_USER_OR_PASSWORD);
        }
    }
}
