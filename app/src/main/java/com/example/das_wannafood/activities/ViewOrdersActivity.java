package com.example.das_wannafood.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.das_wannafood.R;

public class ViewOrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_orders);
    }

    // Ir a la actividad de pedir comida
    public void pedidoActual(View v) {
        Intent intent = new Intent(this, ActualOrderActivity.class);
        startActivity(intent);
    }

    // Ir a la actividad de mirar los pedidos
    public void antiguosPedidos(View v) {
        Intent intent = new Intent(this, PreviousOrderActivity.class);
        startActivity(intent);
    }
}