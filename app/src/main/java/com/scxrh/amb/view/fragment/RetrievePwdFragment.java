package com.scxrh.amb.view.fragment;

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
import com.scxrh.amb.component.DaggerRetComponent;
import com.scxrh.amb.module.ActivityModule;
import com.scxrh.amb.module.RetModule;
import com.scxrh.amb.presenter.RetPresenter;
import com.scxrh.amb.view.iview.RetView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

// 找回密码
public class RetrievePwdFragment extends BaseFragment implements RetView
{
    public static final String TAG = RetrievePwdFragment.class.getSimpleName();
    @Bind(R.id.txtHeader)
    TextView txtHeader;
    @Bind(R.id.txt1)
    TextView txt1;
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
    RetPresenter presenter;
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
        txtHeader.setText("找回密码");
        txt1.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
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
        return R.layout.fragment_retrieve_pwd;
    }

    @Override
    protected void injectDependencies()
    {
        DaggerRetComponent.builder().appComponent(App.get(getActivity()).getComponent())
                          .activityModule(new ActivityModule(getActivity())).retModule(new RetModule(this)).build()
                          .inject(this);
    }

    @OnClick(R.id.btnBack)
    void btnBack()
    {
        getActivity().setResult(Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    @OnClick(R.id.btnGetVerify)
    void getVerify()
    {
        btnGetVerify.setEnabled(false);
        timer.start();
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
