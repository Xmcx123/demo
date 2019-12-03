package com.example.liaozhihua.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class MyViewGroup extends ViewGroup {
    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int currentHeight=t;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            childAt.layout(l,currentHeight,l+childAt.getMeasuredWidth(),currentHeight+childAt.getMeasuredHeight());
            currentHeight=currentHeight+childAt.getMeasuredHeight();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int childCount = getChildCount();
        if (childCount == 0) {
            setMeasuredDimension(0, 0);
            return;
        }
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            int totalHeight = getTotalHeight();
            int maxWidth = getMaxWidth();
            setMeasuredDimension(maxWidth, totalHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(getMaxWidth(), heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, getTotalHeight());
        }
    }

    private int getMaxWidth() {
        int childCount = getChildCount();
        int maxWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(0);
            if (childAt.getMeasuredWidth() > maxWidth) {
                maxWidth = childAt.getMeasuredWidth();
            }
        }
        return maxWidth;

    }

    private int getTotalHeight() {
        int childCount = getChildCount();
        int currentHeight = 0;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            int measuredHeight = childAt.getMeasuredHeight();
            currentHeight += measuredHeight;
        }
        return currentHeight;
    }
}
