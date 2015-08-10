package com.scxrh.amb.common;

import android.content.res.Resources;
import android.os.Environment;
import android.text.TextUtils;
import android.util.TypedValue;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils
{
    public static boolean hasSDCard()
    {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static int dpToPx(int dip)
    {
        Resources r = Resources.getSystem();
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.getDisplayMetrics());
    }

    public static boolean regex(String target, Pattern regex)
    {
        Matcher m = regex.matcher(target);
        return m.find();
    }

    public static List<JSONObject> toList(JSONArray array)
    {
        List<JSONObject> result = new ArrayList<>();
        int len = array.length();
        for (int i = 0; i < len; i++)
        {
            JSONObject json = array.optJSONObject(i);
            if (json == null) { continue; }
            result.add(json);
        }
        return result;
    }

    public static float tryParse(String value, float defaultValue)
    {
        if (TextUtils.isEmpty(value)) { return defaultValue; }
        float result;
        try
        {
            result = Float.parseFloat(value);
        }
        catch (NumberFormatException e)
        {
            result = defaultValue;
        }
        return result;
    }

    public static double tryParse(String value, double defaultValue)
    {
        if (TextUtils.isEmpty(value)) { return defaultValue; }
        double result;
        try
        {
            result = Double.parseDouble(value);
        }
        catch (NumberFormatException e)
        {
            result = defaultValue;
        }
        return result;
    }

    public static String tryParse(Date date, String format)
    {
        String result = null;
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
            result = formatter.format(date);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public static int tryParse(String value, int defaultValue)
    {
        if (TextUtils.isEmpty(value)) { return defaultValue; }
        int result;
        try
        {
            result = Integer.parseInt(value);
        }
        catch (NumberFormatException e)
        {
            result = defaultValue;
        }
        return result;
    }

    public static String replaceNull(String s)
    {
        return s == null ? "" : s;
    }
}
