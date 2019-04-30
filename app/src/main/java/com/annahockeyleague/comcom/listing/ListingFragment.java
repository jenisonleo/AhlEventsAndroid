package com.annahockeyleague.comcom.listing;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annahockeyleague.comcom.ComComApplication;
import com.annahockeyleague.comcom.R;
import com.annahockeyleague.comcom.listing.viewmodel.ListingInterface;
import com.annahockeyleague.comcom.listing.viewmodel.ListingViewModel;
import com.annahockeyleague.comcom.listing.viewmodel.ListingViewModelFactory;
import com.annahockeyleague.comcom.login.LoginActvity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonArray;

public class ListingFragment extends Fragment implements ListingInterface {
    public static String tag="ListingFragment";//NO I18N

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        if(getContext()!=null &&getContext().getApplicationContext()!=null) {
            ListingViewModel listingViewModel = ViewModelProviders.of(this, new ListingViewModelFactory(((ComComApplication) getContext().getApplicationContext()).getLoginHandler(),this)).get(ListingViewModel.class);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.listing_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((BottomNavigationView)view.findViewById(R.id.bottom_navigation)).setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_info:{
                        ((ViewFlipper)view.findViewById(R.id.data_flipper)).setDisplayedChild(0);
                        break;
                    }
                    case R.id.action_events:{
                        ((ViewFlipper)view.findViewById(R.id.data_flipper)).setDisplayedChild(1);
                        break;
                    }
                    default:
                        break;
                }
                return true;
            }
        });
        InfoAdapter infoAdapter=new InfoAdapter();
        ((RecyclerView)view.findViewById(R.id.info_recyclerview)).setLayoutManager(new LinearLayoutManager(getContext()));
        ((RecyclerView)view.findViewById(R.id.info_recyclerview)).setAdapter(infoAdapter);
        EventsAdapter eventsAdapter=new EventsAdapter();
        ((RecyclerView)view.findViewById(R.id.event_recyclerview)).setLayoutManager(new LinearLayoutManager(getContext()));
        ((RecyclerView)view.findViewById(R.id.event_recyclerview)).setAdapter(eventsAdapter);
        if(((BottomNavigationView)view.findViewById(R.id.bottom_navigation)).getSelectedItemId()==R.id.action_info){
            ((ViewFlipper)view.findViewById(R.id.data_flipper)).setDisplayedChild(0);
        }else {
            ((ViewFlipper)view.findViewById(R.id.data_flipper)).setDisplayedChild(1);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.listing_context_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:{
                break;
            }
            case R.id.logout:{
                if(getContext()!=null &&getContext().getApplicationContext()!=null && getActivity()!=null) {
                    ((ComComApplication) getContext().getApplicationContext()).getLoginHandler().updateLoginToken(null);
                    Intent intent=new Intent(getContext(), LoginActvity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                break;
            }
            default:
                break;
        }
        return true;
    }

    @Override
    public void onEventsLoaded(JsonArray data) {
        getActivity().runOnUiThread(() -> {
            if (getView() != null){
                ((EventsAdapter)((RecyclerView) getView().findViewById(R.id.event_recyclerview)).getAdapter()).data=data;
                ((RecyclerView) getView().findViewById(R.id.event_recyclerview)).getAdapter().notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onInfoloaded(JsonArray data) {
        getActivity().runOnUiThread(() -> {
            if (getView() != null){
                ((InfoAdapter)((RecyclerView) getView().findViewById(R.id.info_recyclerview)).getAdapter()).data=data;
                ((RecyclerView) getView().findViewById(R.id.info_recyclerview)).getAdapter().notifyDataSetChanged();
            }
        });
    }
}
