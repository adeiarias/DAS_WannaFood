package com.example.das_wannafood.models;

public class Food {

    private String name;
    private String image;
    private float price;

    public Food(String pname, String pimage, float pprice) {
        this.name = pname;
        this.image = pimage;
        this.price = pprice;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float pprice) {
        this.price = pprice;
    }
}
