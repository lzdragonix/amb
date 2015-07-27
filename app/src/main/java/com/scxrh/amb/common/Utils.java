package com.scxrh.amb.common;

import android.content.res.Resources;
import android.os.Environment;
import android.text.TextUtils;
import android.util.TypedValue;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
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
}
