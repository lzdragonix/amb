package com.scxrh.amb.presenter;

import android.util.Log;

import com.scxrh.amb.view.iview.MainView;

import javax.inject.Inject;

public class MainPresenter
{
    private MainView mainView;

    @Inject
    public MainPresenter(MainView mainView)
    {
        Log.i("===============","MainPresenter create.");
        this.mainView = mainView;
    }

    public void initialize()
    {
        mainView.initTab();
    }

    public void changeTab(String tab)
    {
        mainView.changeTab(tab);
    }
}
