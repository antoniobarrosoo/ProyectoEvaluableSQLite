package com.example.proyectoevaluablesqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import database.ClienteDAO;
import database.EventoDAO;
import database.ReservaDAO;
import database.TipoEntradaDAO;
import entidades.Cliente;
import entidades.Evento;
import entidades.Reserva;
import entidades.TipoEntrada;

/**
 * Activity para gestionar la creación de nuevas reservas.
 * Permite seleccionar cliente, evento y tipo de entrada,
 * y guardar la reserva en la base de datos.
 */
public class GestionReservaActivity extends AppCompatActivity {

    /** Spinners para seleccionar cliente, evento y tipo de entrada */
    private Spinner spinnerClientes, spinnerEventos, spinnerTipos;

    /** Botón para guardar la reserva */
    private Button btnGuardarReserva;

    /** Listas de clientes, eventos y tipos de entrada disponibles */
    private List<Cliente> listaClientes;
    private List<Evento> listaEventos;
    private List<TipoEntrada> listaTipos;

    /** IDs seleccionados por el usuario */
    private int clienteId = -1, eventoId = -1, tipoId = -1;

    /**
     * Inicializa la activity, carga los datos necesarios en los spinners
     * y configura los eventos de interacción.
     *
     * @param savedInstanceState Estado previo de la activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_reserva);

        // Inicializar spinners y botón
        spinnerClientes = findViewById(R.id.spinnerClientes);
        spinnerEventos = findViewById(R.id.spinnerEventos);
        spinnerTipos = findViewById(R.id.spinnerTipos);
        btnGuardarReserva = findViewById(R.id.btnGuardarReserva);

        // Cargar datos en los spinners
        cargarClientes();
        cargarEventos();
        cargarTipos();

        // Acción del botón Guardar
        btnGuardarReserva.setOnClickListener(v -> {
            if (clienteId == -1 || eventoId == -1 || tipoId == -1) {
                Toast.makeText(this, "Selecciona cliente, evento y tipo", Toast.LENGTH_SHORT).show();
                return;
            }

            // Crear reserva y guardarla en la base de datos
            Reserva r = new Reserva(clienteId, eventoId, tipoId);
            ReservaDAO dao = new ReservaDAO(this);
            long id = dao.insertarReserva(r);

            if (id != -1) {
                Toast.makeText(this, "Reserva creada con ID: " + id, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al crear reserva", Toast.LENGTH_SHORT).show();
            }
        });

        // Acción del icono para volver a la pantalla principal
        ImageView ivVolver = findViewById(R.id.ivVolver);
        ivVolver.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Carga los clientes disponibles en el spinner y configura la selección.
     */
    private void cargarClientes() {
        ClienteDAO dao = new ClienteDAO(this);
        listaClientes = dao.obtenerTodosLosClientes();

        if (listaClientes.isEmpty()) {
            Toast.makeText(this, "Registra clientes primero", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] nombres = new String[listaClientes.size()];
        for (int i = 0; i < listaClientes.size(); i++) {
            nombres[i] = listaClientes.get(i).getNombre();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClientes.setAdapter(adapter);

        spinnerClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                clienteId = listaClientes.get(pos).getId_cliente();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                clienteId = -1;
            }
        });
    }

    /**
     * Carga los eventos disponibles en el spinner y configura la selección.
     */
    private void cargarEventos() {
        EventoDAO dao = new EventoDAO(this);
        listaEventos = dao.obtenerTodosLosEventos();

        if (listaEventos.isEmpty()) {
            Toast.makeText(this, "Crea eventos primero", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] nombres = new String[listaEventos.size()];
        for (int i = 0; i < listaEventos.size(); i++) {
            nombres[i] = listaEventos.get(i).getNombre_evento() + " (" + listaEventos.get(i).getFecha() + ")";
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEventos.setAdapter(adapter);

        spinnerEventos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                eventoId = listaEventos.get(pos).getId_evento();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                eventoId = -1;
            }
        });
    }

    /**
     * Carga los tipos de entrada disponibles en el spinner y configura la selección.
     */
    private void cargarTipos() {
        TipoEntradaDAO dao = new TipoEntradaDAO(this);
        listaTipos = dao.obtenerTodos();

        if (listaTipos.isEmpty()) {
            Toast.makeText(this, "Crea tipos de entrada primero", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] nombres = new String[listaTipos.size()];
        for (int i = 0; i < listaTipos.size(); i++) {
            nombres[i] = listaTipos.get(i).getNombre_tipo() + " (" + listaTipos.get(i).getPrecio() + "€)";
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipos.setAdapter(adapter);

        spinnerTipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                tipoId = listaTipos.get(pos).getId_tipo_entrada();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tipoId = -1;
            }
        });
    }
}
