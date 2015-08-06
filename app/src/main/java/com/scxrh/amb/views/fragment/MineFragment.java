package com.scxrh.amb.views.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.scxrh.amb.App;
import com.scxrh.amb.Const;
import com.scxrh.amb.R;
import com.scxrh.amb.injector.component.DaggerMvpComponent;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.presenter.MinePresenter;
import com.scxrh.amb.views.activity.WindowActivity;
import com.scxrh.amb.views.view.MineView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

// 我的
public class MineFragment extends BaseFragment implements MineView
{
    public static final String TAG = MineFragment.class.getSimpleName();
    @Bind(R.id.imgAvatar)
    SimpleDraweeView imgAvatar;
    @Bind(R.id.txt_account)
    TextView txtAccount;
    @Inject
    MinePresenter presenter;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_mine;
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
        presenter.init();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        presenter.onDestroyView();
    }

    @OnClick(R.id.imgAvatar)
    void showLogin()
    {
        if ("请登录您的账户".equals(txtAccount.getText().toString()))
        {
            Intent intent = new Intent(getActivity(), WindowActivity.class);
            intent.putExtra(Const.KEY_FRAGMENT, LoginFragment.class.getName());
            startActivity(intent);
        }
    }

    @Override
    public void changeAvatar(String path)
    {
        if (TextUtils.isEmpty(path)) { return; }
        imgAvatar.setImageURI(Uri.parse(path));
    }

    @Override
    public void setAccount(String account)
    {
        txtAccount.setText(account);
    }

    @OnClick(R.id.grxx)
    void showGRXX()
    {
        presenter.openWindow(1);
    }

    @OnClick(R.id.favorite)
    void favorite()
    {
        presenter.openWindow(2);
    }

    @OnClick(R.id.dd)
    void order()
    {
        presenter.openWindow(3);
    }

    @OnClick(R.id.tsjy)
    void tsjy()
    {
        presenter.openWindow(4);
    }

    @OnClick(R.id.jf)
    void jf()
    {
        presenter.openWindow(5);
    }
}
