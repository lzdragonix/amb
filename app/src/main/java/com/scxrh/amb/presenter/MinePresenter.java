package com.scxrh.amb.presenter;

import com.scxrh.amb.model.SysInfo;
import com.scxrh.amb.views.view.MineView;
import com.scxrh.amb.views.view.MvpView;

import javax.inject.Inject;

public class MinePresenter
{
    @Inject
    SysInfo sysInfo;
    private MineView view;

    @Inject
    public MinePresenter(MvpView view)
    {
        this.view = (MineView)view;
    }

    public void initView()
    {
        view.changeAvatar(sysInfo.getAvatar());
        sysInfo.observable("avatar", this, String.class).subscribe(view::changeAvatar);
    }

    public void onDestroyView()
    {
        sysInfo.unobservable(this);
    }
}
