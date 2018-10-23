package es.upm.miw.SolitarioCelta;

import android.os.Parcel;
import android.os.Parcelable;

public class Puntuacion implements Parcelable {
    private int id;
    private String nombreJugador;
    private String fecha;
    private String puntuacion;

    public Puntuacion(int id, String nombreJugador, String fecha, String puntuacion) {
        this.id = id;
        this.nombreJugador = nombreJugador;
        this.fecha = fecha;
        this.puntuacion = puntuacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(String puntuacion) {
        this.puntuacion = puntuacion;
    }

    @Override
    public String toString() {
        return "Puntuacion{" +
                "id='" + id + '\'' +
                ", nombreJugador='" + nombreJugador + '\'' +
                ", fecha='" + fecha + '\'' +
                ", puntuacion='" + puntuacion + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.nombreJugador);
        dest.writeString(this.fecha);
        dest.writeString(this.puntuacion);
    }

    protected Puntuacion(Parcel in) {
        this.id = in.readInt();
        this.nombreJugador = in.readString();
        this.fecha = in.readString();
        this.puntuacion = in.readString();
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