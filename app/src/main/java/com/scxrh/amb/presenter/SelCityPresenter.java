package com.scxrh.amb.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.scxrh.amb.Const;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.manager.SettingsManager;
import com.scxrh.amb.model.City;
import com.scxrh.amb.model.SysInfo;
import com.scxrh.amb.rest.RestClient;
import com.scxrh.amb.views.view.MvpView;
import com.scxrh.amb.views.view.ProgressView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

public class SelCityPresenter
{
    @Inject
    MessageManager message;
    @Inject
    RestClient rest;
    @Inject
    SysInfo sysInfo;
    @Inject
    SettingsManager settings;
    private ProgressView view;
    private List<City> mData = new ArrayList<>();
    private List<String> pys = new ArrayList<>();
    private Comparator<City> mComparator = (lhs, rhs) -> {
        int i = lhs.getHot() - rhs.getHot();
        if (i == 0)
        {
            return lhs.getPinyin().compareToIgnoreCase(rhs.getPinyin());
        }
        else { return -i; }
    };

    @Inject
    public SelCityPresenter(MvpView view)
    {
        this.view = (ProgressView)view;
    }

    public void loadCity()
    {
        view.showProgress(message.getMessage(Const.MSG_LOADING));
        rest.queryCities().observeOn(AndroidSchedulers.mainThread()).subscribe(cities -> {
            if (cities != null)
            {
                mData.clear();
                mData.addAll(prepareData(cities, true));
                view.showData(mData);
                view.finish();
            }
        }, e -> {
            view.showError(message.getMessage(Const.MSG_LOADING_FAILED));
            view.finish();
            e.printStackTrace();
        });
    }

    public void loadCommunity()
    {
        view.showProgress(message.getMessage(Const.MSG_LOADING));
        rest.queryCommunities(sysInfo.getCity().getId())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(cities -> {
                if (cities != null)
                {
                    mData.clear();
                    mData.addAll(prepareData(cities, false));
                    view.showData(mData);
                    view.finish();
                }
            }, e -> {
                view.showError(message.getMessage(Const.MSG_LOADING_FAILED));
                view.finish();
                e.printStackTrace();
            });
    }

    public void filter(String s)
    {
        if (!TextUtils.isEmpty(s))
        {
            List<City> cities = new ArrayList<>();
            for (City city : mData)
            {
                if (cities.contains(city) || "0".equals(city.getId())) { continue; }
                if (city.getName().contains(s) || city.getPinyin().contains(s))
                {
                    cities.add(city);
                }
            }
            view.showData(cities);
        }
        else
        {
            view.showData(mData);
        }
    }

    public void changeCity(City city)
    {
        sysInfo.setCity(city);
        Gson gson = new Gson();
        settings.setValue(Const.KEY_CITY, gson.toJson(city));
    }

    public void changeCommunity(City city)
    {
        sysInfo.setCommunity(city);
        Gson gson = new Gson();
        settings.setValue(Const.KEY_COMMUNITY, gson.toJson(city));
    }

    private List<City> prepareData(List<List<City>> cities, boolean isCity)
    {
        List<City> data = new ArrayList<>();
        pys.clear();
        //normal
        List<City> normal = cities.get(1);
        for (City city : normal)
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
        mData.addAll(normal);
        if (isCity)
        {
            //hot
            List<City> hot = cities.get(0);
            for (City city : hot)
            {
                city.setHot(1);
            }
            mData.addAll(hot);
            City city = new City();
            city.setId("0");
            city.setPinyin("a");
            city.setName("热门城市");
            city.setHot(1);
            mData.add(city);
        }
        Collections.sort(mData, mComparator);
        return data;
    }
}
