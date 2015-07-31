package com.scxrh.amb.presenter;

import com.scxrh.amb.Const;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.model.AppInfo;
import com.scxrh.amb.model.Points;
import com.scxrh.amb.rest.RestClient;
import com.scxrh.amb.views.view.MvpView;
import com.scxrh.amb.views.view.ProgressView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

public class PointsPresenter
{
    @Inject
    RestClient rest;
    @Inject
    MessageManager message;
    @Inject
    AppInfo appInfo;
    private ProgressView view;
    private List<Points> mData = new ArrayList<>();

    @Inject
    public PointsPresenter(MvpView view)
    {
        this.view = (ProgressView)view;
    }

    public void loadData()
    {
        view.showProgress(message.getMessage(Const.MSG_LOADING));
        String userId = appInfo.getUserInfo().getUserId();
        rest.queryPoint(userId).observeOn(AndroidSchedulers.mainThread()).subscribe(list -> {
            mData.clear();
            mData.addAll(list);
            view.showData(list);
            view.finish();
        }, throwable -> {
            view.showMessage(message.getMessage(Const.MSG_LOADING_FAILED));
            view.finish();
        });
    }

    public int sumPoint()
    {
        int result = 0;
        for (Points points : mData)
        {
            result += Integer.valueOf(points.getPoint());
        }
        return result;
    }
}
