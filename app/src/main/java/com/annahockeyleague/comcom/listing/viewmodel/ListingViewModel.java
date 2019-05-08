package com.annahockeyleague.comcom.listing.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.annahockeyleague.comcom.ComComApplication;
import com.annahockeyleague.comcom.login.viewmodel.LoginHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
                listingInterface.onEventsFailed(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s=new String(response.body().bytes());
                Log.e("events", " " + s);
                try {
                    JsonElement parse = new JsonParser().parse(s);
                    if (parse.isJsonArray()) {
                        JsonArray parser = parse.getAsJsonArray();
                        listingInterface.onEventsLoaded(parser);
                    }else {
                        listingInterface.onEventsFailed(s);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    listingInterface.onEventsFailed(s);
                }
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
                listingInterface.onInfoFailed(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s=new String(response.body().bytes());
                Log.e("jenison1"," "+s);
                try {
                    JsonElement parse = new JsonParser().parse(s);
                    if (parse.isJsonArray()) {
                        JsonArray parser = parse.getAsJsonArray();
                        listingInterface.onInfoloaded(parser);
                    }else {
                        listingInterface.onInfoFailed(s);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    listingInterface.onInfoFailed(s);
                }
            }
        });
    }
}
