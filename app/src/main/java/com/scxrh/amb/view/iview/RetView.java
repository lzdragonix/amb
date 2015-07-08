package com.scxrh.amb.view.iview;

public interface RetView
{
    void showProgress(String msg);

    void showError(String msg);

    void finish();

    void regSuccess();
}
