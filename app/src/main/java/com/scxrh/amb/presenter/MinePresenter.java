package com.scxrh.amb.presenter;

import android.text.TextUtils;

import com.scxrh.amb.manager.SettingsManager;
import com.scxrh.amb.model.AppInfo;
import com.scxrh.amb.model.UserInfo;
import com.scxrh.amb.views.view.MineView;
import com.scxrh.amb.views.view.MvpView;

import javax.inject.Inject;

public class MinePresenter
{
    @Inject
    AppInfo appInfo;
    @Inject
    SettingsManager settings;
    private MineView view;

    @Inject
    public MinePresenter(MvpView view)
    {
        this.view = (MineView)view;
    }

    public void initView()
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
}
