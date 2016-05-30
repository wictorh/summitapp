package br.com.summitpcm.app.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.summitpcm.app.R;
import br.com.summitpcm.app.model.Venda;

public class VendaListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Venda> vendaItems;

    public VendaListAdapter(Activity activity, List< Venda> vendaItems) {
        this.activity = activity;
        this.vendaItems = vendaItems;
    }

    @Override
    public int getCount() {
        return vendaItems.size();
    }

    @Override
    public Object getItem(int location) {
        return vendaItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.venda_list_item, null);


        TextView descricao = (TextView) convertView.findViewById(R.id.vendaDescricao);
        TextView data = (TextView) convertView.findViewById(R.id.dataVenda);
        TextView valor = (TextView) convertView.findViewById(R.id.valorVenda);
        TextView vendaId = (TextView) convertView.findViewById(R.id.vendaId);

        Venda m = vendaItems.get(position);
        descricao.setText(m.getDescricao());
        data.setText(String.valueOf(m.getData()));
        valor.setText("R$ " + String.valueOf(m.getValorTotal()));
        vendaId.setText(String.valueOf(m.getVendaId()));

        return convertView;
    }

}