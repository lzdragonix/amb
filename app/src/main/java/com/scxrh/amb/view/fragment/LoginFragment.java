package com.scxrh.amb.view.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.SDKInitializer;
import com.scxrh.amb.R;

// 登录
public class LoginFragment extends BaseFragment
{
    public static final String TAG = LoginFragment.class.getSimpleName();

    @Override
    protected View genView(LayoutInflater inflater, ViewGroup container)
    {
        SDKInitializer.initialize(getActivity().getApplicationContext());
        return inflater.inflate(R.layout.fragment_login, container, false);
    }
}
