package com.scxrh.amb.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.scxrh.amb.common.WindowNavigator;
import com.scxrh.amb.manager.SettingsManager;
import com.scxrh.amb.model.AppInfo;
import com.scxrh.amb.model.UserInfo;
import com.scxrh.amb.views.fragment.PerInfoFragment;
import com.scxrh.amb.views.view.MineView;
import com.scxrh.amb.views.view.MvpView;

import javax.inject.Inject;

public class MinePresenter
{
    @Inject
    AppInfo appInfo;
    @Inject
    SettingsManager settings;
    @Inject
    WindowNavigator navigator;
    @Inject
    Activity activity;
    private MineView view;

    @Inject
    public MinePresenter(MvpView view)
    {
        this.view = (MineView)view;
    }

    public void init()
    {
        view.changeAvatar(appInfo.getAvatar());
        appInfo.observable("avatar", this, String.class).subscribe(view::changeAvatar);
        appInfo.observable("userInfo", this, UserInfo.class).subscribe(userInfo -> {
            if (TextUtils.isEmpty(userInfo.getUserName()))
            {
                view.setAccount(userInfo.getTelephone());
            }
            else
            {
                view.setAccount(userInfo.getUserName());
            }
        });
    }

    public void onDestroyView()
    {
        appInfo.unobservable(this);
    }

    public void showGRXX()
    {
        if (!appInfo.isLogin())
        {
            navigator.startLogin(activity);
            return;
        }
        navigator.startWindow(activity, PerInfoFragment.class.getName());
    }
}
