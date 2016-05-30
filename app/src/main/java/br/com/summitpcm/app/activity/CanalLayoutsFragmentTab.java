package br.com.summitpcm.app.activity;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.summitpcm.app.R;
import br.com.summitpcm.app.config.AppConfig;
import br.com.summitpcm.app.config.AppController;
import br.com.summitpcm.app.model.Canal;
import br.com.summitpcm.app.model.EspecificacoesProduto;

/**
 * A simple {@link Fragment} subclass.
 */
public class CanalLayoutsFragmentTab extends Fragment {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    TextView nome,site,descricao,codigo;
    NetworkImageView thumbNail;

    public CanalLayoutsFragmentTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String value = getArguments().getString("CodigoCanal");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_canal_layouts_tab, container, false);


        return rootView;
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

}
