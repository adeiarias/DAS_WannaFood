package com.example.das_wannafood.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.das_wannafood.R;
import com.example.das_wannafood.adapters.AdapterRestaurantListView;
import com.example.das_wannafood.database.MiDB;
import com.example.das_wannafood.models.Restaurant;

import java.util.ArrayList;
import java.util.Iterator;

public class PlaceOrderActivity extends AppCompatActivity {

    private ListView list_rest;
    private AdapterRestaurantListView adaptadorRestaurantes;
    private EditText cityName;
    private MiDB db;
    private ArrayList<Restaurant> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_order);

        list_rest = findViewById(R.id.restaurant_list);
        cityName = findViewById(R.id.cityEdit);
        db = new MiDB(this, "App", null ,1);

        // Gestionar qué hacer cuando se haga click en un elemento de la lista de restaurantes
        list_rest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                createOrder(i);
            }
        });

    }

    private void createOrder(int i) {
        Intent intent = new Intent(this, OrderCreatorActivity.class);
        intent.putExtra("restaurant", lista.get(i).getName());
        startActivity(intent);
    }

    public void searchCity(View v) {
        if(cityName.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.camposVacios), Toast.LENGTH_SHORT).show();
        } else {
            lista = db.getRestaurantList(cityName.getText().toString().toLowerCase());
            if (lista.size() != 0) {
                String[][] arrayRestaurantes = getRestaurantDataInArray(lista);
                adaptadorRestaurantes = new AdapterRestaurantListView(getApplicationContext(), arrayRestaurantes[0], arrayRestaurantes[1]);
                list_rest.setAdapter(adaptadorRestaurantes);
            } else {
                Toast.makeText(this, getString(R.string.noresturants), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String[][] getRestaurantDataInArray(ArrayList<Restaurant> l) {
        Iterator<Restaurant> iter = l.iterator();
        Restaurant rest;
        // Array de dos filas (porque en cada fila de la lista hay una imagen y un texto
        // Y de n columnas (donde n es el número de restaurantes)
        String[][] arrayData = new String[2][l.size()];
        int i = 0; // para saber la posición del restaurante en la lista
        while(iter.hasNext()) {
            rest = iter.next();
            arrayData[0][i] = rest.getName(); // fila 0 de la matriz son los nombre de los restaurantes
            arrayData[1][i] = rest.getImage_path(); // fila 1 de la matriz son las rutas de las imágenes
            i++;
        }

        return arrayData;
    }
}