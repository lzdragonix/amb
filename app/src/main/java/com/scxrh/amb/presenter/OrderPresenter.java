package com.scxrh.amb.presenter;

import android.app.Activity;

import com.scxrh.amb.Const;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.common.WindowNavigator;
import com.scxrh.amb.model.AppInfo;
import com.scxrh.amb.rest.RestClient;
import com.scxrh.amb.views.view.MvpView;
import com.scxrh.amb.views.view.OrderView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

public class OrderPresenter
{
    @Inject
    RestClient rest;
    @Inject
    MessageManager message;
    @Inject
    AppInfo appInfo;
    @Inject
    WindowNavigator windowNavigator;
    @Inject
    Activity activity;
    private OrderView view;

    @Inject
    public OrderPresenter(MvpView view)
    {
        this.view = (OrderView)view;
    }

    public void loadData()
    {
        view.showProgress(message.getMessage(Const.MSG_LOADING));
        String userId = appInfo.getUserInfo().getUserId();
        rest.queryOrder(userId, "", "0").observeOn(AndroidSchedulers.mainThread()).subscribe(list -> {
            view.showData(list);
            view.finish();
        }, throwable -> {
            if (throwable.getMessage().contains("un-login"))
            {
                view.finish();
                windowNavigator.startLogin(activity);
                view.close();
            }
            else
            {
                view.showMessage(message.getMessage(Const.MSG_LOADING_FAILED));
                view.finish();
            }
        });
    }
}
