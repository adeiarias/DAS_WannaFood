package com.example.das_wannafood.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.RecoverySystem;
import android.widget.ListView;

import com.example.das_wannafood.R;
import com.example.das_wannafood.database.MiDB;

public class PreviousOrderActivity extends AppCompatActivity {

    private RecyclerView previous_orders;
    private MiDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.previous_orders);

        previous_orders = (RecyclerView) findViewById(R.id.recycler_previous_orders);

        db = new MiDB(this, "App", null ,1);
    }
}