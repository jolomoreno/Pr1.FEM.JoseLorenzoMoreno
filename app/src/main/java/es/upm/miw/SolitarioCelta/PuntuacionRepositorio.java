package es.upm.miw.SolitarioCelta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static es.upm.miw.SolitarioCelta.PuntuacionContract.tablaPuntuacion;

public class PuntuacionRepositorio extends SQLiteOpenHelper {

    // Nombre de la base de datos
    private static final String DB_NAME = tablaPuntuacion.TABLE_NAME + ".db";

    // Número de version
    private static final int DB_VERSION = 1;

    public PuntuacionRepositorio(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // CREATE TABLE
    @Override
    public void onCreate(SQLiteDatabase db) {
        String consultaSQL = "CREATE TABLE " + tablaPuntuacion.TABLE_NAME + " ("
                + tablaPuntuacion.COL_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + tablaPuntuacion.COL_NAME_NOMBRE  + " TEXT, "
                + tablaPuntuacion.COL_NAME_FECHA     + " TEXT, "
                + tablaPuntuacion.COL_NAME_PUNTUACION     + " TEXT)";
        db.execSQL(consultaSQL);
    }

    // RESET INFO TABLE
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /* String consultaSQL = "DROP TABLE IF EXISTS " + tablaPuntuacion.TABLE_NAME;
        db.execSQL(consultaSQL);
        onCreate(db); */
    }

    // COUNT
    public long count() {
        SQLiteDatabase db = this.getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, tablaPuntuacion.TABLE_NAME);
    }

    // POST
    public long add(String nombre, String fecha, String puntuacion) {

        // Obtiene la DB en modo escritura
        SQLiteDatabase db = this.getWritableDatabase();

        // Mapa de valores: parejas nombreColumna:valor
        ContentValues valores = new ContentValues();
        valores.put(tablaPuntuacion.COL_NAME_NOMBRE, nombre);
        valores.put(tablaPuntuacion.COL_NAME_FECHA, fecha);
        valores.put(tablaPuntuacion.COL_NAME_PUNTUACION, puntuacion);

        // Realiza la inserción
        return db.insert(tablaPuntuacion.TABLE_NAME, null, valores);
    }

    // GET ALL
    public ArrayList<Puntuacion> getAll() {
        String consultaSQL = "SELECT * FROM " + tablaPuntuacion.TABLE_NAME;
        ArrayList<Puntuacion> listaPuntuaciones = new ArrayList<>();

        // Accedo a la DB en modo lectura
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(consultaSQL, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Puntuacion puntuacion = new Puntuacion(
                        cursor.getInt(cursor.getColumnIndex(tablaPuntuacion.COL_NAME_ID)),
                        cursor.getString(cursor.getColumnIndex(tablaPuntuacion.COL_NAME_NOMBRE)),
                        cursor.getString(cursor.getColumnIndex(tablaPuntuacion.COL_NAME_FECHA)),
                        cursor.getString(cursor.getColumnIndex(tablaPuntuacion.COL_NAME_PUNTUACION))
                );

                listaPuntuaciones.add(puntuacion);
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();

        return listaPuntuaciones;
    }
}
