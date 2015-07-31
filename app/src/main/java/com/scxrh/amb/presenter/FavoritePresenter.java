package com.scxrh.amb.presenter;

import android.app.Activity;

import com.scxrh.amb.Const;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.manager.WindowManager;
import com.scxrh.amb.model.FavoriteItem;
import com.scxrh.amb.rest.RestClient;
import com.scxrh.amb.views.view.FavoriteView;
import com.scxrh.amb.views.view.MvpView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

public class FavoritePresenter
{
    @Inject
    RestClient rest;
    @Inject
    MessageManager message;
    @Inject
    WindowManager windowManager;
    @Inject
    Activity activity;
    private FavoriteView view;
    private List<FavoriteItem> mData = new ArrayList<>();
    private FavoriteItem delItem;

    @Inject
    public FavoritePresenter(MvpView view)
    {
        this.view = (FavoriteView)view;
    }

    public void loadData()
    {
        view.showProgress(message.getMessage(Const.MSG_LOADING));
        rest.queryFavorite().observeOn(AndroidSchedulers.mainThread()).subscribe(list -> {
            mData.clear();
            mData.addAll(list);
            view.showData(mData);
            view.finish();
        }, throwable -> {
            if (throwable.getMessage().contains("un-login"))
            {
                view.finish();
                windowManager.startLogin(activity);
                view.close();
            }
            else
            {
                view.showMessage(message.getMessage(Const.MSG_LOADING_FAILED));
                view.finish();
            }
        });
    }

    public void delFav()
    {
        for (FavoriteItem item : mData)
        {
            if ("1".equals(item.getChecked()))
            {
                delItem = item;
                break;
            }
        }
        if (delItem == null) { return; }
        view.showProgress(message.getMessage(Const.MSG_SUBMITTING));
        rest.cancelFavorite(delItem.getFavoriteId()).observeOn(AndroidSchedulers.mainThread()).subscribe(response -> {
            mData.remove(delItem);
            delItem = null;
            view.showMessage(message.getMessage(Const.MSG_SUBMIT_SUCCESS));
            view.showData(mData);
            view.finish();
            view.changeButton("取消");
        }, throwable -> {
            view.showMessage(message.getMessage(Const.MSG_SUBMIT_FAILED));
            view.finish();
        });
    }
}
