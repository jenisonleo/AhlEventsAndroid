package com.annahockeyleague.comcom.admin.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.annahockeyleague.comcom.ComComApplication;
import com.annahockeyleague.comcom.login.viewmodel.LoginHandler;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AdminViewModel extends ViewModel {

    private LoginHandler loginHandler;
    private AdminInterface adminInterface;
    private OkHttpClient okhttpClient;
    String dns= ComComApplication.dns;


    public AdminViewModel(LoginHandler loginHandler,AdminInterface adminInterface){
        this.loginHandler = loginHandler;
        this.adminInterface = adminInterface;
        okhttpClient=new OkHttpClient();

    }
    public void addEvent(String title, String description,String place,long fromTime,long toTime) {
        String registerApi="/api/event";
        RequestBody requestBody = new FormBody.Builder()
                .add("title", title)
                .add("description",description)
                .add("place",place)
                .add("fromDate",String.valueOf(fromTime))
                .add("toDate",String.valueOf(toTime))
                .build();
        Request request = new Request.Builder().url(dns + registerApi).addHeader("Authorization",loginHandler.getLoginToken()).post(requestBody).build();
        okhttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                adminInterface.addEventError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s=new String(response.body().bytes());
                Log.e("events", " " + s);
                try {
                    JsonObject parse = new JsonParser().parse(s).getAsJsonObject();
                    if(parse.has("message")) {
                        String message = parse.get("message").getAsString();
                        if (message.equals("event added successfully")) {
                            adminInterface.addEventSuccessful();
                        } else {
                            adminInterface.addEventError(message);
                        }
                    }else {
                        adminInterface.addEventError(s);
                    }
                }catch (Exception e){
                    adminInterface.addEventError(s);
                }
            }
        });
    }

    public void addInfo(String title, String description){
        String registerApi="/api/info";
        RequestBody requestBody = new FormBody.Builder()
                .add("title", title)
                .add("description",description)
                .build();
        Request request = new Request.Builder().url(dns + registerApi).addHeader("Authorization",loginHandler.getLoginToken()).post(requestBody).build();
        okhttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                adminInterface.addInfoError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s=new String(response.body().bytes());
                Log.e("events", " " + s);
                try {
                    JsonObject parse = new JsonParser().parse(s).getAsJsonObject();
                    if(parse.has("message")) {
                        String message = parse.get("message").getAsString();
                        if (message.equals("info added successfully")) {
                            adminInterface.addInfoSuccessful();
                        } else {
                            adminInterface.addInfoError(message);
                        }
                    }else {
                        adminInterface.addInfoError(s);
                    }
                }catch (Exception e){
                    adminInterface.addInfoError(s);
                }
            }
        });

    }

}
