package com.example.jose.sunshine.app.util;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.jose.sunshine.app.App;

/**
 * Created by jose on 27/01/2015.
 */
public final class NetworkUtil {

    private static final String LOGTAG = NetworkUtil.class.getSimpleName();

    public static boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) App.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }

    public static String getRequestString(String url) throws IOException, NetworkErrorException {
        if(!isConnected())
            throw new NetworkErrorException("Don't has Internet.");

        String result = null;
        InputStream is = null;
        HttpURLConnection conn = null;

        try {
            URL u = new URL(url);
            conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            Log.d(LOGTAG, "status code: "+conn.getResponseCode());
            is = conn.getInputStream();
            if(is != null)
                result = StreamUtil.readToString(is);
        } finally {
            if(is != null)
                is.close();

            if(conn != null)
                conn.disconnect();
        }
        return result;
    }

    public static JSONObject getRequestJson(String url) throws IOException  {
        JSONObject result = null;
        try {
            String response = NetworkUtil.getRequestString(url);
            result = new JSONObject(response);
        } catch (JSONException e) {
            Log.e(LOGTAG, e.getMessage());
        } catch (NetworkErrorException e) {
            Log.e(LOGTAG, e.getMessage());
        }
        return result;
    }
}
