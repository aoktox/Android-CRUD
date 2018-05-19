package prasetiyo.id.d4ljpjjcrud.utils;

import org.json.JSONException;
import org.json.JSONObject;

public interface AsyncResponse {
    void onFinished(JSONObject response) throws JSONException;
    void onFailed(JSONObject response) throws JSONException;
}
