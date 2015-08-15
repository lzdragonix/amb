package com.scxrh.amb.views.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.scxrh.amb.Const;
import com.scxrh.amb.R;

public class WindowActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        String clz = getIntent().getStringExtra(Const.KEY_FRAGMENT);
        if (TextUtils.isEmpty(clz))
        {
            throw new IllegalArgumentException("the fragment is null.");
        }
        try
        {
            Fragment fragment = Fragment.instantiate(this, clz);
            Bundle data = getIntent().getExtras();
            Bundle bundle = data == null ? new Bundle() : new Bundle(data);
            fragment.setArguments(bundle);
            replaceFragment(R.id.container, fragment, fragment.getClass().getSimpleName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_main;
    }
}
