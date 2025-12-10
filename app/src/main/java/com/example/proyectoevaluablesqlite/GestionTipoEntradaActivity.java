package com.example.proyectoevaluablesqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import database.TipoEntradaDAO;
import entidades.TipoEntrada;

/**
 * Activity para gestionar la creación de nuevos tipos de entrada.
 * Permite ingresar nombre, precio y descripción, guardarlos en la base de datos
 * y notificar al usuario sobre el resultado.
 */
public class GestionTipoEntradaActivity extends AppCompatActivity {

    /** Campos de entrada para el nombre, precio y descripción del tipo de entrada */
    private EditText etNombre, etPrecio, etDescripcion;

    /** Botón para guardar el tipo de entrada */
    private Button btnGuardar;

    /** Acceso a las operaciones de base de datos de tipos de entrada */
    private TipoEntradaDAO tipoDAO;

    /**
     * Inicializa la activity, configura la interfaz y establece
     * los eventos de interacción.
     *
     * @param savedInstanceState Estado previo de la activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_tipo_entrada);

        // Inicializar vistas
        etNombre = findViewById(R.id.etNombreTipo);
        etPrecio = findViewById(R.id.etPrecio);
        etDescripcion = findViewById(R.id.etDescripcion);
        btnGuardar = findViewById(R.id.btnGuardarTipo);

        // Inicializar DAO
        tipoDAO = new TipoEntradaDAO(this);

        // Acción del botón Guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = etNombre.getText().toString().trim();
                String precioStr = etPrecio.getText().toString().trim();
                String desc = etDescripcion.getText().toString().trim();

                // Validación de campos obligatorios
                if (nombre.isEmpty() || precioStr.isEmpty() || desc.isEmpty()) {
                    Toast.makeText(GestionTipoEntradaActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                    return;
                }

                double precio;

                // Validación de precio positivo
                try {
                    precio = Double.parseDouble(precioStr);
                    if (precio <= 0) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    Toast.makeText(GestionTipoEntradaActivity.this, "Precio debe ser un número positivo", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Crear tipo de entrada y guardarlo en la base de datos
                TipoEntrada tipo = new TipoEntrada(nombre, precio, desc);
                long id = tipoDAO.insertarTipo(tipo);

                if (id != -1) {
                    Toast.makeText(GestionTipoEntradaActivity.this, "Tipo guardado con ID: " + id, Toast.LENGTH_SHORT).show();
                    // Limpiar campos después de guardar
                    etNombre.setText("");
                    etPrecio.setText("");
                    etDescripcion.setText("");
                } else {
                    Toast.makeText(GestionTipoEntradaActivity.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Acción del icono para volver a la pantalla principal
        ImageView ivVolver = findViewById(R.id.ivVolver);
        ivVolver.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}
