package com.example.das_wannafood.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.das_wannafood.R;
import com.example.das_wannafood.adapters.AdapterRestaurantListView;
import com.example.das_wannafood.database.MiDB;
import com.example.das_wannafood.fragments.ActualOrder_fragment;
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

        // Asignar del Toolbar personalizado
        setSupportActionBar(findViewById(R.id.toolbarPrincipal));

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

    // Método para asignar menu.xml con la definición del menú al Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // Método para gestionar la interacción del usuario ante cualquier elección del menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.shopping) { // Si se ha pulsado shopping, se creará una actividad para mostrar el pedido actual
            Intent intent = new Intent(this, ActualOrderActivity.class);
            startActivity(intent);
            /*Intent i = new Intent (this, FavoritosActivity.class);
            i.putExtra("usuario", usuario);
            startActivity(i);*/

            System.out.println("hola");
        } else if (id == R.id.preferences) { // Si se ha pulsado preferencias, se podrán gestionar las preferencias de la app
            /*Intent i = new Intent (this, VerMasTardeActivity.class);
            i.putExtra("usuario", usuario);
            startActivity(i);*/
            System.out.println("hola");
        } else if (id == R.id.logout) { // Si se ha pulsado logout, se volverá a la pantalla del login
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}