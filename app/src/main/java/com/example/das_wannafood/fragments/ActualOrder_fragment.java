package com.example.das_wannafood.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.das_wannafood.R;
import com.example.das_wannafood.activities.PlaceOrderActivity;
import com.example.das_wannafood.database.MiDB;
import com.example.das_wannafood.models.Order;

public class ActualOrder_fragment extends Fragment {

    private TextView orderId;
    private TextView total;
    private RecyclerView recycler;
    private MiDB db;

    private ActualOrderListener listener;

    public interface ActualOrderListener {
        void pedidoPendiente(String username);
    }

    // Une el listener con los métodos implementados en la actividad
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (ActualOrderListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException("La clase " + context.toString() + "debe implementar listenerDelFragment");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.actual_order_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recycler = (RecyclerView) getView().findViewById(R.id.recycler_actual_order);
        orderId = getView().findViewById(R.id.actual_order_id);
        total = getView().findViewById(R.id.total_actual_order);
        db = new MiDB(getActivity(), "App", null ,1);
        setOrderInformation();
    }

    private void setOrderInformation() {
        //Order order = db.getPendingOrder();
        Order order = null;
        if(order == null) { // No se ha hecho ninguna orden, se vuelve a la pantalla inicial
            Intent intent = new Intent(getActivity(), PlaceOrderActivity.class);
            startActivity(intent);
            Toast.makeText(getActivity(), getString(R.string.noOrder), Toast.LENGTH_SHORT).show();
            //finish();
        } else {
            orderId.setText(order.getId());

        }
    }

}