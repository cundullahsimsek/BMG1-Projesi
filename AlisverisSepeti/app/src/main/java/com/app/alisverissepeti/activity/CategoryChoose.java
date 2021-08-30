package com.app.alisverissepeti.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.alisverissepeti.R;
import com.app.alisverissepeti.adapter.GridViewAdapter;
import com.app.alisverissepeti.database.Database;
import com.app.alisverissepeti.fragment.Fragment2;
import com.app.alisverissepeti.model.Products;

import java.util.ArrayList;

public class CategoryChoose extends AppCompatActivity {

    GridView gridView;
    TextView productNameFirst;
    Button backButton;

    public ArrayList<Products> listGrid;
    private String productNames, productPrices;
    private Database database;
    public Fragment2 f2 = new Fragment2();
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_choose);

        database = new Database(this);
        initViews();
        getProducts();
        listGrid = f2.grid();
        productNameFirst.setText(productNames);
        GridViewAdapter adapter = new GridViewAdapter(CategoryChoose.this, listGrid);
        gridView = (GridView) findViewById(R.id.grid_view_adapter);
        gridView.setAdapter(adapter);
        back();
        selectedGridView();
    }

    private void initViews() {
        productNameFirst = (TextView) findViewById(R.id.selected_product);
        backButton = (Button) findViewById(R.id.backButton);
    }

    private void getProducts() {
        Bundle args = getIntent().getExtras();
        productNames = args.getString("productName");
        productPrices = args.getString("productPrice");
    }

    private void back() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryChoose.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void selectedGridView() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setMessage(getResources().getString(R.string.add_data)).setCancelable(false).
                        setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                database.AddRecord(listGrid.get(position).getCategoryName().toString(), productNames, productPrices);
                                Toast.makeText(CategoryChoose.this, productNames + " "+getResources().getString(R.string.list_added), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CategoryChoose.this, MainActivity.class);
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
        });
    }
}