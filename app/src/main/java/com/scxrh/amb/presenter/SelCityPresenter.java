package com.scxrh.amb.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.scxrh.amb.Const;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.model.City;
import com.scxrh.amb.net.http.HttpClient;
import com.scxrh.amb.net.http.IHttpResponse;
import com.scxrh.amb.view.iview.SelCityView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

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
    private List<City> mData = new ArrayList<>();
    private List<String> pys = new ArrayList<>();
    private Comparator<City> mComparator = (lhs, rhs) -> lhs.getPinyin().compareToIgnoreCase(rhs.getPinyin());

    @Inject
    public SelCityPresenter() { }

    public void loadData()
    {
        RequestParams params = new RequestParams();
        client.post(activity, Const.URL_QUERY_CITY, params, new IHttpResponse()
        {
            @Override
            public void onHttpStart()
            {
                view.showProgress(messager.getMessage(Const.MSG_LOADING));
            }

            @Override
            public void onHttpSuccess(JSONObject response)
            {
                Gson gson = new Gson();
                List<City> data = gson.fromJson(response.optJSONObject("data").optJSONArray("normal").toString(),
                                                new TypeToken<List<City>>()
                                                { }.getType());
                if (data != null)
                {
                    mData.clear();
                    mData.addAll(data);
                    prepareData();
                    view.showData();
                }
            }

            @Override
            public void onHttpFailure(JSONObject response)
            {
                view.showError(messager.getMessage(Const.MSG_LOADING_FAILED));
            }

            @Override
            public void onHttpFinish()
            {
                City c = new City();
                c.setId("cccccc");
                c.setName("cd");
                c.setPinyin("cd");
                c.setPy("cd");
                mData.add(c);
                view.showData();
                view.finish();
            }
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
