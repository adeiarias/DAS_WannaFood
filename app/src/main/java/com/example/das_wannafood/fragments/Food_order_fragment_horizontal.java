package com.example.das_wannafood.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.das_wannafood.R;
import com.example.das_wannafood.adapters.AdapterRecycler;
import com.example.das_wannafood.adapters.ElViewHolder;
import com.example.das_wannafood.database.MiDB;
import com.example.das_wannafood.dialogs.DialogoPedidoPendiente;
import com.example.das_wannafood.models.Food;

import java.util.ArrayList;
import java.util.Iterator;

public class Food_order_fragment_horizontal extends Fragment implements ElViewHolder.onFoodListener {

    private TextView restaurant_name;
    private RecyclerView recycler;
    private Button btn_order;
    private MiDB db;
    private ArrayList<Food> lista; //lista en la que se almacenará la comida del restaurante
    private AdapterRecycler eladaptador;
    private String username;
    private String city;

    private Food_order_fragment_horizontal.FoodListenerFragmentHorizontal listener;

    public interface FoodListenerFragmentHorizontal {
        void hayPedidoPedienteHorizontal(String username);
    }

    // Une el listener con los métodos implementados en la actividad
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (Food_order_fragment_horizontal.FoodListenerFragmentHorizontal) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException("La clase " + context.toString() + "debe implementar listenerDelFragment");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_order_horizontal, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle extra_values = getActivity().getIntent().getExtras();
        username = extra_values.getString("username");
        city = extra_values.getString("city");
        String restaurant = extra_values.getString("restaurant");

        //initializeTable(username, restaurant, city);
    }

    // Este método nos permitirá poder inicializar la tabla tanto cuando se inicialice la clase con un intent
    // como cuando se inicialice con el fragment
    public void initializeTable(String pusername, String prestaurant, String pcity) {
        restaurant_name = (TextView) getView().findViewById(R.id.restaurant_order);
        username = pusername;
        city = pcity;
        restaurant_name.setText(prestaurant);
        recycler = getView().findViewById(R.id.recycle_food);
        db = new MiDB(getActivity(), "App", null ,1);

        // Para que aparezca la comida del restaurante
        searchFood();
    }

    public void searchFood() {
        lista = db.getFoodFromRestaurant(restaurant_name.getText().toString());
        if (lista.size() != 0) {
            String[][] arrayRestaurantes = getFoodDataInArray(lista);
            eladaptador = new AdapterRecycler(getImageIds(arrayRestaurantes[0]),arrayRestaurantes[1],arrayRestaurantes[2], this);
            recycler.setAdapter(eladaptador);
            GridLayoutManager elLayoutRejillaIgual= new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
            recycler.setLayoutManager(elLayoutRejillaIgual);
        } else {
            Toast.makeText(getActivity(), getString(R.string.without_food), Toast.LENGTH_SHORT).show();
        }
    }

    private String[][] getFoodDataInArray(ArrayList<Food> l) {
        Iterator<Food> iter = l.iterator();
        Food food;
        // Array de dos filas (porque en cada fila de la lista hay una imagen y un texto
        // Y de n columnas (donde n es el número de restaurantes)
        String[][] arrayData = new String[3][l.size()];
        int i = 0; // para saber la posición del restaurante en la lista
        while(iter.hasNext()) {
            food = iter.next();
            arrayData[0][i] = food.getImage(); // fila 0 de la matriz son los nombre de los restaurantes
            arrayData[1][i] = food.getName(); // fila 1 de la matriz son las rutas de las imágenes
            arrayData[2][i] = Float.toString(food.getPrice());
            i++;
        }

        return arrayData;
    }

    private int[] getImageIds(String[] images) {
        // Este método va a conseguir el identificador drawable de cada una de las imágenes
        int[] returnList = new int[images.length];
        for (int i=0; i<images.length; i++) {
            returnList[i] = getActivity().getApplicationContext().getResources().getIdentifier(images[i], "drawable", getActivity().getApplicationContext().getPackageName());
        }
        return returnList;
    }

    public void onFoodListener(int position) {
        Food food = lista.get(position);
        // Se le pasa el nombre de usuario y el nombre del restaurante, porque puede ser que haya dos pedidos de dos usuarios distintos
        // en el mismo restaurante
        ArrayList<String> lista = db.getPendingOrder(username, restaurant_name.getText().toString(), city);
        // si no hubiera ninguna orden reciente, se crea una nueva
        // Añadir producto en una orden ya existente del mismo restaurante
        // Mostrar dialogo cuando se intente añadir una orden en un restaurante cuando haya ya una orden creada en otro restaurante
        if(lista.size() == 0) {
            // Si el boton de view order ya estuviera visible, no se haria nada
            db.createOrder("1", restaurant_name.getText().toString(), username, food.getName(), food.getPrice());
            Toast.makeText(getActivity(), food.getName() + " " + getString(R.string.addedToOrder), Toast.LENGTH_SHORT).show();
        } else {
            DialogFragment dialogFragment = new DialogoPedidoPendiente(lista.get(1), lista.get(2));
            dialogFragment.show(getActivity().getSupportFragmentManager(), "pedido existente");
        }
    }

    public void pedidoPendiente() {
        listener.hayPedidoPedienteHorizontal(username);
    }
}