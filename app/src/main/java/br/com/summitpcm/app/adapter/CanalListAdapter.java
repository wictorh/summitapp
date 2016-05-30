package br.com.summitpcm.app.adapter;


import br.com.summitpcm.app.R;
import br.com.summitpcm.app.config.AppController;
import br.com.summitpcm.app.model.Canal;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class CanalListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Canal> canalItems;
    private List<Canal> origcanalItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CanalListAdapter(Activity activity, List<Canal> canalItems) {
        this.activity = activity;
        this.canalItems = canalItems;
    }

    @Override
    public int getCount() {
        return canalItems.size();
    }

    @Override
    public Object getItem(int location) {
        return canalItems.get(location);
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
            convertView = inflater.inflate(R.layout.canal_list_item, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView nome = (TextView) convertView.findViewById(R.id.nomeCanal);
        TextView codigo = (TextView) convertView.findViewById(R.id.codigoCanal);

        // getting movie data for the row
        Canal m = canalItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        nome.setText(m.getNome());

        codigo.setText(String.valueOf(m.getId()));

        return convertView;
    }

}