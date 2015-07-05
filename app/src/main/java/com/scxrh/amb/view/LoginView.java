package com.scxrh.amb.view;

public interface LoginView extends MvpView
{
    void showProgress(String msg);

    void showError(String error);
}
