package com.example.proyectoevaluablesqlite;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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
 * Activity encargada de editar los datos de un evento existente.
 * Permite cargar su información, modificarla y guardar los cambios en la base de datos.
 */
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
        if (eventoId == -1) { finish(); return; }

        etNombre = findViewById(R.id.etNombreEvento);
        etFecha = findViewById(R.id.etFecha);
        etAforo = findViewById(R.id.etAforo);
        btnGuardar = findViewById(R.id.btnGuardar);

        eventoDAO = new EventoDAO(this);

        // Cargar datos actuales
        Evento evento = eventoDAO.obtenerEventoPorId(eventoId);
        if (evento != null) {
            etNombre.setText(evento.getNombre_evento());
            etFecha.setText(evento.getFecha());
            etAforo.setText(String.valueOf(evento.getAforo_maximo()));
        }

        // --- Abrir calendario al tocar el EditText de fecha ---
        etFecha.setFocusable(false);
        etFecha.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int año = calendar.get(Calendar.YEAR);
            int mes = calendar.get(Calendar.MONTH);
            int dia = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    EditarEventoActivity.this,
                    (DatePicker view, int year, int month, int dayOfMonth) -> {
                        String fechaSeleccionada = year + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
                        etFecha.setText(fechaSeleccionada);
                    }, año, mes, dia
            );
            datePickerDialog.show();
        });

        // Guardar cambios
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
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView ivVolver = findViewById(R.id.ivVolver);
        ivVolver.setOnClickListener(v -> startActivity(new Intent(this, ListaEventosActivity.class)));
    }
}
