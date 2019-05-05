package com.annahockeyleague.comcom.login.viewmodel;

public interface LoginInterface {

    public void onLoggedIn();

    public void onRegistered();

    public void registrationError(String message);

    public void loginError(String message);
}
