package es.upm.miw.SolitarioCelta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class PuntuacionAdapter extends ArrayAdapter {
    private Context contexto;
    private List<Puntuacion> puntuaciones;
    private int idRecursoLayout;

    public PuntuacionAdapter(Context context, int resource, List<Puntuacion> puntuaciones) {
        super(context, resource, puntuaciones);
        this.contexto = context;
        this.idRecursoLayout = resource;
        this.puntuaciones = puntuaciones;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout vista;

        if (null != convertView) {
            vista = (LinearLayout) convertView;
        } else {
            LayoutInflater inflador = (LayoutInflater) contexto
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vista = (LinearLayout) inflador.inflate(idRecursoLayout, parent, false);
        }

        Puntuacion puntuacion = this.puntuaciones.get(position);

        TextView itemNombre = (TextView) vista.findViewById(R.id.itemNombre);
        itemNombre.setText(puntuacion.getNombreJugador());

        TextView itemPuntuacion = (TextView) vista.findViewById(R.id.itemPuntuacion);
        itemPuntuacion.setText(String.valueOf(puntuacion.getPuntuacion()));

        TextView itemId = (TextView) vista.findViewById(R.id.itemFecha);
        String[] diaMesAnnio = puntuacion.getFecha().split(" ");
        itemId.setText(diaMesAnnio[1]);





        return vista;
    }

}
