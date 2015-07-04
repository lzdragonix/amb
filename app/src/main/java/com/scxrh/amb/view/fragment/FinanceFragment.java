package com.scxrh.amb.view.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.SDKInitializer;
import com.scxrh.amb.R;

// 生活服务
public class FinanceFragment extends BaseFragment
{
    public static final String TAG = FinanceFragment.class.getSimpleName();

    @Override
    protected View genView(LayoutInflater inflater, ViewGroup container)
    {
        SDKInitializer.initialize(getActivity().getApplicationContext());
        return inflater.inflate(R.layout.fragment_finance, container, false);
    }
}
