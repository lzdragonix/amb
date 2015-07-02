package com.scxrh.amb;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper
{
    Toast toast = null;

    public ToastHelper()
    { }

    public void toast(Context context, CharSequence text)
    {
        if (toast == null)
        {
            toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        }
        else
        {
            toast.setText(text);
        }
        toast.show();
    }
}
