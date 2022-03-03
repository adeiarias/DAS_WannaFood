package com.example.das_wannafood.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.das_wannafood.R;

public class MainPageActivity extends AppCompatActivity {

    private TextView current_user;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        // Asignar del Toolbar personalizado
        setSupportActionBar(findViewById(R.id.toolbarPrincipal));

        current_user = findViewById(R.id.welcome_text);

        Bundle extra_values = getIntent().getExtras();
        username = extra_values.getString("username");
        current_user.setText(current_user.getText().toString() + " " + username);
    }

    // Método para asignar menu.xml con la definición del menú al Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // Ir a la actividad de pedir comida
    public void place_order(View v) {
        Intent intent = new Intent(this, PlaceOrderActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    // Ir a la actividad de mirar los pedidos
    public void view_orders(View v) {
        Intent intent = new Intent(this, PreviousOrderActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    // Método para gestionar la interacción del usuario ante cualquier elección del menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.shopping) { // Si se ha pulsado shopping, se creará una actividad para mostrar el pedido actual
            /*Intent i = new Intent (this, FavoritosActivity.class);
            i.putExtra("usuario", usuario);
            startActivity(i);*/
            System.out.println("hola");
        } else if (id == R.id.preferences) { // Si se ha pulsado preferencias, se podrán gestionar las preferencias de la app
            /*Intent i = new Intent (this, VerMasTardeActivity.class);
            i.putExtra("usuario", usuario);
            startActivity(i);*/
            System.out.println("hola");
        } else if (id == R.id.logout) { // Si se ha pulsado logout, se volverá a la pantalla del login
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}