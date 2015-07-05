package com.scxrh.amb.view.fragment;

import android.view.View;
import android.widget.TextView;

import com.scxrh.amb.R;
import com.scxrh.amb.component.DaggerLoginComponent;
import com.scxrh.amb.module.LoginModule;
import com.scxrh.amb.mvp.MvpFragment;
import com.scxrh.amb.presenter.impl.LoginPresenterImpl;
import com.scxrh.amb.view.LoginView;

import butterknife.Bind;
import butterknife.OnClick;

// 登录
public class LoginFragment extends MvpFragment<LoginView, LoginPresenterImpl> implements LoginView
{
    public static final String TAG = LoginFragment.class.getSimpleName();
    @Bind(R.id.txtUser)
    TextView txtUser;
    @Bind(R.id.txtPwd)
    TextView txtPwd;
    @Bind(R.id.btnLogin)
    View btnLogin;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_login;
    }

    @Override
    protected void injectDependencies()
    {
        DaggerLoginComponent.builder().loginModule(new LoginModule(getActivity())).build().inject(this);
    }

    @OnClick(R.id.btnLogin)
    void login()
    {
        btnLogin.setEnabled(false);
        presenter.login(txtUser.getText().toString(), txtPwd.getText().toString());
    }

    @Override
    public LoginPresenterImpl createPresenter()
    {
        return new LoginPresenterImpl();
    }

    @Override
    public void showProgress(String msg)
    {
        showProgressDialog(msg);
    }

    @Override
    public void showError(String error)
    {
        toast(error);
        btnLogin.setEnabled(true);
        closeProgressDialog();
    }
}
