package com.enjoy.liaozhihua.test.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ForegroundService extends Service {

    public static final int NOTIFICATION_ID=0x11;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(NOTIFICATION_ID, new Notification());
        return super.onStartCommand(intent, flags, startId);
    }
}
