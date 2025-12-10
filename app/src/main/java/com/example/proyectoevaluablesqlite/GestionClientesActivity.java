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
 * Activity para gestionar la creación de nuevos clientes.
 * Permite ingresar datos, guardarlos en la base de datos y navegar a la lista de clientes.
 */
public class GestionClientesActivity extends AppCompatActivity {

    /** Campos de entrada para los datos del cliente */
    private EditText etNombre, etEmail, etTelefono;

    /** Botón para guardar el cliente y botón para ver la lista de clientes */
    private Button btnGuardar, btnVerLista;

    /** Acceso a las operaciones de base de datos de clientes */
    private ClienteDAO clienteDAO;

    /**
     * Inicializa la actividad, configura la interfaz y establece
     * los eventos de interacción.
     *
     * @param savedInstanceState Estado previo de la activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_clientes);

        // Inicializar vistas
        etNombre = findViewById(R.id.etNombre);
        etEmail = findViewById(R.id.etEmail);
        etTelefono = findViewById(R.id.etTelefono);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnVerLista = findViewById(R.id.btnVerLista);

        // Inicializar DAO
        clienteDAO = new ClienteDAO(this);

        // Acción del botón Guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = etNombre.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String telefono = etTelefono.getText().toString().trim();

                // Validación de campos obligatorios
                if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
                    Toast.makeText(GestionClientesActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Crear cliente y guardarlo en la base de datos
                Cliente cliente = new Cliente(nombre, email, telefono);
                long id = clienteDAO.insertarCliente(cliente);

                if (id != -1) {
                    Toast.makeText(GestionClientesActivity.this, "Cliente guardado con ID: " + id, Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                } else {
                    Toast.makeText(GestionClientesActivity.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Acción del botón para ver la lista de clientes
        btnVerLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GestionClientesActivity.this, ListaClientesActivity.class);
                startActivity(intent);
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
     * Limpia los campos de entrada después de guardar un cliente.
     */
    private void limpiarCampos() {
        etNombre.setText("");
        etEmail.setText("");
        etTelefono.setText("");
    }
}
