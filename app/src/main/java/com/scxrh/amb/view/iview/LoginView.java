package com.scxrh.amb.view.iview;

public interface LoginView
{
    void showProgress(String msg);

    void showMain();

    void showError(String msg);

    void loginFinished();
}
