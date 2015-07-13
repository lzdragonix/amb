package com.scxrh.amb.views.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.scxrh.amb.App;
import com.scxrh.amb.R;
import com.scxrh.amb.injector.component.DaggerMvpComponent;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.presenter.ManagerPresenter;
import com.scxrh.amb.rest.RestRepository;
import com.scxrh.amb.views.view.ProgressView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

// 客户经理
public class ManagerFragment extends BaseFragment implements ProgressView
{
    public static final String TAG = ManagerFragment.class.getSimpleName();
    @Bind(R.id.rvList)
    RecyclerView mRecyclerView;
    @Bind(R.id.txtHeader)
    TextView txtHeader;
    @Inject
    RestRepository rest;
    @Inject
    ManagerPresenter presenter;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_manager;
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
        txtHeader.setText(getString(R.string.txt_manager));
    }

    @OnClick(R.id.btnBack)
    void btnBack()
    {
        getActivity().finish();
    }

    @Override
    public void showProgress(String msg)
    {
    }

    @Override
    public void showError(String msg)
    {
    }

    @Override
    public void showData()
    {
    }

    @Override
    public void finish()
    {
    }
}
