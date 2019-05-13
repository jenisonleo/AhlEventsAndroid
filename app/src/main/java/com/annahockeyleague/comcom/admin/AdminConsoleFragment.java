package com.annahockeyleague.comcom.admin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.annahockeyleague.comcom.ComComApplication;
import com.annahockeyleague.comcom.R;
import com.annahockeyleague.comcom.admin.viewmodel.AdminInterface;
import com.annahockeyleague.comcom.admin.viewmodel.AdminViewModel;
import com.annahockeyleague.comcom.admin.viewmodel.AdminViewModelFactory;
import com.annahockeyleague.comcom.utils.BackPressListener;
import com.dx.dxloadingbutton.lib.LoadingButton;

import java.util.Calendar;

public class AdminConsoleFragment extends Fragment implements AdminInterface, BackPressListener {

    public static String adminConsoleFragment="AdminConsoleFragment";//NO I18N

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.admin_fragment,container,false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(android.R.id.home==item.getItemId()){
            handleBackPress();
            return true;
        }
        return false;
    }

    @Override
    public boolean onBackPressed() {
        return handleBackPress();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        AdminViewModel adminViewModel = ViewModelProviders.of(this, new AdminViewModelFactory(((ComComApplication) getContext().getApplicationContext()).getLoginHandler(),this)).get(AdminViewModel.class);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        view.findViewById(R.id.edit_info).setOnClickListener(v->{
            Toast.makeText(getContext(),"Coming soon",Toast.LENGTH_LONG).show();
        });
        view.findViewById(R.id.edit_event).setOnClickListener(v->{
            Toast.makeText(getContext(),"Coming soon",Toast.LENGTH_LONG).show();
        });
        view.findViewById(R.id.new_event).setOnClickListener(v->{
            //reset all views
            ((TextView)view.findViewById(R.id.from_date_picker)).setText("From Date");
            ((TextView)view.findViewById(R.id.to_date_picker)).setText("To Date");
            ((TextView)view.findViewById(R.id.from_time_picker)).setText("From Time");
            ((TextView)view.findViewById(R.id.to_time_picker)).setText("To Time");
            ((EditText)view.findViewById(R.id.event_title_editor)).setText("");
            ((EditText)view.findViewById(R.id.event_description_editor)).setText("");
            ((EditText)view.findViewById(R.id.event_place_editor)).setText("");
            ((LoadingButton)view.findViewById(R.id.submit_event)).reset();

            ((ViewFlipper)view.findViewById(R.id.admin_flipper)).setDisplayedChild(2);
        });

        view.findViewById(R.id.from_date_picker).setOnClickListener(v->{
            Calendar c = Calendar.getInstance();
            DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker v, int year, int month, int dayOfMonth) {
                    ((TextView)view.findViewById(R.id.from_date_picker)).setText(dayOfMonth+"-"+month+"-"+year);
                }
            },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });
        view.findViewById(R.id.to_date_picker).setOnClickListener(v->{
            Calendar c = Calendar.getInstance();
            DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker v, int year, int month, int dayOfMonth) {
                    ((TextView)view.findViewById(R.id.to_date_picker)).setText(dayOfMonth+"-"+month+"-"+year);
                }
            },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });
        view.findViewById(R.id.from_time_picker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                TimePickerDialog timePickerDialog=new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker v, int hourOfDay, int minute) {
                        ((TextView)view.findViewById(R.id.from_time_picker)).setText(hourOfDay+"-"+minute);
                    }
                },c.get(Calendar.HOUR),c.get(Calendar.MINUTE),true);
                timePickerDialog.show();
            }
        });
        view.findViewById(R.id.to_time_picker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                TimePickerDialog timePickerDialog=new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker v, int hourOfDay, int minute) {
                        ((TextView)view.findViewById(R.id.to_time_picker)).setText(hourOfDay+"-"+minute);
                    }
                },c.get(Calendar.HOUR),c.get(Calendar.MINUTE),true);
                timePickerDialog.show();
            }
        });
        view.findViewById(R.id.submit_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int l1=((EditText)view.findViewById(R.id.event_title_editor)).getText().toString().length();
                int l2=((EditText)view.findViewById(R.id.event_description_editor)).getText().toString().length();
                int l3=((EditText)view.findViewById(R.id.event_place_editor)).getText().toString().length();
                String fromDate=((TextView)view.findViewById(R.id.from_date_picker)).getText().toString();
                String toDate=((TextView)view.findViewById(R.id.to_date_picker)).getText().toString();
                String fromTime=((TextView)view.findViewById(R.id.from_time_picker)).getText().toString();
                String toTime=((TextView)view.findViewById(R.id.to_time_picker)).getText().toString();
                if(l1>0 && l2>0 && l3>0 && fromDate.contains("-") && toDate.contains("-") && fromTime.contains("-") && toTime.contains("-")) {
                    ((LoadingButton)getView().findViewById(R.id.submit_event)).startLoading();
                    String[] fromdatesplit = fromDate.split("-");
                    String[] fromtimesplit = fromTime.split("-");
                    Calendar from = Calendar.getInstance();
                    from.set(Integer.parseInt(fromdatesplit[2]),Integer.parseInt(fromdatesplit[1]),Integer.parseInt(fromdatesplit[0]),Integer.parseInt(fromtimesplit[0]),Integer.parseInt(fromtimesplit[1]));
                    String[] todatesplit = toDate.split("-");
                    String[] totimesplit = toTime.split("-");
                    Calendar to = Calendar.getInstance();
                    to.set(Integer.parseInt(todatesplit[2]),Integer.parseInt(todatesplit[1]),Integer.parseInt(todatesplit[0]),Integer.parseInt(totimesplit[0]),Integer.parseInt(totimesplit[1]));
                    adminViewModel.addEvent(((EditText)view.findViewById(R.id.event_title_editor)).getText().toString(),
                            ((EditText)view.findViewById(R.id.event_description_editor)).getText().toString(),
                            ((EditText)view.findViewById(R.id.event_place_editor)).getText().toString(),
                            from.getTimeInMillis(),to.getTimeInMillis());
                }else {
                    Toast.makeText(getContext(),"Data cannot be empty",Toast.LENGTH_SHORT).show();
                }
            }
        });





        view.findViewById(R.id.new_info).setOnClickListener(v->{
            //reset all views
            ((EditText)view.findViewById(R.id.info_title_editor)).setText("");
            ((EditText)view.findViewById(R.id.info_description_editor)).setText("");
            ((LoadingButton)view.findViewById(R.id.submit_info)).reset();

            ((ViewFlipper)view.findViewById(R.id.admin_flipper)).setDisplayedChild(1);
        });
        view.findViewById(R.id.submit_info).setOnClickListener(v->{
            int l1=((EditText)view.findViewById(R.id.info_title_editor)).getText().toString().length();
            int l2=((EditText)view.findViewById(R.id.info_description_editor)).getText().toString().length();

            if(l1>0 && l2>0){
                ((LoadingButton)v).startLoading();
                adminViewModel.addInfo(((EditText)view.findViewById(R.id.info_title_editor)).getText().toString(),((EditText)view.findViewById(R.id.info_description_editor)).getText().toString());
            }else {
                Toast.makeText(getContext(),"Data cannot be empty",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean handleBackPress(){
        ViewFlipper flipper=getView().findViewById(R.id.admin_flipper);
        if (flipper.getDisplayedChild()==1 || flipper.getDisplayedChild()==2){
            flipper.setDisplayedChild(0);
            return true;
        }
        return false;
    }

    @Override
    public void addInfoSuccessful() {
        getActivity().runOnUiThread(()->{
            ((LoadingButton)getView().findViewById(R.id.submit_info)).loadingSuccessful();
        });
    }

    @Override
    public void addInfoError(String errorMessage) {
        getActivity().runOnUiThread(()->{
            ((LoadingButton)getView().findViewById(R.id.submit_info)).loadingFailed();
            Toast.makeText(getContext(),errorMessage,Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void addEventSuccessful() {
        getActivity().runOnUiThread(()->{
            ((LoadingButton)getView().findViewById(R.id.submit_event)).loadingSuccessful();
        });
    }

    @Override
    public void addEventError(String errorMessage) {
        getActivity().runOnUiThread(()->{
            ((LoadingButton)getView().findViewById(R.id.submit_event)).loadingFailed();
            Toast.makeText(getContext(),errorMessage,Toast.LENGTH_LONG).show();
        });
    }


}
