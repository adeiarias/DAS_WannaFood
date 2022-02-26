package com.example.das_wannafood.models;

public class Food {

    private String name;
    private String image;

    public Food(String pname, String pimage) {
        this.name = pname;
        this.image = pimage;
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
}
