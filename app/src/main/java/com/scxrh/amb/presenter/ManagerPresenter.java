package com.scxrh.amb.presenter;

import android.util.Log;

import com.scxrh.amb.model.SalesManager;
import com.scxrh.amb.rest.RestClient;
import com.scxrh.amb.views.view.MvpView;
import com.scxrh.amb.views.view.ProgressView;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

public class ManagerPresenter
{
    private ProgressView view;
    @Inject
    RestClient rest;

    @Inject
    public ManagerPresenter(MvpView view)
    {
        this.view = (ProgressView)view;
    }

    public void loadData(String communityId)
    {
        List<SalesManager> data;
        rest.queryManagers(communityId).observeOn(AndroidSchedulers.mainThread()).subscribe(list->{
            Log.i("ManagerPresenter", "list size="+list.size());
        });
    }
}
