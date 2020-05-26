package com.enjoy.liaozhihua.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.lang.ref.WeakReference;

import com.enjoy.liaozhihua.test.activity.KeepActivity;
import com.enjoy.liaozhihua.test.broadcast.KeyReceiver;

public class KeepManager {
    private static class Instance {
        private static final KeepManager INSTANCE = new KeepManager();
    }
    private KeepManager(){}
    public static KeepManager getManager(){
        return Instance.INSTANCE;
    }

    private KeyReceiver mKeyReceiver;
    private WeakReference<Activity> mKeepAct;

    /**
     * 注册开关屏广播
     * @param context
     */
    public void registerReceiver(Context context) {
        if (mKeyReceiver !=null ) return;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        mKeyReceiver = new KeyReceiver();
        context.registerReceiver(mKeyReceiver,intentFilter);
    }

    /**
     * 注销广播
     * @param context
     */
    public void unRegisterReceiver(Context context){
        if (mKeyReceiver != null) {
            context.unregisterReceiver(mKeyReceiver);
        }
    }

    public void setKeepActivity(KeepActivity activity){
        mKeepAct = new WeakReference<Activity>(activity);
    }

    /**
     * 关屏时打开1px的Activity
     * @param context
     */
    public void startKeepActivity(Context context){
        Intent intent = new Intent(context, KeepActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 开屏时把Activity销毁
     */
    public void finishActivity(){
        if (mKeepAct != null) {
            Activity activity = mKeepAct.get();
            if (activity != null) {
                activity.finish();
            }
            mKeepAct = null;
        }
    }
}
