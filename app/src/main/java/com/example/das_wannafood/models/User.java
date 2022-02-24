package com.example.das_wannafood.models;

public class User {

    // No se necesita crear un atributo de contraseña
    private String username;

    public User(String uname) {
        this.username = uname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
