package com.example.das_wannafood.models;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Order {

    private String path;
    private String food;
    private Double price;

    public Order(Double pprice, String ppath, String pname) {
        path = ppath;
        food = pname;
        price = pprice;
    }

    public String getPath() {
        return path;
    }

    public String getFood() {
        return food;
    }

    public Double getPrice() {
        return price;
    }
}
