package prasetiyo.id.d4ljpjjcrud.utils;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
    public static final int GET = 1024;
    public static final int POST = 1025;

    private AsyncResponse delegate;

    private String url;
    private HashMap<String, String> params;
    private int requestCode;

    public PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode, AsyncResponse callback) {
        this.url = url;
        this.params = params;
        this.requestCode = requestCode;
        this.delegate = callback;
    }

    public PerformNetworkRequest(String url, int requestCode, AsyncResponse callback) {
        this.url = url;
        this.requestCode = requestCode;
        this.delegate = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(s);
            if (jsonObject.getBoolean("error"))
                delegate.onFailed(jsonObject);
            else
                delegate.onFinished(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        RequestHandler requestHandler = new RequestHandler();
        if (requestCode == POST)
            return requestHandler.sendPostRequest(url, params);
        if (requestCode == GET)
            return requestHandler.sendGetRequest(url);
        return null;
    }
}
