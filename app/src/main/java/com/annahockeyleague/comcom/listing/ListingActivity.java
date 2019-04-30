package com.annahockeyleague.comcom.listing;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.annahockeyleague.comcom.R;

public class ListingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listing_activity);
        if(getSupportFragmentManager().findFragmentByTag(ListingFragment.tag)==null){
            ListingFragment listingFragment=new ListingFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.container,listingFragment,ListingFragment.tag).commit();
        }
    }
}
