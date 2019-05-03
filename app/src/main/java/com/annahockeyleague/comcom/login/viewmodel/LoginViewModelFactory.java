package com.annahockeyleague.comcom.login.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class LoginViewModelFactory implements ViewModelProvider.Factory {
    private LoginInterface loginInterface;
    private LoginHandler loginHandler;

    public LoginViewModelFactory(LoginInterface loginInterface,LoginHandler loginHandler){

        this.loginInterface = loginInterface;
        this.loginHandler = loginHandler;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            Constructor<T> constructor = modelClass.getConstructor(LoginInterface.class,LoginHandler.class);
            return constructor.newInstance(loginInterface,loginHandler);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
