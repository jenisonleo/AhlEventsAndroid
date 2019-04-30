package com.annahockeyleague.comcom.login.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class LoginHandler {

    private static final String loginDiectory="loginDirectory";//NO I18N
    private static final String loginToken="loginToken";//NO I18N
    private SharedPreferences loginPreference;

    public LoginHandler(Context context){
        loginPreference=context.getSharedPreferences(loginDiectory,Context.MODE_PRIVATE);
    }
    public boolean isUserLoggedIn(){
        return loginPreference.getString(loginToken, null)!=null;
    }

    public String getLoginToken(){
        if(loginPreference.getString(loginToken, null)==null){
            throw new RuntimeException("user not logged in");
        }
        return loginPreference.getString(loginToken, null);
    }

    @SuppressLint("ApplySharedPref")
    public boolean updateLoginToken(String token){
        SharedPreferences.Editor editor = loginPreference.edit();
        editor.putString(loginToken, token);
        return editor.commit();
    }
}
