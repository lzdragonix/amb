package com.scxrh.amb.presenter;

import android.text.TextUtils;

import com.scxrh.amb.Const;
import com.scxrh.amb.common.Utils;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.manager.SettingsManager;
import com.scxrh.amb.rest.RestClient;
import com.scxrh.amb.views.view.MvpView;
import com.scxrh.amb.views.view.RetView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

public class LivePresenter
{
    private static final String TAG = LivePresenter.class.getSimpleName();
    RetView view;
    @Inject
    MessageManager message;
    @Inject
    RestClient rest;
    @Inject
    SettingsManager settings;

    @Inject
    public LivePresenter(MvpView view)
    {
        this.view = (RetView)view;
    }

    public void initUser()
    {
        String user = settings.getString(Const.KEY_ACCOUNT);
        if (TextUtils.isEmpty(user)) { return; }
        view.initUser(user);
    }

    public void retrieve(String user, String pwd, String pwd1, String verify, boolean agree)
    {
        if (!Utils.regex(user, Const.REGEX_MOBILE))
        {
            view.showError(message.getMessage(Const.MSG_MOBILE_ILLEGAL));
            view.finish();
            return;
        }
        if (TextUtils.isEmpty(verify))
        {
            view.showError(message.getMessage(Const.MSG_INPUT_VERIFY_CODE));
            view.finish();
            return;
        }
        if (pwd == null || pwd.length() < 6)
        {
            view.showError(message.getMessage(Const.MSG_PWD_IS_SHORT));
            view.finish();
            return;
        }
        if (!pwd.equals(pwd1))
        {
            view.showError(message.getMessage(Const.MSG_PWD_INCONFORMITY));
            view.finish();
            return;
        }
        if (!agree)
        {
            view.showError(message.getMessage(Const.MSG_AGREE_REG_PROTOCOL));
            view.finish();
            return;
        }
        view.showProgress(message.getMessage(Const.MSG_SUBMITTING));
        rest.restorePwd(user, pwd, "111111").observeOn(AndroidSchedulers.mainThread()).subscribe(response -> {
            view.finish();
            view.success();
        }, throwable -> {
            view.showError(message.getMessage(Const.MSG_SERVER_ERROR));
        });
    }
}
