package br.com.summitpcm.app.activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.summitpcm.app.R;
import br.com.summitpcm.app.adapter.CanalListAdapter;
import br.com.summitpcm.app.helper.Listener;
import br.com.summitpcm.app.helper.NetworkManager;
import br.com.summitpcm.app.helper.RequestManager;
import br.com.summitpcm.app.model.Canal;
import br.com.summitpcm.app.config.AppConfig;
import br.com.summitpcm.app.config.AppController;

public class CanaisFragment extends Fragment {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private List<Canal> canalList = new ArrayList<Canal>();
    private ListView listView;
    private CanalListAdapter adapter;
    private TextView msgErro;
    private TextView subMsgErro;
    Fragment fragment = null;
    int retryCount =0;
    public CanaisFragment() {
        // Required empty public constructor
    }
    private int mContainerId;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title
        ((MainActivity)getActivity()).getSupportActionBar().setTitle( getString(R.string.title_canais));
        ((MainActivity)getActivity()).getSupportActionBar().setSubtitle(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_canais, container, false);

        msgErro = (TextView) rootView.findViewById(R.id.msgErro);
        subMsgErro = (TextView) rootView.findViewById(R.id.subMsgErro);
        listView = (ListView) rootView.findViewById(R.id.listView);
        adapter = new CanalListAdapter(getActivity(), canalList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
                TextView nome = (TextView) v.findViewById(R.id.nomeCanal);
                TextView codigo = (TextView) v.findViewById(R.id.codigoCanal);
                fragment = new CanalDetalhadoFragment();
                Bundle args = new Bundle();
                try {
                    if (fragment != null) {
                        args.putString("CodigoCanal",codigo.getText().toString());
                        fragment.setArguments(args);
                        fragmentTransaction = ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container_body, fragment,"CanaisFragment");
                        fragmentTransaction.addToBackStack("CanaisFragment");
                        fragmentTransaction.commitAllowingStateLoss();
                        getFragmentManager().executePendingTransactions();

                        //getSupportActionBar().setTitle(title);
                        ((MainActivity)getActivity()).getSupportActionBar().setSubtitle(nome.getText());
                    }
                    // Toast.makeText(getActivity(), nome.getText(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                    Toast.makeText(getActivity(), "Erro ao executar solicitação", Toast.LENGTH_SHORT).show();
                }

            }});





        if(canalList.isEmpty()){
            if(NetworkManager.getConnectivityStatus(((MainActivity)getActivity()).getApplicationContext()) != 0)
            {
                LoadDados();
            }
            else
            {
                msgErro.setVisibility(View.VISIBLE);
                msgErro.setText("Sem Conexão");
            }
        }
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    public  void LoadDados(){
        msgErro.setVisibility(View.INVISIBLE);
        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Carregando...");
        pDialog.show();
        // Creating volley request obj
        JsonArrayRequest movieReq = new JsonArrayRequest(AppConfig.URL_CANAIS,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
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
                            }
                        }
                        retryCount =0;
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    if(retryCount <3){
                        retryCount++;
                        hidePDialog();
                        LoadDados();
                    }else{
                        hidePDialog();
                        msgErro.setVisibility(View.VISIBLE);
                        msgErro.setText("Conexão Lenta");
                        subMsgErro.setText("");
                    }
                }
                else {
                    msgErro.setVisibility(View.VISIBLE);
                    msgErro.setText("Erro");
                    subMsgErro.setText("Entre em contatos com os administradores do App");
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    hidePDialog();
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);

    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
