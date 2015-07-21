package com.scxrh.amb.presenter;

import android.net.Uri;

import com.scxrh.amb.Const;
import com.scxrh.amb.manager.DirManager;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.manager.SettingsManager;
import com.scxrh.amb.model.City;
import com.scxrh.amb.model.SysInfo;
import com.scxrh.amb.rest.RestClient;
import com.scxrh.amb.views.view.MvpView;
import com.scxrh.amb.views.view.PerInfoView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

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
    @Inject
    DirManager dir;
    private PerInfoView view;
    private Uri uriFile;

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
        view.changeAvatar(sysInfo.getAvatar());
        sysInfo.observable("city", this, City.class).subscribe(view::changeCity);
        sysInfo.observable("community", this, City.class).subscribe(view::changeCommunity);
        sysInfo.observable("name", this, String.class).subscribe(view::setName);
        sysInfo.observable("avatar", this, String.class).subscribe(view::changeAvatar);
    }

    public void onDestroyView()
    {
        sysInfo.unobservable(this);
    }

    public void loadData()
    {
        view.showProgress(message.getMessage(Const.MSG_LOADING));
        rest.queryCurUserInfo().observeOn(AndroidSchedulers.mainThread()).subscribe(userInfo -> {
            view.showData(userInfo);
            view.finish();
        }, throwable -> {
            if (throwable.getMessage().contains("un-login"))
            {
                view.finish();
                view.showLogin();
                view.close();
            }
            else
            {
                view.showError(message.getMessage(Const.MSG_LOADING));
                view.finish();
            }
        });
    }

    public void modifyName(String name)
    {
        sysInfo.setName(name);
        settings.setValue(Const.KEY_USER_NAME, name);
    }

    public Uri getUriFile()
    {
        if (uriFile == null)
        {
            String path = "file://" + dir.getPath() + "/" + System.currentTimeMillis();
            uriFile = Uri.parse(path);
        }
        return uriFile;
    }

    public void modifyAvatar()
    {
        String path = "file://" + uriFile.getPath();
        sysInfo.setAvatar(path);
        settings.setValue(Const.KEY_AVATAR, path);
    }

    public void submit()
    { }
}
