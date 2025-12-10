package com.example.proyectoevaluablesqlite;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import adaptadores.ClienteAdapter;
import database.ClienteDAO;
import entidades.Cliente;

/**
 * Activity que muestra la lista de clientes registrados.
 * Permite editar clientes al hacer clic y eliminar clientes deslizando el item.
 */
public class ListaClientesActivity extends AppCompatActivity {

    /** RecyclerView que muestra la lista de clientes */
    private RecyclerView recyclerView;

    /** Adaptador para enlazar los datos de clientes con el RecyclerView */
    private ClienteAdapter adapter;

    /** DAO para operaciones sobre la tabla de clientes */
    private ClienteDAO clienteDAO;

    /** Lista de clientes obtenida de la base de datos */
    private List<Cliente> listaClientes;

    /**
     * Inicializa la activity, configura el RecyclerView, carga los datos
     * y establece los eventos de interacción.
     *
     * @param savedInstanceState Estado previo de la activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);

        // Inicializar RecyclerView
        recyclerView = findViewById(R.id.recyclerViewClientes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar DAO y cargar clientes
        clienteDAO = new ClienteDAO(this);
        listaClientes = clienteDAO.obtenerTodosLosClientes();

        // Botón para volver a la pantalla principal
        ImageView ivVolver = findViewById(R.id.ivVolver);
        ivVolver.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        // Configurar adaptador con clic para editar cliente
        adapter = new ClienteAdapter(listaClientes, new ClienteAdapter.OnClienteClickListener() {
            @Override
            public void onClienteClick(Cliente cliente) {
                Intent i = new Intent(ListaClientesActivity.this, EditarClienteActivity.class);
                i.putExtra("cliente_id", cliente.getId_cliente());
                startActivityForResult(i, 1);
            }
        });
        recyclerView.setAdapter(adapter);

        // Configurar swipe para eliminar clientes
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            //Cuando queremos mover un item se ejecuta:
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false; // No se permite mover items
            }

            @Override
            //Se ejecuta cuando se desliza el item
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

    /**
     * Recibe el resultado de la actividad EditarClienteActivity.
     * Si se actualizó un cliente, recarga la lista de clientes.
     *
     * @param requestCode Código de la solicitud.
     * @param resultCode Código del resultado devuelto.
     * @param data Intent con los datos devueltos.
     */
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
