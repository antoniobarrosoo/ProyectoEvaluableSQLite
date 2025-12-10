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

public class EditarReservaActivity extends AppCompatActivity {

    private Spinner spinnerClientes, spinnerEventos, spinnerTipos;
    private Button btnGuardar;
    private ReservaDAO reservaDAO;
    private int reservaId;

    private int clienteSeleccionadoId = -1;
    private int eventoSeleccionadoId = -1;
    private int tipoSeleccionadoId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_reserva);

        reservaId = getIntent().getIntExtra("reserva_id", -1);
        if (reservaId == -1) {
            Toast.makeText(this, "Reserva no válida", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        spinnerClientes = findViewById(R.id.spinnerClientes);
        spinnerEventos = findViewById(R.id.spinnerEventos);
        spinnerTipos = findViewById(R.id.spinnerTipos);
        btnGuardar = findViewById(R.id.btnGuardar);

        reservaDAO = new ReservaDAO(this);

        // Cargar la reserva actual
        Reserva reserva = reservaDAO.obtenerReservaPorId(reservaId);
        if (reserva == null) {
            Toast.makeText(this, "No se encontró la reserva", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Cargar y seleccionar clientes
        cargarYSeleccionarClientes(reserva.getId_cliente());
        // Cargar y seleccionar eventos
        cargarYSeleccionarEventos(reserva.getId_evento());
        // Cargar y seleccionar tipos
        cargarYSeleccionarTipos(reserva.getId_tipo_entrada());

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
                setResult(RESULT_OK); // ← NOTIFICA EL CAMBIO
                finish();
            } else {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        });

                ImageView ivVolver = findViewById(R.id.ivVolver);
        ivVolver.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListaReservasActivity.class);

            startActivity(intent);
        });
    }

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