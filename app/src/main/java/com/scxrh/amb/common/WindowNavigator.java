package com.scxrh.amb.common;

import android.app.Activity;
import android.content.Intent;

import com.scxrh.amb.Const;
import com.scxrh.amb.views.activity.WindowActivity;
import com.scxrh.amb.views.fragment.LoginFragment;

public class WindowNavigator
{
    public void startWindow(Activity context, String fragment)
    {
        Intent intent = new Intent(context, WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, fragment);
        context.startActivity(intent);
    }

    public void startLogin(Activity context)
    {
        Intent intent = new Intent(context, WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, LoginFragment.class.getName());
        context.startActivity(intent);
    }
}
