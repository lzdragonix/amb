package com.scxrh.amb.presenter;

import com.scxrh.amb.Const;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.rest.RestClient;
import com.scxrh.amb.views.view.MvpView;
import com.scxrh.amb.views.view.ProgressView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

public class FavoritePresenter
{
    @Inject
    RestClient rest;
    @Inject
    MessageManager message;
    private ProgressView view;

    @Inject
    public FavoritePresenter(MvpView view)
    {
        this.view = (ProgressView)view;
    }

    public void loadData()
    {
        view.showProgress(message.getMessage(Const.MSG_LOADING));
        rest.queryFavorite().observeOn(AndroidSchedulers.mainThread()).subscribe(list -> {
            view.showData(list);
            view.finish();
        }, throwable -> {
            view.showMessage(message.getMessage(Const.MSG_LOADING_FAILED));
            view.finish();
        });
    }
}
