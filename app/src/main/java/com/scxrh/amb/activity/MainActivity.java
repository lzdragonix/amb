package com.scxrh.amb.activity;

import android.os.Bundle;

import com.scxrh.amb.R;
import com.scxrh.amb.fragment.MainFragment;

public class MainActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        replaceFragment(R.id.container, new MainFragment(), MainFragment.TAG);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_main;
    }
}
