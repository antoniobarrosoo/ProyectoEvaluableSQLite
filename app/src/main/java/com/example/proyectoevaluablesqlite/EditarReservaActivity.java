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
 * Activity destinada a editar una reserva existente. Permite seleccionar nuevamente
 * el cliente, el evento y el tipo de entrada asociados, y guardar los cambios aplicados.
 */
public class EditarReservaActivity extends AppCompatActivity {

    /** Spinners para seleccionar cliente, evento y tipo de entrada */
    private Spinner spinnerClientes, spinnerEventos, spinnerTipos;

    /** Botón que guarda la reserva modificada */
    private Button btnGuardar;

    /** Acceso a operaciones en la tabla reservas */
    private ReservaDAO reservaDAO;

    /** Identificador de la reserva que se va a editar */
    private int reservaId;

    /** IDs seleccionados en los spinners */
    private int clienteSeleccionadoId = -1;
    private int eventoSeleccionadoId = -1;
    private int tipoSeleccionadoId = -1;

    /**
     * Inicializa la activity, carga la reserva existente y configura los elementos interactivos.
     *
     * @param savedInstanceState Estado previo de la activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_reserva);

        // Se obtiene el ID de la reserva desde el intent
        reservaId = getIntent().getIntExtra("reserva_id", -1);
        if (reservaId == -1) {
            Toast.makeText(this, "Reserva no válida", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Referencias a los elementos de la interfaz
        spinnerClientes = findViewById(R.id.spinnerClientes);
        spinnerEventos = findViewById(R.id.spinnerEventos);
        spinnerTipos = findViewById(R.id.spinnerTipos);
        btnGuardar = findViewById(R.id.btnGuardar);

        reservaDAO = new ReservaDAO(this);

        // Se obtiene la reserva actual de la base de datos
        Reserva reserva = reservaDAO.obtenerReservaPorId(reservaId);
        if (reserva == null) {
            Toast.makeText(this, "No se encontró la reserva", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Cargar los datos necesarios en los spinners y seleccionar los valores actuales
        cargarYSeleccionarClientes(reserva.getId_cliente());
        cargarYSeleccionarEventos(reserva.getId_evento());
        cargarYSeleccionarTipos(reserva.getId_tipo_entrada());

        // Guardar cambios de la reserva
        btnGuardar.setOnClickListener(v -> {
            if (clienteSeleccionadoId == -1 || eventoSeleccionadoId == -1 || tipoSeleccionadoId == -1) {
                Toast.makeText(this, "Selecciona todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Reserva r = new Reserva(clienteSeleccionadoId, eventoSeleccionadoId, tipoSeleccionadoId);
            r.setId_reserva(reservaId);

            int filas = reservaDAO.actualizarReserva(r);
            if (filas > 0) {
                Toast.makeText(this, "Reserva actualizada", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        });

        // Acción del botón para regresar a la lista de reservas
        ImageView ivVolver = findViewById(R.id.ivVolver);
        ivVolver.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListaReservasActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Carga en el spinner los clientes registrados y selecciona el cliente
     * correspondiente a la reserva actual.
     *
     * @param idReservaCliente ID del cliente asociado a la reserva.
     */
    private void cargarYSeleccionarClientes(int idReservaCliente) {
        ClienteDAO dao = new ClienteDAO(this);
        List<Cliente> clientes = dao.obtenerTodosLosClientes();

        if (clientes.isEmpty()) {
            Toast.makeText(this, "No hay clientes", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] nombres = new String[clientes.size()];
        int indiceSeleccionado = 0;

        for (int i = 0; i < clientes.size(); i++) {
            nombres[i] = clientes.get(i).getNombre();
            if (clientes.get(i).getId_cliente() == idReservaCliente) {
                indiceSeleccionado = i;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerClientes.setAdapter(adapter);
        spinnerClientes.setSelection(indiceSeleccionado);

        spinnerClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                clienteSeleccionadoId = clientes.get(pos).getId_cliente();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                clienteSeleccionadoId = -1;
            }
        });
    }

    /**
     * Carga en el spinner los eventos disponibles y selecciona el evento
     * asignado actualmente a la reserva.
     *
     * @param idReservaEvento ID del evento ligado a la reserva.
     */
    private void cargarYSeleccionarEventos(int idReservaEvento) {
        EventoDAO dao = new EventoDAO(this);
        List<Evento> eventos = dao.obtenerTodosLosEventos();

        if (eventos.isEmpty()) {
            Toast.makeText(this, "No hay eventos", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] nombres = new String[eventos.size()];
        int indiceSeleccionado = 0;

        for (int i = 0; i < eventos.size(); i++) {
            nombres[i] = eventos.get(i).getNombre_evento() + " (" + eventos.get(i).getFecha() + ")";
            if (eventos.get(i).getId_evento() == idReservaEvento) {
                indiceSeleccionado = i;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerEventos.setAdapter(adapter);
        spinnerEventos.setSelection(indiceSeleccionado);

        spinnerEventos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                eventoSeleccionadoId = eventos.get(pos).getId_evento();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                eventoSeleccionadoId = -1;
            }
        });
    }

    /**
     * Carga en el spinner los tipos de entrada y selecciona el tipo asociado
     * actualmente a la reserva.
     *
     * @param idReservaTipo ID del tipo de entrada de la reserva.
     */
    private void cargarYSeleccionarTipos(int idReservaTipo) {
        TipoEntradaDAO dao = new TipoEntradaDAO(this);
        List<TipoEntrada> tipos = dao.obtenerTodos();

        if (tipos.isEmpty()) {
            Toast.makeText(this, "No hay tipos de entrada", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] nombres = new String[tipos.size()];
        int indiceSeleccionado = 0;

        for (int i = 0; i < tipos.size(); i++) {
            nombres[i] = tipos.get(i).getNombre_tipo() + " (" + tipos.get(i).getPrecio() + "€)";
            if (tipos.get(i).getId_tipo_entrada() == idReservaTipo) {
                indiceSeleccionado = i;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerTipos.setAdapter(adapter);
        spinnerTipos.setSelection(indiceSeleccionado);

        spinnerTipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                tipoSeleccionadoId = tipos.get(pos).getId_tipo_entrada();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tipoSeleccionadoId = -1;
            }
        });
    }
}
