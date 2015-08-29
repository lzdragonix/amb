package com.scxrh.amb.views.fragment;

import android.content.Intent;
import android.text.TextUtils;

import com.scxrh.amb.Const;
import com.scxrh.amb.R;
import com.scxrh.amb.manager.SettingsManager;
import com.scxrh.amb.views.activity.WindowActivity;

import butterknife.OnClick;

// 生活服务 生活
public class LiveSEHFragment extends BaseFragment
{
    public static final String TAG = LiveSEHFragment.class.getSimpleName();

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

    @OnClick(R.id.life_sjcz)
    void life_sjcz()
    {
        open("sjcz");
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
            case "sjcz":
                SettingsManager settings = new SettingsManager(getActivity());
                String mobile = settings.getString(Const.KEY_ACCOUNT);
                if (!TextUtils.isEmpty(mobile) && isUnicom(mobile))
                {
                    url = "http://upay.10010.com/npfwap/npfMobWap/bankcharge/index.html";
                }
                else
                {
                    url = "http://shop.10086.cn/i/mobile/rechargecredit.html";
                }
                break;
        }
        intent.putExtra("url", url);
        startActivity(intent);
    }

    private boolean isUnicom(String mobile)
    {
        return mobile.startsWith("130") || mobile.startsWith("131") || mobile.startsWith("132") ||
               mobile.startsWith("155") || mobile.startsWith("156") || mobile.startsWith("186");
    }
}
