package es.upm.miw.SolitarioCelta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class MejoresJugadores  extends AppCompatActivity{
    private ListView lvListado;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.bestplayers);

        this.lvListado = (ListView) findViewById(R.id.lvListadoElementos);

        ArrayList<Puntuacion> puntuaciones = this.getIntent().getParcelableArrayListExtra("puntuaciones");

        this.setDataList(puntuaciones);
    }

    public void setDataList(ArrayList<Puntuacion> puntuaciones) {
        PuntuacionAdapter adaptador = new PuntuacionAdapter(
                this,
                R.layout.itempuntuaciones,
                puntuaciones
        );

        this.lvListado.setAdapter(adaptador);
    }

}
