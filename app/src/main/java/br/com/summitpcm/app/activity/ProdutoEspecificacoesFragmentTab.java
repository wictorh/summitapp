package br.com.summitpcm.app.activity;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import br.com.summitpcm.app.adapter.EspecificacoesProdutoListAdapter;
import br.com.summitpcm.app.config.AppConfig;
import br.com.summitpcm.app.config.AppController;
import br.com.summitpcm.app.model.Atributo;
import br.com.summitpcm.app.model.EspecificacoesProduto;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProdutoEspecificacoesFragmentTab extends Fragment {
    private FloatingActionButton fab;
    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private List<EspecificacoesProduto> atributoList = new ArrayList<EspecificacoesProduto>();
    private ListView listView;
    private EspecificacoesProdutoListAdapter adapter;

    public ProdutoEspecificacoesFragmentTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Oncreate :: ","Entrou");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d("onResume :: ","Entrou");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String value = getArguments().getString("CodigoProduto");

        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_produto_especificacoes_tab, container, false);

        listView = (ListView) rootView.findViewById(R.id.listView);
        adapter = new EspecificacoesProdutoListAdapter(getActivity(), atributoList);
        listView.setAdapter(adapter);

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), "fab fac!", Toast.LENGTH_SHORT).show();
            }
        });

            pDialog = new ProgressDialog(getActivity());
            // Showing progress dialog before making http request
            pDialog.setMessage("Carregando...");
            pDialog.show();
            JsonArrayRequest movieReq = new JsonArrayRequest(String.format(AppConfig.URL_PRODUTOS_ESPECIFICACOES,value),
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("testets", response.toString());
                            hidePDialog();

                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    EspecificacoesProduto atributo = new EspecificacoesProduto();
                                    atributo.setAtributoId(obj.getInt("AtributoId"));
                                    atributo.setDescricao(obj.getString("Descricao"));
                                    atributo.setTipo(obj.getString("Tipo"));
                                    atributo.setValor(obj.getString("Valor"));
                                    atributo.setAtributoProdutoId(obj.getInt("AtributoProdutoId"));
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
