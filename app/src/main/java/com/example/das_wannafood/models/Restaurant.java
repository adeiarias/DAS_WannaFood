package com.example.das_wannafood.models;

public class Restaurant {

    private String name;
    private String image_path;
    private String city;

    public Restaurant(String name, String image_path, String city) {
        this.name = name;
        this.image_path = image_path;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public String getImage_path() {
        return image_path;
    }

    public String getCity() {
        return city;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
