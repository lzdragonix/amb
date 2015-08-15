package com.scxrh.amb.views.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.scxrh.amb.R;
import com.scxrh.amb.common.Logger;

import butterknife.Bind;
import butterknife.OnClick;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

// 生活 附近
public class LiveFJFragment extends BaseFragment
{
    public static final String TAG = LiveFJFragment.class.getSimpleName();
    @Bind(R.id.map_container)
    FrameLayout container;
    private MapView mapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private PoiNearbySearchOption searchOption = new PoiNearbySearchOption();
    private PoiSearch mPoiSearch = PoiSearch.newInstance();
    private OnGetPoiSearchResultListener onGetPoiSearchResultListener = new OnGetPoiSearchResultListener()
    {
        @Override
        public void onGetPoiResult(PoiResult poiResult)
        {
            if (poiResult.getAllPoi() == null)
            {
                Logger.i(TAG, "poi is null.");
                return;
            }
            mBaiduMap.clear();
            PoiOverlay overlay = new PoiOverlay(mBaiduMap);
            overlay.setData(poiResult);
            overlay.addToMap();
            overlay.zoomToSpan();
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult)
        { }
    };
    private BDLocationListener locationListener = new BDLocationListener()
    {
        @Override
        public void onReceiveLocation(BDLocation location)
        {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Logger.i(TAG, "latitude=" + latitude + " longitude=" + longitude);
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder().latitude(latitude).longitude(longitude).build();
            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);
            //
            searchOption.location(new LatLng(latitude, longitude)).pageNum(10).radius(5000);
        }
    };

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
        BaiduMapOptions options = new BaiduMapOptions();
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(17f);
        options.mapStatus(builder.build());
        mapView = new MapView(getActivity(), options);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        container.addView(mapView, params);
        mLocationClient = new LocationClient(getActivity());
        mBaiduMap = mapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.mipmap.location_self);
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING,
                                                                     true, mCurrentMarker);
        mBaiduMap.setMyLocationConfigeration(config);
        //
        mPoiSearch.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener);
        initLocation();
        mLocationClient.start();
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

    @OnClick({ R.id.sj, R.id.cy, R.id.yh })
    void nearBy(View v)
    {
        String keyword = "";
        switch (v.getId())
        {
            case R.id.cy:
                keyword = "餐饮";
                break;
            case R.id.yh:
                keyword = "银行";
                break;
            default:
                keyword = "商家";
                break;
        }
        mPoiSearch.searchNearby(searchOption.keyword(keyword));
    }

    private void initLocation()
    {
        LocationClientOption option = new LocationClientOption();
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认gcj02，设置返回的定位结果坐标系，
        option.setCoorType("bd09ll");
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setScanSpan(0);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(false);
        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);
        //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setLocationNotify(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(true);
        //可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(true);
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(locationListener);
    }
}
