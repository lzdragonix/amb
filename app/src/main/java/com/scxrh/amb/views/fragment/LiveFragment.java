package com.scxrh.amb.views.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.scxrh.amb.App;
import com.scxrh.amb.R;
import com.scxrh.amb.injector.component.DaggerMvpComponent;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.views.view.LiveView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

// 生活服务
public class LiveFragment extends BaseFragment implements LiveView
{
    public static final String TAG = LiveFragment.class.getSimpleName();
    static final ButterKnife.Setter<TextView, Integer> TEXTCOLOR = (view, value, index) -> view.setTextColor(value);
    static final ButterKnife.Setter<TextView, View.OnClickListener> ONCLICKLISTENER = (view, value, index) -> {
        view.setOnClickListener(value);
        view.setTag(index);
    };
    @Bind({ R.id.tabFJ, R.id.tabMS, R.id.tabSAH, R.id.tabSEH, R.id.tabYL, R.id.tabJD })
    List<TextView> tabs;
    private int textColorNormal;
    private int textColorTap;
    private Fragment mCurrent;
    private View.OnClickListener listener = v -> changeTab((int)v.getTag());

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        textColorNormal = getResources().getColor(R.color.c333333);
        textColorTap = getResources().getColor(R.color.cfbc000);
        TEXTCOLOR.set(tabs.get(0), textColorTap, 0);
        ButterKnife.apply(tabs, ONCLICKLISTENER, listener);
        changeTab(0);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_live;
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

    public void changeTab(int index)
    {
        ButterKnife.apply(tabs, TEXTCOLOR, textColorNormal);
        TEXTCOLOR.set(tabs.get(index), textColorTap, index);
        //用hide和show的方式实现nav body的切换，避免丢失界面状态
        String tag = "tag=" + index;
        FragmentManager fm = getChildFragmentManager();
        Fragment fragment = fm.findFragmentByTag(tag);
        FragmentTransaction ft = fm.beginTransaction();
        if (mCurrent != null)
        {
            //点击当前的 item，则返回
            if (mCurrent.getTag().equals(tag)) { return; }
            ft.hide(mCurrent);
        }
        if (fragment == null)
        {
            switch (index)
            {
                case 0:
                    fragment = new LiveFJFragment();
                    break;
                case 1:
                    fragment = new LiveMSFragment();
                    break;
                case 3:
                    fragment = new LiveSEHFragment();
                    break;
                default:
                    fragment = new BlankFragment();
                    break;
            }
            Bundle bundle = new Bundle();
            ft.add(R.id.content, fragment, tag);
            mCurrent = fragment;
        }
        else
        {
            ft.show(fragment);
            mCurrent = fragment;
        }
        ft.commit();
    }
}
