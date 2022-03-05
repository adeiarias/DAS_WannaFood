package com.example.das_wannafood.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.das_wannafood.R;
import com.example.das_wannafood.adapters.AdapterRestaurantListView;
import com.example.das_wannafood.database.MiDB;
import com.example.das_wannafood.fragments.Food_order_fragment;
import com.example.das_wannafood.fragments.PlaceOrderFragment;
import com.example.das_wannafood.models.Restaurant;

import java.util.ArrayList;
import java.util.Iterator;

public class PlaceOrderActivity extends AppCompatActivity implements PlaceOrderFragment.PlaceOrderListenerFragment {

    private PlaceOrderFragment placeOrderFragment; // Fragment para elegir restaurante
    private Food_order_fragment food_order_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_order);

        // Inicializar el fragment en función de su orientación
        int orientacion = getResources().getConfiguration().orientation;
        if(orientacion == Configuration.ORIENTATION_PORTRAIT) {
            placeOrderFragment = (PlaceOrderFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPortrait);
        } else {
            placeOrderFragment = (PlaceOrderFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentLandscape);
        }

    }

    @Override
    public void seleccionarRestaurantes(String username, String restaurant, String city) {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            // Si la orientación es landscape, se obtiene el fragment de food_order
            food_order_fragment = (Food_order_fragment) getSupportFragmentManager().findFragmentById(R.id.fragment_order_horiz);
        }
        else {
            // Mirar si es landscape or potrait
            Intent intent = new Intent(this, OrderCreatorActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("restaurant", restaurant);
            intent.putExtra("city", city);
            startActivity(intent);
        }
    }
}