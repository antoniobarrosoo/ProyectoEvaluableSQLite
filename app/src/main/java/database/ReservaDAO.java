package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import entidades.Reserva;

/**
 * Clase ReservaDAO que proporciona métodos para gestionar la tabla Reserva
 * de la base de datos, incluyendo operaciones CRUD.
 */
public class ReservaDAO {

    /** Helper para la base de datos SQLite */
    private DbHelper dbHelper;

    /**
     * Constructor que inicializa el DbHelper.
     *
     * @param context Contexto de la aplicación
     */
    public ReservaDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    /**
     * Inserta una nueva reserva en la base de datos.
     *
     * @param reserva Reserva a insertar
     * @return ID generado para la nueva reserva, o -1 si hubo error
     */
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

    /**
     * Obtiene todas las reservas de la base de datos.
     *
     * @return Lista de reservas
     */
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
        c.close();
        db.close();
        return lista;
    }

    /**
     * Obtiene una reserva por su ID.
     *
     * @param id ID de la reserva
     * @return Reserva encontrada o null si no existe
     */
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
        c.close();
        db.close();
        return r;
    }

    /**
     * Actualiza una reserva existente en la base de datos.
     *
     * @param reserva Reserva con datos actualizados
     * @return Número de filas afectadas
     */
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

    /**
     * Elimina una reserva por su ID.
     *
     * @param id ID de la reserva a eliminar
     */
    public void eliminarReserva(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Reserva", "id_reserva = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
