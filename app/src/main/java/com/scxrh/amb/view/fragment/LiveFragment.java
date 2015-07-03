package com.scxrh.amb.view.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scxrh.amb.R;

// 生活服务
public class LiveFragment extends BaseFragment
{
    public static final String TAG = LiveFragment.class.getSimpleName();

    @Override
    protected View genView(LayoutInflater inflater, ViewGroup container)
    {
        return inflater.inflate(R.layout.fragment_live, container, false);
    }
}
