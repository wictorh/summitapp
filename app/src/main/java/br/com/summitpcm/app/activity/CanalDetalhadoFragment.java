package br.com.summitpcm.app.activity;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import br.com.summitpcm.app.R;
import br.com.summitpcm.app.adapter.ViewPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CanalDetalhadoFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public CanalDetalhadoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_canal_detalhado, container, false);
       // String value = getArguments().getString("CodigoCanal");

        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        // Inflate the layout for this fragment
        return  rootView;//inflater.inflate(R.layout.fragment_canal_detalhado, container, false);
    }

    private void setupViewPager(ViewPager viewPager) {

        Bundle args = new Bundle();
        args.putString("CodigoCanal",getArguments().getString("CodigoCanal").toString());
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        Fragment cIFrag = new CanalInfosFragmentTab();
        cIFrag.setArguments(args);
        adapter.addFragment(cIFrag, "Info");

        Fragment cVFrag = new CanalVendasFragmentTab();
        cVFrag.setArguments(args);
        adapter.addFragment(cVFrag, "Vendas");

        Fragment cLFrag = new CanalLayoutsFragmentTab();
        cLFrag.setArguments(args);
        adapter.addFragment(cLFrag, "Layouts");

        viewPager.setAdapter(adapter);
    }
}
