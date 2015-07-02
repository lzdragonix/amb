package com.scxrh.amb.feature;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper
{
    private Toast mToast = null;

    public void toast(Context context, String msg, int gravity)
    {
        if (mToast == null)
        {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        }
        else
        {
            mToast.setText(msg);
        }
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setGravity(gravity, 0, 0);
        mToast.show();
    }
}
