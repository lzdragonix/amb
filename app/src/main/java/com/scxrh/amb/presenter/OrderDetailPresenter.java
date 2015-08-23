package com.scxrh.amb.presenter;

import android.text.TextUtils;

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

    public void submit(DetailItem item, int typeDeliveries, int typePays, String user, String phone, String addr)
    {
        if (typeDeliveries < 0)
        {
            view.showMessage("请选择配送方式");
            view.finish();
            return;
        }
        if (typePays < 0)
        {
            view.showMessage("请选择付款方式");
            view.finish();
            return;
        }
        if (TextUtils.isEmpty(user))
        {
            view.showMessage("请输入收货人");
            view.finish();
            return;
        }
        if (TextUtils.isEmpty(phone))
        {
            view.showMessage("请输入电话");
            view.finish();
            return;
        }
        if (TextUtils.isEmpty(addr))
        {
            view.showMessage("请输入收货地址");
            view.finish();
            return;
        }
        view.showProgress(message.getMessage(Const.MSG_SUBMITTING));
        JSONArray jsonArray = new JSONArray();
        try
        {
            JSONObject json = new JSONObject();
            json.put("itemId", item.getItemId());
            json.put("amount", item.getAmount());
            jsonArray.put(json);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        UserInfo userInfo = appInfo.getUserInfo();
        String userId = userInfo.getUserId();
        String addItems = jsonArray.toString();
        String communityId = appInfo.getCommunity().getId();
        rest.addOrder(userId, communityId, phone, addr, user, "", addItems)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(list -> {
                view.finish();
                view.showMessage("购买成功");
                view.close();
            }, throwable -> {
                view.showMessage(message.getMessage(Const.MSG_SUBMIT_FAILED));
                view.finish();
            });
    }
}
