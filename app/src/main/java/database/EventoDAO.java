package database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import entidades.Evento;

public class EventoDAO {

    private DbHelper dbHelper;

    public EventoDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    // Insertar evento
    public long insertarEvento(Evento evento) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre_evento", evento.getNombre_evento());
        values.put("fecha", evento.getFecha());
        values.put("aforo_maximo", evento.getAforo_maximo());
        long id = db.insert("Evento", null, values);
        db.close();
        return id;
    }

    // Obtener todos los eventos
    public List<Evento> obtenerTodosLosEventos() {
        List<Evento> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("Evento", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Evento e = new Evento();
                e.setId_evento(cursor.getInt(0));
                e.setNombre_evento(cursor.getString(1));
                e.setFecha(cursor.getString(2));
                e.setAforo_maximo(cursor.getInt(3));
                lista.add(e);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }

    // Obtener evento por ID
    public Evento obtenerEventoPorId(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("Evento", null, "id_evento = ?", new String[]{String.valueOf(id)}, null, null, null);
        Evento e = null;
        if (cursor.moveToFirst()) {
            e = new Evento();
            e.setId_evento(cursor.getInt(0));
            e.setNombre_evento(cursor.getString(1));
            e.setFecha(cursor.getString(2));
            e.setAforo_maximo(cursor.getInt(3));
        }
        cursor.close();
        db.close();
        return e;
    }

    // Actualizar evento
    public int actualizarEvento(Evento evento) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre_evento", evento.getNombre_evento());
        values.put("fecha", evento.getFecha());
        values.put("aforo_maximo", evento.getAforo_maximo());
        int filas = db.update("Evento", values, "id_evento = ?", new String[]{String.valueOf(evento.getId_evento())});
        db.close();
        return filas;
    }

    // Eliminar evento
    public void eliminarEvento(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Evento", "id_evento = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}