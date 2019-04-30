package com.annahockeyleague.comcom.login;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.annahockeyleague.comcom.ComComApplication;
import com.annahockeyleague.comcom.R;
import com.annahockeyleague.comcom.login.viewmodel.LoginInterface;
import com.annahockeyleague.comcom.login.viewmodel.LoginViewModel;
import com.annahockeyleague.comcom.login.viewmodel.LoginViewModelFactory;

public class LoginFragment extends Fragment implements LoginInterface {

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
        LoginViewModel loginViewModel = ViewModelProviders.of(this,new LoginViewModelFactory(this)).get(LoginViewModel.class);
        view.findViewById(R.id.sign_up).setOnClickListener(v->{
            String name=((TextView)view.findViewById(R.id.full_name)).getText().toString();
            String userId=((TextView)view.findViewById(R.id.user_id)).getText().toString();
            String email=((TextView)view.findViewById(R.id.email_id)).getText().toString();
            String password=((TextView)view.findViewById(R.id.password)).getText().toString();
            loginViewModel.doRegistration(name,userId,email,password);
        });
        view.findViewById(R.id.sign_in).setOnClickListener(v->{
            String username=((TextView)view.findViewById(R.id.login_username)).getText().toString();
            String password=((TextView)view.findViewById(R.id.login_password)).getText().toString();
            loginViewModel.doLogin(username,password);
        });
        view.findViewById(R.id.sign_up_viewflipper).setOnClickListener(v->{
            ((ViewFlipper)view.findViewById(R.id.login_flipper)).setDisplayedChild(1);
        });
    }

    @Override
    public void onLoggedIn(String token) {
        if(getContext()!=null &&getContext().getApplicationContext()!=null) {
            ((ComComApplication) getContext().getApplicationContext()).getLoginHandler().updateLoginToken(token);

        }
        Log.e("s"," "+token);
        if(getActivity()!=null) {
            ((LoginActvity) getActivity()).launchListing();
        }
    }
}
