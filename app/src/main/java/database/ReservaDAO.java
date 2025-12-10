package database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import entidades.Reserva;


public class ReservaDAO {

    private DbHelper dbHelper;

    public ReservaDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public long insertarReserva(Reserva reserva) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_cliente", reserva.getId_cliente());
        values.put("id_evento", reserva.getId_evento());
        values.put("id_tipo_entrada", reserva.getId_tipo_entrada());
        long id = db.insert("Reserva", null, values);
        db.close();
        return id;
    }
    public List<Reserva> obtenerTodasLasReservas() {
        List<Reserva> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query("Reserva", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                Reserva r = new Reserva();
                r.setId_reserva(c.getInt(0));
                r.setId_cliente(c.getInt(1));
                r.setId_evento(c.getInt(2));
                r.setId_tipo_entrada(c.getInt(3));
                lista.add(r);
            } while (c.moveToNext());
        }
        c.close(); db.close();
        return lista;
    }
    public Reserva obtenerReservaPorId(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query("Reserva", null, "id_reserva = ?", new String[]{String.valueOf(id)}, null, null, null);
        Reserva r = null;
        if (c.moveToFirst()) {
            r = new Reserva();
            r.setId_reserva(c.getInt(0));
            r.setId_cliente(c.getInt(1));
            r.setId_evento(c.getInt(2));
            r.setId_tipo_entrada(c.getInt(3));
        }
        c.close(); db.close();
        return r;
    }
    public int actualizarReserva(Reserva reserva) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("id_cliente", reserva.getId_cliente());
        v.put("id_evento", reserva.getId_evento());
        v.put("id_tipo_entrada", reserva.getId_tipo_entrada());
        int filas = db.update("Reserva", v, "id_reserva = ?",
                new String[]{String.valueOf(reserva.getId_reserva())});
        db.close();
        return filas;
    }
    public void eliminarReserva(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Reserva", "id_reserva = ?", new String[]{String.valueOf(id)});
        db.close();
    }


}
