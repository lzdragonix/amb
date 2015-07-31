package com.scxrh.amb.views.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;

import com.scxrh.amb.Const;
import com.scxrh.amb.R;
import com.scxrh.amb.views.fragment.MainFragment;

public class MainActivity extends BaseActivity
{
    private BroadcastReceiver mReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            if (Const.ACTION_EXIT.equals(action))
            {
                exit();
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit()
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        replaceFragment(R.id.container, new MainFragment(), MainFragment.TAG);
        registerReceiver(mReceiver, new IntentFilter(Const.ACTION_EXIT));
    }

    @Override
    protected void onDestroy()
    {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_main;
    }
}
