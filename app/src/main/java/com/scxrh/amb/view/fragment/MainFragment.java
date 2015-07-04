package com.scxrh.amb.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.scxrh.amb.App;
import com.scxrh.amb.R;
import com.scxrh.amb.component.DaggerMainComponent;
import com.scxrh.amb.component.MainComponent;
import com.scxrh.amb.module.MainModule;
import com.scxrh.amb.presenter.MainPresenter;
import com.scxrh.amb.view.MainView;
import com.scxrh.amb.widget.FragmentTabHostState;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainFragment extends BaseFragment implements MainView, TabHost.OnTabChangeListener
{
    public static final String TAG = MainFragment.class.getSimpleName();
    private static final String TAB_RECOMM = "TAB_RECOMM";
    private static final String TAB_LIVE = "TAB_LIVE";
    private static final String TAB_FINANCE = "TAB_FINANCE";
    private static final String TAB_MINE = "TAB_MINE";
    @Inject
    MainPresenter presenter;
    @Bind(android.R.id.tabhost)
    FragmentTabHostState tabhost;
    @Bind(R.id.txt_header)
    TextView txtHeader;
    private Map<String, TabHolder> tabs = new HashMap<>();

    @SuppressLint("InflateParams")
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        MainComponent component = DaggerMainComponent.builder().appComponent(App.get(getActivity()).getComponent())
                                                     .mainModule(new MainModule(this)).build();
        component.inject(this);
        presenter.initialize();
        tabhost.setOnTabChangedListener(this);
        presenter.changeTab(TAB_RECOMM);
    }

    @Override
    public void onTabChanged(String tabId)
    {
        presenter.changeTab(tabId);
    }

    @Override
    protected View genView(LayoutInflater inflater, ViewGroup container)
    {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void initTab()
    {
        tabhost.setup(getActivity(), getChildFragmentManager(), R.id.content);
        // recommend
        TabHolder tab = new TabHolder()
                .init(R.mipmap.icon_6, R.mipmap.icon_6_on, R.color.cffffff, R.color.cfbc000, R.string.txt_recommend);
        tabs.put(TAB_RECOMM, tab);
        // livelihood
        tab = new TabHolder()
                .init(R.mipmap.icon_7, R.mipmap.icon_7_on, R.color.cffffff, R.color.cfbc000, R.string.txt_livelihood);
        tabs.put(TAB_LIVE, tab);
        // finance
        tab = new TabHolder()
                .init(R.mipmap.icon_8, R.mipmap.icon_8_on, R.color.cffffff, R.color.cfbc000, R.string.txt_finance);
        tabs.put(TAB_FINANCE, tab);
        // mine
        tab = new TabHolder()
                .init(R.mipmap.icon_9, R.mipmap.icon_9_on, R.color.cffffff, R.color.cfbc000, R.string.txt_mine);
        tabs.put(TAB_MINE, tab);
        // add tab
        tabhost.addTab(tabhost.newTabSpec(TAB_RECOMM).setIndicator(tabs.get(TAB_RECOMM).root), RecommendFragment.class,
                       null);
        tabhost.addTab(tabhost.newTabSpec(TAB_LIVE).setIndicator(tabs.get(TAB_LIVE).root), LiveFragment.class, null);
        tabhost.addTab(tabhost.newTabSpec(TAB_FINANCE).setIndicator(tabs.get(TAB_FINANCE).root), FinanceFragment.class,
                       null);
        tabhost.addTab(tabhost.newTabSpec(TAB_MINE).setIndicator(tabs.get(TAB_MINE).root), FinanceFragment.class, null);
    }

    @Override
    public void changeTab(String name)
    {
        reset();
        TabHolder tab = tabs.get(name);
        if (tab == null) { return; }
        tab.tap();
        txtHeader.setText(tab.txt.getText());
    }

    private void reset()
    {
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
