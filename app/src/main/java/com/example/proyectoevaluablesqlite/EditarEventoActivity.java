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

/**
 * Activity encargada de editar los datos de un evento existente.
 * Permite cargar su información, modificarla y guardar los cambios en la base de datos.
 */
public class EditarEventoActivity extends AppCompatActivity {

    /** Campos de entrada para los datos del evento */
    private EditText etNombre, etFecha, etAforo;

    /** Botón que guarda los cambios aplicados al evento */
    private Button btnGuardar;

    /** Acceso a las operaciones de base de datos para eventos */
    private EventoDAO eventoDAO;

    /** Identificador del evento que se va a editar */
    private int eventoId;

    /**
     * Método principal de inicialización de la actividad. Configura la interfaz,
     * carga los datos del evento seleccionado y establece las acciones de los botones.
     *
     * @param savedInstanceState Estado previo de la activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_evento);

        // Obtener el ID del evento desde el Intent
        eventoId = getIntent().getIntExtra("evento_id", -1);

        // Si no se recibe un ID válido, no tiene sentido continuar
        if (eventoId == -1) {
            finish();
            return;
        }

        // Referencias a los campos de la interfaz
        etNombre = findViewById(R.id.etNombreEvento);
        etFecha = findViewById(R.id.etFecha);
        etAforo = findViewById(R.id.etAforo);
        btnGuardar = findViewById(R.id.btnGuardar);

        eventoDAO = new EventoDAO(this);

        // Carga de los datos actuales del evento
        Evento evento = eventoDAO.obtenerEventoPorId(eventoId);
        if (evento != null) {
            etNombre.setText(evento.getNombre_evento());
            etFecha.setText(evento.getFecha());
            etAforo.setText(String.valueOf(evento.getAforo_maximo()));
        }

        // Acción del botón Guardar
        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String fecha = etFecha.getText().toString().trim();
            String aforoStr = etAforo.getText().toString().trim();

            // Validación básica de campos vacíos
            if (nombre.isEmpty() || fecha.isEmpty() || aforoStr.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            int aforo;

            // Validación del valor numérico del aforo
            try {
                aforo = Integer.parseInt(aforoStr);
                if (aforo <= 0) {
                    throw new NumberFormatException();
                }
            } catch (Exception ex) {
                Toast.makeText(this, "Aforo debe ser un número positivo", Toast.LENGTH_SHORT).show();
                return;
            }

            // Crear objeto actualizado con los nuevos datos
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

        // Acción del icono para volver a la lista de eventos
        ImageView ivVolver = findViewById(R.id.ivVolver);
        ivVolver.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListaEventosActivity.class);
            startActivity(intent);
        });
    }
}
