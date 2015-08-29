package com.scxrh.amb.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.scxrh.amb.Const;
import com.scxrh.amb.common.WindowNavigator;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.manager.SettingsManager;
import com.scxrh.amb.model.AppInfo;
import com.scxrh.amb.model.DetailItem;
import com.scxrh.amb.model.UIData;
import com.scxrh.amb.rest.RestClient;
import com.scxrh.amb.views.fragment.OrderDetailFragment;
import com.scxrh.amb.views.view.DetailView;
import com.scxrh.amb.views.view.MvpView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

public class DetailPresenter
{
    @Inject
    RestClient rest;
    @Inject
    MessageManager message;
    @Inject
    SettingsManager settings;
    @Inject
    AppInfo appInfo;
    @Inject
    WindowNavigator navigator;
    @Inject
    Activity activity;
    private DetailView view;

    @Inject
    public DetailPresenter(MvpView view)
    {
        this.view = (DetailView)view;
    }

    public void loadData(UIData.Item item)
    {
        switch (item.getContentType())
        {
            case "ad":
            case "ad2":
                queryAd(item.getItemId());
                break;
            case "product":
                queryProduct(item.getItemId());
                break;
            case "finance":
                queryFinance(item.getItemId());
                break;
            case "lottery":
                view.showData(appInfo.getUserInfo().getUserId());
                break;
            case "shop":
                queryShop(item.getItemId());
                break;
        }
    }

    private void queryShop(String itemId)
    {
        view.showProgress(message.getMessage(Const.MSG_LOADING));
        rest.queryShop(itemId).observeOn(AndroidSchedulers.mainThread()).subscribe(detailItem -> {
            view.showData(detailItem);
            view.finish();
        }, throwable -> {
            view.showMessage(message.getMessage(Const.MSG_LOADING_FAILED));
            view.finish();
        });
    }

    public void favorite(String itemId, String contentType)
    {
        if (!appInfo.isLogin())
        {
            navigator.startLogin(activity);
            return;
        }
        rest.addFavorite(itemId, contentType).observeOn(AndroidSchedulers.mainThread()).subscribe(response -> {
            String fav = settings.getString(Const.KEY_FAV);
            settings.setValue(Const.KEY_FAV, fav + "," + itemId + "-" + contentType);
            view.hideFavButton();
            view.showMessage("收藏成功");
        }, throwable -> {
            view.showMessage("收藏失败");
        });
    }

    public boolean hasFav(UIData.Item item)
    {
        String fav = settings.getString(Const.KEY_FAV);
        return !TextUtils.isEmpty(fav) && fav.contains(item.getItemId() + "-" + item.getContentType());
    }

    private void queryAd(String itemId)
    {
        view.showProgress(message.getMessage(Const.MSG_LOADING));
        rest.queryAd(itemId).observeOn(AndroidSchedulers.mainThread()).subscribe(detailItem -> {
            view.showData(detailItem);
            view.finish();
        }, throwable -> {
            view.showMessage(message.getMessage(Const.MSG_LOADING_FAILED));
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
            view.showMessage(message.getMessage(Const.MSG_LOADING_FAILED));
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
            view.showMessage(message.getMessage(Const.MSG_LOADING_FAILED));
            view.finish();
        });
    }

    public void buy(DetailItem di)
    {
        if (!appInfo.isLogin())
        {
            navigator.startLogin(activity);
            return;
        }
        navigator.startWindow(activity, OrderDetailFragment.class.getName(), di);
    }
}
