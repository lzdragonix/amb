package com.scxrh.amb.views.fragment;

import android.os.Bundle;

import com.scxrh.amb.App;
import com.scxrh.amb.R;
import com.scxrh.amb.common.Logger;
import com.scxrh.amb.injector.component.DaggerMvpComponent;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.presenter.HomemakingPresenter;
import com.scxrh.amb.views.view.ProgressView;

import javax.inject.Inject;

import butterknife.OnClick;

// 家政服务
public class HomemakingFragment extends BaseFragment implements ProgressView
{
    public static final String TAG = HomemakingFragment.class.getSimpleName();
    @Inject
    HomemakingPresenter presenter;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_homemarking;
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
        presenter.loadData();
    }

    @OnClick(R.id.btnBack)
    void btnBack()
    {
        getActivity().finish();
    }

    @Override
    public void showProgress(String msg)
    {
        showProgressDialog(msg);
    }

    @Override
    public void showMessage(String msg)
    {
        toast(msg);
    }

    @Override
    public void showData(Object data)
    {
        Logger.i(TAG, data.toString());
    }

    @Override
    public void finish()
    {
        closeProgressDialog();
    }
}
