package com.scxrh.amb.presenter;

import com.scxrh.amb.Const;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.manager.SettingsManager;
import com.scxrh.amb.model.City;
import com.scxrh.amb.model.SysInfo;
import com.scxrh.amb.rest.RestClient;
import com.scxrh.amb.views.view.MvpView;
import com.scxrh.amb.views.view.PerInfoView;

import javax.inject.Inject;

public class PerInfoPresenter
{
    @Inject
    RestClient rest;
    @Inject
    MessageManager message;
    @Inject
    SysInfo sysInfo;
    @Inject
    SettingsManager settings;
    private PerInfoView view;

    @Inject
    public PerInfoPresenter(MvpView view)
    {
        this.view = (PerInfoView)view;
    }

    public void initView()
    {
        view.setName(sysInfo.getName());
        view.changeCity(sysInfo.getCity());
        view.changeCommunity(sysInfo.getCommunity());
        sysInfo.observable("city", this, City.class).subscribe(view::changeCity);
        sysInfo.observable("community", this, City.class).subscribe(view::changeCommunity);
        sysInfo.observable("name", this, String.class).subscribe(view::setName);
    }

    public void onDestroyView()
    {
        sysInfo.unobservable(this);
    }

    public void modifyName(String name)
    {
        sysInfo.setName(name);
        settings.setValue(Const.KEY_USER_NAME, name);
    }

    public void submit()
    {
    }
}
