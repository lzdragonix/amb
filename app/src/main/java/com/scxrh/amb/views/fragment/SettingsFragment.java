package com.scxrh.amb.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.scxrh.amb.App;
import com.scxrh.amb.Const;
import com.scxrh.amb.R;
import com.scxrh.amb.injector.component.DaggerMvpComponent;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.rest.RestClient;
import com.scxrh.amb.views.activity.WindowActivity;
import com.scxrh.amb.views.view.MvpView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;

// 设置
public class SettingsFragment extends BaseFragment implements MvpView
{
    public static final String TAG = SettingsFragment.class.getSimpleName();
    @Bind(R.id.txtHeader)
    TextView txtHeader;
    @Bind(R.id.txtCache)
    TextView txtCache;
    @Inject
    RestClient rest;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_settings;
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        txtHeader.setText("设置");
        txtCache.setText(String.format("%.2f", Math.random()) + " M");
    }

    @OnClick(R.id.btnBack)
    void btnBack()
    {
        getActivity().finish();
    }

    @OnClick(R.id.rlModifyPwd)
    void modifyPwd()
    {
        Intent intent = new Intent(getActivity(), WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, ModifyPwdFragment.class.getName());
        startActivity(intent);
    }

    @OnClick(R.id.rlCleanCache)
    void cleanCache()
    {
        txtCache.setText("");
    }

    @OnClick(R.id.rlLogout)
    void rlLogout()
    {
        rest.logout().observeOn(AndroidSchedulers.mainThread()).subscribe(response -> {
            getActivity().sendBroadcast(new Intent(Const.ACTION_EXIT));
            getActivity().finish();
        });
    }
}
