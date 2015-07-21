package com.scxrh.amb.views.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

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
        presenter.initView();
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
        Intent intent = new Intent(getActivity(), WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, LoginFragment.class.getName());
        startActivity(intent);
    }

    @Override
    public void changeAvatar(String path)
    {
        if (TextUtils.isEmpty(path)) { return; }
        imgAvatar.setImageURI(Uri.parse(path));
    }

    @OnClick(R.id.grxx)
    void showGRXX()
    {
        Intent intent = new Intent(getActivity(), WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, PerInfoFragment.class.getName());
        startActivity(intent);
    }

    @OnClick(R.id.favorite)
    void favorite()
    {
        Intent intent = new Intent(getActivity(), WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, FavoriteFragment.class.getName());
        startActivity(intent);
    }

    @OnClick(R.id.tsjy)
    void tsjy()
    {
        Intent intent = new Intent(getActivity(), WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, SuggestionFragment.class.getName());
        startActivity(intent);
    }

    @OnClick(R.id.jf)
    void jf()
    {
        Intent intent = new Intent(getActivity(), WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, PointsFragment.class.getName());
        startActivity(intent);
    }

    @OnClick(R.id.dd)
    void order()
    {
        Intent intent = new Intent(getActivity(), WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, OrderFragment.class.getName());
        startActivity(intent);
    }
}
