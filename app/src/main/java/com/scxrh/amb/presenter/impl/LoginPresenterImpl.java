package com.scxrh.amb.presenter.impl;

import com.scxrh.amb.presenter.MvpBasePresenter;
import com.scxrh.amb.view.LoginView;

/**
 * Created by leo on 7/5/15.
 */
public class LoginPresenterImpl extends MvpBasePresenter<LoginView>
{
    public void login(String user, String pwd)
    {
        getView().showProgress(user + "======" + pwd);
    }
}
