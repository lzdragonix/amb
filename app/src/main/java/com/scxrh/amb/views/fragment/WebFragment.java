package com.scxrh.amb.views.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.scxrh.amb.R;

import butterknife.Bind;

//
public class WebFragment extends BaseFragment
{
    public static final String TAG = WebFragment.class.getSimpleName();
    @Bind(R.id.webView)
    WebView webView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        String url = getArguments().getString("url");
        WebSettings ws = webView.getSettings();
        ws.setSupportZoom(false);
        ws.setBuiltInZoomControls(false);
        ws.setJavaScriptEnabled(true);
        ws.setLoadWithOverviewMode(true);
        ws.setBlockNetworkImage(true);
        webView.setHorizontalScrollbarOverlay(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollbarOverlay(false);
        webView.setVerticalScrollBarEnabled(false);
        if (TextUtils.isEmpty(url)) { return; }
        webView.loadUrl(url);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_web;
    }
}
