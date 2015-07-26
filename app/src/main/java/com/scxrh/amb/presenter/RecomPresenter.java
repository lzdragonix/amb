package com.scxrh.amb.presenter;

import com.scxrh.amb.Const;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.model.AppInfo;
import com.scxrh.amb.rest.RestClient;
import com.scxrh.amb.views.fragment.MainFragment;
import com.scxrh.amb.views.view.MvpView;
import com.scxrh.amb.views.view.ProgressView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

public class RecomPresenter
{
    private static final String TAG = RecomPresenter.class.getSimpleName();
    ProgressView view;
    @Inject
    MessageManager message;
    @Inject
    RestClient rest;
    @Inject
    AppInfo appInfo;

    @Inject
    public RecomPresenter(MvpView view)
    {
        this.view = (ProgressView)view;
    }

    public void loadData()
    {
        view.showProgress(message.getMessage(Const.MSG_LOADING));
        rest.queryUIData("home").observeOn(AndroidSchedulers.mainThread()).subscribe(list -> {
            view.showData(list);
            view.finish();
        }, throwable -> {
            view.showMessage(message.getMessage(Const.MSG_LOADING_FAILED));
            view.finish();
        });
    }

    public void goFinTab()
    {
        appInfo.changeTab(MainFragment.TAB_FINANCE);
    }
}
