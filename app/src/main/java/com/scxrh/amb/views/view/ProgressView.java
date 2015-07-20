package com.scxrh.amb.views.view;

public interface ProgressView extends MvpView
{
    void showProgress(String msg);

    void showError(String msg);

    void showData(Object data);

    void finish();
}
