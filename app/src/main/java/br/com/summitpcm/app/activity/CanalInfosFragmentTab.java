package br.com.summitpcm.app.activity;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.summitpcm.app.R;
import br.com.summitpcm.app.config.AppConfig;
import br.com.summitpcm.app.config.AppController;

/**
 * A simple {@link Fragment} subclass.
 */
public class CanalInfosFragmentTab extends Fragment {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    TextView nome,site,descricao,codigo;
    NetworkImageView thumbNail;

    public CanalInfosFragmentTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String value = getArguments().getString("CodigoCanal");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_canal_infos_tab, container, false);

        nome = (TextView) rootView.findViewById(R.id.canalInfoNome);
        site = (TextView) rootView.findViewById(R.id.canalInfoSite);
        descricao = (TextView) rootView.findViewById(R.id.canalInfoDescricao);
        codigo = (TextView) rootView.findViewById(R.id.canalInfoCodigo);
        thumbNail = (NetworkImageView) rootView.findViewById(R.id.thumbnailCanalInfo);

        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Carregando...");
        pDialog.show();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, String.format(AppConfig.URL_CANAL,value), null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        hidePDialog();
                        try {
                            nome.setText(response.getString("Nome"));
                            site.setText(response.getString("Site"));
                            descricao.setText(response.getString("Descricao"));
                            codigo.setText(String.valueOf(response.getInt("CanalId")));
                            if (imageLoader == null)
                                imageLoader = AppController.getInstance().getImageLoader();
                            thumbNail.setImageUrl(response.getString("Image"), imageLoader);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            VolleyLog.d(TAG, "Error: " + e.getMessage());
                            hidePDialog();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        hidePDialog();
                    }
                });



        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsObjRequest);

        return rootView;
    }
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
