package com.distributedpos.app.api;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkMonitor {
    private Context context;

    NetworkMonitor(Context context) {
        this.context = context.getApplicationContext();
    }

    boolean isConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return null != activeNetwork && activeNetwork.isConnectedOrConnecting();
    }
}
