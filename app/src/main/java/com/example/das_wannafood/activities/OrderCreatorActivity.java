package com.example.das_wannafood.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.das_wannafood.R;
import com.example.das_wannafood.database.MiDB;

public class OrderCreatorActivity extends AppCompatActivity {

    private TextView restaurant_name;
    private MiDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_order);

        Bundle extras = getIntent().getExtras();

        restaurant_name = findViewById(R.id.restaurant_order);
        restaurant_name.setText(extras.getString("restaurant"));

        db = new MiDB(this, "App", null ,1);
    }
}