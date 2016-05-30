package br.com.summitpcm.app.helper;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.summitpcm.app.config.AppConfig;
import br.com.summitpcm.app.model.Canal;
import br.com.summitpcm.app.model.Login;

/**
 * Created by wictor.huggo
 */
public class RequestProxy {
    private static final String TAG = "RequestProxy";
    private RequestQueue mRequestQueue;
    List<Canal> canalList = new ArrayList<Canal>();
    // package access constructor
    RequestProxy(Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public RequestQueue getResquestQeue(){
        return this.mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        mRequestQueue.add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        mRequestQueue.add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public Login login() {
        return new Login();
    }

    public void BuscaCanais(final Listener<List<Canal>> listener){


        JsonArrayRequest request = new JsonArrayRequest(AppConfig.URL_CANAIS,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Canal canal = new Canal();
                                canal.setId(obj.getInt("CanalId"));

                                canal.setNome(obj.getString("Nome"));
                                Log.d("Nome", obj.getString("Nome"));
                                canal.setDescricao(obj.getString("Descricao"));
                                canal.setSite(obj.getString("Site"));
                                canal.setThumbnailUrl(obj.getString("Image"));
                                canal.setStatus(obj.getBoolean("Status"));
                                canalList.add(canal);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("erro", e.getMessage());
                            }
                        }
                            listener.getResult(canalList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (null != error.networkResponse)
                {
                    Log.d(TAG + ": ", "Error Response code: " + error.networkResponse.statusCode);
                    listener.getResult(new ArrayList<Canal>());
                };
            }
        });
        addToRequestQueue(request);
    }
}
