package com.scxrh.amb.presenter;

import android.text.TextUtils;

import com.scxrh.amb.Const;
import com.scxrh.amb.manager.SettingsManager;
import com.scxrh.amb.model.AppInfo;
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
        String acc = settings.getString(Const.KEY_ACCOUNT);
        if (!TextUtils.isEmpty(acc))
        {
            view.setAccount(acc);
        }
    }

    public void onDestroyView()
    {
        appInfo.unobservable(this);
    }
}
