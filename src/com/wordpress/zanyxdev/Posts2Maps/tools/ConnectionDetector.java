package com.wordpress.zanyxdev.mskpostsorg;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConnectionDetector {
    private Context _context;
    private String LOG_TAG = getClass().getSimpleName();
    // Whether there is a Wi-Fi connection.
    private static boolean wifiConnected = false;
    // Whether there is a mobile connection.
    private static boolean mobileConnected = false;

    public ConnectionDetector(Context context) {
        this._context = context;
    }

    public boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connectivity.getActiveNetworkInfo();

        if (connectivity != null && activeInfo != null && activeInfo.isConnected()) {
            try {
                URL url = new URL("http://www.google.com");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(3000);
                urlConnection.connect();
                if (urlConnection.getResponseCode() == 200) {
                    Log.v(LOG_TAG, "ONLINE");
                    wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
                    mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.v(LOG_TAG, "OFFLINE");
            wifiConnected = false;
            mobileConnected = false;
        }
        return false;
    }
}
