package com.scxrh.amb.presenter;

import android.text.TextUtils;

import com.scxrh.amb.Const;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.model.AppInfo;
import com.scxrh.amb.model.City;
import com.scxrh.amb.rest.RestClient;
import com.scxrh.amb.views.view.FinanceView;
import com.scxrh.amb.views.view.MvpView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

public class FinancePresenter
{
    @Inject
    RestClient rest;
    @Inject
    MessageManager message;
    @Inject
    AppInfo appInfo;
    private FinanceView view;

    @Inject
    public FinancePresenter(MvpView view)
    {
        this.view = (FinanceView)view;
    }

    public void init()
    {
        appInfo.observable("community", this, City.class).subscribe(city -> {
            if (TextUtils.isEmpty(city.getId())) { return; }
            loadBank();
            loadManager();
            loadProduct();
        });
    }

    public void loadBank()
    {
        view.showProgress(message.getMessage(Const.MSG_LOADING));
        String cityCode = appInfo.getCity().getId();
        String communityId = appInfo.getCommunity().getId();
        rest.queryBank(cityCode, communityId).observeOn(AndroidSchedulers.mainThread()).subscribe(list -> {
            view.showData(list);
            view.finish();
        }, throwable -> {
            view.showMessage(message.getMessage(Const.MSG_LOADING_FAILED));
            view.finish();
        });
    }

    public void loadManager()
    {
        view.showProgress(message.getMessage(Const.MSG_LOADING));
        String communityId = appInfo.getCommunity().getId();
        rest.queryManagers(communityId).observeOn(AndroidSchedulers.mainThread()).subscribe(list -> {
            view.showManager(list);
            view.finish();
        }, throwable -> {
            view.showMessage(message.getMessage(Const.MSG_LOADING_FAILED));
            view.finish();
        });
    }

    public void loadProduct()
    {
        view.showProgress(message.getMessage(Const.MSG_LOADING));
        String communityId = appInfo.getCommunity().getId();
        rest.queryFinancialProduct(communityId, "", "").observeOn(AndroidSchedulers.mainThread()).subscribe(list -> {
            view.showProduct(list);
            view.finish();
        }, throwable -> {
            view.showMessage(message.getMessage(Const.MSG_LOADING_FAILED));
            view.finish();
        });
    }
}
