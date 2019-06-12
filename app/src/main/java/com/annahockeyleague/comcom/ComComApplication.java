package com.annahockeyleague.comcom;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.annahockeyleague.comcom.login.viewmodel.LoginHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class ComComApplication extends Application {

    public static final String dns="https://ahlcomcom.herokuapp.com";
    private LoginHandler loginHandler;
    @Override
    public void onCreate() {
        super.onCreate();
        loginHandler=new LoginHandler(this);
        doNotificationRegistration();
    }

    private void doNotificationRegistration(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e("failed", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        // Log and toast
                        Log.e("jow", token);
                    }
                });
    }



    public LoginHandler getLoginHandler(){
        return loginHandler;
    }
}
