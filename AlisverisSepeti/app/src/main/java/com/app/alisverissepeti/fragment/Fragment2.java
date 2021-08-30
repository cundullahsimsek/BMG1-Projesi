package com.app.alisverissepeti.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.alisverissepeti.R;
import com.app.alisverissepeti.adapter.GridViewAdapter;
import com.app.alisverissepeti.model.Products;

import java.util.ArrayList;

public class Fragment2 extends Fragment {

    private GridView gridView;
    public ArrayList<Products> listGrid;
    public Fragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_two, container, false);
        grid();
        GridViewAdapter adapter=new GridViewAdapter(getActivity(),listGrid);
        gridView=(GridView)v.findViewById(R.id.grid_view);
        gridView.setAdapter(adapter);
        return v;
    }

    public ArrayList<Products> grid() {
        listGrid = new ArrayList<Products>();
        listGrid.add(new Products("Market", R.drawable.bakkal));
        listGrid.add(new Products("Meyve", R.drawable.meyve));
        listGrid.add(new Products("Bebek", R.drawable.bebek));
        listGrid.add(new Products("İçecek", R.drawable.icecek));
        listGrid.add(new Products("Kasap", R.drawable.kasap));
        listGrid.add(new Products("Sebze", R.drawable.sebze));
        listGrid.add(new Products("Donmuş Gıda", R.drawable.donmus));
        listGrid.add(new Products("Hırdavat", R.drawable.hirdavat));
        listGrid.add(new Products("Aperatif", R.drawable.aperatif));
        listGrid.add(new Products("Fırın", R.drawable.firin));
        listGrid.add(new Products("Pastane", R.drawable.pastane));
        listGrid.add(new Products("Evcil Hayvan", R.drawable.evcilhayvan));
        listGrid.add(new Products("Parfümeri", R.drawable.parfumeri));
        listGrid.add(new Products("Balıkçı", R.drawable.balik));
        listGrid.add(new Products("Ev+", R.drawable.ev));
        listGrid.add(new Products("Ötekiler", R.drawable.otekiler));
        return listGrid;
    }
}

