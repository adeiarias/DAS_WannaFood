package com.example.das_wannafood.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.das_wannafood.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
public class ElViewHolder extends RecyclerView.ViewHolder {

    public TextView nombre;
    public TextView precio;
    public ImageView imagen;

    public ElViewHolder(@NonNull View itemView) {
        super(itemView);

        // Inicilizar los valores con los elementos definidos en el layout del CardView
        nombre = itemView.findViewById(R.id.nombre_comida);
        precio = itemView.findViewById(R.id.precio_comida);
        imagen = itemView.findViewById(R.id.imagen_comida);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
