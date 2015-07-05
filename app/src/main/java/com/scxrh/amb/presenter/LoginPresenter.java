package com.scxrh.amb.presenter;

import android.text.TextUtils;

import com.scxrh.amb.Const;
import com.scxrh.amb.view.LoginView;

import javax.inject.Inject;

public class LoginPresenter
{
    private LoginView view;

    @Inject
    public LoginPresenter(LoginView view)
    {
        this.view = view;
    }

    public void login(String user, String pwd)
    {
        view.showLogining();
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pwd))
        {
            view.showError(Const.TOAST_EMPTY_USER_OR_PASSWORD);
        }
    }
}
