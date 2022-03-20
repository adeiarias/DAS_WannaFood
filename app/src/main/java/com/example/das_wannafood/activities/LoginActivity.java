package com.example.das_wannafood.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.das_wannafood.R;
import com.example.das_wannafood.database.MiDB;
import com.example.das_wannafood.models.User;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    private EditText user_text;
    private EditText pass_text;
    private ImageView logo;
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

        setContentView(R.layout.login_page);

        user_text = findViewById(R.id.uname_login);
        pass_text = findViewById(R.id.passwd_login);
        logo = findViewById(R.id.imageView);

        // Añadir el logo de la aplicación al inicio
        logo.setImageResource(getApplicationContext().getResources().getIdentifier("logo", "drawable", getApplicationContext().getPackageName()));

        db = new MiDB(this, "App", null ,1);
    }

    // En este método se verificarán las credenciales de los usuarios
    public void onLogin(View v) {
        String username = user_text.getText().toString();
        String password = pass_text.getText().toString();

        // Los campos del login no pueden estar vacios
        if(username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, getString(R.string.camposVacios), Toast.LENGTH_SHORT).show();
        } else {
            User user = db.login(username, password);

            // Credenciales incorrectas
            if(user == null) {
                Toast.makeText(this, getString(R.string.invalidCredentials), Toast.LENGTH_SHORT).show();
            } else { // Credenciales correctas, pasar a la siguiente actividad
                Intent intent = new Intent(this, PlaceOrderActivity.class);
                intent.putExtra("username", user.getUsername());
                startActivity(intent);
                finish();
            }
        }
    }

    // Botón para crear un nuevo usuario
    public void onCreateAccount(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}