package br.com.summitpcm.app.activity;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import java.util.Date;
import java.util.List;

import br.com.summitpcm.app.R;
import br.com.summitpcm.app.adapter.EspecificacoesProdutoListAdapter;
import br.com.summitpcm.app.adapter.VendaListAdapter;
import br.com.summitpcm.app.config.AppConfig;
import br.com.summitpcm.app.config.AppController;
import br.com.summitpcm.app.model.EspecificacoesProduto;
import br.com.summitpcm.app.model.Venda;

/**
 * A simple {@link Fragment} subclass.
 */
public class CanalVendasFragmentTab extends Fragment {
    private FloatingActionButton fab;
    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private List<Venda> vendaList = new ArrayList<Venda>();
    private ListView listView;
    private VendaListAdapter adapter;
    Fragment fragment = null;
    private FragmentTransaction fragmentTransaction;
    TextView msg;
    public CanalVendasFragmentTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String value = getArguments().getString("CodigoCanal");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_canal_vendas_tab, container, false);

        listView = (ListView) rootView.findViewById(R.id.listView);
        msg = (TextView) rootView.findViewById(R.id.msgCanalVendas);
        adapter = new VendaListAdapter(getActivity(), vendaList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
                TextView nome = (TextView) v.findViewById(R.id.vendaDescricao);
                TextView codigo = (TextView) v.findViewById(R.id.vendaId);
                fragment = new CanalVendasProdutosFragmentTab();
                Bundle args = new Bundle();
                try {
                    if (fragment != null) {
                        args.putString("CodigoCanal",getArguments().getString("CodigoCanal"));
                        args.putString("CodigoVenda",codigo.getText().toString());
                        fragment.setArguments(args);
                        fragmentTransaction = ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fcvt1, fragment,"CanalVendasFragmentTab");
                        fragmentTransaction.addToBackStack("CanalVendasFragmentTab");
                        fragmentTransaction.commitAllowingStateLoss();
                        getFragmentManager().executePendingTransactions();

                        //getSupportActionBar().setTitle(title);
                        //((MainActivity)getActivity()).getSupportActionBar().setSubtitle(nome.getText());
                    }
                    // Toast.makeText(getActivity(), nome.getText(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                    Toast.makeText(getActivity(), "Erro ao executar solicitação", Toast.LENGTH_SHORT).show();
                }

            }});


        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Carregando...");
        pDialog.show();
        JsonArrayRequest movieReq = new JsonArrayRequest(String.format(AppConfig.URL_CANAIS_VENDAS,value),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        hidePDialog();
                        if(response.length() >=1) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    Venda venda = new Venda();
                                    venda.setVendaId(obj.getInt("VendaId"));
                                    venda.setDescricao(obj.getString("Descricao"));
                                    venda.setData(obj.getString("Data"));
                                    venda.setValorTotal(obj.getDouble("ValorTotal"));
                                    vendaList.add(venda);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    msg.setVisibility(View.VISIBLE);
                                    msg.setText("Erro!");
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                        else{
                            msg.setVisibility(View.VISIBLE);
                            msg.setText("Sem vendas");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                msg.setVisibility(View.VISIBLE);
                msg.setText("Erro!");
                hidePDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);


        return rootView;
    }

    public void onBackPressed() {
        getFragmentManager().popBackStack();
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
