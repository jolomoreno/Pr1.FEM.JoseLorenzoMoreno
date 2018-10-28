package es.upm.miw.SolitarioCelta;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MejoresJugadores  extends AppCompatActivity implements View.OnClickListener {
    private ListView lvListado;
    private Button botonBorrarPuntuaciones;
    PuntuacionRepositorio db;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.bestplayers);
        db = new PuntuacionRepositorio(getApplicationContext());

        this.lvListado = (ListView) findViewById(R.id.lvListadoElementos);
        this.botonBorrarPuntuaciones = (Button) findViewById(R.id.borrarPuntuaciones);

        ArrayList<Puntuacion> puntuaciones = this.getIntent().getParcelableArrayListExtra("puntuaciones");

        PuntuacionAdapter adaptador = new PuntuacionAdapter(
                this,
                R.layout.itempuntuaciones,
                puntuaciones
        );

        this.lvListado.setAdapter(adaptador);

        this.botonBorrarPuntuaciones.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.borrarPuntuaciones) {
            new DeleteDialogFragment().show(getFragmentManager(), "DELETE DIALOG");
        }
    }

    public void borrarPuntuaciones(){
        db.deleteAll();
    }



}
