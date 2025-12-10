package com.example.proyectoevaluablesqlite;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity principal de la aplicación.
 * Permite acceder a las diferentes secciones: clientes, eventos, tipos de entrada y reservas.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Inicializa la activity y configura los botones de navegación hacia otras actividades.
     *
     * @param savedInstanceState Estado previo de la activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Botones para gestionar clientes
        findViewById(R.id.btnGestionClientes).setOnClickListener(v ->
                startActivity(new Intent(this, GestionClientesActivity.class)));
        findViewById(R.id.btnVerClientes).setOnClickListener(v ->
                startActivity(new Intent(this, ListaClientesActivity.class)));

        // Botones para gestionar eventos
        findViewById(R.id.btnGestionEventos).setOnClickListener(v ->
                startActivity(new Intent(this, GestionEventosActivity.class)));
        findViewById(R.id.btnVerEventos).setOnClickListener(v ->
                startActivity(new Intent(this, ListaEventosActivity.class)));

        // Botones para gestionar tipos de entrada
        findViewById(R.id.btnGestionTipos).setOnClickListener(v ->
                startActivity(new Intent(this, GestionTipoEntradaActivity.class)));
        findViewById(R.id.btnVerTipos).setOnClickListener(v ->
                startActivity(new Intent(this, ListaTiposActivity.class)));

        // Botones para crear y ver reservas
        findViewById(R.id.btnCrearReserva).setOnClickListener(v ->
                startActivity(new Intent(this, GestionReservaActivity.class)));
        findViewById(R.id.btnVerReservas).setOnClickListener(v ->
                startActivity(new Intent(this, ListaReservasActivity.class)));
    }
}
