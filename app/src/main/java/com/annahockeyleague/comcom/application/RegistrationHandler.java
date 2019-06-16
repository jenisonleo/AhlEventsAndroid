package com.annahockeyleague.comcom.application;

import com.annahockeyleague.comcom.ComComApplication;
import com.annahockeyleague.comcom.login.viewmodel.LoginHandler;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegistrationHandler {
     private OkHttpClient okhttpClient;
    private LoginHandler loginHandler;
    String dns= ComComApplication.dns;

    public RegistrationHandler(LoginHandler loginHandler){
        this.loginHandler = loginHandler;
        okhttpClient = new OkHttpClient();
    }


    public void doNotificationegistration(String deviceToken){
        if(loginHandler.isUserLoggedIn() && deviceToken!=null){
            String loginApi="/notifications/register";
            RequestBody requestBody = new FormBody.Builder()
                    .add("devicetoken", deviceToken)
                    .add("devicetype","android")
                    .build();
            Request request=new Request.Builder().url(dns+loginApi).addHeader("Authorization",loginHandler.getLoginToken()).post(requestBody).build();
            okhttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String s = new String(response.body().bytes());
                    try {
                        JsonElement parse = new JsonParser().parse(s);
                        if (parse.isJsonObject()) {
                            String message = parse.getAsJsonObject().get("message").getAsString();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void doDeregistration(String deviceToken){

    }
}
