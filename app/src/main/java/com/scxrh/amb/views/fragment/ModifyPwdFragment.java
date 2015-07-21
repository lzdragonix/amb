package com.scxrh.amb.views.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.scxrh.amb.App;
import com.scxrh.amb.R;
import com.scxrh.amb.injector.component.DaggerMvpComponent;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.presenter.ModifyPwdPresenter;
import com.scxrh.amb.views.view.ProgressView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

// 找回密码
public class ModifyPwdFragment extends BaseFragment implements ProgressView
{
    public static final String TAG = ModifyPwdFragment.class.getSimpleName();
    @Bind(R.id.txtHeader)
    TextView txtHeader;
    @Bind(R.id.txtOldPwd)
    TextView txtOldPwd;
    @Bind(R.id.txtNewPwd)
    TextView txtNewPwd;
    @Bind(R.id.txtNewPwd1)
    TextView txtNewPwd1;
    @Bind(R.id.btnSubmit)
    View btnSubmit;
    @Inject
    ModifyPwdPresenter presenter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        txtHeader.setText("找回密码");
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_modify_pwd;
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

    @OnClick(R.id.btnSubmit)
    void submit()
    {
        btnSubmit.setEnabled(false);
        presenter.submit(txtOldPwd.getText().toString(), txtNewPwd.getText().toString(),
                         txtNewPwd1.getText().toString());
    }

    @Override
    public void showProgress(String msg)
    {
        showProgressDialog(msg);
    }

    @Override
    public void showError(String msg)
    {
        toast(msg);
    }

    @Override
    public void showData(Object data)
    { }

    @Override
    public void finish()
    {
        btnSubmit.setEnabled(true);
        closeProgressDialog();
    }
}
