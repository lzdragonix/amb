package com.scxrh.amb.views.activity;

import android.os.Bundle;
import android.view.KeyEvent;

import com.scxrh.amb.R;
import com.scxrh.amb.views.fragment.MainFragment;

public class MainActivity extends BaseActivity
{
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            try
            {
                finish();
                // 杀死该应用进程
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

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
