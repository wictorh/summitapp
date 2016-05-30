package br.com.summitpcm.app.activity;


import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

import br.com.summitpcm.app.R;
import br.com.summitpcm.app.adapter.GridViewAdapter;
import br.com.summitpcm.app.model.ImageItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProdutoImagensFragmentTab extends Fragment {
    private GridView gridView;
    private GridViewAdapter gridAdapter;

    public ProdutoImagensFragmentTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int value = Integer.parseInt( getArguments().getString("CodigoProduto"));

        View rootView = inflater.inflate(R.layout.fragment_produto_imagens_tab, container, false);
        gridView = (GridView)rootView. findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(getActivity(), R.layout.produto_imagens_item, getData(value));
        gridView.setAdapter(gridAdapter);
        // Inflate the layout for this fragment
        return rootView;
    }

    // Prepare some dummy data for gridview
    private ArrayList<ImageItem> getData(int value) {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        if(value==1){
            for (int i = 0; i < 3; i++) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
                imageItems.add(new ImageItem(bitmap, "Image#" + i));
            }
        }else  if(value==2){
            for (int i = 3; i < 7; i++) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
                imageItems.add(new ImageItem(bitmap, "Image#" + i));
            }
        }else  if(value==3){
            for (int i = 7; i < 10; i++) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
                imageItems.add(new ImageItem(bitmap, "Image#" + i));
            }
        }
        else  if(value==4){
            for (int i = 10; i < 11; i++) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
                imageItems.add(new ImageItem(bitmap, "Image#" + i));
            }
        }else  if(value==5){
            for (int i = 11; i < 13; i++) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
                imageItems.add(new ImageItem(bitmap, "Image#" + i));
            }
        }
        else  if(value==6){
            for (int i = 13; i < 17; i++) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
                imageItems.add(new ImageItem(bitmap, "Image#" + i));
            }
        }
        else  if(value==7){
            for (int i = 17; i < 19; i++) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
                imageItems.add(new ImageItem(bitmap, "Image#" + i));
            }
        }


        return imageItems;
    }

}
