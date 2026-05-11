package com.example.lab6b;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String msg =
                intent.getStringExtra("message");

        Intent i =
                new Intent(context, MainActivity.class);

        PendingIntent pendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        i,
                        PendingIntent.FLAG_IMMUTABLE
                );

        NotificationManager notificationManager =
                (NotificationManager)
                        context.getSystemService(
                                Context.NOTIFICATION_SERVICE
                        );

        String channelId = "lab6_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel =
                    new NotificationChannel(

                            channelId,

                            "Lab6 Notification",

                            NotificationManager.IMPORTANCE_HIGH
                    );

            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(
                        context,
                        channelId
                )

                        .setSmallIcon(android.R.drawable.ic_dialog_info)

                        .setContentTitle("Reminder Notification")

                        .setContentText(msg)

                        .setAutoCancel(true)

                        .setContentIntent(pendingIntent)

                        .setPriority(
                                NotificationCompat.PRIORITY_HIGH
                        );

        notificationManager.notify(1, builder.build());
    }
}