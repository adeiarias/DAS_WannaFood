package com.example.das_wannafood.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.das_wannafood.R;
import com.example.das_wannafood.database.MiDB;

public class RegisterActivity extends AppCompatActivity {

    private EditText user_text;
    private EditText pass_text;
    private EditText email_text;
    private MiDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        user_text = findViewById(R.id.uname_register);
        pass_text = findViewById(R.id.passwd_register);
        email_text = findViewById(R.id.email_register);

        db = new MiDB(this, "App", null ,1);
    }

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
                db.insertUser(username, email, password);
                Toast.makeText(this, getString(R.string.userCreated), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("username_register", user_text.getText().toString());
        outState.putString("email_register", email_text.getText().toString());
        outState.putString("password_register", pass_text.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        user_text.setText(savedInstanceState.getString("username_register"));
        pass_text.setText(savedInstanceState.getString("password_register"));
        email_text.setText(savedInstanceState.getString("email_register"));
    }
}