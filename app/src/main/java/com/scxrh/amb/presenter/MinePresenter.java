package com.scxrh.amb.presenter;

import com.scxrh.amb.model.AppInfo;
import com.scxrh.amb.views.view.MineView;
import com.scxrh.amb.views.view.MvpView;

import javax.inject.Inject;

public class MinePresenter
{
    @Inject
    AppInfo appInfo;
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
    }

    public void onDestroyView()
    {
        appInfo.unobservable(this);
    }
}
