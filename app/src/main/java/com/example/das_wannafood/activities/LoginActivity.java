package com.example.das_wannafood.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.das_wannafood.R;
import com.example.das_wannafood.database.MiDB;
import com.example.das_wannafood.models.User;

public class LoginActivity extends AppCompatActivity {

    EditText user_text;
    EditText pass_text;
    MiDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        user_text = findViewById(R.id.uname_login);
        pass_text = findViewById(R.id.passwd_login);

        db = new MiDB(this, "App", null ,1);
    }

    public void onLogin(View v) {
        String username = user_text.getText().toString();
        String password = pass_text.getText().toString();

        // Los campos del login no pueden estar vacios
        if(username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, getString(R.string.camposVacios), Toast.LENGTH_SHORT).show();
        }

        User user = db.login(username, password);

        // Credenciales incorrectas
        if(user == null) {
            Toast.makeText(this, getString(R.string.invalidCredentials), Toast.LENGTH_SHORT).show();
        } else { // Credenciales correctas, pasar a la siguiente actividad
            Intent intent = new Intent(this, MainPageActivity.class);
            intent.putExtra("username", user.getUsername());
            startActivity(intent);
            finish();
        }
    }

    // Botón para crear un nuevo usuario
    public void onCreateAccount(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}