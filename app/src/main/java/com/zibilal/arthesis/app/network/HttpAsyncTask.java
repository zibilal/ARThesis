package com.zibilal.arthesis.app.network;

import android.os.AsyncTask;

import com.zibilal.arthesis.app.model.Response;

/**
 * Created by bmuhamm on 4/14/14.
 */
public class HttpAsyncTask extends AsyncTask<String, Integer, Response> {

    private HttpClient mHttpClient;
    private Class<? extends  Response> mClass;
    private Callback mCallback;

    public HttpAsyncTask(Class<? extends Response> clss, Callback callback){
        mHttpClient = new HttpClient();
        mClass = clss;
        mCallback=callback;
    }

    @Override
    protected Response doInBackground(String... strings) {
        Response response = (Response) mHttpClient.get(mClass, strings[0]);
        return response;
    }

    @Override
    protected void onPostExecute(Response response) {
        mCallback.onFinished(response);
    }

    public static interface Callback {
        public void onFinished(Response response);
    }
}
