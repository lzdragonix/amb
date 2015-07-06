package com.scxrh.amb.common;

import android.content.res.Resources;
import android.util.TypedValue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils
{
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
}
