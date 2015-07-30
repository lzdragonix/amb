package com.scxrh.amb.presenter;

import com.scxrh.amb.Const;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.model.AppInfo;
import com.scxrh.amb.model.DetailItem;
import com.scxrh.amb.model.UserInfo;
import com.scxrh.amb.rest.RestClient;
import com.scxrh.amb.views.view.MvpView;
import com.scxrh.amb.views.view.OrderDetailView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

public class OrderDetailPresenter
{
    @Inject
    RestClient rest;
    @Inject
    MessageManager message;
    @Inject
    AppInfo appInfo;
    private OrderDetailView view;

    @Inject
    public OrderDetailPresenter(MvpView view)
    {
        this.view = (OrderDetailView)view;
    }

    public void init()
    {
        UserInfo userInfo = appInfo.getUserInfo();
        if (userInfo != null)
        {
            view.initView(userInfo);
        }
    }

    public void submit(DetailItem item)
    {
        view.showProgress(message.getMessage(Const.MSG_SUBMITTING));
        JSONObject jsonObject = new JSONObject();
        try
        {
            JSONObject json = new JSONObject();
            json.put("itemId", item.getItemId());
            json.put("amount", 1);
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(json);
            jsonObject.put("addItems", jsonArray);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        UserInfo userInfo = appInfo.getUserInfo();
        String userId = userInfo.getUserId();
        String telephone = userInfo.getTelephone();
        String address = userInfo.getAddress();
        String receiverName = userInfo.getUserName();
        String addItems = jsonObject.toString();
        String communityId = appInfo.getCommunity().getId();
        rest.addOrder(userId, communityId, telephone, address, receiverName, "", addItems)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(list -> {
                view.finish();
            }, throwable -> {
                view.showMessage(message.getMessage(Const.MSG_SUBMIT_FAILED));
                view.finish();
            });
    }
}
