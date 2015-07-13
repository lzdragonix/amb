package com.scxrh.amb.views.fragment;

import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.scxrh.amb.App;
import com.scxrh.amb.Const;
import com.scxrh.amb.R;
import com.scxrh.amb.injector.component.DaggerMvpComponent;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.rest.RestRepository;
import com.scxrh.amb.views.view.MvpView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;

//
public class AgreementFragment extends BaseFragment implements MvpView
{
    public static final String TAG = AgreementFragment.class.getSimpleName();
    @Bind(R.id.txtAgreement)
    TextView txtAgreement;
    @Bind(R.id.txtHeader)
    TextView txtHeader;
    @Inject
    RestRepository rest;
    @Inject
    MessageManager messager;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_agreement;
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
        txtHeader.setText("注册协议");
        txtAgreement.setMovementMethod(ScrollingMovementMethod.getInstance());
        showProgressDialog(messager.getMessage(Const.MSG_LOADING));
        rest.queryAgreement().observeOn(AndroidSchedulers.mainThread()).subscribe(response -> {
            txtAgreement.setText(Html.fromHtml(response.get("data").getAsString()));
            closeProgressDialog();
        }, throwable -> {
            toast(messager.getMessage(Const.MSG_LOADING_FAILED));
            closeProgressDialog();
        });
    }

    @OnClick(R.id.btnBack)
    void btnBack()
    {
        getActivity().finish();
    }
}
