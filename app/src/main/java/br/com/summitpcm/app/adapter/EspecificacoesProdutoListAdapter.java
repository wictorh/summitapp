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
import br.com.summitpcm.app.model. EspecificacoesProduto;

/**
 * Created by wictor.huggo on 29/05/2016.
 */
public class EspecificacoesProdutoListAdapter  extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List< EspecificacoesProduto>  especificacoesProdutoItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public EspecificacoesProdutoListAdapter(Activity activity, List< EspecificacoesProduto> especificacoesProdutoItems) {
        this.activity = activity;
        this.especificacoesProdutoItems = especificacoesProdutoItems;
    }

    @Override
    public int getCount() {
        return especificacoesProdutoItems.size();
    }

    @Override
    public Object getItem(int location) {
        return especificacoesProdutoItems.get(location);
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
            convertView = inflater.inflate(R.layout.produto_especificacoes_item, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView nome = (TextView) convertView.findViewById(R.id.especificacao);
        TextView tipo = (TextView) convertView.findViewById(R.id.tipoEspecificacao);
        TextView valor = (TextView) convertView.findViewById(R.id.valorEspecificacao);
        TextView epId = (TextView) convertView.findViewById(R.id.especificacaoProdutoId);

        // getting movie data for the row
        EspecificacoesProduto m = especificacoesProdutoItems.get(position);
        nome.setText(m.getDescricao());
        tipo.setText("Tipo : " + String.valueOf(m.getTipo()));
        valor.setText(m.getValor());
        epId.setText(String.valueOf(m.getAtributoProdutoId()));

        return convertView;
    }

}