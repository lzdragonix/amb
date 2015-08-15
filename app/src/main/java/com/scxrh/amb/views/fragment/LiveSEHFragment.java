package com.scxrh.amb.views.fragment;

import android.content.Intent;

import com.scxrh.amb.Const;
import com.scxrh.amb.R;
import com.scxrh.amb.common.WindowNavigator;
import com.scxrh.amb.views.activity.WindowActivity;

import javax.inject.Inject;

import butterknife.OnClick;

// 生活服务 生活
public class LiveSEHFragment extends BaseFragment
{
    public static final String TAG = LiveSEHFragment.class.getSimpleName();
    @Inject
    WindowNavigator navigator;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_live_seh;
    }

    @OnClick(R.id.life_sddj)
    void life_sddj()
    {
        open("sddj");
    }

    @OnClick(R.id.life_ghkd)
    void life_ghkd()
    {
        open("ghkd");
    }

    @OnClick(R.id.life_tv)
    void life_tv()
    {
        open("tv");
    }

    @OnClick(R.id.life_wyfw)
    void life_wyfw()
    {
        open("wyfw");
    }

    @OnClick(R.id.life_fjyd)
    void life_fjyd()
    {
        open("fjyd");
    }

    @OnClick(R.id.life_hcyd)
    void life_hcyd()
    {
        open("hcyd");
    }

    @OnClick(R.id.life_ylfw)
    void life_ylfw()
    {
        open("ylfw");
    }

    @OnClick(R.id.life_sqjy)
    void life_sqjy()
    {
        open("sqjy");
    }

    @OnClick(R.id.life_jzfw)
    void life_jzfw()
    {
        open("jzfw");
    }

    void open(String type)
    {
        Intent intent = new Intent(getActivity(), WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, WebFragment.class.getName());
        String url = null;
        switch (type)
        {
            case "sddj":
                url = "https://jiaofei.alipay.com/jiaofei.htm?_pdType=aecfbbfgeabbifdfdieh&_scType=bacfajegafddadijhagd";
                break;
            case "ghkd":
                url = "https://jiaofei.alipay.com/guhua.htm?_pdType=bbcjbfefaccjfbjdabhd";
                break;
            case "tv":
                url = "https://jiaofei.alipay.com/fare/ebppChargeEntering.htm?chargeType=catv";
                break;
            case "wyfw":
                url = "https://ebppprod.alipay.com/estate.htm?_pdType=accibafdfbdebceidcae";
                break;
            case "fjyd":
                url = "http://h5.m.taobao.com/trip/flight/search/index.html";
                break;
            case "hcyd":
                url = "http://h5.m.taobao.com/trip/train/search/index.html";
                break;
            case "ylfw":
                url = "http://app.alipay.com/home/appGateway.htm?appId=1000000113";
                break;
            case "sqjy":
                url = "http://app.alipay.com/home/appGateway.htm?appId=1000000118";
                break;
            case "jzfw":
                Intent it = new Intent(getActivity(), WindowActivity.class);
                it.putExtra(Const.KEY_FRAGMENT, HomemakingFragment.class.getName());
                startActivity(it);
                return;
        }
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
