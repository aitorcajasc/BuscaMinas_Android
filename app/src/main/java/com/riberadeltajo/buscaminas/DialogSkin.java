package com.riberadeltajo.buscaminas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogSkin extends DialogFragment {
    OnRespuestaSkin respuesta;
    String mensaje="";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v= getLayoutInflater().inflate(R.layout.panel_skin, null);

        Spinner listaBombas=v.findViewById(R.id.spinnerSkin);
        Bomba[] arrayBombas=new Bomba[3];
        arrayBombas[0]=new Bomba(R.drawable.bomba_classic, "Bomba classic");
        arrayBombas[1]=new Bomba(R.drawable.bomba_pinchos, "Bomba pinchos");
        arrayBombas[2]=new Bomba(R.drawable.bomba_dibujo, "Bomba dibujo");

        Adaptador adaptador=new Adaptador(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1, arrayBombas);
        listaBombas.setAdapter(adaptador);

        AlertDialog.Builder b=new AlertDialog.Builder(getActivity());
        b.setTitle("Aspecto");
        b.setView(v);
        listaBombas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(arrayBombas[position].nombre.equals("Bomba classic")){
                    mensaje="u";
                } else if (arrayBombas[position].nombre.equals("Bomba pinchos")) {
                    mensaje="d";
                } else if (arrayBombas[position].nombre.equals("Bomba dibujo")) {
                    mensaje="t";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                respuesta.onSkin(mensaje);
            }
        });
        return b.create();
    }

    public interface OnRespuestaSkin{
        public void onSkin(String mensaje);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        respuesta= (OnRespuestaSkin) context;
    }

    public class Adaptador extends ArrayAdapter<Bomba>{
        Bomba[] bombas;

        public Adaptador(@NonNull Context context, int resource, @NonNull Bomba[] objects) {
            super(context, resource, objects);
            bombas=objects;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return crearFila(position, convertView, parent);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return crearFila(position, convertView, parent);
        }

        private View crearFila(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater=getLayoutInflater();
            View fila=inflater.inflate(R.layout.fila_bomba, parent, false);

            ImageView imgB=fila.findViewById(R.id.imgBomba);
            TextView nom=fila.findViewById(R.id.nombre);

            imgB.setImageResource(bombas[position].img);
            nom.setText(bombas[position].nombre);

            return fila;
        }
    }
}