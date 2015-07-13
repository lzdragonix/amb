package com.scxrh.amb.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.scxrh.amb.Const;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.model.City;
import com.scxrh.amb.net.http.HttpClient;
import com.scxrh.amb.rest.RestRepository;
import com.scxrh.amb.views.view.SelCityView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

public class SelCityPresenter
{
    @Inject
    SelCityView view;
    @Inject
    HttpClient client;
    @Inject
    Activity activity;
    @Inject
    MessageManager messager;
    @Inject
    RestRepository rest;
    private List<City> mData = new ArrayList<>();
    private List<String> pys = new ArrayList<>();
    private Comparator<City> mComparator = (lhs, rhs) -> lhs.getPinyin().compareToIgnoreCase(rhs.getPinyin());

    @Inject
    public SelCityPresenter() { }

    public void loadData()
    {
        view.showProgress(messager.getMessage(Const.MSG_LOADING));
        rest.getCities().observeOn(AndroidSchedulers.mainThread()).subscribe(cities -> {
            if (cities != null)
            {
                mData.clear();
                mData.addAll(cities);
                prepareData();
                view.showData();
                view.finish();
            }
        }, e -> {
            view.showError(messager.getMessage(Const.MSG_LOADING_FAILED));
            view.finish();
            e.printStackTrace();
        });
    }

    public int getCount()
    {
        return mData.size();
    }

    public City getItem(int index)
    {
        return mData.get(index);
    }

    private void prepareData()
    {
        pys.clear();
        for (City city : mData)
        {
            if (!TextUtils.isEmpty(city.getPinyin()))
            {
                String py = city.getPinyin().substring(0, 1).toUpperCase();
                if (!pys.contains(py)) { pys.add(py); }
                city.setPy(py);
            }
        }
        for (String py : pys)
        {
            City city = new City();
            city.setId("0");
            city.setPinyin(py);
            city.setName(py);
            mData.add(city);
        }
        Collections.sort(mData, mComparator);
    }
}
