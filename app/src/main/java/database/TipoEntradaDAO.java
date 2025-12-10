package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import entidades.TipoEntrada;

/**
 * Clase TipoEntradaDAO que proporciona métodos para gestionar la tabla TipoEntrada
 * de la base de datos, incluyendo operaciones CRUD.
 */
public class TipoEntradaDAO {

    /** Helper para la base de datos SQLite */
    private DbHelper dbHelper;

    /**
     * Constructor que inicializa el DbHelper.
     *
     * @param context Contexto de la aplicación
     */
    public TipoEntradaDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    /**
     * Inserta un nuevo tipo de entrada en la base de datos.
     *
     * @param tipo TipoEntrada a insertar
     * @return ID generado para el nuevo tipo de entrada, o -1 si hubo error
     */
    public long insertarTipo(TipoEntrada tipo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("nombre_tipo", tipo.getNombre_tipo());
        v.put("precio", tipo.getPrecio());
        v.put("descripcion", tipo.getDescripcion());
        long id = db.insert("TipoEntrada", null, v);
        db.close();
        return id;
    }

    /**
     * Obtiene todos los tipos de entrada de la base de datos.
     *
     * @return Lista de tipos de entrada
     */
    public List<TipoEntrada> obtenerTodos() {
        List<TipoEntrada> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query("TipoEntrada", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                TipoEntrada t = new TipoEntrada();
                t.setId_tipo_entrada(c.getInt(0));
                t.setNombre_tipo(c.getString(1));
                t.setPrecio(c.getDouble(2));
                t.setDescripcion(c.getString(3));
                lista.add(t);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return lista;
    }

    /**
     * Obtiene un tipo de entrada por su ID.
     *
     * @param id ID del tipo de entrada
     * @return TipoEntrada encontrado o null si no existe
     */
    public TipoEntrada obtenerPorId(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query("TipoEntrada", null, "id_tipo_entrada = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        TipoEntrada t = null;
        if (c.moveToFirst()) {
            t = new TipoEntrada();
            t.setId_tipo_entrada(c.getInt(0));
            t.setNombre_tipo(c.getString(1));
            t.setPrecio(c.getDouble(2));
            t.setDescripcion(c.getString(3));
        }
        c.close();
        db.close();
        return t;
    }

    /**
     * Actualiza un tipo de entrada existente en la base de datos.
     *
     * @param tipo TipoEntrada con datos actualizados
     * @return Número de filas afectadas
     */
    public int actualizarTipo(TipoEntrada tipo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("nombre_tipo", tipo.getNombre_tipo());
        v.put("precio", tipo.getPrecio());
        v.put("descripcion", tipo.getDescripcion());
        int filasAfectadas = db.update("TipoEntrada", v,
                "id_tipo_entrada = ?",
                new String[]{String.valueOf(tipo.getId_tipo_entrada())});
        db.close();
        return filasAfectadas;
    }

    /**
     * Elimina un tipo de entrada por su ID.
     *
     * @param id ID del tipo de entrada a eliminar
     */
    public void eliminarTipo(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("TipoEntrada", "id_tipo_entrada = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
