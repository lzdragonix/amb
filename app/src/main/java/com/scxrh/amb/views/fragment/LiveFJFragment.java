package com.scxrh.amb.views.fragment;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

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
    LocationManager locationManager;
    private double latitude = 0.0;
    private double longitude = 0.0;

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
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null)
            {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        }
        else
        {
            LocationListener locationListener = new LocationListener()
            {
                //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
                @Override
                public void onLocationChanged(Location location)
                {
                    if (location != null)
                    {
                        Log.e("Map",
                              "Location changed : Lat: " + location.getLatitude() + " Lng: " + location.getLongitude());
                    }
                }

                // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras)
                {
                }

                // Provider被enable时触发此函数，比如GPS被打开
                @Override
                public void onProviderEnabled(String provider)
                {
                }

                // Provider被disable时触发此函数，比如GPS被关闭
                @Override
                public void onProviderDisabled(String provider)
                {
                }
            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null)
            {
                latitude = location.getLatitude(); //经度
                longitude = location.getLongitude(); //纬度
            }
        }
        //
        BaiduMap mBaiduMap = mapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 构造定位数据
        MyLocationData locData = new MyLocationData.Builder().latitude(latitude).longitude(longitude).build();
        // 设置定位数据
        mBaiduMap.setMyLocationData(locData);
        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_location);
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true,
                                                                     mCurrentMarker);
        mBaiduMap.setMyLocationConfigeration(config);
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
}
