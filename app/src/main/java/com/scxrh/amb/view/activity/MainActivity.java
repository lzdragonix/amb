package com.scxrh.amb.view.activity;

import android.os.Bundle;

import com.scxrh.amb.R;
import com.scxrh.amb.view.fragment.LoginFragment;
import com.scxrh.amb.view.fragment.MainFragment;

public class MainActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        replaceFragment(R.id.container, new MainFragment(), LoginFragment.TAG);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_main;
    }
}
