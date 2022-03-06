package com.example.das_wannafood.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.das_wannafood.R;
import com.example.das_wannafood.database.MiDB;
import com.example.das_wannafood.fragments.ActualOrder_fragment;
import com.example.das_wannafood.models.Order;

public class ActualOrderActivity extends AppCompatActivity implements ActualOrder_fragment.ActualOrderListener {

    private MiDB db;
    private ActualOrder_fragment actualOrder_fragment;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actual_order);
        db = new MiDB(this, "App", null ,1);
        username = getIntent().getExtras().getString("username");

        // Inicializar el fragment en función de su orientación
        int orientacion = getResources().getConfiguration().orientation;
        if(orientacion == Configuration.ORIENTATION_PORTRAIT) {
            actualOrder_fragment = (ActualOrder_fragment) getSupportFragmentManager().findFragmentById(R.id.fragemtActualPort);
        } else {
            actualOrder_fragment = (ActualOrder_fragment) getSupportFragmentManager().findFragmentById(R.id.fragemtActualLand);
        }
    }

    public void deleteOrder(View v) {
        Toast.makeText(this, getString(R.string.deleteOrder), Toast.LENGTH_SHORT).show();
        db.deleteOrder(username);
        finish();
    }

    public void finishOrder(View v) {
        db.deleteOrder(username);
        finish();
    }

    @Override
    public void finish_activity() {
        finish();
    }
}