package com.example.proyectoevaluablesqlite;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import database.EventoDAO;
import entidades.Evento;

/**
 * Activity para gestionar la creación de nuevos eventos.
 * Permite ingresar nombre, fecha y aforo, guardarlos en la base de datos
 * y navegar a la lista de eventos.
 */
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

        // --- Abrir calendario al tocar el EditText de fecha ---
        etFecha.setFocusable(false); // Para que no aparezca teclado
        etFecha.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int año = calendar.get(Calendar.YEAR);
            int mes = calendar.get(Calendar.MONTH);
            int dia = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    GestionEventosActivity.this,
                    (DatePicker view, int year, int month, int dayOfMonth) -> {
                        // Formato YYYY-MM-DD para la base de datos
                        String fechaSeleccionada = year + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
                        etFecha.setText(fechaSeleccionada);
                    }, año, mes, dia);

            datePickerDialog.show();
        });

        // Botón Guardar Evento
        btnGuardarEvento.setOnClickListener(v -> {
            String nombre = etNombreEvento.getText().toString().trim();
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
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Aforo debe ser un número positivo", Toast.LENGTH_SHORT).show();
                return;
            }

            Evento evento = new Evento(nombre, fecha, aforo);
            long id = eventoDAO.insertarEvento(evento);
            if (id != -1) {
                Toast.makeText(this, "Evento guardado con ID: " + id, Toast.LENGTH_SHORT).show();
                limpiarCampos();
            } else {
                Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show();
            }
        });

        btnVerEventos.setOnClickListener(v -> startActivity(new Intent(this, ListaEventosActivity.class)));

        ImageView ivVolver = findViewById(R.id.ivVolver);
        ivVolver.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
    }

    private void limpiarCampos() {
        etNombreEvento.setText("");
        etFecha.setText("");
        etAforo.setText("");
    }
}
