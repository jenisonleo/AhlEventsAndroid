package com.annahockeyleague.comcom.listing.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.annahockeyleague.comcom.ComComApplication;
import com.annahockeyleague.comcom.login.viewmodel.LoginHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListingViewModel extends ViewModel {


    String dns= ComComApplication.dns;
    private LoginHandler loginHandler;
    private ListingInterface listingInterface;
    OkHttpClient okhttpClient;


    public ListingViewModel(LoginHandler loginHandler,ListingInterface listingInterface){
        this.loginHandler = loginHandler;
        this.listingInterface = listingInterface;
        okhttpClient=new OkHttpClient();
        fetchEvents();
        fetchInfo();
    }


    public void fetchEvents(){
        String api="/api/events";
        Log.e("s"," "+loginHandler.getLoginToken());
        Request request = new Request.Builder().url(dns + api).addHeader("Authorization",loginHandler.getLoginToken()).build();
        okhttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s=new String(response.body().bytes());
                Log.e("jenison"," "+s);JsonArray parser=new JsonParser().parse(s).getAsJsonArray();
                listingInterface.onEventsLoaded(parser);
            }
        });
    }

    public void fetchInfo(){
        String api="/api/infos";
        Request request = new Request.Builder().url(dns + api).addHeader("Authorization",loginHandler.getLoginToken()).build();
        okhttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s=new String(response.body().bytes());
                Log.e("jenison1"," "+s);
                JsonArray parser=new JsonParser().parse(s).getAsJsonArray();
                listingInterface.onInfoloaded(parser);
            }
        });
    }
}
