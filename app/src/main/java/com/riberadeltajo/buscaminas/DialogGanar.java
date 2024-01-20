package com.riberadeltajo.buscaminas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogGanar extends DialogFragment {
    OnRespuestaVictoria respuesta;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder b=new AlertDialog.Builder(getActivity());
        b.setTitle("VICTORIA");
        b.setMessage("¡HAS GANADO!");
        b.setNegativeButton("Reiniciar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                respuesta.onReiniciar();
            }
        });
        return b.create();
    }

    public interface OnRespuestaVictoria{
        public void onReiniciar();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        respuesta= (OnRespuestaVictoria) context;
    }
}
