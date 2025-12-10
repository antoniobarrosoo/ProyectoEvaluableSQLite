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


public class GestionClientesActivity extends AppCompatActivity {

    private EditText etNombre, etEmail, etTelefono;
    private Button btnGuardar, btnVerLista;
    private ClienteDAO clienteDAO;

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

                if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
                    Toast.makeText(GestionClientesActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                    return;
                }

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

        // Ir a la lista de clientes
        btnVerLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GestionClientesActivity.this, ListaClientesActivity.class);
                startActivity(intent);
            }
        });
        ImageView ivVolver = findViewById(R.id.ivVolver);
        ivVolver.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);
        });
    }

    private void limpiarCampos() {
        etNombre.setText("");
        etEmail.setText("");
        etTelefono.setText("");
    }
}