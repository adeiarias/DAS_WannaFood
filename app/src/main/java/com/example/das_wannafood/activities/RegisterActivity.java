package com.example.das_wannafood.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.das_wannafood.R;
import com.example.das_wannafood.database.MiDB;

import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private EditText user_text;
    private EditText pass_text;
    private EditText email_text;
    private MiDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Acceder a las preferencias para conseguir el valor de la clave del idioma
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String idioma = prefs.getString("idioma", "es");

        // Cambiar el idioma
        Locale nuevaloc = new Locale(idioma);
        Locale.setDefault(nuevaloc);
        Configuration configuration = getBaseContext().getResources().getConfiguration();
        configuration.setLocale(nuevaloc);
        configuration.setLayoutDirection(nuevaloc);

        Context context = getBaseContext().createConfigurationContext(configuration);
        getBaseContext().getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());

        setContentView(R.layout.register_page);

        user_text = findViewById(R.id.uname_register);
        pass_text = findViewById(R.id.passwd_register);
        email_text = findViewById(R.id.email_register);

        db = new MiDB(this, "App", null ,1);
    }

    // Método que se usa para crear un registro de un usuario
    public void onRegister(View v) {
        String username = user_text.getText().toString();
        String password = pass_text.getText().toString();
        String email = email_text.getText().toString();

        // Los campos del registro no pueden estar vacios
        if(username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, getString(R.string.camposVacios), Toast.LENGTH_SHORT).show();
        } else {
            // Comprobar que el usuario no existe
            if(db.userExist(username)) {
                Toast.makeText(this, getString(R.string.userExist), Toast.LENGTH_SHORT).show();
            } else {
                // Si el usuario no existiera, se añade a la base de datos
                db.insertUser(username, email, password);
                Toast.makeText(this, getString(R.string.userCreated), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}