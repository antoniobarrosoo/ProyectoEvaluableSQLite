package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Clase DbHelper que extiende SQLiteOpenHelper para crear y gestionar la base de datos.
 * Define las tablas Cliente, TipoEntrada, Evento y Reserva, así como sus relaciones.
 */
public class DbHelper extends SQLiteOpenHelper {

    /** Nombre de la base de datos */
    private static final String DB_NAME = "discoteca.db";

    /** Versión de la base de datos */
    private static final int DB_VERSION = 1;


    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla Cliente
        db.execSQL("CREATE TABLE Cliente (" +
                "id_cliente INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "email TEXT, " +
                "telefono TEXT);");

        // Crear tabla TipoEntrada
        db.execSQL("CREATE TABLE TipoEntrada (" +
                "id_tipo_entrada INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre_tipo TEXT NOT NULL, " +
                "precio REAL NOT NULL, " +
                "descripcion TEXT);");

        // Crear tabla Evento
        db.execSQL("CREATE TABLE Evento (" +
                "id_evento INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre_evento TEXT NOT NULL, " +
                "fecha TEXT NOT NULL, " +
                "aforo_maximo INTEGER NOT NULL);");

        // Crear tabla Reserva
        db.execSQL("CREATE TABLE Reserva (" +
                "id_reserva INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_cliente INTEGER NOT NULL, " +
                "id_evento INTEGER NOT NULL, " +
                "id_tipo_entrada INTEGER NOT NULL, " +
                "FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente) ON DELETE CASCADE, " +
                "FOREIGN KEY (id_evento) REFERENCES Evento(id_evento) ON DELETE CASCADE, " +
                "FOREIGN KEY (id_tipo_entrada) REFERENCES TipoEntrada(id_tipo_entrada) ON DELETE CASCADE);");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Reserva");
        db.execSQL("DROP TABLE IF EXISTS Cliente");
        db.execSQL("DROP TABLE IF EXISTS Evento");
        db.execSQL("DROP TABLE IF EXISTS TipoEntrada");
        onCreate(db);
    }
}
