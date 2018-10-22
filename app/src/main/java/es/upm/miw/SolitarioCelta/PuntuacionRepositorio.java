package es.upm.miw.SolitarioCelta;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PuntuacionRepositorio extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Puntuaciones.db";

    public PuntuacionRepositorio(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Comandos SQL
        sqLiteDatabase.execSQL("CREATE TABLE " + PuntuacionContract.TABLE_NAME + " ("
                + PuntuacionContract.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PuntuacionContract.NOMBRE + " TEXT NOT NULL,"
                + PuntuacionContract.FECHA + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }
}
