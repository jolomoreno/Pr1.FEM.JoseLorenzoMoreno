package es.upm.miw.SolitarioCelta;

import android.os.Parcel;
import android.os.Parcelable;

public class Puntuacion implements Parcelable {
    private String id;
    private String nombreJugador;
    private String fecha;

    protected Puntuacion(Parcel in) {
        id = in.readString();
        nombreJugador = in.readString();
        fecha = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Puntuacion{" +
                "id='" + id + '\'' +
                ", nombreJugador='" + nombreJugador + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nombreJugador);
        dest.writeString(fecha);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Puntuacion> CREATOR = new Parcelable.Creator<Puntuacion>() {
        @Override
        public Puntuacion createFromParcel(Parcel in) {
            return new Puntuacion(in);
        }

        @Override
        public Puntuacion[] newArray(int size) {
            return new Puntuacion[size];
        }
    };
}