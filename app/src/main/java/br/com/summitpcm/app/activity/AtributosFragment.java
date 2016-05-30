package br.com.summitpcm.app.activity;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.summitpcm.app.R;
import br.com.summitpcm.app.adapter.AtributoListAdapter;
import br.com.summitpcm.app.adapter.CanalListAdapter;
import br.com.summitpcm.app.config.AppConfig;
import br.com.summitpcm.app.config.AppController;
import br.com.summitpcm.app.model.Atributo;
import br.com.summitpcm.app.model.Canal;

public class AtributosFragment extends Fragment {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private List<Atributo> atributoList = new ArrayList<Atributo>();
    private ListView listView;
    private AtributoListAdapter adapter;
    Fragment fragment = null;
    private int mContainerId;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    public AtributosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title
        ((MainActivity)getActivity()).getSupportActionBar().setTitle( getString(R.string.title_atributos));
        ((MainActivity)getActivity()).getSupportActionBar().setSubtitle(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_atributos, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        adapter = new AtributoListAdapter(getActivity(), atributoList);
        listView.setAdapter(adapter);


        if(atributoList.isEmpty()){
            pDialog = new ProgressDialog(getActivity());
            // Showing progress dialog before making http request
            pDialog.setMessage("Carregando...");
            pDialog.show();

            JsonArrayRequest movieReq = new JsonArrayRequest(AppConfig.URL_ATRIBUTOS,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d(TAG, response.toString());
                            hidePDialog();

                            // Parsing json
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    Atributo atributo = new Atributo();
                                    atributo.setAtributoId(obj.getInt("AtributoId"));
                                    atributo.setDescricao(obj.getString("Descricao"));
                                    atributo.setTipo(obj.getString("Tipo"));
                                    atributoList.add(atributo);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            adapter.notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    hidePDialog();

                }
            });

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(movieReq);
        }
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

}
