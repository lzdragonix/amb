package com.scxrh.amb.presenter;

import android.app.Activity;

import com.scxrh.amb.common.WindowNavigator;
import com.scxrh.amb.model.AppInfo;
import com.scxrh.amb.model.City;
import com.scxrh.amb.views.fragment.SettingsFragment;
import com.scxrh.amb.views.view.MainView;
import com.scxrh.amb.views.view.MvpView;

import javax.inject.Inject;

public class MainPresenter
{
    @Inject
    AppInfo appInfo;
    @Inject
    WindowNavigator navigator;
    @Inject
    Activity activity;
    private MainView view;

    @Inject
    public MainPresenter(MvpView view)
    {
        this.view = (MainView)view;
    }

    public void initView()
    {
        view.changeCity(appInfo.getCity());
        view.changeCommunity(appInfo.getCommunity());
        appInfo.observable("city", this, City.class).subscribe(view::changeCity);
        appInfo.observable("community", this, City.class).subscribe(view::changeCommunity);
        appInfo.observable("tab", this, String.class).subscribe(view::changeTab);
    }

    public void onDestroyView()
    {
        appInfo.unobservable(this);
    }

    public void showSettings()
    {
        if (!appInfo.isLogin())
        {
            navigator.startLogin(activity);
            return;
        }
        navigator.startWindow(activity, SettingsFragment.class.getName());
    }
}
