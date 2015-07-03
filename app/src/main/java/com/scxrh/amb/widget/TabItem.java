package com.scxrh.amb.widget;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scxrh.amb.common.Utils;

public class TabItem extends LinearLayout
{
    private ImageView img;
    private TextView txt;

    public TabItem(Context context, int icon, int iconTap, int color, int colorTap)
    {
        super(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        //img
        img = new ImageView(context);
        img.setImageResource(icon);
        MarginLayoutParams margin = new MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT,
                                                           MarginLayoutParams.WRAP_CONTENT);
        margin.topMargin = (int)Utils.dpToPx(context, (float)5);
    }
}
