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

import adaptadores.EventoAdapter;
import database.EventoDAO;
import entidades.Evento;

/**
 * Activity que muestra la lista de eventos registrados.
 * Permite editar eventos al hacer clic y eliminar eventos deslizando el item.
 */
public class ListaEventosActivity extends AppCompatActivity {

    /** RecyclerView que muestra la lista de eventos */
    private RecyclerView recyclerView;

    /** Adaptador para enlazar los datos de eventos con el RecyclerView */
    private EventoAdapter adapter;

    /** DAO para operaciones sobre la tabla de eventos */
    private EventoDAO eventoDAO;

    /** Lista de eventos obtenida de la base de datos */
    private List<Evento> listaEventos;

    /**
     * Inicializa la activity, configura el RecyclerView, carga los datos
     * y establece los eventos de interacción.
     *
     * @param savedInstanceState Estado previo de la activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_eventos);

        // Inicializar RecyclerView
        recyclerView = findViewById(R.id.recyclerViewEventos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar DAO y cargar eventos
        eventoDAO = new EventoDAO(this);
        listaEventos = eventoDAO.obtenerTodosLosEventos();

        // Configurar adaptador con clic para editar evento
        adapter = new EventoAdapter(listaEventos, new EventoAdapter.OnEventoClickListener() {
            @Override
            public void onEventoClick(Evento evento) {
                Intent i = new Intent(ListaEventosActivity.this, EditarEventoActivity.class);
                i.putExtra("evento_id", evento.getId_evento());
                startActivityForResult(i, 1);
            }
        });
        recyclerView.setAdapter(adapter);

        // Configurar swipe para eliminar eventos
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false; // No se permite mover items
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Evento evento = listaEventos.get(position);
                eventoDAO.eliminarEvento(evento.getId_evento());
                listaEventos.remove(position);
                adapter.notifyItemRemoved(position);
                Toast.makeText(ListaEventosActivity.this, "Evento eliminado", Toast.LENGTH_SHORT).show();
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
     * Recibe el resultado de la actividad EditarEventoActivity.
     * Si se actualizó un evento, recarga la lista de eventos.
     *
     * @param requestCode Código de la solicitud.
     * @param resultCode Código del resultado devuelto.
     * @param data Intent con los datos devueltos.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            listaEventos.clear();
            listaEventos.addAll(eventoDAO.obtenerTodosLosEventos());
            adapter.notifyDataSetChanged();
        }
    }
}
