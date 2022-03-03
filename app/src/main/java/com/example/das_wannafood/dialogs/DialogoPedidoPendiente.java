package com.example.das_wannafood.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.das_wannafood.R;
import com.example.das_wannafood.database.MiDB;

public class DialogoPedidoPendiente extends DialogFragment {

    private MiDB db;
    private AlertDialog.Builder builder;
    private String restaurant;
    private String city;

    ListenerdelDialogo miListener;

    public interface ListenerdelDialogo {
        void pedidoPendiente();
    }

    public DialogoPedidoPendiente(String pRest, String pCity) {
        this.restaurant = pRest;
        this.city = pCity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        miListener = (ListenerdelDialogo) getActivity();
        db = new MiDB(getActivity(), "App", null ,1);
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.warning));
        builder.setMessage(getString(R.string.pedingOrder) + " \nRestaurant: " + this.restaurant + " \nCity: " + this.city + "\n" + getString(R.string.cancelPedidoMessage));
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Llamar al metodo pedidoPendiente del listener de la actividad
                miListener.pedidoPendiente();
            }
        });
        return builder.create();
    }
}
