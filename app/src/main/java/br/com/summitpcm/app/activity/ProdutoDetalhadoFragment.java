package br.com.summitpcm.app.activity;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.summitpcm.app.R;
import br.com.summitpcm.app.adapter.ViewPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProdutoDetalhadoFragment extends Fragment {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private FragmentTabHost mTabHost;

    public ProdutoDetalhadoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onResume() {
        super.onResume();

    }

    private void setupViewPager(ViewPager viewPager) {

        Bundle args = new Bundle();
        args.putString("CodigoProduto",getArguments().getString("CodigoProduto").toString());
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        Fragment PeFrag = new ProdutoEspecificacoesFragmentTab();
        PeFrag.setArguments(args);
        adapter.addFragment(PeFrag, "Especificações");
        Log.d("555 555 :: ",getArguments().getString("CodigoProduto").toString());
        Fragment PiFrag = new ProdutoImagensFragmentTab();
        PiFrag.setArguments(args);
        adapter.addFragment(PiFrag, "Midia");
        viewPager.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_produto_detalhado, container, false);

//        ((MainActivity)getActivity()). getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }

}
