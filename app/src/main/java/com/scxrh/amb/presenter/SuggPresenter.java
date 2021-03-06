package com.scxrh.amb.presenter;

import android.text.TextUtils;

import com.scxrh.amb.Const;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.model.AppInfo;
import com.scxrh.amb.model.UserInfo;
import com.scxrh.amb.rest.RestClient;
import com.scxrh.amb.views.view.MvpView;
import com.scxrh.amb.views.view.SuggView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

public class SuggPresenter
{
    @Inject
    RestClient rest;
    @Inject
    MessageManager message;
    @Inject
    AppInfo appInfo;
    private SuggView view;

    @Inject
    public SuggPresenter(MvpView view)
    {
        this.view = (SuggView)view;
    }

    public void init()
    {
        UserInfo userInfo = appInfo.getUserInfo();
        if (userInfo != null)
        {
            view.initView(userInfo);
        }
    }

    public void submit(String name, String contactInfo, String content)
    {
        if (TextUtils.isEmpty(content))
        {
            view.showMessage("请输入投诉建议内容");
            view.finish();
            return;
        }
        if (TextUtils.isEmpty(name))
        {
            view.showMessage("请输入您的姓名");
            view.finish();
            return;
        }
        if (TextUtils.isEmpty(contactInfo))
        {
            view.showMessage("请输入联系方式");
            view.finish();
            return;
        }
        view.showProgress(message.getMessage(Const.MSG_SUBMITTING));
        rest.submitSuggest(name, contactInfo, content).observeOn(AndroidSchedulers.mainThread()).subscribe(response -> {
            String code = response.optString(Const.KEY_CODE);
            String msg = response.optString(Const.KEY_MESSAGE);
            if (Const.RETURNCODE_0000.equals(code))
            {
                view.showMessage(message.getMessage(Const.MSG_SUBMIT_SUCCESS));
                view.close();
            }
            else if (Const.RETURNCODE_0001.equals(code))
            {
                view.showLogin();
                view.close();
            }
            else
            {
                if (TextUtils.isEmpty(msg))
                {
                    view.showMessage(message.getMessage(Const.MSG_SUBMIT_FAILED));
                }
                else
                {
                    view.showMessage(msg);
                }
            }
            view.finish();
        }, throwable -> {
            view.showMessage(message.getMessage(Const.MSG_SUBMIT_FAILED));
            view.finish();
        });
    }
}
