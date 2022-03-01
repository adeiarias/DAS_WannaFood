package com.example.das_wannafood.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.das_wannafood.R;

public class MainPageActivity extends AppCompatActivity {

    private TextView current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        current_user = findViewById(R.id.welcome_text);

        Bundle extra_values = getIntent().getExtras();
        //current_user.setText(current_user.getText().toString() + " " + extra_values.getString("username"));
    }

    // Cerrar sesión
    public void onLogOut(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        Toast.makeText(this, getString(R.string.logout), Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }

    public void onPrefs(View v) {

    }

    // Ir a la actividad de pedir comida
    public void place_order(View v) {
        Intent intent = new Intent(this, PlaceOrderActivity.class);
        startActivity(intent);
    }

    // Ir a la actividad de mirar los pedidos
    public void view_orders(View v) {
        Intent intent = new Intent(this, ViewOrdersActivity.class);
        startActivity(intent);
    }
}