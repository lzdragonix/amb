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

    public void initView()
    {
        view.changeCity(sysInfo.getCity());
        view.changeCommunity(sysInfo.getCommunity());
        sysInfo.observable("city", this, City.class).subscribe(view::changeCity);
        sysInfo.observable("community", this, City.class).subscribe(view::changeCommunity);
    }

    public void onDestroyView()
    {
        sysInfo.unobservable(this);
    }
}
