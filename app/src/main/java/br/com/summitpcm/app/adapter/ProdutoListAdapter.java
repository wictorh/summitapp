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
import br.com.summitpcm.app.model.Produto;


public class ProdutoListAdapter  extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Produto> produtoItems;
    private List<Produto> origprodutoItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public ProdutoListAdapter(Activity activity, List<Produto> canalItems) {
        this.activity = activity;
        this.produtoItems = canalItems;
    }

    @Override
    public int getCount() {
        return produtoItems.size();
    }

    @Override
    public Object getItem(int location) {
        return produtoItems.get(location);
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
            convertView = inflater.inflate(R.layout.produto_list_item, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnailProduto);
        TextView nome = (TextView) convertView.findViewById(R.id.nomeProduto);
        TextView codigo = (TextView) convertView.findViewById(R.id.codigoProduto);

        // getting movie data for the row
        Produto m = produtoItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getImage(), imageLoader);

        nome.setText(m.getNome());

        codigo.setText(String.valueOf(m.getProdutoId()));

        return convertView;
    }

}