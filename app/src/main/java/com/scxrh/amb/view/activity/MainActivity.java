package com.scxrh.amb.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.scxrh.amb.Const;
import com.scxrh.amb.R;
import com.scxrh.amb.presenter.LoginPresenter;
import com.scxrh.amb.view.fragment.LoginFragment;

public class MainActivity extends BaseActivity
{
    public static final int REQUESTCODE_REG = 0xf000;
    private LoginPresenter presenter;

    public void setPresenter(LoginPresenter presenter)
    {
        this.presenter = presenter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if (requestCode == REQUESTCODE_REG && resultCode == Activity.RESULT_OK)
        {
            String user = intent.getStringExtra(Const.KEY_ACCOUNT);
            String pwd = intent.getStringExtra(Const.KEY_PASSWORD);
            presenter.inputUser(user);
            presenter.inputPwd(pwd);
            presenter.login(user, pwd);
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        replaceFragment(R.id.container, new LoginFragment(), LoginFragment.TAG);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_main;
    }
}
