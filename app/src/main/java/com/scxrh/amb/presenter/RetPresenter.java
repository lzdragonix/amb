package com.scxrh.amb.presenter;

import android.text.TextUtils;

import com.scxrh.amb.Const;
import com.scxrh.amb.common.Utils;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.rest.RestRepository;
import com.scxrh.amb.views.view.RetView;

import javax.inject.Inject;

public class RetPresenter
{
    private static final String TAG = RetPresenter.class.getSimpleName();
    @Inject
    MessageManager message;
    @Inject
    RetView view;
    @Inject
    RestRepository rest;

    @Inject
    public RetPresenter() { }

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
        if (!agree)
        {
            view.showError(message.getMessage(Const.MSG_AGREE_REG_PROTOCOL));
            view.finish();
            return;
        }
    }
}
