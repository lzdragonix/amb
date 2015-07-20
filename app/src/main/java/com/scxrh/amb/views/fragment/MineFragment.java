package com.scxrh.amb.views.fragment;

import android.content.Intent;

import com.scxrh.amb.Const;
import com.scxrh.amb.R;
import com.scxrh.amb.views.activity.WindowActivity;

import butterknife.OnClick;

// 我的
public class MineFragment extends BaseFragment
{
    public static final String TAG = MineFragment.class.getSimpleName();

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_mine;
    }

    @OnClick(R.id.login)
    void showLogin()
    {
        Intent intent = new Intent(getActivity(), WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, LoginFragment.class.getName());
        startActivity(intent);
    }

    @OnClick(R.id.grxx)
    void showGRXX()
    {
        Intent intent = new Intent(getActivity(), WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, PerInfoFragment.class.getName());
        startActivity(intent);
    }
}
