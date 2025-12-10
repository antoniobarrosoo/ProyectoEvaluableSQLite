package com.example.proyectoevaluablesqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Clientes
        findViewById(R.id.btnGestionClientes).setOnClickListener(v ->
                startActivity(new Intent(this, GestionClientesActivity.class)));
        findViewById(R.id.btnVerClientes).setOnClickListener(v ->
                startActivity(new Intent(this, ListaClientesActivity.class)));

        // Eventos
        findViewById(R.id.btnGestionEventos).setOnClickListener(v ->
                startActivity(new Intent(this, GestionEventosActivity.class)));
        findViewById(R.id.btnVerEventos).setOnClickListener(v ->
                startActivity(new Intent(this, ListaEventosActivity.class)));

        // Tipos de Entrada
        findViewById(R.id.btnGestionTipos).setOnClickListener(v ->
                startActivity(new Intent(this, GestionTipoEntradaActivity.class)));
        findViewById(R.id.btnVerTipos).setOnClickListener(v ->
                startActivity(new Intent(this, ListaTiposActivity.class)));

        // Reservas
        findViewById(R.id.btnCrearReserva).setOnClickListener(v ->
                startActivity(new Intent(this, GestionReservaActivity.class)));
        findViewById(R.id.btnVerReservas).setOnClickListener(v ->
                startActivity(new Intent(this, ListaReservasActivity.class)));
    }
}