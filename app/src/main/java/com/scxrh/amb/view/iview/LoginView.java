package com.scxrh.amb.view.iview;

public interface LoginView
{
    void initUser(String user);

    void initPwd(String pwd);

    void showProgress(String msg);

    void showMain();

    void showError(String msg);

    void finish();
}
