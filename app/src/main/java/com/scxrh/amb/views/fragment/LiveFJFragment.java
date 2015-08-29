package com.scxrh.amb.views.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
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
    @Bind(R.id.txt_sj)
    TextView txt_sj;
    @Bind(R.id.txt_cy)
    TextView txt_cy;
    @Bind(R.id.txt_yh)
    TextView txt_yh;
    private MapView mapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private PoiNearbySearchOption searchOption = new PoiNearbySearchOption();
    private PoiSearch mPoiSearch = PoiSearch.newInstance();
    private PoiResult mPoiResult;
    private OnGetPoiSearchResultListener onGetPoiSearchResultListener = new OnGetPoiSearchResultListener()
    {
        @Override
        public void onGetPoiResult(PoiResult poiResult)
        {
            mPoiResult = poiResult;
            if (mPoiResult.getAllPoi() == null)
            {
                Logger.i(TAG, "poi is null.");
                return;
            }
            mBaiduMap.clear();
            MyPoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
            overlay.setData(mPoiResult);
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
    private BaiduMap.OnMarkerClickListener mOnMarkerClickListener = new BaiduMap.OnMarkerClickListener()
    {
        @Override
        public boolean onMarkerClick(Marker marker)
        {
            //创建InfoWindow展示的view
            TextView text = new TextView(getActivity());
            text.setBackgroundResource(R.mipmap.input_m);
            text.setGravity(Gravity.CENTER);
            text.setPadding(30, 0, 30, 0);
            int index = marker.getExtraInfo().getInt("index", -1);
            if (index >= 0 && mPoiResult.getAllPoi() != null && mPoiResult.getAllPoi().size() > 0)
            {
                text.setText(mPoiResult.getAllPoi().get(index).name);
            }
            //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
            InfoWindow infoWindow = new InfoWindow(text, marker.getPosition(), -47);
            //显示InfoWindow
            mBaiduMap.showInfoWindow(infoWindow);
            return true;
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
        mBaiduMap.setOnMarkerClickListener(mOnMarkerClickListener);
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener()
        {
            @Override
            public void onMapClick(LatLng latLng)
            {
                mBaiduMap.hideInfoWindow();
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi)
            {
                return false;
            }
        });
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
        String keyword;
        reset();
        switch (v.getId())
        {
            case R.id.cy:
                keyword = "餐饮";
                txt_cy.setTextColor(getResources().getColor(R.color.cfbc000));
                break;
            case R.id.yh:
                keyword = "银行";
                txt_yh.setTextColor(getResources().getColor(R.color.cfbc000));
                break;
            default:
                keyword = "商铺";
                txt_sj.setTextColor(getResources().getColor(R.color.cfbc000));
                break;
        }
        mPoiSearch.searchNearby(searchOption.keyword(keyword));
    }

    private void reset()
    {
        txt_sj.setTextColor(getResources().getColor(R.color.c929292));
        txt_cy.setTextColor(getResources().getColor(R.color.c929292));
        txt_yh.setTextColor(getResources().getColor(R.color.c929292));
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

    class MyPoiOverlay extends PoiOverlay
    {
        public MyPoiOverlay(BaiduMap baiduMap)
        {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index)
        {
            super.onPoiClick(index);
            return true;
        }
    }
}
