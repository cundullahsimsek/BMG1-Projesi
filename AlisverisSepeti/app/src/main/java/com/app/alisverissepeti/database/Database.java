package com.app.alisverissepeti.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.alisverissepeti.model.Category;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    public ArrayList<Category> data;
    private static final String DATABASE_NAME = "DatabaseList.db";
    private static final int DATABASE_VERSION = 1;

    public Database(Context con) {
        super(con, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE products (id INTEGER PRIMARY KEY AUTOINCREMENT,productsName TEXT,catogoryNAME TEXT,price TEXT);";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS products");
        onCreate(db);
    }

    public void deleteContact(String name, String category, String price) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM products WHERE " + "productsName='" + name + "' AND catogoryNAME='" + category + "' AND price='" + price + "'");
        db.close();
    }

    public void deleteCategory(String category_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM products WHERE " + "catogoryNAME='" + category_name + "'");
        db.close();
    }

    public void AddRecord(String categoryName, String productNames, String productPrices) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("productsName", productNames);
        content.put("price", productPrices);
        content.put("catogoryNAME", categoryName);
        db.insertOrThrow("products", null, content);

    }

    public ArrayList<Category> bringData() {
        data = new ArrayList<Category>();
        String[] sutunlar = {"id", "productsName", "catogoryNAME", "price"};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorData = db.query("products", sutunlar, null, null, null, null, null);
        while (cursorData.moveToNext()) {
            int id = cursorData.getInt(cursorData.getColumnIndex("id"));
            String proName = cursorData.getString(cursorData.getColumnIndex("productsName"));
            String catName = cursorData.getString(cursorData.getColumnIndex("catogoryNAME"));
            String prices = cursorData.getString(cursorData.getColumnIndex("price"));
            data.add(new Category(proName, catName, prices));
        }
        return data;
    }

    public void allDeleted() {
        SQLiteDatabase db =this.getWritableDatabase();
        db.delete("products",null, null);
        db.close();
    }
}
