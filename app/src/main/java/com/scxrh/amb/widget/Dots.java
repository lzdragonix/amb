package com.scxrh.amb.widget;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.scxrh.amb.R;
import com.scxrh.amb.common.Utils;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class Dots extends LinearLayout
{
    private int mCount;

    public Dots(Context context)
    {
        super(context);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
    }

    public void init(int count)
    {
        mCount = count;
        for (int i = 0; i < mCount; i++)
        {
            ImageView img = new ImageView(getContext());
            img.setBackgroundResource(R.drawable.selector_dots);
            img.setEnabled(false);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            int margin = Utils.dpToPx(5);
            param.setMargins(margin, margin, margin, margin);
            addView(img, param);
        }
    }

    public void changeDot(int index)
    {
        if (index >= mCount) { return; }
        for (int i = 0; i < mCount; i++)
        {
            getChildAt(i).setEnabled(false);
        }
        getChildAt(index).setEnabled(true);
    }
}
