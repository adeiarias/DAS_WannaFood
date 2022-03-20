package com.example.das_wannafood.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.das_wannafood.R;
import com.example.das_wannafood.database.MiDB;
import com.example.das_wannafood.dialogs.DialogoPedidoPendiente;
import com.example.das_wannafood.dialogs.DialogoTerminarPedido;
import com.example.das_wannafood.fragments.ActualOrder_fragment;
import com.example.das_wannafood.models.Order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ActualOrderActivity extends AppCompatActivity implements ActualOrder_fragment.ActualOrderListener, DialogoTerminarPedido.ListenerDialogTerminar {

    private MiDB db;
    private ActualOrder_fragment actualOrder_fragment;
    String username;
    private Double total_price = 0.0;
    private TextView price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Acceder a las preferencias para conseguir el valor de la clave del idioma
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String idioma = prefs.getString("idioma", "es");

        // Cambiar el idioma
        Locale nuevaloc = new Locale(idioma);
        Locale.setDefault(nuevaloc);
        Configuration configuration = getBaseContext().getResources().getConfiguration();
        configuration.setLocale(nuevaloc);
        configuration.setLayoutDirection(nuevaloc);
        Context context = getBaseContext().createConfigurationContext(configuration);
        getBaseContext().getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());

        // Aplicamos el layout
        setContentView(R.layout.actual_order);

        db = new MiDB(this, "App", null ,1);
        username = getIntent().getExtras().getString("username");
        total_price = db.getTotalOrderPrice(username);

        // Inicializar el fragment en función de su orientación
        int orientacion = getResources().getConfiguration().orientation;
        // Dependiendo de la orientación del dispositivo, el identificador del textfield del precio total será diferente
        // por esa razón, se llama al método 'findViewById' en cada orientación
        if(orientacion == Configuration.ORIENTATION_PORTRAIT) {
            price = findViewById(R.id.price_port);
            price.setText(price.getText().toString() + " " + total_price.toString() + " euros");
            actualOrder_fragment = (ActualOrder_fragment) getSupportFragmentManager().findFragmentById(R.id.fragemtActualPort);
        } else {
            price = findViewById(R.id.price_lands);
            price.setText(price.getText().toString() + " " + total_price.toString() + " euros");
            actualOrder_fragment = (ActualOrder_fragment) getSupportFragmentManager().findFragmentById(R.id.fragemtActualLand);
        }
    }

    // Este método borrará de la base de datos los registros del pedido actual
    public void deleteOrder(View v) {
        Toast.makeText(this, getString(R.string.deleteOrder), Toast.LENGTH_SHORT).show();
        db.deleteOrder(username);
        finish();
    }

    // Este método al igual que el anterior borrará todos los registros del pedido actual,
    // pero en este caso, aparecerá un dialogo para confirmar esta acción
    public void finishOrder(View v) {
        DialogFragment dialogFragment = new DialogoTerminarPedido();
        dialogFragment.show(getSupportFragmentManager(), "terminar pedido");
    }

    // Este método se usa en el fragment Actual_Order y permitirá en caso de que no haya ninguna orden
    // que la actividad no se inicie y que automáticamente se cierre.
    // Nota importante: Se podría mejorar
    @Override
    public void finish_activity() {
        finish();
    }

    // Esta notificación se creará cuando se termine el pedido actual
    private void createOrderFinishNotif(String restaurante, Double price, String fecha) {
        NotificationManager elManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder elBuilder = new NotificationCompat.Builder(this, "IdCanal");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel elCanal = new NotificationChannel("IdCanal", "NombreCanal",
                    NotificationManager.IMPORTANCE_DEFAULT);

            // Crear un canal para la notificación
            elCanal.setDescription("Canal para mostrar información sobre el pedido efectuado");
            elCanal.enableLights(true);
            elCanal.setLightColor(Color.RED);
            elCanal.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            elCanal.enableVibration(true);

            elManager.createNotificationChannel(elCanal);
        }

        // Definir las características de la notificación
        elBuilder.setSmallIcon(R.drawable.done)
                .setContentTitle(getString(R.string.notifTitle))
                .setContentText(getString(R.string.notifContent1) + restaurante + " " + getString(R.string.notifContent2) + Double.toString(price) + " " + getString(R.string.notifContent3) + fecha)
                .setSubText(getString(R.string.notifSub))
                .setVibrate(new long[]{0, 1000, 500 , 1000})
                .setAutoCancel(true);
        // lanzar la notificación
        elManager.notify(1, elBuilder.build());
    }

    // Este método se llama desde la clase 'DialogoTerminarPedido' y permitirá por un lado mostrar la notificación,
    // y por el otro lado, terminar la actividad con el método 'finish()'
    // basicamente queremos que cuando se termine el pedido vuelva a la actividad anterior (en este caso, al placeOrderActivity)
    @Override
    public void terminarPedido() {
        String restaurant = db.getOrderRestaurant(username);
        Double totalPrice = db.getOrderPrice(username);
        Date date = new Date(); // This object contains the current date value
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String fecha = formatter.format(date);
        createOrderFinishNotif(restaurant, totalPrice, fecha);
        db.deleteOrder(username);
        finish();
    }
}