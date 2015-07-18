package com.scxrh.amb.views.fragment;

import com.baidu.mapapi.SDKInitializer;
import com.scxrh.amb.R;

// 生活 附近
public class LiveFJFragment extends BaseFragment
{
    public static final String TAG = LiveFJFragment.class.getSimpleName();

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_live_fj;
    }

    @Override
    protected void beforeContentView()
    {
        SDKInitializer.initialize(getActivity().getApplicationContext());
    }
}
