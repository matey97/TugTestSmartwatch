package es.uji.geotec.tugtest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;

import es.uji.geotec.tugtest.messaging.ResultMessagingProtocol;

public class NotificationProvider {

    private static final String REQUEST_PERMISSIONS_CHANNEL = "REQUEST_PERMISSIONS_CHANNEL";
    private static final int REQUEST_PERMISSIONS_NOTIFICATION_ID = 23;

    private static final String SENSOR_RECORDING_CHANNEL = "SENSOR_RECORDING_CHANNEL";
    private static final int SENSOR_RECORDING_NOTIFICATION_ID = 24;

    private Context context;
    private NotificationManagerCompat notificationManager;

    public NotificationProvider(Context context) {
        this.context = context;
        this.notificationManager = NotificationManagerCompat.from(context);
    }

    public void createNotificationForPermissions(
            ArrayList<String> permissions,
            String sourceNodeId,
            ResultMessagingProtocol resultProtocol
    ) {
        setupNotificationChannelIfNeeded(
                REQUEST_PERMISSIONS_CHANNEL,
                context.getResources().getString(R.string.permissions_channel_description)
        );

        PendingIntent pendingIntent = IntentManager.pendingIntentFromPermissionsToRequest(
                context,
                permissions,
                sourceNodeId,
                resultProtocol
        );

        Notification notification = buildNotification(
                REQUEST_PERMISSIONS_CHANNEL,
                context.getResources().getString(R.string.permissions_notification_title),
                context.getResources().getString(R.string.permissions_notification_body),
                pendingIntent
        );

        notificationManager.notify(REQUEST_PERMISSIONS_NOTIFICATION_ID, notification);
    }

    public Notification getNotificationForRecordingService() {
        setupNotificationChannelIfNeeded(
                SENSOR_RECORDING_CHANNEL,
                context.getResources().getString(R.string.sensoring_channel_description)
        );

        Notification notification = buildNotification(
                SENSOR_RECORDING_CHANNEL,
                context.getResources().getString(R.string.sensoring_notification_title),
                context.getResources().getString(R.string.sensoring_notification_body),
                null
        );

        return notification;
    }

    public int getRecordingServiceNotificationId() {
        return SENSOR_RECORDING_NOTIFICATION_ID;
    }

    private void setupNotificationChannelIfNeeded(String id, String name) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager.getNotificationChannel(id) != null) {
                return;
            }

            NotificationChannel channel = new NotificationChannel(
                    id,
                    name,
                    NotificationManager.IMPORTANCE_HIGH
            );

            // channel.enableVibration(true);

            notificationManager.createNotificationChannel(channel);
        }
    }

    private Notification buildNotification(
            String channelId,
            String title,
            String text,
            PendingIntent pendingIntent
    ) {
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, channelId)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});

        if (pendingIntent != null) {
            notificationBuilder.setContentIntent(pendingIntent);
            notificationBuilder.setAutoCancel(true);
        }

        return notificationBuilder.build();
    }
}
