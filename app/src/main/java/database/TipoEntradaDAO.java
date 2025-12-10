package database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import entidades.TipoEntrada;

public class TipoEntradaDAO {

    private DbHelper dbHelper;

    public TipoEntradaDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

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
        c.close(); db.close();
        return lista;
    }

    public void eliminarTipo(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("TipoEntrada", "id_tipo_entrada = ?", new String[]{String.valueOf(id)});
        db.close();
    }
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
}