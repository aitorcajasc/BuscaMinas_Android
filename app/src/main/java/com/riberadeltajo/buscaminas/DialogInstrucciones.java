package com.riberadeltajo.buscaminas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogInstrucciones extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder b=new AlertDialog.Builder(getActivity());
        b.setTitle("INSTRUCCIONES");
        b.setMessage("Algunas casillas tienen un número," +
                " este número indica las minas que son en todas las casillas circundantes." +
                " Así, si una casilla tiene el número 3, significa que de las ocho casillas" +
                " que hay alrededor (si no es en una esquina o borde) hay 3 con minas y 5 sin minas." +
                " Si se descubre una casilla sin número indica que ninguna de las casillas" +
                " vecinas tiene mina y estas se descubren automáticamente." +
                "Si se descubre una casilla con una mina se pierde la partida.");
        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return b.create();
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
