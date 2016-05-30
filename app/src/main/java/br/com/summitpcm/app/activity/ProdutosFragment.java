package br.com.summitpcm.app.activity;


import android.app.Activity;
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
import br.com.summitpcm.app.adapter.CanalListAdapter;
import br.com.summitpcm.app.adapter.ProdutoListAdapter;
import br.com.summitpcm.app.config.AppConfig;
import br.com.summitpcm.app.config.AppController;
import br.com.summitpcm.app.model.Canal;
import br.com.summitpcm.app.model.Produto;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProdutosFragment extends Fragment {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private List<Produto> produtoList = new ArrayList<Produto>();
    private ListView listView;
    private ProdutoListAdapter adapter;
    Fragment fragment = null;
    private int mContainerId;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    public ProdutosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onResume() {
        super.onResume();
        // Set title
        ((MainActivity)getActivity()).getSupportActionBar().show();
        ((MainActivity)getActivity()).getSupportActionBar().setTitle( getString(R.string.title_produtos));
        ((MainActivity)getActivity()).getSupportActionBar().setSubtitle(null);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_produtos, container, false);

        listView = (ListView) rootView.findViewById(R.id.listView);
        adapter = new ProdutoListAdapter(getActivity(), produtoList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
                TextView nome = (TextView) v.findViewById(R.id.nomeProduto);
                TextView codigo = (TextView) v.findViewById(R.id.codigoProduto);
                fragment = new ProdutoDetalhadoFragment();
                Bundle args = new Bundle();
                try {
                    if (fragment != null) {
                        args.putString("CodigoProduto",codigo.getText().toString());
                        fragment.setArguments(args);
                        fragmentTransaction = ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container_body, fragment,"ProdutosFragment");
                        fragmentTransaction.addToBackStack("ProdutosFragment");
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

        if(produtoList.isEmpty()){
            pDialog = new ProgressDialog(getActivity());
            // Showing progress dialog before making http request
            pDialog.setMessage("Carregando...");
            pDialog.show();
            JsonArrayRequest request = new JsonArrayRequest(AppConfig.URL_PRODUTOS,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d(TAG, response.toString());
                            hidePDialog();

                            for (int i = 0; i < response.length(); i++) {
                                try {

                                    JSONObject obj = response.getJSONObject(i);
                                    Produto produto = new Produto();
                                    produto.setProdutoId(obj.getInt("ProdutoId"));
                                    produto.setNome(obj.getString("Nome"));
                                    produto.setImage(obj.getString("Image"));
                                    produtoList.add(produto);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            // notifying list adapter about data changes
                            // so that it renders the list view with updated data
                            adapter.notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    hidePDialog();

                }
            });
            AppController.getInstance().addToRequestQueue(request);
        }
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


    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
