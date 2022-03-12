package com.example.das_wannafood.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.das_wannafood.R;

public class AdapterRestaurantListView extends BaseAdapter {

    private Context contexto;
    private LayoutInflater inflater;
    // Elementos de la lista personalizada
    private String[] nombres;
    private String[] imagenes;

    public AdapterRestaurantListView(Context context, String[] pnombres, String[] pimagenes) {
        contexto = context;
        nombres = pnombres;
        imagenes = pimagenes;
        inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return nombres.length;
    }

    @Override
    public Object getItem(int i) {
        return nombres[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // fila personalizada para mostrar en la lista
        view = inflater.inflate(R.layout.fila_lista_restaurante, null);
        // Inicializar valores de la fila
        ImageView img = (ImageView) view.findViewById(R.id.imagen_restaurante);
        TextView nombre = (TextView) view.findViewById(R.id.texto_restaurante);
        // Añadirlos a la lista
        // Conseguir el drawable
        int resourceId = contexto.getResources().getIdentifier(imagenes[i], "drawable", contexto.getPackageName());
        System.out.println(imagenes[i]);
        img.setImageResource(resourceId);
        nombre.setText(nombres[i]);

        return view;
    }
}
