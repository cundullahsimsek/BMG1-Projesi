package com.app.alisverissepeti.model;

public class Category {
    public String products;
    public String categories;
    public String price;


    public Category(String products, String categories, String price) {
        this.products = products;
        this.categories = categories;
        this.price = price;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
