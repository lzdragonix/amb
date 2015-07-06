package com.scxrh.amb.view.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.scxrh.amb.App;
import com.scxrh.amb.R;
import com.scxrh.amb.component.DaggerRegComponent;
import com.scxrh.amb.module.ActivityModule;
import com.scxrh.amb.module.RegModule;
import com.scxrh.amb.presenter.RegPresenter;
import com.scxrh.amb.view.iview.RegView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

// 注册
public class RegFragment extends BaseFragment implements RegView
{
    public static final String TAG = RegFragment.class.getSimpleName();
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
    @Inject
    RegPresenter presenter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        txtHeader.setText("注册");
        txt1.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_reg;
    }

    @Override
    protected void injectDependencies()
    {
        DaggerRegComponent.builder().appComponent(App.get(getActivity()).getComponent())
                          .activityModule(new ActivityModule(getActivity())).regModule(new RegModule(this)).build()
                          .inject(this);
    }

    @OnClick(R.id.btnBack)
    void btnBack()
    {
        getActivity().finish();
    }

    @OnClick(R.id.btnReg)
    void reg()
    {
        presenter.reg(txtUser.getText().toString(), txtPwd.getText().toString());
    }

    @Override
    public void showReging()
    {
    }

    @Override
    public void showError(int toast)
    {
    }
}
