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


public class EditarTipoEntradaActivity extends AppCompatActivity {

    private EditText etNombre, etPrecio, etDescripcion;
    private Button btnGuardar;
    private TipoEntradaDAO tipoDAO;
    private int tipoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tipo_entrada);

        tipoId = getIntent().getIntExtra("tipo_id", -1);
        if (tipoId == -1) {
            finish();
            return;
        }

        etNombre = findViewById(R.id.etNombreTipo);
        etPrecio = findViewById(R.id.etPrecio);
        etDescripcion = findViewById(R.id.etDescripcion);
        btnGuardar = findViewById(R.id.btnGuardar);

        tipoDAO = new TipoEntradaDAO(this);

        // Cargar datos del tipo
        TipoEntrada tipo = tipoDAO.obtenerPorId(tipoId);
        if (tipo != null) {
            etNombre.setText(tipo.getNombre_tipo());
            etPrecio.setText(String.valueOf(tipo.getPrecio()));
            etDescripcion.setText(tipo.getDescripcion());
        }

        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String precioStr = etPrecio.getText().toString().trim();
            String desc = etDescripcion.getText().toString().trim();

            if (nombre.isEmpty() || precioStr.isEmpty()) {
                Toast.makeText(this, "Nombre y precio obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            double precio;
            try {
                precio = Double.parseDouble(precioStr);
                if (precio <= 0) throw new NumberFormatException();
            } catch (Exception ex) {
                Toast.makeText(this, "Precio debe ser un número positivo", Toast.LENGTH_SHORT).show();
                return;
            }

            TipoEntrada tipoActualizado = new TipoEntrada(nombre, precio, desc);
            tipoActualizado.setId_tipo_entrada(tipoId);
            int filas = tipoDAO.actualizarTipo(tipoActualizado);
            if (filas > 0) {
                Toast.makeText(this, "Tipo actualizado", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK); // ← NOTIFICA EL CAMBIO
                finish();
            } else {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        });

                ImageView ivVolver = findViewById(R.id.ivVolver);
        ivVolver.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListaTiposActivity.class);

            startActivity(intent);
        });
    }
}