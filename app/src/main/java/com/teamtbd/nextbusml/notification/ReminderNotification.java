package com.teamtbd.nextbusml.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.teamtbd.nextbusml.R;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by crejaud on 12/6/16.
 */

public class ReminderNotification extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";
    public static String TAG = "NotificationTAG";
    private Set<Integer> idset;

    private Context ctx;

    public ReminderNotification(Context context) {
        this.ctx = context;
        idset = new HashSet<>();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("ALARM", "ALARM SHOULD GO OFF");

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        String msg = intent.getStringExtra(NOTIFICATION);
        Notification notification = createNotification(msg);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        if (!idset.contains(id)) {
            Log.d("ALARM", "msg = " + msg);
            Log.d("ALARM", "id = " + id);
            notificationManager.notify(TAG, id, notification);
            idset.add(id);
        }
    }

    private Notification createNotification(String content) {
        Notification.Builder builder =
                new Notification.Builder(this.ctx)
                        .setContentTitle("Your class is almost starting.")
                        .setContentText(content)
                        .setSmallIcon(R.drawable.ic_add_black_24dp)
                        .setPriority(Notification.PRIORITY_MAX);
        return builder.build();
    }

}
