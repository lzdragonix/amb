package com.scxrh.amb.views.fragment;

import android.os.Bundle;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.scxrh.amb.R;

import butterknife.Bind;

// 生活 附近
public class LiveFJFragment extends BaseFragment
{
    public static final String TAG = LiveFJFragment.class.getSimpleName();
    @Bind(R.id.mapView)
    MapView mapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;

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

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        //
        mLocationClient = new LocationClient(getActivity());
        mLocationClient.registerLocationListener(new BDLocationListener()
        {
            @Override
            public void onReceiveLocation(BDLocation location)
            {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Log.i("LiveFJFragment", "latitude=" + latitude + " longitude=" + longitude);
                // 构造定位数据
                MyLocationData locData = new MyLocationData.Builder().latitude(latitude).longitude(longitude).build();
                // 设置定位数据
                mBaiduMap.setMyLocationData(locData);
            }
        });
        mBaiduMap = mapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_location);
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING,
                                                                     true, mCurrentMarker);
        mBaiduMap.setMyLocationConfigeration(config);
        initLocation();
        mLocationClient.start();
        //        //
        //        //定义Maker坐标点
        //        LatLng point = new LatLng(latitude, longitude);
        //        //构建Marker图标
        //        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_location);
        //        //构建MarkerOption，用于在地图上添加Marker
        //        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
        //        //在地图上添加Marker，并显示
        //        mBaiduMap.addOverlay(option);
    }

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

    private void initLocation()
    {
        LocationClientOption option = new LocationClientOption();
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认gcj02，设置返回的定位结果坐标系，
        option.setCoorType("gcj02");
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setScanSpan(2000);
        //可选，设置是否需要地址信息，默认不需要
        //option.setIsNeedAddress(checkGeoLocation.isChecked());
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setLocationNotify(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(true);
        //可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(true);
        mLocationClient.setLocOption(option);
    }
}
