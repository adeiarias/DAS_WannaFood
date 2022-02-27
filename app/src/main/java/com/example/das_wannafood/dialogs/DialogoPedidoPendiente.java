package com.example.das_wannafood.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.das_wannafood.R;
import com.example.das_wannafood.database.MiDB;
import com.example.das_wannafood.models.Order;

public class DialogoPedidoPendiente extends DialogFragment {

    private MiDB db;
    private AlertDialog.Builder builder;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        db = new MiDB(getActivity(), "App", null ,1);
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.warning));
        pendingOrder();
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.out.println("No hacer nada");
            }
        });
        return builder.create();
    }

    private void pendingOrder() {
        Order order = db.getPendingOrder();
        if(order != null) {
            builder.setMessage(getString(R.string.pedingOrder) + " Restaurant: " + order.getRestaurant() + " City: " + order.getCity());
        }
    }
}
