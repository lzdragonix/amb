package com.scxrh.amb.feature;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.scxrh.amb.Const;

import java.util.HashMap;
import java.util.Map;

public class ToastHelper
{
    private Map<Integer, String> map = new HashMap<>();
    private Toast mToast = null;

    public ToastHelper()
    {
        map.put(Const.TOAST_EMPTY_USER_OR_PASSWORD, "用户名或密码为空！");
    }

    public void toast(Context context, int toastId, int gravity)
    {
        String msg = map.get(toastId);
        if (TextUtils.isEmpty(msg))
        {
            throw new IllegalArgumentException("the toastId:" + toastId + "is undefined in Const class.");
        }
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
