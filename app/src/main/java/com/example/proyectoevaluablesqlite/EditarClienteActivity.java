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


public class EditarClienteActivity extends AppCompatActivity {

    private EditText etNombre, etEmail, etTelefono;
    private Button btnGuardar;
    private ClienteDAO clienteDAO;
    private int clienteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cliente);

        clienteId = getIntent().getIntExtra("cliente_id", -1);
        if (clienteId == -1) {
            Toast.makeText(this, "Error: ID de cliente no válido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        etNombre = findViewById(R.id.etNombre);
        etEmail = findViewById(R.id.etEmail);
        etTelefono = findViewById(R.id.etTelefono);
        btnGuardar = findViewById(R.id.btnGuardar);

        clienteDAO = new ClienteDAO(this);

        // Cargar datos del cliente
        Cliente cliente = clienteDAO.obtenerClientePorId(clienteId);
        if (cliente != null) {
            etNombre.setText(cliente.getNombre());
            etEmail.setText(cliente.getEmail());
            etTelefono.setText(cliente.getTelefono());
        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String telefono = etTelefono.getText().toString().trim();

                if (nombre.isEmpty()) {
                    Toast.makeText(EditarClienteActivity.this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show();
                    return;
                }

                Cliente clienteActualizado = new Cliente(nombre, email, telefono);
                clienteActualizado.setId_cliente(clienteId);

                int filas = clienteDAO.actualizarCliente(clienteActualizado);
                if (filas > 0) {
                    Toast.makeText(EditarClienteActivity.this, "Cliente actualizado", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK); // ← NOTIFICA EL CAMBIO
                    finish();
                } else {
                    Toast.makeText(EditarClienteActivity.this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ImageView ivVolver = findViewById(R.id.ivVolver);
        ivVolver.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListaClientesActivity.class);

            startActivity(intent);
        });
    }
}