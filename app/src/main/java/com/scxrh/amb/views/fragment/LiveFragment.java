package com.scxrh.amb.views.fragment;

import com.baidu.mapapi.SDKInitializer;
import com.scxrh.amb.R;

// 生活服务
public class LiveFragment extends BaseFragment
{
    public static final String TAG = LiveFragment.class.getSimpleName();

    @Override
    protected void beforeContentView()
    {
        SDKInitializer.initialize(getActivity().getApplicationContext());
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_live;
    }
}
