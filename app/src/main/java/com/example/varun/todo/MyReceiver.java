package com.example.varun.todo;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Varun on 1/28/2018.
 */

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "NOTE TIME ALERT", Toast.LENGTH_SHORT).show();
        Notification notification = new NotificationCompat.Builder(context, "test")

                .setContentTitle("Hello from Foreground Service")
                .setSmallIcon(R.mipmap.ic_launcher)

/*
                .addAction(new NotificationCompat.Action(R.mipmap.ic_launcher, "Hello", pendingIntent))
*/

                .build();



        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1234,notification);
    }
}
