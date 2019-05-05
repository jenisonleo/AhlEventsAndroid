package com.annahockeyleague.comcom.login.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.annahockeyleague.comcom.ComComApplication;
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

public class LoginViewModel extends ViewModel {

    OkHttpClient okhttpClient;
    String dns= ComComApplication.dns;
    private LoginInterface loginInterface;
    private LoginHandler loginHandler;

    public LoginViewModel(LoginInterface loginInterface,LoginHandler loginHandler) {
        this.loginInterface = loginInterface;
        this.loginHandler = loginHandler;
        okhttpClient=new OkHttpClient();
    }


    public void doRegistration(String fullName,String userID,String email, String password){
        String registerApi="/api/register";
        RequestBody requestBody = new FormBody.Builder()
                .add("fullname", fullName)
                .add("username",userID)
                .add("email",email)
                .add("password",password)
                .build();

        Request request = new Request.Builder().url(dns + registerApi).post(requestBody).build();
        okhttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                loginInterface.registrationError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s=new String(response.body().bytes());
                JsonParser parser=new JsonParser();
                try {
                    JsonObject parse = parser.parse(s).getAsJsonObject();
                    if(parse.has("token")){
                        parseResponse(parse);
                        loginInterface.onRegistered();
                    }else {
                        loginInterface.registrationError(s);
                    }
                }catch (Exception e){
                    loginInterface.registrationError(s);
                }
            }
        });
    }

    public void doLogin(String username, String password) {
        String loginApi="/api/login";
        RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("password",password)
                .build();
        Request request = new Request.Builder().url(dns + loginApi).post(requestBody).build();
        okhttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                loginInterface.loginError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s=new String(response.body().bytes());
                JsonParser parser=new JsonParser();
                try {
                    JsonObject parse = parser.parse(s).getAsJsonObject();
                    if(parse.has("token")){
                        parseResponse(parse);
                        loginInterface.onLoggedIn();
                    }else {
                        loginInterface.loginError(s);
                    }
                }catch (Exception e){
                    loginInterface.loginError(s);
                }
            }
        });
    }

    private void parseResponse(JsonObject parse) throws Exception{
        String token = parse.get("token").getAsString();
        String email = parse.get("email").getAsString();
        String username = parse.get("name").getAsString();
        boolean isAdmin = parse.get("isAdmin").getAsBoolean();
        loginHandler.updateLoginToken(token);
        loginHandler.updateEmail(email);
        loginHandler.updateUsername(username);
        loginHandler.updateIsAdmin(isAdmin);
    }
}
