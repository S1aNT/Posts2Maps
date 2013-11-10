package com.wordpress.zanyxdev.Posts2Maps.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import com.wordpress.zanyxdev.Posts2Maps.IAsyncResponse;


public class ConnectionDetectorTask extends AsyncTask<Void, Void, Boolean> {
    private Context mContext;
    public IAsyncResponse iAsyncResponse=null;
    private String LOG_TAG = "Posts2Map::".concat(getClass().getSimpleName());

    public ConnectionDetectorTask(Context context) {
        this.mContext = context;

    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo activeInfo = connectivity.getActiveNetworkInfo();
            if (activeInfo != null && activeInfo.isConnected()) {
                try {
                    URL url = new URL("http://www.google.com");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setConnectTimeout(3000);
                    urlConnection.connect();
                    if (urlConnection.getResponseCode() == 200) {
                        Log.v(LOG_TAG, "ONLINE");

                        return true;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.v(LOG_TAG, "OFFLINE");
        return false;
    }

    //other methods like onPreExecute etc.
    protected void onPostExecute(Boolean result) {
        Log.v(LOG_TAG, "onPostExecute result:".concat(result.toString()));
        super.onPostExecute(result);
        iAsyncResponse.processFinish(result);
    }

}
