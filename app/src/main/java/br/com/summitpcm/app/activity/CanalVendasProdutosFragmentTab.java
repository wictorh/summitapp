package br.com.summitpcm.app.activity;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import br.com.summitpcm.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CanalVendasProdutosFragmentTab extends Fragment {
    private FloatingActionButton fab;
    Fragment fragment = null;
    private FragmentTransaction fragmentTransaction;
    private static final String TAG = MainActivity.class.getSimpleName();
    public CanalVendasProdutosFragmentTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_canal_vendas_tab_produtos, container, false);

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


        return rootView;
    }


    public void onBackPressed() {
        getFragmentManager().popBackStack();
    }

}
