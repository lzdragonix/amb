package com.scxrh.amb.presenter;

import com.scxrh.amb.Const;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.model.AppInfo;
import com.scxrh.amb.model.UserInfo;
import com.scxrh.amb.rest.RestClient;
import com.scxrh.amb.views.view.MvpView;
import com.scxrh.amb.views.view.OrderDetailView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

public class OrderDetailPresenter
{
    @Inject
    RestClient rest;
    @Inject
    MessageManager message;
    @Inject
    AppInfo appInfo;
    private OrderDetailView view;

    @Inject
    public OrderDetailPresenter(MvpView view)
    {
        this.view = (OrderDetailView)view;
    }

    public void init()
    {
        UserInfo userInfo = appInfo.getUserInfo();
        if (userInfo != null)
        {
            view.initView(userInfo);
        }
    }

    public void submit()
    {
        view.showProgress(message.getMessage(Const.MSG_SUBMITTING));
        String cityCode = appInfo.getCity().getId();
        String communityId = appInfo.getCommunity().getId();
        rest.queryBank(cityCode, communityId).observeOn(AndroidSchedulers.mainThread()).subscribe(list -> {
            view.showData(list);
            view.finish();
        }, throwable -> {
            view.showMessage(message.getMessage(Const.MSG_SUBMIT_FAILED));
            view.finish();
        });
    }
}
