package com.example.das_wannafood.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.das_wannafood.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
public class ElViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView nombre;
    public TextView precio;
    public ImageView imagen;
    onFoodListener onFoodListener;

    public ElViewHolder(@NonNull View itemView, onFoodListener onFoodListener) {
        super(itemView);

        // Inicilizar los valores con los elementos definidos en el layout del CardView
        nombre = itemView.findViewById(R.id.nombre_comida);
        precio = itemView.findViewById(R.id.precio_comida);
        imagen = itemView.findViewById(R.id.imagen_comida);
        this.onFoodListener = onFoodListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onFoodListener.onFoodListener(getAdapterPosition());
    }

    // Esta interfaz se usará en la actividad donde se gestiona el recyclerview para gestionar las acciones del usuario
    public interface onFoodListener {
        void onFoodListener(int position);
    }
}
