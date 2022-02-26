package com.example.das_wannafood.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.das_wannafood.models.Food;
import com.example.das_wannafood.models.Restaurant;
import com.example.das_wannafood.models.User;

import java.util.ArrayList;

public class MiDB extends SQLiteOpenHelper {

    public MiDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Creación de la tabla de los usuarios
        sqLiteDatabase.execSQL("create table users('id' integer primary key autoincrement not null, 'username' varchar(255) not null, 'email' varchar(255) not null, 'password' varchar(255) not null)");
        sqLiteDatabase.execSQL("create table restaurants('id' integer primary key autoincrement not null, 'name' varchar(255) not null, 'image_path' varchar(255) not null, 'city' varchar(255) not null)");
        sqLiteDatabase.execSQL("create table food('id' integer primary key autoincrement not null, 'name' varchar(255) not null, 'image_path' integer not null)");
        sqLiteDatabase.execSQL("create table restfood('id' integer primary key autoincrement not null, 'rest_name' varchar(255) not null, 'food_name' varchar(255) not null, foreign key(rest_name) references restaurants(name), foreign key(food_name) references food(name))");
        sqLiteDatabase.execSQL("create table orders('id' integer not null, 'username' varchar(255) not null, 'rest_name' varchar(255) not null, 'food_name' varchar(255) not null, foreign key(rest_name) references restaurants(name), foreign key(food_name) references food(name), foreign key(username) references users(username), primary key(id, rest_name, food_name, username))");


        sqLiteDatabase.execSQL("insert into restaurants(\"name\", \"image_path\", \"city\") values(\"Tagliatella\", \"tagliatella\", \"bilbao\")");
        sqLiteDatabase.execSQL("insert into restaurants(\"name\", \"image_path\", \"city\") values(\"Burger King\", \"burger\", \"bilbao\")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    // Al hacer el registro, comprobar que el usuario no exista
    public Boolean userExist(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("Select username from users where username='" + username + "'", null);
        if(c.getCount() == 0) {
            return false;
        } else {
            return true;
        }
    }

    // Al hacer el registro, insertar un nuevo usuario a la base de datos
    public void insertUser(String username, String email, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("email", email);
        contentValues.put("password", password);
        db.insert("users", null, contentValues);
    }

    // Comprobar las credenciales del usuarios
    public User login(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("Select username from users where username='" + username + "' and password='" + password + "'", null);
        if(c.getCount() == 0) {
            return null;
        } else {
            return new User(username);
        }
    }

    // Conseguir todos los restaurantes (nombre, path de la imagen y su ciudad)
    public ArrayList<Restaurant> getRestaurantList(String city) {
        ArrayList<Restaurant> list = new ArrayList<Restaurant>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("Select name,image_path,city from restaurants where city='" + city + "'", null);
        while(c.moveToNext()) {
            list.add(new Restaurant(c.getString(0), c.getString(1), c.getString(2)));
        }
        return list;
    }

    /*public ArrayList<Food> getFoodFromRestaurant(String restaurant_name) {
        ArrayList<Food> list = new ArrayList<Food>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("Select f.name, f.image_path from ")
    }*/
}
