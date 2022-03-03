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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MiDB extends SQLiteOpenHelper {

    public MiDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // select r.id,f.name, res.name from restfood as r join food as f join restaurants res on r.id = f.id and r.id = res.id;
        // Creación de la tabla de los usuarios
        sqLiteDatabase.execSQL("create table users('id' integer primary key autoincrement not null, 'username' varchar(255) not null, 'email' varchar(255) not null, 'password' varchar(255) not null)");
        sqLiteDatabase.execSQL("create table restaurants('id' integer primary key autoincrement not null, 'name' varchar(255) not null, 'image_path' varchar(255) not null, 'city' varchar(255) not null)");
        sqLiteDatabase.execSQL("create table food('id' integer primary key autoincrement not null, 'name' varchar(255) not null, 'image_path' varchar(255) not null, 'price' real not null)");
        sqLiteDatabase.execSQL("create table restfood('id' integer primary key autoincrement not null, 'rest_id' integer not null, 'food_id' integer not null, foreign key(\"rest_id\") references restaurants(id), foreign key(\"food_id\") references food(id))");
        sqLiteDatabase.execSQL("create table orders('id' integer not null, 'user_id' integer not null, 'rest_id' integer not null, 'food_id' integer not null, 'pending' integer not null, 'price' real not null, foreign key(rest_id) references restaurants(id), foreign key(food_id) references food(id), foreign key(user_id) references users(id), primary key(id, rest_id, food_id, user_id))");

        sqLiteDatabase.execSQL("insert into restaurants(\"name\", \"image_path\", \"city\") values(\"Tagliatella\", \"tagliatella\", \"bilbao\")");
        sqLiteDatabase.execSQL("insert into restaurants(\"name\", \"image_path\", \"city\") values(\"Burger King\", \"burger\", \"bilbao\")");
        sqLiteDatabase.execSQL("insert into restaurants(\"name\", \"image_path\", \"city\") values(\"Tagliatella\", \"tagliatella\", \"barcelona\")");
        sqLiteDatabase.execSQL("insert into restaurants(\"name\", \"image_path\", \"city\") values(\"Burger King\", \"burger\", \"madrid\")");
        sqLiteDatabase.execSQL("insert into restaurants(\"name\", \"image_path\", \"city\") values(\"Tagliatella\", \"tagliatella\", \"madrid\")");
        sqLiteDatabase.execSQL("insert into restaurants(\"name\", \"image_path\", \"city\") values(\"Burger King\", \"burger\", \"sevilla\")");
        sqLiteDatabase.execSQL("insert into restaurants(\"name\", \"image_path\", \"city\") values(\"Tagliatella\", \"tagliatella\", \"sevilla\")");
        sqLiteDatabase.execSQL("insert into restaurants(\"name\", \"image_path\", \"city\") values(\"Burger King\", \"burger\", \"valencia\")");

        sqLiteDatabase.execSQL("insert into food(\"name\", \"image_path\", \"price\") values(\"Pizza\", \"pizza\", 8.99)");
        sqLiteDatabase.execSQL("insert into food(\"name\", \"image_path\", \"price\") values(\"Patatas\", \"patatas\", 2.99)");
        sqLiteDatabase.execSQL("insert into food(\"name\", \"image_path\", \"price\") values(\"Tarta\", \"tarta\", 3.99)");

        sqLiteDatabase.execSQL("insert into restfood(\"rest_id\", \"food_id\") values(1,1)");
        sqLiteDatabase.execSQL("insert into restfood(\"rest_id\", \"food_id\") values(1,3)");
        sqLiteDatabase.execSQL("insert into restfood(\"rest_id\", \"food_id\") values(2,2)");
        sqLiteDatabase.execSQL("insert into restfood(\"rest_id\", \"food_id\") values(2,3)");
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

    public ArrayList<Food> getFoodFromRestaurant(String restaurant_name) {
        ArrayList<Food> list = new ArrayList<Food>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("Select f.name, f.image_path, f.price from restfood as r join food as f join restaurants as res on r.food_id=f.id and r.rest_id=res.id where res.name='"+restaurant_name+"'", null);
        while(c.moveToNext()) {
            list.add(new Food(c.getString(0), c.getString(1), c.getFloat(2)));
        }
        return list;
    }

    public ArrayList<String> getPendingOrder(String username, String restaurant, String city) {
        // TO DO
        String id = "";
        String rest = "";
        String cityName="";
        ArrayList<String> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select o.id, r.name, r.city from orders as o join restaurants as r join users as u on o.rest_id = r.id and o.user_id = u.id where o.pending = 1 and u.username='"+username+"'", null);
        if(c.getCount() == 0) { // No hay ninguna orden creada, por lo que se podrá crear la orden
            System.out.println("AQUI0");
            return lista;
        } else {
            while(c.moveToNext()) {
                System.out.println("AQUI1");
                id=c.getString(0);
                rest=c.getString(1);
                cityName=c.getString(2);
                break;
            }
            if(!rest.equals(restaurant) || !cityName.equals(city)) { // Ya hay una orden del mismo restaurante, por lo que se añadirá el producto
                System.out.println("AQUI2");
                lista.add(id);
                lista.add(rest);
                lista.add(cityName);
            }
        }
        return lista;
    }

    public void createOrder(String id, String restaurant, String username, String food, Float price) {
        SQLiteDatabase db = getWritableDatabase();
        // Mirar que el producto que se quiere comprar no está ya seleccionado
        Cursor c = db.rawQuery("select o.id from orders as o join restaurants as r join users as u join food as f on o.user_id=u.id and o.rest_id=r.id and o.food_id=f.id where o.pending=1 and f.name='"+food+"'", null);
        if(c.getCount() == 0) {
            db.execSQL("insert into orders values("+Integer.parseInt(id)+", (select id from users where username='"+username+"'), (select id from restaurants where name='"+restaurant+"'), (select id from food where name='"+food+"'), 1, (select price from food where name='"+food+"'))");
        }
    }

    public void deletePedingOrder() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("orders", "pending=0", null);
    }

    public void finishPendingOrder() {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues modificacion = new ContentValues();
        modificacion.put("pending",1);
        db.update("orders", modificacion, "pending=0", null);
    }

}
