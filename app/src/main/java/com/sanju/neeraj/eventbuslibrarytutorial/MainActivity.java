package com.sanju.neeraj.eventbuslibrarytutorial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;

import com.sanju.androidconnectivity.Connectivity;
import com.sanju.androidconnectivity.Events;
import com.sanju.androidconnectivity.GlobalBus;

import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout rlLayout;
     private Connectivity application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    protected void onResume() {
        super.onResume();
        application = new Connectivity(this);
        /**
         **************** register braod cast receiver to chceck network status ********************
         */
        application.checkInternetConnection();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Register event for callbacks if internet connection is available or not
        GlobalBus.getBus().register(this);
    }


    @Subscribe
    public void noInternetConnection(Events.NoInternetConnection noInternetConnection) {

        Log.e("status", "disconnected");
        if(application!=null)
        application.showSnackBar(noInternetConnection.getMessage(), "X",this); // show snack bar no internet connection
    }

    @Subscribe
    public void internetConnectionAvailable(Events.InternetConnectionAvailable internetConnectionAvailable) {

        Log.e("status", "connected");
        application.showSnackBar(internetConnectionAvailable.getMessage(), "OK",this); // connection available
    }


    @Override
    protected void onDestroy() {
        /**
         ********************* unregister the broadcast receiver ************************
         */
        if(application!=null)
            application.unRegister();
        //disable bus
        GlobalBus.getBus().unregister(this);

        super.onDestroy();
    }
}