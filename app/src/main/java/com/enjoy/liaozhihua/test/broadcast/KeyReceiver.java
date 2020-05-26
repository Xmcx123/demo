package com.enjoy.liaozhihua.test.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.enjoy.liaozhihua.test.KeepManager;

public class KeyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (TextUtils.equals(action,Intent.ACTION_SCREEN_OFF)) {
            //当屏幕关闭时
            KeepManager.getManager().startKeepActivity(context);
        } else if (TextUtils.equals(action,Intent.ACTION_SCREEN_ON)) {
            //当屏幕开启
            KeepManager.getManager().finishActivity();
        }
    }
}
