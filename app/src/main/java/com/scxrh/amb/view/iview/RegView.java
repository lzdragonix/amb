package com.scxrh.amb.view.iview;

public interface RegView
{
    void showProgress(String msg);

    void showError(String msg);

    void finish();

    void regSuccess();
}
