package com.example.das_wannafood.models;

public class Food {

    private String name;
    private String image;
    private String price;

    public Food(String pname, String pimage, String pprice) {
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String pprice) {
        this.price = pprice;
    }
}
