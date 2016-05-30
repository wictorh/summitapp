package br.com.summitpcm.app.service;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.summitpcm.app.config.AppConfig;
import br.com.summitpcm.app.config.AppController;
import br.com.summitpcm.app.helper.Listener;
import br.com.summitpcm.app.helper.RequestManager;
import br.com.summitpcm.app.model.Canal;

/**
 * Created by wictor.huggo on 29/05/2016.
 */
public class CanalService {
    private static final String TAG = "CanalService";
   private List<Canal> canalList = new ArrayList<Canal>();

    public List<Canal> BuscaCanais(){



/*        JsonArrayRequest request = new JsonArrayRequest(AppConfig.URL_CANAIS,
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
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });*/

return canalList;


    }
}
