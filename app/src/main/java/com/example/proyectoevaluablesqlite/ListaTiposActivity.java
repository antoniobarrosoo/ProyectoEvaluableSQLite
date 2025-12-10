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

public class ListaTiposActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TipoEntradaAdapter adapter;
    private TipoEntradaDAO tipoDAO;
    private List<TipoEntrada> listaTipos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_tipos);

        recyclerView = findViewById(R.id.recyclerViewTipos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tipoDAO = new TipoEntradaDAO(this);
        listaTipos = tipoDAO.obtenerTodos();


        adapter = new TipoEntradaAdapter(listaTipos, new TipoEntradaAdapter.OnTipoClickListener() {
            @Override
            public void onTipoClick(TipoEntrada tipo) {
                Intent i = new Intent(ListaTiposActivity.this, EditarTipoEntradaActivity.class);
                i.putExtra("tipo_id", tipo.getId_tipo_entrada());
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
                TipoEntrada tipo = listaTipos.get(position);
                tipoDAO.eliminarTipo(tipo.getId_tipo_entrada());
                listaTipos.remove(position);
                adapter.notifyItemRemoved(position);
                Toast.makeText(ListaTiposActivity.this, "Tipo eliminado", Toast.LENGTH_SHORT).show();
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
            listaTipos.clear();
            listaTipos.addAll(tipoDAO.obtenerTodos());
            adapter.notifyDataSetChanged();
        }
    }
}