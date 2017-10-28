package com.alexandergor.uvapp;

import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class UVSFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        RemoteMessage.Notification remoteNotification = remoteMessage.getNotification();

        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_notifications_icon)
            .setContentTitle(remoteNotification.getTitle())
            .setContentText(remoteNotification.getBody());

        NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        int nId = 001;
        nManager.notify(nId, nBuilder.build());
    }
}
