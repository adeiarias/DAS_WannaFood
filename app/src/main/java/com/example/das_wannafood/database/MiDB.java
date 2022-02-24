package com.example.das_wannafood.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.das_wannafood.models.User;

public class MiDB extends SQLiteOpenHelper {

    public MiDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Creación de la tabla de los usuarios
        sqLiteDatabase.execSQL("create table users('id' integer primary key autoincrement not null, 'username' varchar(255) not null, 'email' varchar(255) not null, 'password' varchar(255) not null)");
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
}
