package com.annahockeyleague.comcom.messaging;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class AhlFirebaseService extends FirebaseMessagingService {


    @Override
    public void onNewToken(String s) {
        Log.e("token "," "+s);

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("message "," "+remoteMessage.getData().get("message"));
    }

    @Override
    public void onDeletedMessages() {
        //register with sever
    }
}
