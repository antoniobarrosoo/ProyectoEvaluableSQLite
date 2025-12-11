package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import entidades.Cliente;

/**
 * Clase DAO para manejar operaciones de la tabla Cliente en la base de datos SQLite.
 * Permite insertar, actualizar, eliminar y consultar clientes.
 */
public class ClienteDAO {

    /** Helper para gestionar la base de datos */
    private DbHelper dbHelper;

    /**
     * Constructor que inicializa el DbHelper.
     *
     * @param context Contexto de la aplicación.
     */
    public ClienteDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    /**
     * Inserta un nuevo cliente en la base de datos.
     *
     * @param cliente Objeto Cliente a insertar.
     * @return ID del cliente insertado o -1 si hubo error.
     */
    public long insertarCliente(Cliente cliente) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", cliente.getNombre());
        values.put("email", cliente.getEmail());
        values.put("telefono", cliente.getTelefono());
        long id = db.insert("Cliente", null, values);
        db.close();
        return id;
    }

    /**
     * Obtiene todos los clientes de la base de datos.
     *
     * @return Lista de objetos Cliente.
     */
    public List<Cliente> obtenerTodosLosClientes() {
        List<Cliente> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("Cliente", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Cliente c = new Cliente();
                c.setId_cliente(cursor.getInt(0));
                c.setNombre(cursor.getString(1));
                c.setEmail(cursor.getString(2));
                c.setTelefono(cursor.getString(3));
                lista.add(c);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }

    /**
     * Obtiene un cliente por su ID.
     *
     * @param id ID del cliente a buscar.
     * @return Objeto Cliente si se encuentra
     */
    public Cliente obtenerClientePorId(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("Cliente", null, "id_cliente = ?", new String[]{String.valueOf(id)}, null, null, null);
        Cliente c = null;
        if (cursor.moveToFirst()) {
            c = new Cliente();
            c.setId_cliente(cursor.getInt(0));
            c.setNombre(cursor.getString(1));
            c.setEmail(cursor.getString(2));
            c.setTelefono(cursor.getString(3));
        }
        cursor.close();
        db.close();
        return c;
    }

    /**
     * Actualiza los datos de un cliente existente.
     *
     * @param cliente Objeto Cliente con los datos actualizados.
     * @return Número de filas afectadas.
     */
    public int actualizarCliente(Cliente cliente) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", cliente.getNombre());
        values.put("email", cliente.getEmail());
        values.put("telefono", cliente.getTelefono());
        int filas = db.update("Cliente", values, "id_cliente = ?", new String[]{String.valueOf(cliente.getId_cliente())});
        db.close();
        return filas;
    }

    /**
     * Elimina un cliente de la base de datos por su ID.
     *
     * @param id ID del cliente a eliminar.
     */
    public void eliminarCliente(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Cliente", "id_cliente = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
