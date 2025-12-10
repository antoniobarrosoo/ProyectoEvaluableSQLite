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
 * Activity que permite editar un tipo de entrada existente.
 * Carga los datos actuales, permite modificarlos y guarda los cambios en la base de datos.
 */
public class EditarTipoEntradaActivity extends AppCompatActivity {

    /** Campos de entrada para el nombre, precio y descripción del tipo */
    private EditText etNombre, etPrecio, etDescripcion;

    /** Botón para guardar los cambios realizados */
    private Button btnGuardar;

    /** Acceso a las operaciones de base de datos para tipos de entrada */
    private TipoEntradaDAO tipoDAO;

    /** Identificador del tipo de entrada que se va a editar */
    private int tipoId;

    /**
     * Inicializa la activity, carga los datos del tipo de entrada
     * y configura los elementos interactivos.
     *
     * @param savedInstanceState Estado previo de la activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tipo_entrada);

        // Obtener el ID del tipo de entrada desde el Intent
        tipoId = getIntent().getIntExtra("tipo_id", -1);
        if (tipoId == -1) {
            finish();
            return;
        }

        // Referencias a los componentes de la interfaz
        etNombre = findViewById(R.id.etNombreTipo);
        etPrecio = findViewById(R.id.etPrecio);
        etDescripcion = findViewById(R.id.etDescripcion);
        btnGuardar = findViewById(R.id.btnGuardar);

        tipoDAO = new TipoEntradaDAO(this);

        // Cargar los datos actuales del tipo de entrada
        TipoEntrada tipo = tipoDAO.obtenerPorId(tipoId);
        if (tipo != null) {
            etNombre.setText(tipo.getNombre_tipo());
            etPrecio.setText(String.valueOf(tipo.getPrecio()));
            etDescripcion.setText(tipo.getDescripcion());
        }

        // Acción del botón Guardar
        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String precioStr = etPrecio.getText().toString().trim();
            String desc = etDescripcion.getText().toString().trim();

            // Validación básica de campos obligatorios
            if (nombre.isEmpty() || precioStr.isEmpty()) {
                Toast.makeText(this, "Nombre y precio obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            double precio;

            // Validación de precio positivo
            try {
                precio = Double.parseDouble(precioStr);
                if (precio <= 0) throw new NumberFormatException();
            } catch (Exception ex) {
                Toast.makeText(this, "Precio debe ser un número positivo", Toast.LENGTH_SHORT).show();
                return;
            }

            // Crear objeto actualizado y guardar en la base de datos
            TipoEntrada tipoActualizado = new TipoEntrada(nombre, precio, desc);
            tipoActualizado.setId_tipo_entrada(tipoId);

            int filas = tipoDAO.actualizarTipo(tipoActualizado);

            if (filas > 0) {
                Toast.makeText(this, "Tipo actualizado", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        });

        // Acción del icono para volver a la lista de tipos
        ImageView ivVolver = findViewById(R.id.ivVolver);
        ivVolver.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListaTiposActivity.class);
            startActivity(intent);
        });
    }
}
