package com.scxrh.amb.presenter;

import android.app.Activity;

import com.loopj.android.http.RequestParams;
import com.scxrh.amb.Const;
import com.scxrh.amb.common.Utils;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.net.http.HttpClient;
import com.scxrh.amb.net.http.IHttpResponse;
import com.scxrh.amb.view.iview.SelCityView;

import org.json.JSONObject;

import java.util.ArrayList;
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
    private List<JSONObject> mData = new ArrayList<>();

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
                mData.clear();
                mData.addAll(Utils.toList(response.optJSONObject("data").optJSONArray("normal")));
                view.showData();
            }

            @Override
            public void onHttpFailure(JSONObject response)
            {
                view.showError(messager.getMessage(Const.MSG_LOADING_FAILED));
            }

            @Override
            public void onHttpFinish()
            {
                view.finish();
            }
        });
    }

    public int getCount()
    {
        return mData.size();
    }

    public JSONObject getItem(int index)
    {
        return mData.get(index);
    }
}
