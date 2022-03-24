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
import com.example.das_wannafood.fragments.Food_Place_Order_Fragment;
import com.example.das_wannafood.fragments.Food_order_fragment;
import com.example.das_wannafood.fragments.PlaceOrderFragment;

import java.util.Locale;

public class PlaceOrderActivity extends AppCompatActivity implements PlaceOrderFragment.PlaceOrderListenerFragment {

    private PlaceOrderFragment placeOrderFragment; // Fragment para elegir restaurante
    private Food_Place_Order_Fragment food_place_order_fragment;
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
            placeOrderFragment = (PlaceOrderFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentLandscape);
        }
    }

    // Este método se hereda de PlaceOrderFragment. Cuando se pulsa el botón search, se cogerá el input del
    // textfield y se buscarán los restaurantes de esa ciudad en la base de datos
    @Override
    public void seleccionarRestaurantes(String username, String restaurant, String city) {
        int orientation = getResources().getConfiguration().orientation;
        // Si la orientación del dispositivo es landscape, se inicializarán dos fragments, uno para mostrar
        // lista de los restaurante de una ciudad y el otro para mostrar la comidad de un restaurante, cuando
        // se seleccione el restaurante en la lista
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            // Si la orientación es landscape, se obtiene el fragment de food_order
            food_place_order_fragment = (Food_Place_Order_Fragment) getSupportFragmentManager().findFragmentById(R.id.order_fragment_horiz);
            food_place_order_fragment.initializeTable(username, restaurant, city);
        }
        // Si la orientación es portrait, solo estará el fragment que se ha inicializado al principio
        // (el que muestra la lista de los resturantes), y cuando se haga click en un restaurante, se
        // inicializará una nueva actividad
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
        // Acceder al carrito de la compra
        if (id == R.id.shopping) { // Si se ha pulsado shopping, se creará una actividad para mostrar el pedido actual
            Intent intent = new Intent(this, ActualOrderActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        //Acceder a la pestaña de preferencias
        } else if (id == R.id.preferences) { // Si se ha pulsado preferencias, se podrán gestionar las preferencias de la app
            Intent i = new Intent (this, GestionPreferencias.class);
            i.putExtra("username", username);
            startActivity(i);

        // Logout de la aplicación, volver a la actividad del login
        } else if (id == R.id.logout) { // Si se ha pulsado logout, se volverá a la pantalla del login
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    // Gestionar que no se pierda la información cuando se cambia la orientación del dispositivo
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Se va a guardar el texto que se haya introduccido en el editText del fragment de placeOrder
        outState.putString("cityName", placeOrderFragment.getEditTextString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Recuperamos el string guardado cuando se ha cambiado la orientación y se vuelven a mostrar los elementos si proceden
        if(savedInstanceState != null) {
            String cityName = savedInstanceState.getString("cityName");
            if(!cityName.isEmpty()) { // Si el EditText estuviera vacio no se hace nada
                placeOrderFragment.setEditTextString(cityName);
                placeOrderFragment.searchEvent(cityName);
            }
        }
    }
}