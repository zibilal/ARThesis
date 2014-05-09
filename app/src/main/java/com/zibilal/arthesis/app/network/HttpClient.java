package com.zibilal.arthesis.app.network;

import android.util.Log;

import com.google.gson.Gson;
import com.zibilal.arthesis.app.model.Response;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by bmuhamm on 4/14/14.
 */
public class HttpClient {

    private static final String TAG="HttpClient";

    public Object get(Class<? extends Response> clss, String sUrl) {
        try{
            URL url = new URL(sUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            int responseCode = conn.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK) {
                Reader reader = new InputStreamReader(conn.getInputStream());
                Gson gson = new Gson();
                return gson.fromJson(reader, clss);
            } else {
                throw new Exception("Failed to get data due to response code --> " + responseCode);
            }

        } catch (Exception e) {
            Log.e(TAG, "Exception " + e.getMessage());
        }

        return null;
    }

}
