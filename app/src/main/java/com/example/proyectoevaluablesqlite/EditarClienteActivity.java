package com.example.proyectoevaluablesqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import database.ClienteDAO;
import entidades.Cliente;

/**
 * Activity que permite editar los datos de un cliente ya existente.
 * Carga su información, permite modificarla y guarda los cambios en la base de datos.
 */
public class EditarClienteActivity extends AppCompatActivity {

    /** Campos de entrada para los datos del cliente */
    private EditText etNombre, etEmail, etTelefono;

    /** Botón encargado de guardar los cambios */
    private Button btnGuardar;

    /** Acceso a las operaciones de base de datos relacionadas con clientes */
    private ClienteDAO clienteDAO;

    /** Identificador del cliente que se va a editar */
    private int clienteId;

    /**
     * Inicializa la actividad, carga los elementos de la interfaz,
     * obtiene el cliente a editar y configura los eventos de interacción.
     *
     * @param savedInstanceState Estado previo de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cliente);

        // Se obtiene el ID del cliente recibido desde la actividad anterior
        clienteId = getIntent().getIntExtra("cliente_id", -1);

        // Si no se recibe un ID válido, se finaliza la activity
        if (clienteId == -1) {
            Toast.makeText(this, "Error: ID de cliente no válido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Referencias a los componentes de la interfaz
        etNombre = findViewById(R.id.etNombre);
        etEmail = findViewById(R.id.etEmail);
        etTelefono = findViewById(R.id.etTelefono);
        btnGuardar = findViewById(R.id.btnGuardar);

        clienteDAO = new ClienteDAO(this);

        // Carga de los datos del cliente seleccionado
        Cliente cliente = clienteDAO.obtenerClientePorId(clienteId);
        if (cliente != null) {
            etNombre.setText(cliente.getNombre());
            etEmail.setText(cliente.getEmail());
            etTelefono.setText(cliente.getTelefono());
        }

        // Acción del botón Guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = etNombre.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String telefono = etTelefono.getText().toString().trim();

                // Validación del nombre
                if (nombre.isEmpty()) {
                    Toast.makeText(EditarClienteActivity.this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Se crea un objeto cliente con los datos actualizados
                Cliente clienteActualizado = new Cliente(nombre, email, telefono);
                clienteActualizado.setId_cliente(clienteId);

                int filas = clienteDAO.actualizarCliente(clienteActualizado);

                // Resultado de la operación
                if (filas > 0) {
                    Toast.makeText(EditarClienteActivity.this, "Cliente actualizado", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(EditarClienteActivity.this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Acción del icono para volver atrás
        ImageView ivVolver = findViewById(R.id.ivVolver);
        ivVolver.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListaClientesActivity.class);
            startActivity(intent);
        });
    }
}
