package com.annahockeyleague.comcom.login.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.annahockeyleague.comcom.login.LoginActvity;

import java.util.function.BiConsumer;

public class LoginHandler {

    private static final String loginDiectory="loginDirectory";//NO I18N
    private static final String loginToken="loginToken";//NO I18N
    private static final String isAdmin="isAdmin";//NO I18N
    private static final String userName="userName";//NO I18N
    private static final String email="email";//NO I18N
    private SharedPreferences loginPreference;

    public LoginHandler(Context context){
        loginPreference=context.getSharedPreferences(loginDiectory,Context.MODE_PRIVATE);
    }
    public boolean isUserLoggedIn(){
        return loginPreference.getString(LoginHandler.loginToken, null)!=null;
    }

    public String getLoginToken(){
        if(loginPreference.getString(LoginHandler.loginToken, null)==null){
            throw new RuntimeException("user not logged in");
        }
        return loginPreference.getString(LoginHandler.loginToken, null);
    }

    @SuppressLint("ApplySharedPref")
    public boolean updateLoginToken(String token){
        SharedPreferences.Editor editor = loginPreference.edit();
        editor.putString(LoginHandler.loginToken, token);
        return editor.commit();
    }

    public boolean isAdmin(){
        return loginPreference.getBoolean(LoginHandler.isAdmin, false);
    }

    public boolean updateIsAdmin(boolean isAdmin){
        SharedPreferences.Editor editor = loginPreference.edit();
        editor.putBoolean(LoginHandler.isAdmin, isAdmin);
        return editor.commit();
    }

    public String getUserName(){
        return loginPreference.getString(LoginHandler.userName, null);
    }

    public boolean updateUsername(String userName){
        SharedPreferences.Editor editor = loginPreference.edit();
        editor.putString(LoginHandler.userName, userName);
        return editor.commit();
    }

    public String getEmailId(){
        return loginPreference.getString(LoginHandler.email, null);
    }

    public boolean updateEmail(String email){
        SharedPreferences.Editor editor = loginPreference.edit();
        editor.putString(LoginHandler.email, email);
        return editor.commit();
    }

    public void logoutAction(){
        updateLoginToken(null);
        updateEmail(null);
        updateIsAdmin(false);
        updateUsername(null);
    }



}
