package com.scxrh.amb.views.fragment;

import android.os.Bundle;

import com.scxrh.amb.App;
import com.scxrh.amb.R;
import com.scxrh.amb.injector.component.DaggerMvpComponent;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.presenter.FinProPresenter;
import com.scxrh.amb.views.view.ProgressView;

import java.util.List;

import javax.inject.Inject;

//
public class FinProFragment extends BaseFragment implements ProgressView
{
    public static final String TAG = FinProFragment.class.getSimpleName();
    @Inject
    FinProPresenter presenter;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_finance_product;
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
        presenter.loadData("402883474e4336c2014e43499c43000c", "");
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
    public void showData(List<?> data)
    {
    }

    @Override
    public void finish()
    {
    }
}
