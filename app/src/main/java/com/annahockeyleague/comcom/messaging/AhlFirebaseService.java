package com.annahockeyleague.comcom.messaging;

import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.annahockeyleague.comcom.ComComApplication;
import com.annahockeyleague.comcom.R;
import com.annahockeyleague.comcom.login.LoginActvity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class AhlFirebaseService extends FirebaseMessagingService {
    public static final String AHL_CHANNEL="ahlchannel";
    private int id=0;

    @Override
    public void onNewToken(String s) {
        ((ComComApplication) getApplicationContext()).doNotificationRegistration();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
            if(((ComComApplication) getApplicationContext()).getLoginHandler().isUserLoggedIn()) {
                String custom= remoteMessage.getData().get("custom");
                if(custom!=null){
                    Log.e("message ", " " + custom);
                }else {
                    String action = remoteMessage.getData().get("action");
                    String title = remoteMessage.getData().get("title");
                    String message = remoteMessage.getData().get("message");
                    String notiTitle;
                    switch (action) {
                        case "new_info":
                            notiTitle = "You have a new Information";
                            break;
                        case "new_event":
                            notiTitle = "You have a new Event";
                        default:
                            notiTitle = "You have a new Notification";
                    }
                    NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(getApplicationContext(), AHL_CHANNEL);
                    notiBuilder.setSmallIcon(R.drawable.small_icon);
                    notiBuilder.setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL);
                    notiBuilder.setContentTitle(notiTitle);
                    notiBuilder.setContentText(title);
                    notiBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    if (message != null && title != null) {
                        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
                        bigTextStyle.setBigContentTitle(title);
                        bigTextStyle.bigText(message);
                        notiBuilder.setStyle(bigTextStyle);
                    }
                    Intent intent = new Intent(this, LoginActvity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pendingAction = PendingIntent.getActivity(this, 999, intent, 0);
                    notiBuilder.setContentIntent(pendingAction);
                    notiBuilder.setAutoCancel(true);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

                    notificationManager.notify(id, notiBuilder.build());
                    id++;
                    Log.e("message ", " " + remoteMessage.getData().get("message"));
                }
            }else {
                ((ComComApplication) getApplicationContext()).doDegistration();
            }

    }
}
