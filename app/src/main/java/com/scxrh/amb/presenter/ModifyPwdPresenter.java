package com.scxrh.amb.presenter;

import android.text.TextUtils;

import com.scxrh.amb.Const;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.rest.RestClient;
import com.scxrh.amb.views.view.MvpView;
import com.scxrh.amb.views.view.ProgressView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

public class ModifyPwdPresenter
{
    @Inject
    RestClient rest;
    @Inject
    MessageManager message;
    private ProgressView view;

    @Inject
    public ModifyPwdPresenter(MvpView view)
    {
        this.view = (ProgressView)view;
    }

    public void submit(String oldpwd, String newpwd, String newpwd1)
    {
        if (TextUtils.isEmpty(oldpwd) || TextUtils.isEmpty(newpwd) || TextUtils.isEmpty(newpwd1))
        {
            view.showError(message.getMessage(Const.MSG_EMPTY_USER_OR_PASSWORD));
            view.finish();
            return;
        }
        if (!newpwd.equals(newpwd1))
        {
            view.showError(message.getMessage(Const.MSG_PWD_INCONFORMITY));
            view.finish();
            return;
        }
        rest.modifyPassword(oldpwd, newpwd).observeOn(AndroidSchedulers.mainThread()).subscribe(response -> {
            view.showError("密码修改成功");
            view.finish();
        }, throwable -> {
            view.showError("密码修改失败");
            view.finish();
        });
    }
}
