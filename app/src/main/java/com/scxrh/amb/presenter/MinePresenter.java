package com.scxrh.amb.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.scxrh.amb.common.WindowNavigator;
import com.scxrh.amb.manager.SettingsManager;
import com.scxrh.amb.model.AppInfo;
import com.scxrh.amb.model.UserInfo;
import com.scxrh.amb.views.fragment.FavoriteFragment;
import com.scxrh.amb.views.fragment.OrderFragment;
import com.scxrh.amb.views.fragment.PerInfoFragment;
import com.scxrh.amb.views.fragment.PointsFragment;
import com.scxrh.amb.views.fragment.SuggestionFragment;
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
            if (appInfo.isLogin())
            {
                if (TextUtils.isEmpty(userInfo.getUserName()))
                {
                    view.setAccount(userInfo.getTelephone());
                }
                else
                {
                    view.setAccount(userInfo.getUserName());
                }
            }
            else
            {
                view.setAccount("请登录您的账户");
            }
        });
    }

    public void onDestroyView()
    {
        appInfo.unobservable(this);
    }

    public void openWindow(int type)
    {
        if (!appInfo.isLogin())
        {
            navigator.startLogin(activity);
            return;
        }
        String name = "";
        switch (type)
        {
            case 1:
                name = PerInfoFragment.class.getName();
                break;
            case 2:
                name = FavoriteFragment.class.getName();
                break;
            case 3:
                name = OrderFragment.class.getName();
                break;
            case 4:
                name = SuggestionFragment.class.getName();
                break;
            case 5:
                name = PointsFragment.class.getName();
                break;
        }
        navigator.startWindow(activity, name);
    }
}
