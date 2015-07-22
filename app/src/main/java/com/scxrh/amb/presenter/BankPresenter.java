package com.scxrh.amb.presenter;

import com.scxrh.amb.Const;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.model.SysInfo;
import com.scxrh.amb.rest.RestClient;
import com.scxrh.amb.views.view.BankView;
import com.scxrh.amb.views.view.MvpView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

public class BankPresenter
{
    @Inject
    RestClient rest;
    @Inject
    MessageManager message;
    @Inject
    SysInfo sysInfo;
    private BankView view;

    @Inject
    public BankPresenter(MvpView view)
    {
        this.view = (BankView)view;
    }

    public void loadData()
    {
        view.showProgress(message.getMessage(Const.MSG_LOADING));
        String cityCode = sysInfo.getCity().getId();
        String communityId = sysInfo.getCommunity().getId();
        rest.queryBank(cityCode, communityId).observeOn(AndroidSchedulers.mainThread()).subscribe(list -> {
            view.showData(list);
            view.finish();
        }, throwable -> {
            view.showError(message.getMessage(Const.MSG_LOADING_FAILED));
            view.finish();
        });
    }
}
