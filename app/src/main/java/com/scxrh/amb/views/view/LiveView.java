package com.scxrh.amb.views.view;

public interface LiveView
{
    void showProgress(String msg);

    void showMain();

    void showError(String msg);

    void loginFinished();
}
