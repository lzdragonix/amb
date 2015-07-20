package com.scxrh.amb.views.fragment;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.scxrh.amb.R;

import butterknife.Bind;

// 生活 附近
public class LiveFJFragment extends BaseFragment
{
    public static final String TAG = LiveFJFragment.class.getSimpleName();
    @Bind(R.id.mapView)
    MapView mapView;

    @Override
    public void onResume()
    {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        mapView.onDestroy();
    }

    @Override
    protected void beforeContentView()
    {
        SDKInitializer.initialize(getActivity().getApplicationContext());
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_live_fj;
    }
}
