package com.example.das_wannafood.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.das_wannafood.R;
import com.example.das_wannafood.adapters.AdapterRecycler;
import com.example.das_wannafood.adapters.AdapterRestaurantListView;
import com.example.das_wannafood.adapters.ElViewHolder;
import com.example.das_wannafood.database.MiDB;
import com.example.das_wannafood.dialogs.DialogoPedidoPendiente;
import com.example.das_wannafood.fragments.Food_order_fragment;
import com.example.das_wannafood.fragments.PlaceOrderFragment;
import com.example.das_wannafood.models.Food;
import com.example.das_wannafood.models.Order;
import com.example.das_wannafood.models.Restaurant;

import java.util.ArrayList;
import java.util.Iterator;

public class OrderCreatorActivity extends AppCompatActivity implements Food_order_fragment.FoodListenerFragment{

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
            food_order_fragment = (Food_order_fragment) getSupportFragmentManager().findFragmentById(R.id.fragment_order_horiz);
        }
    }

    @Override
    public void pedidoPendiente(String username) {
        System.out.println("entra?");
        Intent intent = new Intent(this, PlaceOrderActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }
}