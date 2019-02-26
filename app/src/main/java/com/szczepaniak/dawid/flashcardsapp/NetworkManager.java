package com.szczepaniak.dawid.flashcardsapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class NetworkManager extends AsyncTask<Void, Void, Void> {

    private Context context;
    private String path;
    private String arrayName;
    public NetworkManager(Context context, String path, String arrayName) {
        this.context = context;
        this.path = path;
        this.arrayName = arrayName;
    }


    abstract void onDataGet(JSONArray jsonArray, Context context);

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... arg0) {
        HttpHandler sh = new HttpHandler();
        String url = "http://192.168.0.182:8080" + path;
        String jsonStr = sh.makeServiceCall(url);

        Log.e("json", "Response from url: " + jsonStr);
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                JSONArray array = jsonObj.getJSONArray(arrayName);
                onDataGet(array, context);


            } catch (final JSONException e) {}

        } else {}

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }
}
