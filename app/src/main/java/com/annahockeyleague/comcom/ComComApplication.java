package com.annahockeyleague.comcom;

import android.app.Application;

import com.annahockeyleague.comcom.login.viewmodel.LoginHandler;

public class ComComApplication extends Application {

    public static final String dns="https://ahlcomcom.herokuapp.com";
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
