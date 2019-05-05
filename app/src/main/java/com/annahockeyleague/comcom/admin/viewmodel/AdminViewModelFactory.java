package com.annahockeyleague.comcom.admin.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.annahockeyleague.comcom.login.viewmodel.LoginHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class AdminViewModelFactory implements ViewModelProvider.Factory {

    private LoginHandler loginHandler;
    private AdminInterface adminInterface;

    public AdminViewModelFactory(LoginHandler loginHandler,AdminInterface adminInterface){

        this.loginHandler = loginHandler;
        this.adminInterface = adminInterface;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            Constructor<T> constructor = modelClass.getConstructor(LoginHandler.class,AdminInterface.class);
            return constructor.newInstance(loginHandler,adminInterface);
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
