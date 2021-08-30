package com.app.alisverissepeti.model;

public class Products {

    private String categoryName;
    private int productsImages;

    public Products(String categorysName, int productsImages) {
        this.categoryName = categorysName;
        this.productsImages = productsImages;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getProductsImages() {
        return productsImages;
    }

    public void setProductsImages(int productsImages) {
        this.productsImages = productsImages;
    }
}
