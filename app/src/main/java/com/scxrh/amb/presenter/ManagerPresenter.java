package com.scxrh.amb.presenter;

import com.scxrh.amb.views.view.MvpView;
import com.scxrh.amb.views.view.ProgressView;

import javax.inject.Inject;

public class ManagerPresenter
{
    private ProgressView view;

    @Inject
    public ManagerPresenter(MvpView view)
    {
        this.view = (ProgressView)view;
    }
}
