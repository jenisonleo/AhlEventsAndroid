package com.annahockeyleague.comcom;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.annahockeyleague.comcom.application.RegistrationHandler;
import com.annahockeyleague.comcom.login.viewmodel.LoginHandler;
import com.annahockeyleague.comcom.messaging.AhlFirebaseService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class ComComApplication extends Application {

    public static final String dns="https://ahlcomcom1.herokuapp.com";
    private LoginHandler loginHandler;
    private RegistrationHandler registrationHandler;
    @Override
    public void onCreate() {
        super.onCreate();
        loginHandler=new LoginHandler(this);
        registrationHandler=new RegistrationHandler(loginHandler);
        doNotificationRegistration();
        createNotificationChannel();
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channelname_general);
            String description = getString(R.string.channeldescription_general);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(AhlFirebaseService.AHL_CHANNEL, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void doNotificationRegistration(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e("failed", "getInstanceId failed", task.getException());
                            return;
                        }
                        InstanceIdResult result = task.getResult();
                        if(result!=null) {
                            String token = result.getToken();
                            registrationHandler.doNotificationegistration(token);
                        }
                    }
                });
    }



    public LoginHandler getLoginHandler(){
        return loginHandler;
    }
    public void doDegistration(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e("failed", "getInstanceId failed", task.getException());
                            return;
                        }
                        InstanceIdResult result = task.getResult();
                        if(result!=null) {
                            String token = result.getToken();
                            registrationHandler.doDeregistration(token);
                        }
                    }
                });
    }
}
