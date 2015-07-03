package com.scxrh.amb.presenter.impl;

import com.scxrh.amb.presenter.MainPresenter;
import com.scxrh.amb.view.MainView;

public class MainPresenterImpl implements MainPresenter
{
    private MainView mainView;

    public MainPresenterImpl(MainView mainView)
    {
        this.mainView = mainView;
    }

    @Override
    public void initialize()
    {
        mainView.initTab();
    }

    @Override
    public void changeTab(String tab)
    {
        mainView.changeTab(tab);
    }
}
