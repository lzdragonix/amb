package com.scxrh.amb.views.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.scxrh.amb.App;
import com.scxrh.amb.Const;
import com.scxrh.amb.R;
import com.scxrh.amb.injector.component.DaggerMvpComponent;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.model.City;
import com.scxrh.amb.presenter.MainPresenter;
import com.scxrh.amb.views.activity.WindowActivity;
import com.scxrh.amb.views.view.MainView;
import com.scxrh.amb.widget.FragmentTabHostState;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainFragment extends BaseFragment implements MainView, TabHost.OnTabChangeListener
{
    public static final String TAG = MainFragment.class.getSimpleName();
    public static final String TAB_RECOMM = "TAB_RECOMM";
    public static final String TAB_LIVE = "TAB_LIVE";
    public static final String TAB_FINANCE = "TAB_FINANCE";
    public static final String TAB_MINE = "TAB_MINE";
    @Inject
    MainPresenter presenter;
    @Bind(android.R.id.tabhost)
    FragmentTabHostState tabhost;
    @Bind(R.id.txtHeader)
    TextView txtHeader;
    @Bind(R.id.txtCommunity)
    TextView txtCommunity;
    @Bind(R.id.txtCity)
    TextView txtCity;
    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.shezhi)
    ImageView shezhi;
    private Map<String, TabHolder> tabs = new HashMap<>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initTab();
        presenter.initView();
        tabhost.setOnTabChangedListener(this);
        changeTabState(TAB_RECOMM);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        presenter.onDestroyView();
    }

    @Override
    public void onTabChanged(String tabId)
    {
        changeTabState(tabId);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_main;
    }

    @Override
    protected void injectDependencies()
    {
        DaggerMvpComponent.builder()
                          .appComponent(App.getAppComponent())
                          .activityModule(new ActivityModule(getActivity()))
                          .mvpModule(new MvpModule(this))
                          .build()
                          .inject(this);
    }

    private void initTab()
    {
        tabhost.setup(getActivity(), getChildFragmentManager(), R.id.content);
        // recommend
        TabHolder tab = new TabHolder().init(R.mipmap.icon_6, R.mipmap.icon_6_on, R.color.cffffff, R.color.cfbc000,
                                             R.string.txt_recommend);
        tabs.put(TAB_RECOMM, tab);
        // livelihood
        tab = new TabHolder().init(R.mipmap.icon_7, R.mipmap.icon_7_on, R.color.cffffff, R.color.cfbc000,
                                   R.string.txt_livelihood);
        tabs.put(TAB_LIVE, tab);
        // finance
        tab = new TabHolder().init(R.mipmap.icon_8, R.mipmap.icon_8_on, R.color.cffffff, R.color.cfbc000,
                                   R.string.txt_finance);
        tabs.put(TAB_FINANCE, tab);
        // mine
        tab = new TabHolder().init(R.mipmap.icon_9, R.mipmap.icon_9_on, R.color.cffffff, R.color.cfbc000,
                                   R.string.txt_mine);
        tabs.put(TAB_MINE, tab);
        // add tab
        tabhost.addTab(tabhost.newTabSpec(TAB_RECOMM).setIndicator(tabs.get(TAB_RECOMM).root), RecommendFragment.class,
                       null);
        tabhost.addTab(tabhost.newTabSpec(TAB_LIVE).setIndicator(tabs.get(TAB_LIVE).root), LiveFragment.class, null);
        tabhost.addTab(tabhost.newTabSpec(TAB_FINANCE).setIndicator(tabs.get(TAB_FINANCE).root), FinanceFragment.class,
                       null);
        tabhost.addTab(tabhost.newTabSpec(TAB_MINE).setIndicator(tabs.get(TAB_MINE).root), MineFragment.class, null);
    }

    @Override
    public void changeCity(City city)
    {
        txtCity.setText(city.getName());
    }

    @Override
    public void changeCommunity(City city)
    {
        String name = city.getName();
        txtCommunity.setText(TextUtils.isEmpty(name) ? "请选择" : name);
    }

    @Override
    public void changeTab(String tab)
    {
        if (TextUtils.isEmpty(tab)) { return; }
        tabhost.setCurrentTabByTag(tab);
    }

    private void changeTabState(String name)
    {
        reset();
        TabHolder tab = tabs.get(name);
        if (tab == null) { return; }
        tab.tap();
        txtHeader.setText(tab.txt.getText());
        if (TAB_MINE.equals(name))
        {
            shezhi.setVisibility(View.VISIBLE);
            img.setVisibility(View.INVISIBLE);
            txtCity.setVisibility(View.INVISIBLE);
            txtCommunity.setVisibility(View.INVISIBLE);
        }
        else
        {
            img.setVisibility(View.VISIBLE);
            txtCity.setVisibility(View.VISIBLE);
            txtCommunity.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.txtCity)
    void selCity()
    {
        Intent intent = new Intent(getActivity(), WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, SelCityFragment.class.getName());
        startActivity(intent);
    }

    @OnClick(R.id.txtCommunity)
    void selCommunity()
    {
        Intent intent = new Intent(getActivity(), WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, SelCityFragment.class.getName());
        intent.putExtra("type", "community");
        startActivity(intent);
    }

    @OnClick(R.id.shezhi)
    void showSettings()
    {
        Intent intent = new Intent(getActivity(), WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, SettingsFragment.class.getName());
        startActivity(intent);
    }

    private void reset()
    {
        shezhi.setVisibility(View.INVISIBLE);
        for (TabHolder tab : tabs.values())
        {
            tab.untap();
        }
    }

    class TabHolder
    {
        View root;
        @Bind(R.id.ivIcon)
        ImageView img;
        @Bind(R.id.txtName)
        TextView txt;
        int icon, iconTap, color, colorTap;

        @SuppressLint("InflateParams")
        TabHolder()
        {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            root = inflater.inflate(R.layout.layout_tab_item, null);
            ButterKnife.bind(this, root);
        }

        TabHolder init(int icon, int iconTap, int color, int colorTap, int text)
        {
            this.icon = icon;
            this.iconTap = iconTap;
            this.color = color;
            this.colorTap = colorTap;
            untap();
            txt.setText(text);
            return this;
        }

        void tap()
        {
            img.setImageResource(iconTap);
            txt.setTextColor(getResources().getColor(colorTap));
        }

        void untap()
        {
            img.setImageResource(icon);
            txt.setTextColor(getResources().getColor(color));
        }
    }
}
