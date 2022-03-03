package com.example.das_wannafood.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.das_wannafood.R;
import com.example.das_wannafood.adapters.AdapterRecycler;
import com.example.das_wannafood.adapters.AdapterRestaurantListView;
import com.example.das_wannafood.adapters.ElViewHolder;
import com.example.das_wannafood.database.MiDB;
import com.example.das_wannafood.dialogs.DialogoPedidoPendiente;
import com.example.das_wannafood.models.Food;
import com.example.das_wannafood.models.Order;
import com.example.das_wannafood.models.Restaurant;

import java.util.ArrayList;
import java.util.Iterator;

public class OrderCreatorActivity extends AppCompatActivity implements ElViewHolder.onFoodListener, DialogoPedidoPendiente.ListenerdelDialogo {

    private TextView restaurant_name;
    private RecyclerView recycler;
    private Button btn_order;
    private MiDB db;
    private ArrayList<Food> lista; //lista en la que se almacenará la comida del restaurante
    private AdapterRecycler eladaptador;
    private String username;
    private String city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_order);

        Bundle extra_values = getIntent().getExtras();
        username = extra_values.getString("username");
        city = extra_values.getString("city");

        btn_order = (Button) findViewById(R.id.btn_view_order_food);
        // Hasta que no se haya escogido ningún producto el botón estará invisibilizado
        btn_order.setVisibility(View.INVISIBLE);

        restaurant_name = findViewById(R.id.restaurant_order);
        restaurant_name.setText(extra_values.getString("restaurant"));
        recycler = findViewById(R.id.recycle_food);

        db = new MiDB(this, "App", null ,1);

        // Para que aparezca la comida del restaurante
        searchFood();
    }

    public void viewOrder(View v) {
        Intent intent = new Intent(this, ActualOrderActivity.class);
        startActivity(intent);
    }

    // Cuando un producto se haya escogido, se activará el botón
    private void activateButton() {
        if(btn_order.getVisibility() == View.INVISIBLE){
            btn_order.setVisibility(View.VISIBLE);
        }
    }

    public void searchFood() {
        lista = db.getFoodFromRestaurant(restaurant_name.getText().toString());
        if (lista.size() != 0) {
            String[][] arrayRestaurantes = getFoodDataInArray(lista);
            eladaptador = new AdapterRecycler(getImageIds(arrayRestaurantes[0]),arrayRestaurantes[1],arrayRestaurantes[2], this);
            recycler.setAdapter(eladaptador);
            GridLayoutManager elLayoutRejillaIgual= new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
            recycler.setLayoutManager(elLayoutRejillaIgual);
        } else {
            Toast.makeText(this, getString(R.string.without_food), Toast.LENGTH_SHORT).show();
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
            returnList[i] = this.getApplicationContext().getResources().getIdentifier(images[i], "drawable", this.getApplicationContext().getPackageName());
        }
        return returnList;
    }

    @Override
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
            activateButton();
            db.createOrder("1", restaurant_name.getText().toString(), username, food.getName(), food.getPrice());
            Toast.makeText(this, food.getName() + " " + getString(R.string.addedToOrder), Toast.LENGTH_SHORT).show();
        } else {
            DialogFragment dialogFragment = new DialogoPedidoPendiente(lista.get(1), lista.get(2));
            dialogFragment.show(getSupportFragmentManager(), "pedido existente");
        }
    }

    @Override
    public void pedidoPendiente() {
        Intent intent = new Intent(this, MainPageActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }
}