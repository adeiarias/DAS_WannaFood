package com.example.das_wannafood.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.das_wannafood.models.Food;
import com.example.das_wannafood.models.Order;
import com.example.das_wannafood.models.Restaurant;
import com.example.das_wannafood.models.User;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import com.example.das_wannafood.R;

public class MiDB extends SQLiteOpenHelper {

    private InputStream fichero;

    public MiDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        fichero = context.getResources().openRawResource(R.raw.db_init);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Creación de la la base de datos
        readDatabaseFile(sqLiteDatabase);
    }

    private void readDatabaseFile(SQLiteDatabase sqLiteDatabase) {
        InputStream fich = fichero;
        BufferedReader buff = new BufferedReader(new InputStreamReader(fich));
        try {
            String line;
            while ((line = buff.readLine()) != null) {
                sqLiteDatabase.execSQL(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public ArrayList<Food> getFoodFromRestaurant(String restaurant_name, String city) {
        ArrayList<Food> list = new ArrayList<Food>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("Select f.name, f.image_path, f.price from restfood as r join food as f join restaurants as res on r.food_id=f.id and r.rest_id=res.id where res.name='"+restaurant_name+"' and res.city='"+city+"'", null);
        if(c.getCount() != 0) {
            while(c.moveToNext()) {
                list.add(new Food(c.getString(0), c.getString(1), c.getFloat(2)));
            }
        }
        System.out.println("LENGHTTHHTH:" + list.size());
        return list;
    }

    public ArrayList<String> getPendingOrder(String username, String restaurant, String city) {
        // TO DO
        String id = "";
        String rest = "";
        String cityName="";
        ArrayList<String> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select o.id, r.name, r.city from orders as o join restaurants as r join users as u on o.rest_id = r.id and o.user_id = u.id where u.username='"+username+"'", null);
        if(c.getCount() == 0) { // No hay ninguna orden creada, por lo que se podrá crear la orden
            System.out.println("AQUI0");
            return lista;
        } else {
            while(c.moveToNext()) {
                id=c.getString(0);
                rest=c.getString(1);
                cityName=c.getString(2);
                break;
            }
            if(!rest.equals(restaurant) || !cityName.equals(city)) { // Ya hay una orden del mismo restaurante, por lo que se añadirá el producto
                lista.add(id);
                lista.add(rest);
                lista.add(cityName);
            }
        }
        return lista;
    }

    public ArrayList<Order> getOrder(String username) {
        ArrayList<Order> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select f.price, f.image_path, f.name from users as u join orders as o join food as f on u.id = o.user_id and o.food_id = f.id where u.username = '"+username+"'", null);
        if(c.getCount() == 0) {
            return null;
        } else {
            while(c.moveToNext()) {
                Double price = c.getDouble(0);
                String path = c.getString(1);
                String name = c.getString(2);
                lista.add(new Order(price, path, name));
            }
        }
        return lista;
    }

    public Double getOrderPrice(String username) {
        Double price = 0.0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select sum(o.price) from orders as o join users as u on u.id = o.user_id where u.username = '"+username+"'", null);
        while(c.moveToNext()) {
            price = c.getDouble(0);
            break;
        }
        return price;
    }
    
    public String getOrderRestaurant(String username) {
        String restaurant = "";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select r.name from orders as o join users as u join restaurants as r on u.id = o.user_id and r.id = o.rest_id where u.username = '"+username+"'", null);
        while(c.moveToNext()) {
            restaurant = c.getString(0);
            break;
        }
        return restaurant;
    }

    public Double getTotalOrderPrice(String username) {
        Double price = 0.0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select sum(o.price) from orders as o join users u on u.id = o.user_id where u.username='"+username+"'", null);
        while(c.moveToNext()) {
            price = c.getDouble(0);
            break;
        }
        return price;
    }

    public void createOrder(String id, String restaurant, String username, String food, Float price) {
        SQLiteDatabase db = getWritableDatabase();
        // Mirar que el producto que se quiere comprar no está ya seleccionado
        Cursor c = db.rawQuery("select o.id from orders as o join restaurants as r join users as u join food as f on o.user_id=u.id and o.rest_id=r.id and o.food_id=f.id where f.name='"+food+"'", null);
        if(c.getCount() == 0) {
            db.execSQL("insert into orders values("+Integer.parseInt(id)+", (select id from users where username='"+username+"'), (select id from restaurants where name='"+restaurant+"'), (select id from food where name='"+food+"'), (select price from food where name='"+food+"'))");
        }
    }

    public void deleteOrder(String username) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from orders where user_id=(select id from users where username='"+username+"')");
    }
}
