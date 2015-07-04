package com.scxrh.amb.view.fragment;

import com.scxrh.amb.R;

import butterknife.OnClick;

// 登录
public class LoginFragment extends BaseFragment
{
    public static final String TAG = LoginFragment.class.getSimpleName();

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_login;
    }

    @OnClick(R.id.btnLogin)
    void login()
    {
    }
}
