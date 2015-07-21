package com.scxrh.amb.presenter;

import android.text.TextUtils;

import com.scxrh.amb.Const;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.rest.RestClient;
import com.scxrh.amb.views.view.ModifyPwdView;
import com.scxrh.amb.views.view.MvpView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

public class ModifyPwdPresenter
{
    @Inject
    RestClient rest;
    @Inject
    MessageManager message;
    private ModifyPwdView view;

    @Inject
    public ModifyPwdPresenter(MvpView view)
    {
        this.view = (ModifyPwdView)view;
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
        view.showProgress(message.getMessage(Const.MSG_SUBMITTING));
        rest.modifyPassword(oldpwd, newpwd).observeOn(AndroidSchedulers.mainThread()).subscribe(response -> {
            String code = response.optString(Const.KEY_CODE);
            String msg = response.optString(Const.KEY_MESSAGE);
            if (Const.RETURNCODE_0000.equals(code))
            {
                view.showError("密码修改成功");
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
                    view.showError("密码修改失败");
                }
                else
                {
                    view.showError(msg);
                }
            }
            view.finish();
        }, throwable -> {
            view.showError("密码修改失败");
            view.finish();
        });
    }
}
