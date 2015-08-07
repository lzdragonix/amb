package com.scxrh.amb.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;

import com.scxrh.amb.Const;
import com.scxrh.amb.views.activity.WindowActivity;
import com.scxrh.amb.views.fragment.LoginFragment;

public class WindowNavigator
{
    public void startWindow(Activity context, String fragment)
    {
        startWindow(context, fragment, null);
    }

    public void startWindow(Activity context, String fragment, Parcelable data)
    {
        Intent intent = new Intent(context, WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, fragment);
        if (data != null) { intent.putExtra(Const.KEY_DATA, data); }
        context.startActivity(intent);
    }

    public void startLogin(Activity context)
    {
        startWindow(context, LoginFragment.class.getName());
    }
}
