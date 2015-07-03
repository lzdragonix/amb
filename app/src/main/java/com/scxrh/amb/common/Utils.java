package com.scxrh.amb.common;

import android.content.res.Resources;
import android.util.TypedValue;

public class Utils
{
    public static int dpToPx(int dip)
    {
        Resources r = Resources.getSystem();
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.getDisplayMetrics());
    }
}
