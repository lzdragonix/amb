package com.scxrh.amb.presenter;

import com.scxrh.amb.Const;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.manager.SettingsManager;
import com.scxrh.amb.rest.RestClient;
import com.scxrh.amb.views.view.MvpView;
import com.scxrh.amb.views.view.ProgressView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

public class LiveMSPresenter
{
    private static final String TAG = LiveMSPresenter.class.getSimpleName();
    ProgressView view;
    @Inject
    MessageManager message;
    @Inject
    RestClient rest;
    @Inject
    SettingsManager settings;

    @Inject
    public LiveMSPresenter(MvpView view)
    {
        this.view = (ProgressView)view;
    }

    public void loadData(int index)
    {
        view.showProgress(message.getMessage(Const.MSG_LOADING));
        rest.queryUIData(getPageName(index)).observeOn(AndroidSchedulers.mainThread()).subscribe(list -> {
            view.showData(list);
            view.finish();
        }, throwable -> {
            view.showMessage(message.getMessage(Const.MSG_LOADING_FAILED));
            view.finish();
        });
    }

    private String getPageName(int index)
    {
        switch (index)
        {
            case 2:
                return "shop";
            case 4:
                return "entertainment";
            default:
                return "food";
        }
    }
}
