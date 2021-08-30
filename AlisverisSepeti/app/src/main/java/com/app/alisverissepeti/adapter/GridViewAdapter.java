package com.app.alisverissepeti.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.alisverissepeti.R;
import com.app.alisverissepeti.model.Products;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    public ArrayList<Products> gridlist;
    private LinearLayout gridView;
    private Context cont;

    public GridViewAdapter(Context context,ArrayList<Products> list) {
        this.cont=context;
        this.gridlist=list;

        layoutInflater = (LayoutInflater) cont.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return gridlist.size();
    }

    @Override
    public Object getItem(int position) {
        return gridlist.get(position).getCategoryName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View lineView;
        lineView = layoutInflater.inflate(R.layout.gridview, null);
        TextView tv1 = (TextView) lineView.findViewById(R.id.textViewName);
        ImageView iv1=(ImageView)lineView.findViewById(R.id.imageView1);
        gridView = (LinearLayout) lineView.findViewById(R.id.gridViewArea);

        tv1.setText(gridlist.get(position).getCategoryName());
        iv1.setImageResource(gridlist.get(position).getProductsImages());

        return lineView;
    }
}
