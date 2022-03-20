package com.example.das_wannafood.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.das_wannafood.R;
import com.example.das_wannafood.activities.PlaceOrderActivity;
import com.example.das_wannafood.adapters.AdapterRecycler;
import com.example.das_wannafood.adapters.ElViewHolder;
import com.example.das_wannafood.database.MiDB;
import com.example.das_wannafood.models.Order;

import java.util.ArrayList;
import java.util.Iterator;

public class ActualOrder_fragment extends Fragment implements ElViewHolder.onFoodListener {

    private RecyclerView recycler;
    private MiDB db;
    private String username;
    private AdapterRecycler eladaptador;

    private ActualOrderListener listener;

    @Override
    public void onFoodListener(int position) {
        // En este caso no se va a hacer nada, porque no se va a tratar qué hacer cuando se haga click en un producto
        // Pero es necesario heredar el listener para inicializar el adapter
    }

    // Cuando se acceda a esta actividad y no exista orden pendiente, se llamará a este método
    public interface ActualOrderListener {
        void finish_activity();
    }

    // Une el listener con los métodos implementados en la actividad
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ActualOrderListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("La clase " + context.toString() + "debe implementar listenerDelFragment");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.actual_order_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        username = getActivity().getIntent().getExtras().getString("username");
        recycler = (RecyclerView) getView().findViewById(R.id.recycler_actual_order);
        db = new MiDB(getActivity(), "App", null, 1);
        setOrderInformation();
    }


    // Conseguir la información de la orden actual y inicializar el recyclerview
    private void setOrderInformation() {
        ArrayList<Order> list = db.getOrder(username);
        if (list == null) {
            Toast.makeText(getActivity(), getString(R.string.noOrder), Toast.LENGTH_LONG).show();
            listener.finish_activity();
        } else {
            Iterator<Order> itr = list.iterator();
            Order order = null;
            String[] array_nombre_comida = new String[list.size()];
            int[] array_imagen_comida = new int[list.size()];
            String[] precios_comida = new String[list.size()];
            int i = 0;
            while (itr.hasNext()) {
                order = itr.next();
                array_nombre_comida[i] = order.getFood();
                array_imagen_comida[i] = imageNameToInt(order.getPath());
                precios_comida[i] = Double.toString(order.getPrice());
                i++;
            }
            // crear el adapter
            eladaptador = new AdapterRecycler(array_imagen_comida, array_nombre_comida, precios_comida, this);
            recycler.setAdapter(eladaptador);
            // se pondrá filas de dos columnas
            GridLayoutManager elLayoutRejillaIgual= new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
            recycler.setLayoutManager(elLayoutRejillaIgual);
        }
    }

    // Como solo tenemos el nombre de la imagen, necesitamos su identificador drawable
    private int imageNameToInt(String image) {
        // Este método va a conseguir el identificador drawable de cada una de las imágenes
        return getActivity().getApplicationContext().getResources().getIdentifier(image, "drawable", getActivity().getApplicationContext().getPackageName());

    }
}