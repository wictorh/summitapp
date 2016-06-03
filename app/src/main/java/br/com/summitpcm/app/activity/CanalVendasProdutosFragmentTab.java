package br.com.summitpcm.app.activity;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.summitpcm.app.R;
import br.com.summitpcm.app.adapter.EspecificacoesProdutoListAdapter;
import br.com.summitpcm.app.adapter.ProdutoListAdapter;
import br.com.summitpcm.app.config.AppConfig;
import br.com.summitpcm.app.config.AppController;
import br.com.summitpcm.app.model.EspecificacoesProduto;
import br.com.summitpcm.app.model.Produto;

/**
 * A simple {@link Fragment} subclass.
 */
public class CanalVendasProdutosFragmentTab extends Fragment {
    private FloatingActionButton fab;
    Fragment fragment = null;

    private FragmentTransaction fragmentTransaction;
    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private List<Produto> produtoList = new ArrayList<Produto>();
    private ListView listView;
    private TextView descricao;
    private  TextView data;
    private  TextView usuario;
    private  TextView msgErro;
    private  TextView valor;
    int retryCount =0;
    private ProdutoListAdapter adapter;
    public CanalVendasProdutosFragmentTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_canal_vendas_tab_produtos, container, false);

        descricao = (TextView) rootView.findViewById(R.id.txtCvProdutoDescricao);
        data = (TextView) rootView.findViewById(R.id.txtCvProdutoData);
        listView = (ListView) rootView.findViewById(R.id.listView);
        msgErro = (TextView) rootView.findViewById(R.id.msgErro);

        adapter = new ProdutoListAdapter(getActivity(), produtoList);
        listView.setAdapter(adapter);

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab_back_vendas);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new CanalVendasFragmentTab();
                Bundle args = new Bundle();
                try {
                    if (fragment != null) {
                        args.putString("CodigoCanal",getArguments().getString("CodigoCanal"));
                        fragment.setArguments(args);
                        fragmentTransaction = ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fcvt1, fragment,"CanaisFragment");
                        fragmentTransaction.addToBackStack("CanaisFragment");
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
            }
        });

        loadDados(getArguments().getString("CodigoVenda"));

        return rootView;
    }


    public  void loadDados(final String value){
        msgErro.setVisibility(View.INVISIBLE);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Carregando...");
        pDialog.show();
        StringRequest Req = new StringRequest(String.format(AppConfig.URL_VENDA,value),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hidePDialog();
                        try{
                            JSONObject jObj = new JSONObject((response));

                            descricao.setText(jObj.getString("Descricao"));
                            data.setText(jObj.getString("Data"));
                            //JSONObject jObj = new JSONObject((response));
                            JSONArray produtosVenda = jObj.getJSONArray("Produtos");
                            if(produtosVenda.length() == 0){
                                msgErro.setVisibility(View.VISIBLE);
                                msgErro.setText("Sem Produtos");
                            }
                            for (int i = 0; i < produtosVenda.length(); i++) {
                                try {
                                    JSONObject pdt =  produtosVenda.getJSONObject(i);

                                    Produto produto = new Produto();
                                    produto.setProdutoId(pdt.getInt("ProdutoId"));
                                    produto.setNome(pdt.getString("Nome"));
                                    produto.setImage(pdt.getString("Image"));
                                    produtoList.add(produto);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    if(retryCount <3){
                        retryCount++;
                        hidePDialog();
                        loadDados(value);
                    }else{
                        hidePDialog();
                        msgErro.setVisibility(View.VISIBLE);
                        msgErro.setText("Conexão Lenta");
                    }
                }else{
                    msgErro.setVisibility(View.VISIBLE);
                    msgErro.setText("Erro");
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    hidePDialog();
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(Req);}


    public void onBackPressed() {
        getFragmentManager().popBackStack();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
