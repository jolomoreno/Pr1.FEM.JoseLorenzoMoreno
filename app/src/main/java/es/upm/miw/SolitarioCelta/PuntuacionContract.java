package es.upm.miw.SolitarioCelta;

import android.provider.BaseColumns;

final public class PuntuacionContract {

        private PuntuacionContract() {}
        public static abstract class tablaPuntuacion implements BaseColumns {

                public static final String TABLE_NAME = "puntuaciones";

                public static final String COL_NAME_ID = _ID;
                public static final String COL_NAME_NOMBRE = "nombreJugador";
                public static final String COL_NAME_FECHA = "fecha";
                public static final String COL_NAME_PUNTUACION = "puntuacion";
        }
}
