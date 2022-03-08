package com.example.das_wannafood.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.das_wannafood.R;
import com.example.das_wannafood.activities.OrderCreatorActivity;
import com.example.das_wannafood.adapters.AdapterRestaurantListView;
import com.example.das_wannafood.database.MiDB;
import com.example.das_wannafood.models.Restaurant;

import java.util.ArrayList;
import java.util.Iterator;

public class PlaceOrderFragment extends Fragment {

    private ListView list_rest;
    private AdapterRestaurantListView adaptadorRestaurantes;
    private EditText cityName;
    private MiDB db;
    private ArrayList<Restaurant> lista;
    private String username;

    private PlaceOrderListenerFragment listener;

    public interface PlaceOrderListenerFragment {
        void seleccionarRestaurantes(String username, String restaurant, String city);
    }

    // Une el listener con los métodos implementados en la actividad
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (PlaceOrderListenerFragment) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException("La clase " + context.toString() + "debe implementar listenerDelFragment");
        }
    }

    // Enlazar el fragment con el xml adecuado
    @Override
    public View onCreateView(@Nullable  LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.place_order_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Se inicializan los elementos necesarios del layoout
        Bundle extra_values = getActivity().getIntent().getExtras();
        username = extra_values.getString("username");

        list_rest = getView().findViewById(R.id.restaurant_list);
        cityName = getView().findViewById(R.id.cityEdit);
        db = new MiDB(getActivity().getApplicationContext(), "App", null ,1);

        Button search = (Button) getView().findViewById(R.id.btn_search_city);
        // Qué hacer cuando se haga click en el botón
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cityName.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), getString(R.string.camposVacios), Toast.LENGTH_SHORT).show();
                } else {
                    lista = db.getRestaurantList(cityName.getText().toString().toLowerCase());
                    if (lista.size() != 0) {
                        String[][] arrayRestaurantes = getRestaurantDataInArray(lista);
                        adaptadorRestaurantes = new AdapterRestaurantListView(getActivity(), arrayRestaurantes[0], arrayRestaurantes[1]);
                        list_rest.setAdapter(adaptadorRestaurantes);
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.noresturants), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Gestionar qué hacer cuando se haga click en un elemento de la lista de restaurantes
        list_rest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                createOrder(i);
            }
        });
    }

    private void createOrder(int i) {
        listener.seleccionarRestaurantes(username, lista.get(i).getName(), cityName.getText().toString());
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