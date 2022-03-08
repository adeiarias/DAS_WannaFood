package com.example.das_wannafood.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.example.das_wannafood.R;
import com.example.das_wannafood.dialogs.DialogoPedidoPendiente;
import com.example.das_wannafood.fragments.Food_order_fragment;

public class OrderCreatorActivity extends AppCompatActivity implements Food_order_fragment.FoodListenerFragment, DialogoPedidoPendiente.ListenerdelDialogo{

    private Food_order_fragment food_order_fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_order);

        // Inicializar el fragment en función de su orientación
        int orientacion = getResources().getConfiguration().orientation;
        if(orientacion == Configuration.ORIENTATION_PORTRAIT) {
            food_order_fragment = (Food_order_fragment) getSupportFragmentManager().findFragmentById(R.id.fragment_order_verti);
        } else {
            food_order_fragment = (Food_order_fragment) getSupportFragmentManager().findFragmentById(R.id.order_fragment_horiz);
        }
    }

    @Override
    public void hayPedidoPediente(String username) {
        Intent intent = new Intent(this, PlaceOrderActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }

    @Override
    public void pedidoPendiente() {
        food_order_fragment.pedidoPendiente();
    }
}