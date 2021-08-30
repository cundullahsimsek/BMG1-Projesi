package com.app.alisverissepeti.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.alisverissepeti.R;
import com.app.alisverissepeti.database.Database;
import com.app.alisverissepeti.model.Category;

import java.util.ArrayList;

public class Fragment3 extends Fragment {
    private ImageView image_button_basket,image_button_app;
    private Database database;
    private ArrayList<Category> data;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_three, container, false);
        initViews(v);
        sharedClick(v);
        return v;
    }


    private void initViews(View v) {
        image_button_basket = (ImageView) v.findViewById(R.id.shared_icon);
        image_button_app=(ImageView)v.findViewById(R.id.apps);
        database = new Database(getActivity());
        data = new ArrayList<Category>();
    }

    private void sharedClick(View v) {
        image_button_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data = database.bringData();
                String str = "";
                double transformation,total_price = 0.0;
                for (int i = 0;i<data.size();i++){
                    str += data.get(i).getCategories() + "---"  + data.get(i).getProducts() + "---"  + data.get(i).getPrice() + " TL" + "\n";
                    String amount=data.get(i).getPrice();
                    transformation=Double.parseDouble(amount);
                    total_price+=transformation;
                }
                str+="*\n*\n"+getResources().getString(R.string.total_price)+"  "+String.valueOf(total_price)+"  TL";
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, str);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });


        image_button_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link =getResources().getString(R.string.link);
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, link);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
    }
}

