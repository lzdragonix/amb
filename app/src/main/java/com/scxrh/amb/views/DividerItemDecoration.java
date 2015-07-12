package com.scxrh.amb.views;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.scxrh.amb.common.Utils;

public class DividerItemDecoration extends RecyclerView.ItemDecoration
{
    private int mOrientation = LinearLayoutManager.VERTICAL;
    private int mItemSize = 1;
    private Paint mPaint;

    public DividerItemDecoration(int orientation)
    {
        this.mOrientation = orientation;
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL)
        {
            throw new IllegalArgumentException("error param");
        }
        mItemSize = Utils.dpToPx(1);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state)
    {
        if (mOrientation == LinearLayoutManager.VERTICAL)
        {
            drawVertical(c, parent);
        }
        else
        {
            drawHorizontal(c, parent);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        if (mOrientation == LinearLayoutManager.VERTICAL)
        {
            outRect.set(0, 0, 0, mItemSize);
        }
        else
        {
            outRect.set(0, 0, mItemSize, 0);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent)
    {
        int left = parent.getPaddingLeft();
        int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        int childSize = parent.getChildCount();
        if (childSize > 0)
        {
            Log.i("================", "size="+childSize);
            View c0 = parent.getChildAt(0);
            left = left + c0.getPaddingLeft();
            right = right - c0.getPaddingRight();
            for (int i = 0; i < childSize; i++)
            {
                View child = parent.getChildAt(i);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)child.getLayoutParams();
                int top = child.getBottom() + layoutParams.bottomMargin;
                int bottom = top + mItemSize;
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent)
    {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++)
        {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + mItemSize;
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }
}