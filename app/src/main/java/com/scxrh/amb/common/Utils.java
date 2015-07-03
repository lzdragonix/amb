package com.scxrh.amb.common;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class Utils
{
    public static float dpToPx(Context mContext, float size)
    {
        Resources r;
        if (mContext == null) { r = Resources.getSystem(); }
        else { r = mContext.getResources(); }
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, size, r.getDisplayMetrics());
    }
}
