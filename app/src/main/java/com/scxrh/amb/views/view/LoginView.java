package com.scxrh.amb.views.view;

public interface LoginView
{
    void initUser(String user);

    void initPwd(String pwd);

    void showProgress(String msg);

    void showMain();

    void showError(String msg);

    void finish();
}
