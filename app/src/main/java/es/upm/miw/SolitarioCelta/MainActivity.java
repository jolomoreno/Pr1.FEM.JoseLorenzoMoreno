package es.upm.miw.SolitarioCelta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

	JuegoCelta mJuego;
    private final String CLAVE_TABLERO = "TABLERO_SOLITARIO_CELTA";
    private final String SAVEDGAMEFILE = "savedGameFile.txt";
    private int puntuacion = 0;
    SharedPreferences preferencias;
    PuntuacionRepositorio db;
    ArrayList<Puntuacion> puntuaciones;
    TextView result;
    TextView nombreJugador;
    String nombre;

	private final int[][] ids = {
		{       0,        0, R.id.p02, R.id.p03, R.id.p04,        0,        0},
        {       0,        0, R.id.p12, R.id.p13, R.id.p14,        0,        0},
        {R.id.p20, R.id.p21, R.id.p22, R.id.p23, R.id.p24, R.id.p25, R.id.p26},
        {R.id.p30, R.id.p31, R.id.p32, R.id.p33, R.id.p34, R.id.p35, R.id.p36},
        {R.id.p40, R.id.p41, R.id.p42, R.id.p43, R.id.p44, R.id.p45, R.id.p46},
        {       0,        0, R.id.p52, R.id.p53, R.id.p54,        0,        0},
        {       0,        0, R.id.p62, R.id.p63, R.id.p64,        0,        0}
	};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferencias = PreferenceManager.getDefaultSharedPreferences(this);
        result = (TextView) findViewById(R.id.textNumFichas);
        nombreJugador = (TextView) findViewById(R.id.textNombreJugador);
        mJuego = new JuegoCelta();
        mostrarTablero();
        db = new PuntuacionRepositorio(getApplicationContext());
        nombre = obtenerNombreJugador();
        nombreJugador.setText(nombre);
        Log.i("JLMM", "Nombre jugador (onCreate):" + nombre);
    }

    /**
     * Se ejecuta al pulsar una posición
     *
     * @param v Vista de la posición pulsada
     */
    public void posicionPulsada(View v) {
        String resourceName = getResources().getResourceEntryName(v.getId());
        int i = resourceName.charAt(1) - '0';
        int j = resourceName.charAt(2) - '0';
        mJuego.jugar(i, j);

        mostrarTablero();
        if (mJuego.juegoTerminado()) {
            guardarPuntuacion();
            new AlertDialogFragment().show(getFragmentManager(), "ALERT DIALOG");
            puntuaciones = db.getAll();
            Log.i("JLMM", "Puntuaciones => " + puntuaciones);
            long numElementos = db.count();
            Log.i("JLMM", "Número elementos = " + String.valueOf(numElementos));
        }
    }

    /**
     * Visualiza el tablero
     */
    public void mostrarTablero() {
        RadioButton button;
        result.setText(String.valueOf(obtenerPuntuacion()));
        nombre = obtenerNombreJugador();
        nombreJugador.setText(nombre);
        Log.i("JLMM", "Nombre Jugador (Mostrar Tablero): " + nombre);
        // String nombre = obtenerNombreJugador();
        for (int i = 0; i < JuegoCelta.TAMANIO; i++)
            for (int j = 0; j < JuegoCelta.TAMANIO; j++)
                if (ids[i][j] != 0) {
                    button = findViewById(ids[i][j]);
                    button.setChecked(mJuego.obtenerFicha(i, j) == 1);
                }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putString(CLAVE_TABLERO, mJuego.serializaTablero());
        super.onSaveInstanceState(outState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String grid = savedInstanceState.getString(CLAVE_TABLERO);
        mJuego.deserializaTablero(grid);
        mostrarTablero();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAbout:
                startActivity(new Intent(this, About.class));
                return true;
            case R.id.preferences:
                startActivity(new Intent(this, SCeltaPreferences.class));
                return true;
            case R.id.reset:
                new ResetDialogFragment().show(getFragmentManager(), "ALERT DIALOG");
                return true;
            case R.id.save:
                saveGame();
                return true;
            case R.id.restore:
                restoreGame();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveGame() {
        Bundle outputState = new Bundle();
        outputState.putString(CLAVE_TABLERO, mJuego.serializaTablero());
        String serializedGame = outputState.getString(CLAVE_TABLERO);
        saveGameInFile(serializedGame);
        Toast.makeText(this, getString(R.string.saveGameText), Toast.LENGTH_LONG).show();
    }

    public void saveGameInFile(String serializedGame){
        try {  // Añadir al fichero
            FileOutputStream fos;
            fos = openFileOutput(SAVEDGAMEFILE, Context.MODE_PRIVATE);
            fos.write(serializedGame.getBytes());
            fos.write('\n');
            fos.close();
        } catch (Exception e) {
            Log.e("JLMM", "FILE I/O ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void restoreGame() {
        String serializedSavedGame = restoreGameOfFile();
        mJuego.deserializaTablero(serializedSavedGame);
        mostrarTablero();
    }

    public String restoreGameOfFile(){
        boolean hayContenido = false;
        BufferedReader fin;
        String savedGame = "";
        Bundle outputState = new Bundle();


        try {
            fin = new BufferedReader(
                    new InputStreamReader(openFileInput(SAVEDGAMEFILE))); // Memoria interna
            String linea = fin.readLine();
            while (linea != null) {
                hayContenido = true;
                savedGame = linea;
                linea = fin.readLine();
            }
            fin.close();
        } catch (Exception e) {
            Log.e("JLMM", "FILE I/O ERROR: " + e.getMessage());
            e.printStackTrace();
            outputState.putString(CLAVE_TABLERO, mJuego.serializaTablero());
            savedGame = outputState.getString(CLAVE_TABLERO);
            Toast.makeText(this, getString(R.string.failRestoreGameText), Toast.LENGTH_LONG).show();
            return savedGame;
        }

        if(!hayContenido){
            outputState.putString(CLAVE_TABLERO, mJuego.serializaTablero());
            savedGame = outputState.getString(CLAVE_TABLERO);
            Toast.makeText(this, getString(R.string.emptyFileRestoreGameText), Toast.LENGTH_LONG).show();
            return savedGame;
        }

        Toast.makeText(this, getString(R.string.restoreGameText), Toast.LENGTH_LONG).show();

        return savedGame;
    }

    public int obtenerPuntuacion(){
        int puntuacion = 0;

        for (int i = 0; i < JuegoCelta.TAMANIO; i++){
            for (int j = 0; j < JuegoCelta.TAMANIO; j++){
                puntuacion = puntuacion + mJuego.obtenerFicha(i,j);
            }
        }
        return puntuacion;
    }

    public String obtenerFecha(){
        Date fechaActual = new Date();
        //Formateando la fecha:
        DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        DateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
        return (formatoHora.format(fechaActual)+" "+formatoFecha.format(fechaActual));
    }

    public String obtenerNombreJugador(){
        return preferencias.getString("playerName", "Jugador 1");
    }

    public void guardarPuntuacion(){
        puntuacion = obtenerPuntuacion();
        // Log.i("JLMM", String.valueOf(puntuacion));
        String playerName = obtenerNombreJugador();
        // Log.i("JLMM", playerName);
        String fecha = obtenerFecha();
        // Log.i("JLMM", fecha);

        long id = db.add(playerName, fecha, String.valueOf(puntuacion));
        Log.i("JLMM", "Id Puntuacion = " + String.valueOf(id));
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}
