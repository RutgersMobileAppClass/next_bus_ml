package com.teamtbd.nextbusml.notification;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class NotificationService extends Service {

    ReminderNotification reminderNotification;

    @Override
    public void onCreate() {
        Log.d("SERVICE", "service started");
        reminderNotification = new ReminderNotification(this);
        registerReceiver(reminderNotification,new IntentFilter("alarmy"));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        unregisterReceiver(reminderNotification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
