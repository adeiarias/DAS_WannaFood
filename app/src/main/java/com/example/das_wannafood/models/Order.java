package com.example.das_wannafood.models;

public class Order {

    private String id;
    private String restaurant;
    private String food;
    private String city;
    private String user;

    public String getId() {
        return id;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public String getFood() {
        return food;
    }

    public String getCity() {
        return city;
    }

    public String getUser() {
        return user;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
