package com.annahockeyleague.comcom.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.annahockeyleague.comcom.ComComApplication;
import com.annahockeyleague.comcom.R;
import com.annahockeyleague.comcom.utils.BackPressListener;
import com.annahockeyleague.comcom.login.viewmodel.LoginInterface;
import com.annahockeyleague.comcom.login.viewmodel.LoginViewModel;
import com.annahockeyleague.comcom.login.viewmodel.LoginViewModelFactory;
import com.dx.dxloadingbutton.lib.LoadingButton;

public class LoginFragment extends Fragment implements LoginInterface, BackPressListener {

    public static String tag="LoginFragment";//NO I18N
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LoginViewModel loginViewModel = ViewModelProviders.of(this,new LoginViewModelFactory(this,((ComComApplication)getContext().getApplicationContext()).getLoginHandler())).get(LoginViewModel.class);
        view.findViewById(R.id.sign_up).setOnClickListener(v->{
            String name=((TextView)view.findViewById(R.id.full_name)).getText().toString();
            String userId=((TextView)view.findViewById(R.id.user_id)).getText().toString();
            String email=((TextView)view.findViewById(R.id.email_id)).getText().toString();
            String password=((TextView)view.findViewById(R.id.password)).getText().toString();
            ((LoadingButton)v).startLoading();
            loginViewModel.doRegistration(name,userId,email,password);
        });
        view.findViewById(R.id.sign_in).setOnClickListener(v->{
            String username=((TextView)view.findViewById(R.id.login_username)).getText().toString();
            String password=((TextView)view.findViewById(R.id.login_password)).getText().toString();
            ((LoadingButton)v).startLoading();
            loginViewModel.doLogin(username,password);
        });
        view.findViewById(R.id.sign_up_viewflipper).setOnClickListener(v->{
            ((ViewFlipper)view.findViewById(R.id.login_flipper)).setDisplayedChild(1);
        });
    }

    @Override
    public void onLoggedIn() {
        getActivity().runOnUiThread(()->{
            if(getActivity()!=null) {
                ((LoginActvity) getActivity()).launchListing();
            }
        });
    }

    @Override
    public void onRegistered() {
        getActivity().runOnUiThread(()->{
            if(getActivity()!=null) {
                ((LoginActvity) getActivity()).launchListing();
            }
        });
    }

    @Override
    public void registrationError(String message) {
        getActivity().runOnUiThread(()->{
            if(getActivity()!=null) {
                ((LoadingButton)getView().findViewById(R.id.sign_up)).loadingFailed();
                Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void loginError(String message) {
        getActivity().runOnUiThread(()->{
            if(getActivity()!=null) {
                ((LoadingButton)getView().findViewById(R.id.sign_in)).loadingFailed();
                Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        if(((ViewFlipper)getView().findViewById(R.id.login_flipper)).getDisplayedChild()==1){
            ((ViewFlipper)getView().findViewById(R.id.login_flipper)).setDisplayedChild(0);
            return true;
        }
        return false;
    }
}
