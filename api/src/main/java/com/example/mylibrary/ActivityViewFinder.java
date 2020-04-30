package com.example.mylibrary;

import android.app.Activity;
import android.view.View;

/**
 * Activity UI查找器
 */

public class ActivityViewFinder {
    public View findView(Object object, int id) {
        return ((Activity) object).findViewById(id);
    }
}
