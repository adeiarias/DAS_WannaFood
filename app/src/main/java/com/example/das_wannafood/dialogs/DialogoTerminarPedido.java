package com.example.das_wannafood.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.das_wannafood.R;

public class DialogoTerminarPedido extends DialogFragment {

    private AlertDialog.Builder builder;

    ListenerDialogTerminar listenerDialogTerminar;

    public interface ListenerDialogTerminar {
        void terminarPedido();
    }

    public DialogoTerminarPedido() {}

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        listenerDialogTerminar = (ListenerDialogTerminar) getActivity();
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.finishOrder));
        builder.setMessage(getString(R.string.finishOrderMessage));
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Cuando se haga click en el botón 'yes', se terminará la orden
                listenerDialogTerminar.terminarPedido();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Cuando se haga click en el botón 'no', no se hará nada
            }
        });
        return builder.create();
    }
}
