package com.scxrh.amb.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.scxrh.amb.R;
import com.scxrh.amb.activity.BaseActivity;
import com.scxrh.amb.widget.FragmentTabHostState;

public class MainFragment extends BaseFragment implements TabHost.OnTabChangeListener
{
    public static final String TAG = MainFragment.class.getSimpleName();
    private static final String TAB_HOME = "TAB_HOME";
    private static final String TAB_LOAN = "TAB_LOAN";
    private static final String TAB_FINANCE = "TAB_FINANCE";
    private static final String TAB_SETTING = "TAB_SETTING";
    private View layoutHome, layoutLoan, layoutFinance, layoutSetting;

    @Override
    protected View genView(LayoutInflater inflater, ViewGroup container)
    {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @SuppressLint("InflateParams")
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        FragmentTabHostState tabhost = (FragmentTabHostState)findViewById(android.R.id.tabhost);
        tabhost.setup(getActivity(), getChildFragmentManager(), R.id.content);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        layoutHome = inflater.inflate(R.layout.layout_tab_item, null);
        layoutLoan = inflater.inflate(R.layout.layout_tab_item, null);
        layoutFinance = inflater.inflate(R.layout.layout_tab_item, null);
        layoutSetting = inflater.inflate(R.layout.layout_tab_item, null);
        ((ImageView)layoutHome.findViewById(R.id.ivIcon)).setImageResource(R.mipmap.icon_6_on);
        ((ImageView)layoutLoan.findViewById(R.id.ivIcon)).setImageResource(R.mipmap.icon_7);
        ((ImageView)layoutFinance.findViewById(R.id.ivIcon)).setImageResource(R.mipmap.icon_8);
        ((ImageView)layoutSetting.findViewById(R.id.ivIcon)).setImageResource(R.mipmap.icon_9);
        TextView tv = (TextView)layoutHome.findViewById(R.id.txtName);
        tv.setText(getString(R.string.txt_recommend));
        ((TextView)layoutLoan.findViewById(R.id.txtName)).setText(getString(R.string.txt_livelihood));
        ((TextView)layoutFinance.findViewById(R.id.txtName)).setText(getString(R.string.txt_finance));
        ((TextView)layoutSetting.findViewById(R.id.txtName)).setText(getString(R.string.txt_mine));
        tabhost.addTab(tabhost.newTabSpec(TAB_HOME).setIndicator(layoutHome), RecommendFragment.class, null);
        tabhost.addTab(tabhost.newTabSpec(TAB_LOAN).setIndicator(layoutLoan), RecommendFragment.class, null);
        tabhost.addTab(tabhost.newTabSpec(TAB_FINANCE).setIndicator(layoutFinance), RecommendFragment.class, null);
        tabhost.addTab(tabhost.newTabSpec(TAB_SETTING).setIndicator(layoutSetting), RecommendFragment.class, null);
        tabhost.setOnTabChangedListener(this);
        setOnClickListener(R.id.img1);
    }

    @Override
    public void onTabChanged(String tabId)
    { }

    @Override
    public void onClick(View v)
    {
        ((BaseActivity)getActivity()).toast("cccccc");
    }
}
