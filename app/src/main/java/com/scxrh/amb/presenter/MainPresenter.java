package com.scxrh.amb.presenter;

import com.scxrh.amb.model.City;
import com.scxrh.amb.model.SysInfo;
import com.scxrh.amb.views.view.MainView;
import com.scxrh.amb.views.view.MvpView;

import javax.inject.Inject;

public class MainPresenter
{
    @Inject
    SysInfo sysInfo;
    private MainView view;

    @Inject
    public MainPresenter(MvpView view)
    {
        this.view = (MainView)view;
    }

    public void initialize()
    {
        view.initTab();
        sysInfo.observable("city", City.class).subscribe(view::changeCity);
        sysInfo.observable("community", City.class).subscribe(view::changeCommunity);
    }

    public void changeTab(String tab)
    {
        view.changeTab(tab);
    }
}
