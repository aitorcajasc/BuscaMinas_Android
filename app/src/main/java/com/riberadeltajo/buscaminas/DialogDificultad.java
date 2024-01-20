package com.riberadeltajo.buscaminas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogDificultad extends DialogFragment {
    OnRespuestaDificultad respuesta;
    String mensaje="";
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v= getLayoutInflater().inflate(R.layout.panel_dificultad, null);
        AlertDialog.Builder b=new AlertDialog.Builder(getActivity());
        b.setTitle("DIFICULTAD");
        b.setView(v);
        RadioGroup rg=v.findViewById(R.id.grupoDificultad);
        RadioGroup.OnCheckedChangeListener listener=new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.facil){
                    mensaje="f";
                }
                if(checkedId==R.id.medio){
                    mensaje="m";
                }
                if(checkedId==R.id.dificil){
                    mensaje="d";
                }
            }
        };
        rg.setOnCheckedChangeListener(listener);
        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                respuesta.onDificultad(mensaje);
            }
        });
        return b.create();
    }

    public interface OnRespuestaDificultad{
        public void onDificultad(String mensaje);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        respuesta= (OnRespuestaDificultad) context;
    }
}