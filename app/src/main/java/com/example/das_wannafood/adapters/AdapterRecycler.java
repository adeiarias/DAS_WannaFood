package com.example.das_wannafood.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.das_wannafood.R;
import com.example.das_wannafood.adapters.ElViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterRecycler extends RecyclerView.Adapter<ElViewHolder> {

    private int[] imagenes;
    private String[] nombres;
    private String[] precios;
    private ElViewHolder.onFoodListener monFoodListener;

    public AdapterRecycler(int[] pimagenes, String[] pnombres, String[] pprecios, ElViewHolder.onFoodListener onFoodListener) {
        imagenes = pimagenes;
        nombres = pnombres;
        precios = pprecios;
        monFoodListener = onFoodListener;
    }

    @NonNull
    @Override
    public ElViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View elLayoutDeCadaItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_cardview,null);
        ElViewHolder evh = new ElViewHolder(elLayoutDeCadaItem, monFoodListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ElViewHolder holder, int position) {
        holder.imagen.setImageResource(imagenes[position]);
        holder.nombre.setText(nombres[position]);
        holder.precio.setText(precios[position]+" euros");
    }

    @Override
    public int getItemCount() {
        return imagenes.length;
    }
}
