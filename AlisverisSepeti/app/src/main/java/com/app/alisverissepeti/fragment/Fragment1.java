package com.app.alisverissepeti.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.alisverissepeti.R;
import com.app.alisverissepeti.activity.CategoryChoose;
import com.app.alisverissepeti.activity.MainActivity;
import com.app.alisverissepeti.adapter.ExpandListViewAdapter;
import com.app.alisverissepeti.database.Database;
import com.app.alisverissepeti.model.Category;
import com.app.alisverissepeti.model.Products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fragment1 extends Fragment {

    private ExpandableListView exv;
    private Button closePopup, nextButton;
    private LinearLayout addItemButton,linear,categoryDeleteButton;
    private EditText itemText, editPrice;
    private TextView total;
    public Fragment2 f2 = new Fragment2();
    private Database database;
    public ExpandListViewAdapter expand_adapter;
    public ArrayList<Products> parentList;
    public HashMap<Products, List<Category>> child_list;
    public ArrayList<Category> productList;
    public ArrayList<Products> listGrid;
    private ArrayList<String> categorgs;
    public ArrayList<Category> data;
    private int id;
    private String proName;
    private String catName;
    private String prices;
    private int s = 0;
    private int p;
    private int place=-1;
    private Context context;
    private int i = 0;
    public double total_price;

    public Fragment1() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_one, container, false);
        initObjects();
        listGrid = f2.grid();
        initViews(v);
        data=database.bringData();
        while (s < data.size()) {
            expandableListOperation(s);
            expand_adapter = new ExpandListViewAdapter(getActivity(), parentList, child_list);
            exv.setAdapter(expand_adapter);
            s++;
        }
        priceCalculus();
        dropDownButton(v);
        addItem(v);
        holdingDown();
        return v;
    }

    public void priceCalculus() {
        int x=0;
        double transformation=0.0;
        try {
            while (x<data.size()){
                String amount=data.get(x).getPrice();
                transformation=Double.parseDouble(amount);
                total_price+=transformation;
                x++;
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(),getResources().getString(R.string.error),Toast.LENGTH_SHORT).show();
        }
        total.setText(String.valueOf(total_price)+"  TL");

    }

    private void initObjects(){
        database = new Database(getActivity());
        data = new ArrayList<Category>();
        parentList = new ArrayList<Products>();
        child_list = new HashMap<Products, List<Category>>();
        categorgs=new ArrayList<String>();
    }

    private void holdingDown() {
        exv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.window_dialog);
                dialog.show();
                categoryDeleteButton = (LinearLayout) dialog.findViewById(R.id.categoryDelete);
                categoryDeleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        database.deleteCategory(data.get(position).getCategories());
                        Toast.makeText(getActivity(), data.get(position).getCategories() + " " + getResources().getString(R.string.category_deleted), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                return true;
            }
        });
    }

    private void dropDownButton(View v) {
        linear = (LinearLayout) v.findViewById(R.id.cleanLayout);
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popup = new PopupMenu(getActivity(), linear);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals(getResources().getString(R.string.all_delete))) {
                            dialogOperation(item.getTitle().toString(), v);
                        } else if (item.getTitle().equals(getResources().getString(R.string.category_deleted2))) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.category_deleted3), Toast.LENGTH_LONG).show();
                        } else {
                            Log.d("TAG", "Hata : dropDownButton");
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
    }



    private void initViews(View v) {
        exv = (ExpandableListView) v.findViewById(R.id.expandableListView);
        total=(TextView)v.findViewById(R.id.total_price);
    }

    private void addItem(View v) {
        addItemButton = (LinearLayout) v.findViewById(R.id.plustext);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.popup);
                dialog.setTitle(getResources().getString(R.string.addItem2));
                dialog.show();
                closePopup = (Button) dialog.findViewById(R.id.close_popup);
                closePopup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                nextButton = (Button) dialog.findViewById(R.id.addItem);
                editPrice = (EditText) dialog.findViewById(R.id.editPrice);
                itemText = (EditText) dialog.findViewById(R.id.edititem);
                nextButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        String productNames = itemText.getText().toString();
                        String productPrices = editPrice.getText().toString();

                        if (itemText.getText().toString().length()==0) {
                            itemText.setError(getResources().getString(R.string.addItem));
                        }else{
                            Intent intent = new Intent(getActivity(), CategoryChoose.class);
                            Bundle args = new Bundle();
                            args.putString("productName", productNames);
                            args.putString("productPrice", productPrices);
                            intent.putExtras(args);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    }

                });
            }

        });
    }

    private void expandableListOperation(int k) {
        p = 0;
        categorgs.add(data.get(k).getCategories());
        int w = 0;
        while (w < categorgs.size() - 1) {
            if (data.get(k).getCategories().equals(categorgs.get(w).trim())) {
                p = 1;
            }
            w++;
        }
        if (p==0){
            place++;
            for (i = 0; i < listGrid.size(); i++) {
                if (data.get(k).getCategories().equals(listGrid.get(i).getCategoryName())) {
                    parentList.add(new Products(listGrid.get(i).getCategoryName(), listGrid.get(i).getProductsImages()));
                    int h = 0;
                    productList = new ArrayList<Category>();
                    while (h < data.size()) {
                        if (data.get(k).getCategories().equals(data.get(h).getCategories())) {
                            productList.add(new Category(data.get(h).getProducts(), data.get(h).getCategories(), data.get(h).getPrice()));
                        }
                        h++;
                    }
                    child_list.put(parentList.get(place), productList);
                }
            }
        }
    }
    private void dialogOperation(final String item,View v) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
        alertDialog.setMessage(getResources().getString(R.string.deleted_question)).setCancelable(false).
                setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database.allDeleted();
                        Intent intent=new Intent(getActivity(),MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();
    }
}
