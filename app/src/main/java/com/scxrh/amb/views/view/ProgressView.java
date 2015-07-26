package com.scxrh.amb.views.view;

public interface ProgressView extends MvpView
{
    void showProgress(String msg);

    void showMessage(String msg);

    void showData(Object data);

    void finish();
}
