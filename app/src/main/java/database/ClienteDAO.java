package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import entidades.Cliente;

public class ClienteDAO {

    private DbHelper dbHelper;

    public ClienteDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    // Insertar cliente
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

    // Obtener todos los clientes
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

    // Obtener un cliente por ID
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

    // Actualizar cliente
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

    //Actualizar cliente
    public void eliminarCliente(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Cliente", "id_cliente = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}