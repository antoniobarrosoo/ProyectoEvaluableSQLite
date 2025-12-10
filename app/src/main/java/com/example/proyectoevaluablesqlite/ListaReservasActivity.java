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

import adaptadores.ReservaAdapter;
import database.ReservaDAO;
import entidades.Reserva;

/**
 * Activity que muestra la lista de reservas registradas.
 * Permite editar reservas al hacer clic y eliminar reservas deslizando el item.
 */
public class ListaReservasActivity extends AppCompatActivity {

    /** RecyclerView que muestra la lista de reservas */
    private RecyclerView recyclerView;

    /** DAO para operaciones sobre la tabla de reservas */
    private ReservaDAO reservaDAO;

    /** Lista de reservas obtenida de la base de datos */
    private List<Reserva> listaReservas;

    /** Adaptador para enlazar los datos de reservas con el RecyclerView */
    private ReservaAdapter adapter;

    /**
     * Inicializa la activity, configura el RecyclerView, carga los datos
     * y establece los eventos de interacción.
     *
     * @param savedInstanceState Estado previo de la activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_reservas);

        // Inicializar RecyclerView
        recyclerView = findViewById(R.id.recyclerViewReservas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar DAO y cargar reservas
        reservaDAO = new ReservaDAO(this);
        listaReservas = reservaDAO.obtenerTodasLasReservas();

        // Configurar adaptador con clic para editar reserva
        adapter = new ReservaAdapter(listaReservas, this, new ReservaAdapter.OnReservaClickListener() {
            @Override
            public void onReservaClick(Reserva reserva) {
                Intent intent = new Intent(ListaReservasActivity.this, EditarReservaActivity.class);
                intent.putExtra("reserva_id", reserva.getId_reserva());
                startActivityForResult(intent, 1);
            }
        });
        recyclerView.setAdapter(adapter);

        // Configurar swipe para eliminar reservas
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
                Reserva reserva = listaReservas.get(position);
                reservaDAO.eliminarReserva(reserva.getId_reserva());
                listaReservas.remove(position);
                adapter.notifyItemRemoved(position);
                Toast.makeText(ListaReservasActivity.this, "Reserva eliminada", Toast.LENGTH_SHORT).show();
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
     * Recibe el resultado de la actividad EditarReservaActivity.
     * Si se actualizó una reserva, recarga la lista de reservas.
     *
     * @param requestCode Código de la solicitud.
     * @param resultCode Código del resultado devuelto.
     * @param data Intent con los datos devueltos.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            listaReservas.clear();
            listaReservas.addAll(reservaDAO.obtenerTodasLasReservas());
            adapter.notifyDataSetChanged();
        }
    }
}
