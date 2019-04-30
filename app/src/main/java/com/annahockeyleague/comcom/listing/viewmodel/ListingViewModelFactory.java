package com.annahockeyleague.comcom.listing.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.annahockeyleague.comcom.login.viewmodel.LoginHandler;
import com.annahockeyleague.comcom.login.viewmodel.LoginInterface;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ListingViewModelFactory implements ViewModelProvider.Factory {

    private LoginHandler loginHandler;
    private ListingInterface listingInterface;

    public ListingViewModelFactory(LoginHandler loginHandler,ListingInterface listingInterface){

        this.loginHandler = loginHandler;
        this.listingInterface = listingInterface;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            Constructor<T> constructor = modelClass.getConstructor(LoginHandler.class,ListingInterface.class);
            return constructor.newInstance(loginHandler,listingInterface);
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
