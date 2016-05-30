package br.com.summitpcm.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import br.com.summitpcm.app.R;
import br.com.summitpcm.app.config.AppController;
import br.com.summitpcm.app.model.Atributo;
import br.com.summitpcm.app.model.Canal;

/**
 * Created by wictor.huggo on 29/05/2016.
 */
public class AtributoListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Atributo> atributoItems;
    private List<Atributo> origatributoItems;

    public AtributoListAdapter(Activity activity, List<Atributo> atrbiutoItems) {
        this.activity = activity;
        this.atributoItems = atrbiutoItems;
    }

    @Override
    public int getCount() {
        return atributoItems.size();
    }

    @Override
    public Object getItem(int location) {
        return atributoItems.get(location);
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
            convertView = inflater.inflate(R.layout.atributo_list_item, null);

        TextView nome = (TextView) convertView.findViewById(R.id.nomeAtributo);
        TextView tipo  = (TextView) convertView.findViewById(R.id.tipoAtributo);

        Atributo m = atributoItems.get(position);

        nome.setText(m.getDescricao());
        tipo.setText(String.valueOf(m.getTipo()));
        return convertView;
    }

}
