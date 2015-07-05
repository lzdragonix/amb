package com.scxrh.amb.view.fragment;

import android.view.View;
import android.widget.TextView;

import com.scxrh.amb.App;
import com.scxrh.amb.R;
import com.scxrh.amb.component.DaggerLoginComponent;
import com.scxrh.amb.module.LoginModule;
import com.scxrh.amb.presenter.LoginPresenter;
import com.scxrh.amb.view.LoginView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

// 登录
public class LoginFragment extends BaseFragment implements LoginView
{
    public static final String TAG = LoginFragment.class.getSimpleName();
    @Bind(R.id.txtUser)
    TextView txtUser;
    @Bind(R.id.txtPwd)
    TextView txtPwd;
    @Bind(R.id.btnLogin)
    View btnLogin;
    @Inject
    LoginPresenter presenter;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_login;
    }

    @Override
    protected void injectDependencies()
    {
        DaggerLoginComponent.builder().appComponent(App.get(getActivity()).getComponent())
                            .loginModule(new LoginModule(this)).build().inject(this);
    }

    @OnClick(R.id.btnLogin)
    void login()
    {
        btnLogin.setEnabled(false);
        presenter.login(txtUser.getText().toString(), txtPwd.getText().toString());
    }

    @Override
    public void showLogining()
    {
        showProgressDialog("正在登录，请稍候…");
    }

    @Override
    public void showError(int toast)
    {
        toast(toast);
        btnLogin.setEnabled(true);
        closeProgressDialog();
    }
}
