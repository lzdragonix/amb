package com.scxrh.amb.presenter;

import com.scxrh.amb.Const;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.model.UIData;
import com.scxrh.amb.rest.RestClient;
import com.scxrh.amb.views.view.MvpView;
import com.scxrh.amb.views.view.ProgressView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

public class DetailPresenter
{
    @Inject
    RestClient rest;
    @Inject
    MessageManager message;
    private ProgressView view;

    @Inject
    public DetailPresenter(MvpView view)
    {
        this.view = (ProgressView)view;
    }

    public void loadData(UIData.Item item)
    {
        switch (item.getContentType())
        {
            case "ad":
                queryAd(item.getItemId());
                break;
            case "product":
                queryProduct(item.getItemId());
                break;
            case "finance":
                queryFinance(item.getItemId());
                break;
        }
    }

    private void queryAd(String itemId)
    {
        view.showProgress(message.getMessage(Const.MSG_LOADING));
        rest.queryAd(itemId).observeOn(AndroidSchedulers.mainThread()).subscribe(detailItem -> {
            view.showData(detailItem);
            view.finish();
        }, throwable -> {
            view.showError(message.getMessage(Const.MSG_LOADING_FAILED));
            view.finish();
        });
    }

    private void queryProduct(String itemId)
    {
        view.showProgress(message.getMessage(Const.MSG_LOADING));
        rest.queryProduct(itemId).observeOn(AndroidSchedulers.mainThread()).subscribe(detailItem -> {
            view.showData(detailItem);
            view.finish();
        }, throwable -> {
            view.showError(message.getMessage(Const.MSG_LOADING_FAILED));
            view.finish();
        });
    }

    private void queryFinance(String itemId)
    {
        view.showProgress(message.getMessage(Const.MSG_LOADING));
        rest.queryFinancialProduct("", "", itemId).observeOn(AndroidSchedulers.mainThread()).subscribe(detailItem -> {
            view.showData(detailItem);
            view.finish();
        }, throwable -> {
            view.showError(message.getMessage(Const.MSG_LOADING_FAILED));
            view.finish();
        });
    }
}
