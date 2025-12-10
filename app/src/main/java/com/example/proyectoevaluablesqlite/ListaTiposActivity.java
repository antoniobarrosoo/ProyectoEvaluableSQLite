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

import adaptadores.TipoEntradaAdapter;
import database.TipoEntradaDAO;
import entidades.TipoEntrada;

/**
 * Activity que muestra la lista de tipos de entrada registrados.
 * Permite editar tipos al hacer clic y eliminar tipos deslizando el item.
 */
public class ListaTiposActivity extends AppCompatActivity {

    /** RecyclerView que muestra la lista de tipos de entrada */
    private RecyclerView recyclerView;

    /** Adaptador para enlazar los datos de tipos de entrada con el RecyclerView */
    private TipoEntradaAdapter adapter;

    /** DAO para operaciones sobre la tabla de tipos de entrada */
    private TipoEntradaDAO tipoDAO;

    /** Lista de tipos de entrada obtenida de la base de datos */
    private List<TipoEntrada> listaTipos;

    /**
     * Inicializa la activity, configura el RecyclerView, carga los datos
     * y establece los eventos de interacción.
     *
     * @param savedInstanceState Estado previo de la activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_tipos);

        // Inicializar RecyclerView
        recyclerView = findViewById(R.id.recyclerViewTipos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar DAO y cargar tipos de entrada
        tipoDAO = new TipoEntradaDAO(this);
        listaTipos = tipoDAO.obtenerTodos();

        // Configurar adaptador con clic para editar tipo de entrada
        adapter = new TipoEntradaAdapter(listaTipos, new TipoEntradaAdapter.OnTipoClickListener() {
            @Override
            public void onTipoClick(TipoEntrada tipo) {
                Intent i = new Intent(ListaTiposActivity.this, EditarTipoEntradaActivity.class);
                i.putExtra("tipo_id", tipo.getId_tipo_entrada());
                startActivityForResult(i, 1);
            }
        });
        recyclerView.setAdapter(adapter);

        // Configurar swipe para eliminar tipos de entrada
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            //Cuando queremos mover un item se ejecuta:
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false; // No se permite mover items
            }
            //Se ejecuta cuando se desliza el item
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                TipoEntrada tipo = listaTipos.get(position);
                tipoDAO.eliminarTipo(tipo.getId_tipo_entrada());
                listaTipos.remove(position);
                adapter.notifyItemRemoved(position);
                Toast.makeText(ListaTiposActivity.this, "Tipo eliminado", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        // Botón para volver a la pantalla principal
        ImageView ivVolver = findViewById(R.id.ivVolver);
        ivVolver.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Recibe el resultado de la actividad EditarTipoEntradaActivity.
     * Si se actualizó un tipo, recarga la lista de tipos de entrada.
     *
     * @param requestCode Código de la solicitud.
     * @param resultCode Código del resultado devuelto.
     * @param data Intent con los datos devueltos.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            listaTipos.clear();
            listaTipos.addAll(tipoDAO.obtenerTodos());
            adapter.notifyDataSetChanged();
        }
    }
}
