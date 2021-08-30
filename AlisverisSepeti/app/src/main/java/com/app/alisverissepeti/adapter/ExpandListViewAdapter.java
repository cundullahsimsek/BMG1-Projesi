package com.app.alisverissepeti.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.alisverissepeti.R;
import com.app.alisverissepeti.activity.MainActivity;
import com.app.alisverissepeti.database.Database;
import com.app.alisverissepeti.model.Category;
import com.app.alisverissepeti.model.Products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandListViewAdapter extends BaseExpandableListAdapter {

    public HashMap<Products, List<Category>> child;
    public ArrayList<Products> parent;
    public Context context;
    public TextView txt;
    public TextView txt_child_names, txt_child_price;
    public LinearLayout deleteImage;
    public ImageView imageView;
    public LayoutInflater inflater;
    public ArrayList<Object> item = new ArrayList<Object>();
    public Database database;
    private String selectedDataName;
    private String selectedDataPrice;
    private String selectedDataCategory;

    public ExpandListViewAdapter(Context context, ArrayList<Products> parentList, HashMap<Products, List<Category>> child_list) {
        this.context = context;
        this.parent = parentList;
        this.child = child_list;
        database = new Database(context);
    }

    @Override
    public int getGroupCount() {
        return this.parent.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.child.get(this.parent.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.parent.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child.get(this.parent.get(groupPosition)).get(childPosition);


    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        Products title_name = (Products) getGroup(groupPosition);
        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_header, null);
        }


        txt = (TextView) view.findViewById(R.id.txt_parent);
        imageView = (ImageView) view.findViewById(R.id.imageViewParent);
        txt.setText(title_name.getCategoryName());
        imageView.setImageResource(title_name.getProductsImages());

        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View view, final ViewGroup parent) {
        Category txt_child_name = (Category) getChild(groupPosition, childPosition);
        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_child, null);
        }
        view.setBackgroundColor(Color.parseColor("#ffffff"));
        txt_child_names = (TextView) view.findViewById(R.id.txt_items);
        txt_child_price = (TextView) view.findViewById(R.id.txt_price);

        txt_child_names.setText(txt_child_name.getProducts());
        if (txt_child_name.getPrice().length() != 0) {
            txt_child_price.setText(txt_child_name.getPrice() + "   TL");
        }
        deleteProductsName(view, groupPosition, childPosition);
        return view;
    }

    private void deleteProductsName(View view, final int groupPosition, final int childPosition) {
        deleteImage = (LinearLayout)view.findViewById(R.id.imageDelete);

        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage(v.getResources().getString(R.string.sure));
                builder.setCancelable(false);
                builder.setPositiveButton(v.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedDataName = ((Category) getChild(groupPosition, childPosition)).getProducts().toString();
                        selectedDataPrice = ((Category) getChild(groupPosition, childPosition)).getPrice().toString();
                        selectedDataCategory = ((Category) getChild(groupPosition, childPosition)).getCategories().toString();

                        database.deleteContact(selectedDataName, selectedDataCategory, selectedDataPrice);
                        Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);


                    }
                });
                builder.setNegativeButton(v.getResources().getString(R.string.no),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
