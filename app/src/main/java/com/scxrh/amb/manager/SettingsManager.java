package com.scxrh.amb.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

public class SettingsManager
{
    private static String SETTING_NAME = "user_settings";
    private Context mContext;
    private int mode;

    public SettingsManager(Context context)
    {
        mContext = context;
        mode = Build.VERSION.SDK_INT >= 11 ? 4 : 0;
    }

    public void setValue(String key, String value)
    {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(SETTING_NAME, mode);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value).commit();
    }

    public void setValue(String key, boolean b)
    {
        String v = b ? "1" : "0";
        setValue(key, v);
    }

    public String getString(String key)
    {
        SharedPreferences pf = mContext.getSharedPreferences(SETTING_NAME, mode);
        return pf.getString(key, null);
    }

    public boolean getBoolean(String key)
    {
        return "1".equals(getString(key));
    }
}
