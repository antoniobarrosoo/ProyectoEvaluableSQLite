package com.example.proyectoevaluablesqlite;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import database.EventoDAO;
import entidades.Evento;


public class GestionEventosActivity extends AppCompatActivity {

    private EditText etNombreEvento, etFecha, etAforo;
    private Button btnGuardarEvento, btnVerEventos;
    private EventoDAO eventoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_eventos);

        etNombreEvento = findViewById(R.id.etNombreEvento);
        etFecha = findViewById(R.id.etFecha);
        etAforo = findViewById(R.id.etAforo);
        btnGuardarEvento = findViewById(R.id.btnGuardarEvento);
        btnVerEventos = findViewById(R.id.btnVerEventos);

        eventoDAO = new EventoDAO(this);

        btnGuardarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombreEvento.getText().toString().trim();
                String fecha = etFecha.getText().toString().trim();
                String aforoStr = etAforo.getText().toString().trim();

                if (nombre.isEmpty() || fecha.isEmpty() || aforoStr.isEmpty()) {
                    Toast.makeText(GestionEventosActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                    return;
                }

                int aforo;
                try {
                    aforo = Integer.parseInt(aforoStr);
                    if (aforo <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(GestionEventosActivity.this, "Aforo debe ser un número positivo", Toast.LENGTH_SHORT).show();
                    return;
                }

                Evento evento = new Evento(nombre, fecha, aforo);
                long id = eventoDAO.insertarEvento(evento);

                if (id != -1) {
                    Toast.makeText(GestionEventosActivity.this, "Evento guardado con ID: " + id, Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                } else {
                    Toast.makeText(GestionEventosActivity.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnVerEventos.setOnClickListener(v -> {
            startActivity(new Intent(this, ListaEventosActivity.class));
        });
        ImageView ivVolver = findViewById(R.id.ivVolver);
        ivVolver.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);
        });
    }

    private void limpiarCampos() {
        etNombreEvento.setText("");
        etFecha.setText("");
        etAforo.setText("");
    }
}