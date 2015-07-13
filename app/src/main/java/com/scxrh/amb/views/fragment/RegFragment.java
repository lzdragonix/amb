package com.scxrh.amb.views.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.scxrh.amb.App;
import com.scxrh.amb.Const;
import com.scxrh.amb.R;
import com.scxrh.amb.injector.component.DaggerMvpComponent;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.presenter.RegPresenter;
import com.scxrh.amb.views.activity.WindowActivity;
import com.scxrh.amb.views.view.RegView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

// 注册
public class RegFragment extends BaseFragment implements RegView
{
    public static final String TAG = RegFragment.class.getSimpleName();
    @Bind(R.id.txtHeader)
    TextView txtHeader;
    @Bind(R.id.txtAgreement)
    TextView txtAgreement;
    @Bind(R.id.btnReg)
    View btnReg;
    @Bind(R.id.txtUser)
    TextView txtUser;
    @Bind(R.id.txtPwd)
    TextView txtPwd;
    @Bind(R.id.txtVerify)
    TextView txtVerify;
    @Bind(R.id.ckxAgree)
    CheckBox ckxAgree;
    @Bind(R.id.btnGetVerify)
    TextView btnGetVerify;
    @Inject
    RegPresenter presenter;
    private CountDownTimer timer = new CountDownTimer(30000, 1000)
    {
        @Override
        public void onTick(long millisUntilFinished)
        {
            btnGetVerify.setText((millisUntilFinished / 1000) + getString(R.string.txt_resend_verify_code));
        }

        @Override
        public void onFinish()
        {
            btnGetVerify.setEnabled(true);
            btnGetVerify.setText(getString(R.string.txt_get_verify_code));
        }
    };

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        txtHeader.setText(getString(R.string.txt_register));
        txtAgreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public void onDestroy()
    {
        timer.cancel();
        super.onDestroy();
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_reg;
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
        getActivity().setResult(Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    @OnClick(R.id.btnReg)
    void reg()
    {
        btnReg.setEnabled(false);
        presenter.reg(txtUser.getText().toString(), txtPwd.getText().toString(), txtVerify.getText().toString(),
                      ckxAgree.isChecked());
    }

    @OnClick(R.id.btnGetVerify)
    void getVerify()
    {
        btnGetVerify.setEnabled(false);
        presenter.getSMS(txtUser.getText().toString());
        timer.start();
    }

    @OnClick(R.id.txtAgreement)
    void agreement()
    {
        Intent intent = new Intent(getActivity(), WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, AgreementFragment.class.getName());
        startActivity(intent);
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
    public void showData()
    { }

    @Override
    public void finish()
    {
        closeProgressDialog();
        btnReg.setEnabled(true);
    }

    @Override
    public void regSuccess()
    {
        Intent intent = new Intent();
        intent.putExtra(Const.KEY_ACCOUNT, txtUser.getText().toString());
        intent.putExtra(Const.KEY_PASSWORD, txtPwd.getText().toString());
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }
}
