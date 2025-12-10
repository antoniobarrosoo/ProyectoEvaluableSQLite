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
 * Activity para gestionar la creación de nuevos eventos.
 * Permite ingresar nombre, fecha y aforo, guardarlos en la base de datos
 * y navegar a la lista de eventos.
 */
public class GestionEventosActivity extends AppCompatActivity {

    /** Campos de entrada para nombre, fecha y aforo del evento */
    private EditText etNombreEvento, etFecha, etAforo;

    /** Botón para guardar el evento y botón para ver la lista de eventos */
    private Button btnGuardarEvento, btnVerEventos;

    /** Acceso a las operaciones de base de datos de eventos */
    private EventoDAO eventoDAO;

    /**
     * Inicializa la activity, configura la interfaz y establece
     * los eventos de interacción.
     *
     * @param savedInstanceState Estado previo de la activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_eventos);

        // Inicializar vistas
        etNombreEvento = findViewById(R.id.etNombreEvento);
        etFecha = findViewById(R.id.etFecha);
        etAforo = findViewById(R.id.etAforo);
        btnGuardarEvento = findViewById(R.id.btnGuardarEvento);
        btnVerEventos = findViewById(R.id.btnVerEventos);

        // Inicializar DAO
        eventoDAO = new EventoDAO(this);

        // Acción del botón Guardar
        btnGuardarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = etNombreEvento.getText().toString().trim();
                String fecha = etFecha.getText().toString().trim();
                String aforoStr = etAforo.getText().toString().trim();

                // Validación de campos obligatorios
                if (nombre.isEmpty() || fecha.isEmpty() || aforoStr.isEmpty()) {
                    Toast.makeText(GestionEventosActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                    return;
                }

                int aforo;

                // Validación de aforo como número positivo
                try {
                    aforo = Integer.parseInt(aforoStr);
                    if (aforo <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(GestionEventosActivity.this, "Aforo debe ser un número positivo", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Crear evento y guardarlo en la base de datos
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

        // Acción del botón para ver la lista de eventos
        btnVerEventos.setOnClickListener(v -> startActivity(new Intent(this, ListaEventosActivity.class)));

        // Acción del icono para volver a la pantalla principal
        ImageView ivVolver = findViewById(R.id.ivVolver);
        ivVolver.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Limpia los campos de entrada después de guardar un evento.
     */
    private void limpiarCampos() {
        etNombreEvento.setText("");
        etFecha.setText("");
        etAforo.setText("");
    }
}
