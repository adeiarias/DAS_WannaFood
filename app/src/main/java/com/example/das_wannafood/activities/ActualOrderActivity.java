package com.example.das_wannafood.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.das_wannafood.R;
import com.example.das_wannafood.database.MiDB;

public class ActualOrderActivity extends AppCompatActivity {

    private TextView orderId;
    private TextView total;
    private RecyclerView recycler;
    private MiDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actual_order);

        orderId = findViewById(R.id.actual_order_id);
        total = findViewById(R.id.total_actual_order);
        recycler = (RecyclerView) findViewById(R.id.recycler_actual_order);

        db = new MiDB(this, "App", null ,1);
    }



    public void deleteOrder(View v) {
        db.deletePedingOrder();
        Intent intent = new Intent(this, MainPageActivity.class);
        startActivity(intent);
        finish();
    }

    public void finishOrder(View v) {
        db.finishPendingOrder();
        Intent intent = new Intent(this, MainPageActivity.class);
        startActivity(intent);
        finish();
    }
}