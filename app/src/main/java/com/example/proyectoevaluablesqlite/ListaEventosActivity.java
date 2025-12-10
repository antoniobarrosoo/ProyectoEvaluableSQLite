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

public class ListaEventosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventoAdapter adapter;
    private EventoDAO eventoDAO;
    private List<Evento> listaEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_eventos);

        recyclerView = findViewById(R.id.recyclerViewEventos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventoDAO = new EventoDAO(this);
        listaEventos = eventoDAO.obtenerTodosLosEventos();


        adapter = new EventoAdapter(listaEventos, new EventoAdapter.OnEventoClickListener() {
            @Override
            public void onEventoClick(Evento evento) {
                Intent i = new Intent(ListaEventosActivity.this, EditarEventoActivity.class);
                i.putExtra("evento_id", evento.getId_evento());
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
                Evento evento = listaEventos.get(position);
                eventoDAO.eliminarEvento(evento.getId_evento());
                listaEventos.remove(position);
                adapter.notifyItemRemoved(position);
                Toast.makeText(ListaEventosActivity.this, "Evento eliminado", Toast.LENGTH_SHORT).show();
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
            listaEventos.clear();
            listaEventos.addAll(eventoDAO.obtenerTodosLosEventos());
            adapter.notifyDataSetChanged();
        }
    }
}