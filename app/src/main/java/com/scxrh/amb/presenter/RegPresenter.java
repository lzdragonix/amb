package com.scxrh.amb.presenter;

import android.text.TextUtils;

import com.scxrh.amb.Const;
import com.scxrh.amb.common.Utils;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.rest.RestClient;
import com.scxrh.amb.views.view.MvpView;
import com.scxrh.amb.views.view.RegView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

public class RegPresenter
{
    private static final String TAG = RegPresenter.class.getSimpleName();
    @Inject
    MessageManager message;
    @Inject
    RestClient rest;
    private RegView view;

    @Inject
    public RegPresenter(MvpView view)
    {
        this.view = (RegView)view;
    }

    public void reg(String user, String pwd, String verify, boolean agree)
    {
        if (!Utils.regex(user, Const.REGEX_MOBILE))
        {
            view.showMessage(message.getMessage(Const.MSG_MOBILE_ILLEGAL));
            view.finish();
            return;
        }
        if (TextUtils.isEmpty(verify))
        {
            view.showMessage(message.getMessage(Const.MSG_INPUT_VERIFY_CODE));
            view.finish();
            return;
        }
        if (pwd == null || pwd.length() < 6)
        {
            view.showMessage(message.getMessage(Const.MSG_PWD_IS_SHORT));
            view.finish();
            return;
        }
        if (!agree)
        {
            view.showMessage(message.getMessage(Const.MSG_AGREE_REG_PROTOCOL));
            view.finish();
            return;
        }
        view.showProgress(message.getMessage(Const.MSG_SUBMITTING));
        rest.register(user, pwd, verify).observeOn(AndroidSchedulers.mainThread()).subscribe(response -> {
            view.regSuccess();
            view.showMessage(message.getMessage(Const.MSG_REG_SUCCESS));
            rest.login(user, pwd).subscribe();
            view.finish();
        }, throwable -> {
            view.showMessage(message.getMessage(Const.MSG_REG_FAILED));
            view.finish();
        });
    }

    public void getSMS(String moblie)
    {
        if (!Utils.regex(moblie, Const.REGEX_MOBILE))
        {
            view.showMessage(message.getMessage(Const.MSG_MOBILE_ILLEGAL));
            view.finish();
            return;
        }
        view.startTimer();
    }
}
