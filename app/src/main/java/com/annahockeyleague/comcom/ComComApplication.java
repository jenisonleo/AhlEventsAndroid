package com.annahockeyleague.comcom;

import android.app.Application;

import com.annahockeyleague.comcom.login.viewmodel.LoginHandler;

public class ComComApplication extends Application {

    public static final String dns="http://172.22.137.124:8002";
    private LoginHandler loginHandler;
    @Override
    public void onCreate() {
        super.onCreate();
        loginHandler=new LoginHandler(this);
    }



    public LoginHandler getLoginHandler(){
        return loginHandler;
    }
}
