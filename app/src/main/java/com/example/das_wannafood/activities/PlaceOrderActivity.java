package com.example.das_wannafood.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.das_wannafood.R;
import com.example.das_wannafood.dialogs.DialogoPedidoPendiente;
import com.example.das_wannafood.fragments.Food_order_fragment;
import com.example.das_wannafood.fragments.Food_order_fragment_horizontal;
import com.example.das_wannafood.fragments.PlaceOrderFragment;

import java.util.Locale;

public class PlaceOrderActivity extends AppCompatActivity implements PlaceOrderFragment.PlaceOrderListenerFragment, Food_order_fragment_horizontal.FoodListenerFragmentHorizontal, DialogoPedidoPendiente.ListenerdelDialogo {

    private PlaceOrderFragment placeOrderFragment; // Fragment para elegir restaurante
    private Food_order_fragment food_order_fragment;
    private Food_order_fragment_horizontal food_order_fragment_horizontal;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Acceder a las preferencias para conseguir el valor de la clave del idioma
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String idioma = prefs.getString("idioma", "es");
        System.out.println(idioma);

        // Cambiar el idioma
        Locale nuevaloc = new Locale(idioma);
        Locale.setDefault(nuevaloc);
        Configuration configuration = getBaseContext().getResources().getConfiguration();
        configuration.setLocale(nuevaloc);
        configuration.setLayoutDirection(nuevaloc);

        Context context = getBaseContext().createConfigurationContext(configuration);
        getBaseContext().getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());

        setContentView(R.layout.place_order);

        username = getIntent().getExtras().getString("username");

        // Asignar del Toolbar personalizado
        setSupportActionBar(findViewById(R.id.toolbarPrincipal));

        // Inicializar el fragment en función de su orientación
        int orientacion = getResources().getConfiguration().orientation;
        if(orientacion == Configuration.ORIENTATION_PORTRAIT) {
            placeOrderFragment = (PlaceOrderFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPortrait);
        } else {
            food_order_fragment_horizontal = (Food_order_fragment_horizontal) getSupportFragmentManager().findFragmentById(R.id.order_fragment_horiz);
            placeOrderFragment = (PlaceOrderFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentLandscape);
        }
    }

    @Override
    public void seleccionarRestaurantes(String username, String restaurant, String city) {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            // Si la orientación es landscape, se obtiene el fragment de food_order
            food_order_fragment_horizontal.initializeTable(username, restaurant, city);
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
            intent.putExtra("username", username);
            startActivity(intent);

        } else if (id == R.id.preferences) { // Si se ha pulsado preferencias, se podrán gestionar las preferencias de la app
            Intent i = new Intent (this, GestionPreferencias.class);
            startActivity(i);

        } else if (id == R.id.logout) { // Si se ha pulsado logout, se volverá a la pantalla del login
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void pedidoPendiente() {
        food_order_fragment_horizontal.pedidoPendiente();
    }

    @Override
    public void hayPedidoPedienteHorizontal(String username) {

    }

    // Gestionar que no se pierda la información cuando se cambia la orientación del dispositivo
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Se va a guardar el texto que se haya introduccido en el editText del fragment de placeOrder
        outState.putString("cityName", placeOrderFragment.getEditTextString());
        //outState.putInt("count", placeOrderFragment.getListViewItemCount()); // Conseguir el número de elementos que hay en el listview
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Recuperamos el string guardado cuando se ha cambiado la orientación y se vuelven a mostrar los elementos si proceden
        if(savedInstanceState != null) {
            String cityName = savedInstanceState.getString("cityName");
            if(!cityName.isEmpty()) {
                //int listViewCount = savedInstanceState.getInt("count");
                //if(listViewCount != 0) { // Si no hay elementos en el listview no se hará la petición a la base de datos
                placeOrderFragment.setEditTextString(cityName);
                placeOrderFragment.searchEvent(cityName);
                //}
            }
        }
    }
}