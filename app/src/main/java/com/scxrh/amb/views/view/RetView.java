package com.scxrh.amb.views.view;

public interface RetView
{
    void showProgress(String msg);

    void showError(String msg);

    void finish();

    void regSuccess();
}
