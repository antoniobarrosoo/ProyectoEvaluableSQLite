package com.example.proyectoevaluablesqlite;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import adaptadores.ClienteAdapter;
import database.ClienteDAO;
import entidades.Cliente;

public class ListaClientesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ClienteAdapter adapter;
    private ClienteDAO clienteDAO;
    private List<Cliente> listaClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);

        recyclerView = findViewById(R.id.recyclerViewClientes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        clienteDAO = new ClienteDAO(this);
        listaClientes = clienteDAO.obtenerTodosLosClientes();

        ImageView ivVolver = findViewById(R.id.ivVolver);
        ivVolver.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);
        });


        adapter = new ClienteAdapter(listaClientes, new ClienteAdapter.OnClienteClickListener() {
            @Override
            public void onClienteClick(Cliente cliente) {
                Intent i = new Intent(ListaClientesActivity.this, EditarClienteActivity.class);
                i.putExtra("cliente_id", cliente.getId_cliente());
                startActivityForResult(i, 1);
            }
        });
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Cliente cliente = listaClientes.get(position);
                clienteDAO.eliminarCliente(cliente.getId_cliente());
                listaClientes.remove(position);
                adapter.notifyItemRemoved(position);
                Toast.makeText(ListaClientesActivity.this, "Cliente eliminado", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            listaClientes.clear();
            listaClientes.addAll(clienteDAO.obtenerTodosLosClientes());
            adapter.notifyDataSetChanged();
        }
    }
}