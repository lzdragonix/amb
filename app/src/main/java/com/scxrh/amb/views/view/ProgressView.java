package com.scxrh.amb.views.view;

import java.util.List;

public interface ProgressView extends MvpView
{
    void showProgress(String msg);

    void showError(String msg);

    void showData(List<?> data);

    void finish();
}
