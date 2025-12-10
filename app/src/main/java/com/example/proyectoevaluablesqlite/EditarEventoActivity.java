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

public class EditarEventoActivity extends AppCompatActivity {

    private EditText etNombre, etFecha, etAforo;
    private Button btnGuardar;
    private EventoDAO eventoDAO;
    private int eventoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_evento);

        eventoId = getIntent().getIntExtra("evento_id", -1);
        if (eventoId == -1) {
            finish();
            return;
        }

        etNombre = findViewById(R.id.etNombreEvento);
        etFecha = findViewById(R.id.etFecha);
        etAforo = findViewById(R.id.etAforo);
        btnGuardar = findViewById(R.id.btnGuardar);

        eventoDAO = new EventoDAO(this);

        // Cargar datos del evento
        Evento evento = eventoDAO.obtenerEventoPorId(eventoId);
        if (evento != null) {
            etNombre.setText(evento.getNombre_evento());
            etFecha.setText(evento.getFecha());
            etAforo.setText(String.valueOf(evento.getAforo_maximo()));
        }

        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String fecha = etFecha.getText().toString().trim();
            String aforoStr = etAforo.getText().toString().trim();

            if (nombre.isEmpty() || fecha.isEmpty() || aforoStr.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            int aforo;
            try {
                aforo = Integer.parseInt(aforoStr);
                if (aforo <= 0) throw new NumberFormatException();
            } catch (Exception ex) {
                Toast.makeText(this, "Aforo debe ser un número positivo", Toast.LENGTH_SHORT).show();
                return;
            }

            Evento eventoActualizado = new Evento(nombre, fecha, aforo);
            eventoActualizado.setId_evento(eventoId);
            int filas = eventoDAO.actualizarEvento(eventoActualizado);
            if (filas > 0) {
                Toast.makeText(this, "Evento actualizado", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK); // ← NOTIFICA EL CAMBIO
                finish();
            } else {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        });

                ImageView ivVolver = findViewById(R.id.ivVolver);
        ivVolver.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListaEventosActivity.class);

            startActivity(intent);
        });
    }
}