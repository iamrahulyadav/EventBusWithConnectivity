package com.sanju.androidconnectivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by NEERAJ on 4/29/2017.
 */

public class Connectivity {
    BroadcastReceiver br = null;
    Context context;
    private String mInternetConnection;
    private String mNoInternetConnection;
    private Snackbar snackbar;
    public Connectivity(Context context)
    {
        this.context=context;
    }
    public Connectivity(Context context,String mInternetConnection,String mNoInternetConnection)
    {
        this.context=context;
        this.mInternetConnection=mInternetConnection;
        this.mNoInternetConnection=mNoInternetConnection;
    }

    public void checkInternetConnection() {

        if(mNoInternetConnection==null)
        {
            mNoInternetConnection="No Internet Connection";
        }
        if(mInternetConnection==null)
        {
            mInternetConnection="Internet Conneciton Available";
        }

        if (br == null) {

            br = new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {

                    Bundle extras = intent.getExtras();

                    NetworkInfo info = (NetworkInfo) extras
                            .getParcelable("networkInfo");

                    NetworkInfo.State state = info.getState();
                    Log.e("TEST Internet", info.toString() + " "
                            + state.toString());

                    if (state == NetworkInfo.State.CONNECTED) {
                        Events.InternetConnectionAvailable internetConnectionAvailable =
                                new Events.InternetConnectionAvailable(mInternetConnection);
                        GlobalBus.getBus().post(internetConnectionAvailable);

                    } else {
                         Events.NoInternetConnection noInternetConnection =
                                new Events.NoInternetConnection(mNoInternetConnection);
                        GlobalBus.getBus().post(noInternetConnection);

                    }

                }
            };

            final IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            intentFilter.addAction("android.net.wifi.STATE_CHANGE");
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

            context.registerReceiver((BroadcastReceiver) br, intentFilter);
        }
    }

        public  int getConnectivityStatus() {
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (null != activeNetwork) {
                if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                    return Constants.TYPE_WIFI;

                if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                    return Constants.TYPE_MOBILE;
            }
            return Constants.TYPE_NOT_CONNECTED;
        }

        public  String getConnectivityStatusString() {
            int conn = getConnectivityStatus();
            String status = null;
            if (conn == Constants.TYPE_WIFI) {
                status = "Wifi enabled";
            } else if (conn == Constants.TYPE_MOBILE) {
                status = "Mobile data enabled";
            } else if (conn == Constants.TYPE_NOT_CONNECTED) {
                status = "Not connected to Internet";
            }
            return status;
        }

        public void unRegister()
        {
            if(br!=null)
            {

                context.unregisterReceiver((BroadcastReceiver)br);
            }
        }
public void showSnackBar(String status, String actionType, Activity activity)
{
    snackbar = Snackbar
            .make(activity.findViewById(android.R.id.content), status, Snackbar.LENGTH_LONG)
            .setAction(actionType, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                }
            });
    // Changing message text color
    snackbar.setActionTextColor(Color.WHITE);

    // Changing action button text color
    View sbView = snackbar.getView();
    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
    textView.setTextColor(Color.WHITE);

    snackbar.show();



}
}
