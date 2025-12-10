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


public class GestionTipoEntradaActivity extends AppCompatActivity {

    private EditText etNombre, etPrecio, etDescripcion;
    private Button btnGuardar;
    private TipoEntradaDAO tipoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_tipo_entrada);

        etNombre = findViewById(R.id.etNombreTipo);
        etPrecio = findViewById(R.id.etPrecio);
        etDescripcion = findViewById(R.id.etDescripcion);
        btnGuardar = findViewById(R.id.btnGuardarTipo);

        tipoDAO = new TipoEntradaDAO(this);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString().trim();
                String precioStr = etPrecio.getText().toString().trim();
                String desc = etDescripcion.getText().toString().trim();

                if (nombre.isEmpty() || precioStr.isEmpty() || desc.isEmpty()) {
                    Toast.makeText(GestionTipoEntradaActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                    return;
                }

                double precio;
                try {
                    precio = Double.parseDouble(precioStr);
                    if (precio <= 0) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    Toast.makeText(GestionTipoEntradaActivity.this, "Precio debe ser un número positivo", Toast.LENGTH_SHORT).show();
                    return;
                }

                TipoEntrada tipo = new TipoEntrada(nombre, precio, desc);
                long id = tipoDAO.insertarTipo(tipo);

                if (id != -1) {
                    Toast.makeText(GestionTipoEntradaActivity.this, "Tipo guardado con ID: " + id, Toast.LENGTH_SHORT).show();
                    etNombre.setText("");
                    etPrecio.setText("");
                    etDescripcion.setText("");
                } else {
                    Toast.makeText(GestionTipoEntradaActivity.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                }
            }
        });

                ImageView ivVolver = findViewById(R.id.ivVolver);
        ivVolver.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);
        });
    }
}