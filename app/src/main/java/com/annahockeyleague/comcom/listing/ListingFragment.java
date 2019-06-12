package com.annahockeyleague.comcom.listing;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annahockeyleague.comcom.ComComApplication;
import com.annahockeyleague.comcom.R;
import com.annahockeyleague.comcom.admin.AdminConsoleFragment;
import com.annahockeyleague.comcom.listing.viewmodel.ListingInterface;
import com.annahockeyleague.comcom.listing.viewmodel.ListingViewModel;
import com.annahockeyleague.comcom.listing.viewmodel.ListingViewModelFactory;
import com.annahockeyleague.comcom.login.LoginActvity;
import com.google.android.gms.oss.licenses.OssLicensesActivity;
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;
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
            ViewModelProviders.of(this, new ListingViewModelFactory(((ComComApplication) getContext().getApplicationContext()).getLoginHandler(),this)).get(ListingViewModel.class);
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
                        Animator anim = ViewAnimationUtils.createCircularReveal(view.findViewById(R.id.data_flipper), view.findViewById(R.id.bottom_navigation).getWidth()*1/6, view.findViewById(R.id.data_flipper).getHeight(), 0, view.findViewById(R.id.data_flipper).getHeight());
                        anim.setDuration(300);
                        anim.start();
                        break;
                    }
                    case R.id.action_events:{
                        ((ViewFlipper)view.findViewById(R.id.data_flipper)).setDisplayedChild(1);
                        Animator anim = ViewAnimationUtils.createCircularReveal(view.findViewById(R.id.data_flipper), view.findViewById(R.id.bottom_navigation).getWidth()*3/6, view.findViewById(R.id.data_flipper).getHeight(), 0, view.findViewById(R.id.data_flipper).getHeight());
                        anim.setDuration(300);
                        anim.start();
                        break;
                    }
                    case R.id.action_profile:{
                        ((ViewFlipper)view.findViewById(R.id.data_flipper)).setDisplayedChild(2);
                        Animator anim = ViewAnimationUtils.createCircularReveal(view.findViewById(R.id.data_flipper), view.findViewById(R.id.bottom_navigation).getWidth()*5/6, view.findViewById(R.id.data_flipper).getHeight(), 0, view.findViewById(R.id.data_flipper).getHeight());
                        anim.setDuration(300);
                        anim.start();
                        break;
                    }
                    default:
                        break;
                }
                return true;
            }
        });
        view.findViewById(R.id.sign_out).setOnClickListener(v->{
            if(getContext()!=null &&getContext().getApplicationContext()!=null && getActivity()!=null) {
                ((ComComApplication) getContext().getApplicationContext()).getLoginHandler().logoutAction();
                Intent intent=new Intent(getContext(), LoginActvity.class);
                startActivity(intent);
                getActivity().finish();
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
        }else if(((BottomNavigationView)view.findViewById(R.id.bottom_navigation)).getSelectedItemId()==R.id.action_events) {
            ((ViewFlipper)view.findViewById(R.id.data_flipper)).setDisplayedChild(1);
        }else {
            ((ViewFlipper)view.findViewById(R.id.data_flipper)).setDisplayedChild(2);
        }
        ((TextView)view.findViewById(R.id.user_name_holder)).setText(((ComComApplication) getContext().getApplicationContext()).getLoginHandler().getUserName());
        ((TextView)view.findViewById(R.id.email_holder)).setText(((ComComApplication) getContext().getApplicationContext()).getLoginHandler().getEmailId());
        if(((ComComApplication) getContext().getApplicationContext()).getLoginHandler().isAdmin()){
            view.findViewById(R.id.admin_console).setVisibility(View.VISIBLE);
        }
        view.findViewById(R.id.pivacy_policy).setOnClickListener(v->{
            ((ViewFlipper)view.findViewById(R.id.data_flipper)).setDisplayedChild(3);
            WebView mWebview = (WebView)view.findViewById(R.id.pivacy_policy_webview);
            mWebview.loadUrl("https://comcom.flycricket.io/privacy.html");
//            Dialog dialog=new Dialog(getContext());
//            ScrollView scrollView=new ScrollView(getContext());
//            ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            scrollView.setLayoutParams(params);
//            TextView textView=new TextView(getContext());
//            textView.setText(getResources().getString(R.string.privacy_policy));
//            ViewGroup.LayoutParams params1=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            textView.setLayoutParams(params1);
//            scrollView.addView(textView);
//            dialog.setContentView(scrollView);
//            dialog.show();
        });
        view.findViewById(R.id.aboutus).setOnClickListener(v->{
            Dialog dialog=new Dialog(getContext());
            ScrollView scrollView=new ScrollView(getContext());
            ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            scrollView.setLayoutParams(params);
            TextView textView=new TextView(getContext());
            textView.setText(getResources().getString(R.string.about_us));
            ViewGroup.LayoutParams params1=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            textView.setLayoutParams(params1);
            scrollView.addView(textView);
            dialog.setContentView(scrollView);
            dialog.show();
        });
        view.findViewById(R.id.admin_console).setOnClickListener(v->{
            AdminConsoleFragment adminConsoleFragment=new AdminConsoleFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,adminConsoleFragment,AdminConsoleFragment.adminConsoleFragment).commit();
        });
        view.findViewById(R.id.license).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), OssLicensesActivity.class));
            }
        });
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
                Toast.makeText(getContext(),"Coming Soon",Toast.LENGTH_LONG).show();
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
                getView().findViewById(R.id.event_recyclerview).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.event_load).setVisibility(View.GONE);
                ((EventsAdapter)((RecyclerView) getView().findViewById(R.id.event_recyclerview)).getAdapter()).data=data;
                ((RecyclerView) getView().findViewById(R.id.event_recyclerview)).getAdapter().notifyDataSetChanged();
                if(data.size()==0) {
                    Toast.makeText(getContext(), "No events found", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onEventsFailed(String message) {
        getActivity().runOnUiThread(() -> {
            if (getView() != null){
                getView().findViewById(R.id.event_recyclerview).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.event_load).setVisibility(View.GONE);
                Toast.makeText(getContext(),"Events failed to load "+message,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onInfoloaded(JsonArray data) {
        getActivity().runOnUiThread(() -> {
            if (getView() != null){
                getView().findViewById(R.id.info_recyclerview).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.info_load).setVisibility(View.GONE);
                ((InfoAdapter)((RecyclerView) getView().findViewById(R.id.info_recyclerview)).getAdapter()).data=data;
                ((RecyclerView) getView().findViewById(R.id.info_recyclerview)).getAdapter().notifyDataSetChanged();
                if(data.size()==0) {
                    Toast.makeText(getContext(), "No info found", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onInfoFailed(String message) {
        getActivity().runOnUiThread(() -> {
            if (getView() != null){
                getView().findViewById(R.id.info_recyclerview).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.info_load).setVisibility(View.GONE);
                Toast.makeText(getContext(),"Events failed to load "+message,Toast.LENGTH_LONG).show();
            }
        });
    }
}
