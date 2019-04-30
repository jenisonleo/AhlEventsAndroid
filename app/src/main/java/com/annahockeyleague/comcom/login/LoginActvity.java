package com.annahockeyleague.comcom.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.annahockeyleague.comcom.ComComApplication;
import com.annahockeyleague.comcom.R;
import com.annahockeyleague.comcom.listing.ListingActivity;

public class LoginActvity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        if(((ComComApplication)getApplication()).getLoginHandler().isUserLoggedIn()){
            launchListing();
        }else {
            setContentView(R.layout.activity_main);
            if(getSupportFragmentManager().findFragmentByTag(LoginFragment.tag)==null){
                LoginFragment loginFragment=new LoginFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.container,loginFragment,LoginFragment.tag).commit();
            }
        }

    }


    public void launchListing(){
        Intent intent=new Intent(this, ListingActivity.class);
        startActivity(intent);
        finish();
    }
}
