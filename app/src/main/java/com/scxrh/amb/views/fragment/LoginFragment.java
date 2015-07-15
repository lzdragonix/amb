package com.scxrh.amb.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.scxrh.amb.App;
import com.scxrh.amb.Const;
import com.scxrh.amb.R;
import com.scxrh.amb.injector.component.DaggerMvpComponent;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.presenter.LoginPresenter;
import com.scxrh.amb.views.activity.BaseActivity;
import com.scxrh.amb.views.activity.MainActivity;
import com.scxrh.amb.views.activity.WindowActivity;
import com.scxrh.amb.views.view.LoginView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

// 登录
public class LoginFragment extends BaseFragment implements LoginView, TextView.OnEditorActionListener
{
    public static final String TAG = LoginFragment.class.getSimpleName();
    @Bind(R.id.txtUser)
    EditText txtUser;
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
        txtUser.setOnEditorActionListener(this);
        txtPwd.setOnEditorActionListener(this);
        presenter.initialize();
        ((MainActivity)getActivity()).setPresenter(presenter);
    }

    @Override
    public void initUser(String user)
    {
        txtUser.setText(user);
        txtUser.setSelection(user.length());
    }

    @Override
    public void initPwd(String pwd)
    {
        txtUser.setText(pwd);
        txtUser.setSelection(pwd.length());
    }

    @Override
    public void showMain()
    {
        if (isAdded())
        {
            ((BaseActivity)getActivity()).replaceFragment(R.id.container, new MainFragment(), MainFragment.TAG);
        }
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
    public void showData(List<?> data)
    { }

    @Override
    public void finish()
    {
        closeProgressDialog();
        btnLogin.setEnabled(true);
    }

    @OnClick(R.id.btnLogin)
    void login()
    {
        btnLogin.setEnabled(false);
        presenter.login(txtUser.getText().toString(), txtPwd.getText().toString());
    }

    @OnClick(R.id.btnReg)
    void openReg()
    {
        Intent intent = new Intent(getActivity(), WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, RegFragment.class.getName());
        getActivity().startActivityForResult(intent, MainActivity.REQUESTCODE_REG);
    }

    @OnClick(R.id.btnRetPwd)
    void retPwd()
    {
        Intent intent = new Intent(getActivity(), WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, RetrievePwdFragment.class.getName());
        startActivity(intent);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {
        if (v.getId() == R.id.txtPwd && btnLogin.isEnabled())
        {
            btnLogin.performClick();
        }
        return true;
    }
}
