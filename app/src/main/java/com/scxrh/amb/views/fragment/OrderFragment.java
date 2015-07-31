package com.scxrh.amb.views.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.scxrh.amb.App;
import com.scxrh.amb.R;
import com.scxrh.amb.injector.component.DaggerMvpComponent;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.presenter.OrderPresenter;
import com.scxrh.amb.views.view.ProgressView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

// 订单
public class OrderFragment extends BaseFragment implements ProgressView
{
    public static final String TAG = OrderFragment.class.getSimpleName();
    @Bind(R.id.txtHeader)
    TextView txtHeader;
    @Inject
    OrderPresenter presenter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        txtHeader.setText("订单中心");
        presenter.loadData();
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_order;
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
        String a = "";
    }

    @Override
    public void finish()
    {
        closeProgressDialog();
    }
}
