package com.scxrh.amb.view.iview;

public interface LiveView
{
    void showProgress(String msg);

    void showMain();

    void showError(String msg);

    void loginFinished();
}