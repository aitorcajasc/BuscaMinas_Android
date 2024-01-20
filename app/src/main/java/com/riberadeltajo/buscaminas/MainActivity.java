package com.riberadeltajo.buscaminas;

import static android.graphics.Color.rgb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements DialogDerrota.OnRespuestaDerrota,
        DialogDificultad.OnRespuestaDificultad, DialogSkin.OnRespuestaSkin,
        DialogGanar.OnRespuestaVictoria {
    public int filas = 8;
    public int columnas = 8;
    public int dificultad = 10;
    public int imagen = R.drawable.bomba_classic;
    public int contGanar = 0;
    public int[][] matriz;
    public boolean[][] matrizBoolean;
    public Button[][] matrizBotones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout c = findViewById(R.id.tablero);
        c.post(new Runnable() {
            @Override
            public void run() {
                iniciar();
            }
        });
    }

    private void iniciar() {
        GridLayout t = new GridLayout(getApplicationContext());
        ConstraintLayout constraintLayout = findViewById(R.id.tablero);

        int height = constraintLayout.getHeight();
        int width = constraintLayout.getWidth();

        GridLayout.LayoutParams param = new GridLayout.LayoutParams();
        param.setMargins(0, 0, 0, 0);
        param.height = ViewGroup.LayoutParams.MATCH_PARENT;
        param.width = ViewGroup.LayoutParams.MATCH_PARENT;
        t.setRowCount(filas);
        t.setColumnCount(columnas);
        t.setLayoutParams(param);

        LinearLayout.LayoutParams layoutParams = new
                LinearLayout.LayoutParams(width / columnas, height / filas);
        layoutParams.setMargins(0, 0, 0, 0);

        contGanar = 0;
        matriz = new int[filas][columnas];
        matrizBotones=new Button[filas][columnas];
        matrizBoolean = new boolean[filas][columnas];

        asignarMinas();
        asignarValores();

        View.OnClickListener listenerMostrar = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == -1) {
                    ImageButton b = (ImageButton) v;
                    b.setImageResource(imagen);
                    DialogDerrota dd = new DialogDerrota();
                    dd.show(getSupportFragmentManager(), "Mi diálogo");
                } else {
                    Button b = (Button) v;
                    b.setBackgroundColor(Color.rgb(200, 241, 188));
                    b.setText(b.getId() + "");
                    if (b.getText().equals("1")) {
                        b.setTextColor(Color.BLUE);
                    }
                    if (b.getText().equals("2")) {
                        b.setTextColor(Color.rgb(0, 143, 76));
                    }
                    if (b.getText().equals("3")) {
                        b.setTextColor(Color.RED);
                    }
                    if (b.getText().equals("4")) {
                        b.setTextColor(Color.rgb(87, 35, 100));
                    }
                    if (b.getText().equals("5")) {
                        b.setTextColor(Color.rgb(255, 0, 128));
                    }
                    if (b.getText().equals("6")) {
                        b.setTextColor(Color.rgb(0, 0, 255));
                    }
                    if (b.getText().equals("7")) {
                        b.setTextColor(Color.rgb(171, 42, 62));
                    }
                    if (v.getId() == 0) {
                        b.setText("");
                        String tag = v.getTag().toString();
                        String[] partes = tag.split("_");
                        int fila = Integer.parseInt(partes[0]);
                        int columna = Integer.parseInt(partes[1]);
                        mostrarSinMina(fila, columna);
                    }
                }
            }

            private void mostrarSinMina(int fila, int columna) {
                if(matrizBoolean[fila][columna]==false){
                    matrizBoolean[fila][columna]=true;
                    for (int y = -1; y < 2; y++) {
                        for (int x = -1; x < 2; x++) {
                            try {
                                matrizBotones[fila+y][columna+x].performClick();
                            } catch (ArrayIndexOutOfBoundsException ex) {
                            }
                        }
                    }
                }
            }
        };

        View.OnLongClickListener bandera = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {
                    ImageButton b = (ImageButton) v;
                    b.setImageResource(R.drawable.flag_icon);
                    contGanar++;
                    if (contGanar == dificultad) {
                        DialogGanar dg = new DialogGanar();
                        dg.show(getSupportFragmentManager(), "Mi diálogo");
                    }
                } catch (java.lang.ClassCastException ex) {
                    DialogDerrota dd = new DialogDerrota();
                    dd.show(getSupportFragmentManager(), "Mi diálogo");
                }
                return true;
            }
        };

        int cont = 0;
        View v = new View(getApplicationContext());
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (matriz[i][j] == -1) {
                    v = new ImageButton(getApplicationContext());
                } else {
                    v = new Button(getApplicationContext());
                    matrizBotones[i][j]= (Button) v;
                }
                asignarCasilla(v, layoutParams, matriz[i][j], t, listenerMostrar, bandera, j, i, cont);
            }
            cont++;
        }
        constraintLayout.addView(t);
    }

    private void asignarCasilla(View v, LinearLayout.LayoutParams layoutParams,
                                int matriz, GridLayout t, View.OnClickListener mostrar,
                                View.OnLongClickListener bandera, int j, int cont, int i) {
        v.setLayoutParams(layoutParams);
        v.setId(matriz);
        t.addView(v);
        v.setOnClickListener(mostrar);
        v.setOnLongClickListener(bandera);
        v.setBackgroundColor(asignarColor(j, cont));
        v.setTag(i + "_" + j);
    }

    private int asignarColor(int j, int cont) {
        int color = 0;

        if (cont % 2 == 0) {
            if (j % 2 == 0) {
                color = Color.rgb(83, 236, 83);
            } else {
                color = Color.rgb(46, 209, 46);
            }
        } else {
            if (j % 2 == 0) {
                color = Color.rgb(46, 209, 46);
            } else {
                color = Color.rgb(83, 236, 83);
            }
        }

        return color;
    }

    private void asignarValores() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matrizBoolean[i][j] = false;
                if (matriz[i][j] == 0) {
                    for (int y = -1; y < 2; y++) {
                        for (int x = -1; x < 2; x++) {
                            try {
                                if (matriz[i + y][j + x] == -1) {
                                    matriz[i][j]++;
                                }
                            } catch (ArrayIndexOutOfBoundsException ex) {
                            }
                        }
                    }
                }
            }
        }
    }

    private void asignarMinas() {
        int contadorMinas = 0;
        while (contadorMinas < dificultad) {
            int rFila = (int) (Math.random() * filas);
            int rColumna = (int) (Math.random() * columnas);
            if (matriz[rFila][rColumna] != -1) {
                matriz[rFila][rColumna] = -1;
                contadorMinas++;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.dificultad) {
            DialogDificultad dd = new DialogDificultad();
            dd.show(getSupportFragmentManager(), "Mi diálogo");
        }
        if (item.getItemId() == R.id.instrucciones) {
            DialogInstrucciones di = new DialogInstrucciones();
            di.show(getSupportFragmentManager(), "Mi diálogo");
        }
        if (item.getItemId() == R.id.skin) {
            DialogSkin ds = new DialogSkin();
            ds.show(getSupportFragmentManager(), "Mi diálogo");
        }
        return super.onOptionsItemSelected(item);
    }

    public void onReiniciar() {
        iniciar();
    }

    @Override
    public void onDificultad(String mensaje) {
        if (mensaje.equals("f")) {
            dificultad = 10;
            filas = 8;
            columnas = 8;
        } else if (mensaje.equals("m")) {
            dificultad = 30;
            filas = 12;
            columnas = 12;
        } else if (mensaje.equals("d")) {
            dificultad = 60;
            filas = 16;
            columnas = 16;
        }
        iniciar();
    }

    public void onSkin(String mensaje) {
        if (mensaje.equals("u")) {
            imagen = R.drawable.bomba_classic;
        } else if (mensaje.equals("d")) {
            imagen = R.drawable.bomba_pinchos;
        } else if (mensaje.equals("t")) {
            imagen = R.drawable.bomba_dibujo;
        }
        iniciar();
    }
}