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


public class ListaReservasActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReservaDAO reservaDAO;
    private List<Reserva> listaReservas;
    private ReservaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_reservas);

        recyclerView = findViewById(R.id.recyclerViewReservas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reservaDAO = new ReservaDAO(this);
        listaReservas = reservaDAO.obtenerTodasLasReservas();

        // Guardar el adaptador en un campo
        adapter = new ReservaAdapter(listaReservas, this, new ReservaAdapter.OnReservaClickListener() {
            @Override
            public void onReservaClick(Reserva reserva) {
                Intent intent = new Intent(ListaReservasActivity.this, EditarReservaActivity.class);
                intent.putExtra("reserva_id", reserva.getId_reserva());
                startActivityForResult(intent, 1);
            }

        });

        recyclerView.setAdapter(adapter);

        // Swipe para eliminar (ya funciona en tiempo real)
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

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

                ImageView ivVolver = findViewById(R.id.ivVolver);
        ivVolver.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Recargar datos desde la base de datos
            listaReservas.clear();
            listaReservas.addAll(reservaDAO.obtenerTodasLasReservas());
            adapter.notifyDataSetChanged();
        }
    }
}