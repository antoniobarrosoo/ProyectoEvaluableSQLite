package adaptadores;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoevaluablesqlite.EditarEventoActivity;
import com.example.proyectoevaluablesqlite.R;

import java.util.List;

import entidades.Evento;



    public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.EventoViewHolder> {

        private List<Evento> listaEventos;
        private OnEventoClickListener listener;

        // Constructor con listener
        public EventoAdapter(List<Evento> listaEventos, OnEventoClickListener listener) {
            this.listaEventos = listaEventos;
            this.listener = listener;
        }

        public interface OnEventoClickListener {
            void onEventoClick(Evento evento);
        }

        @NonNull
        @Override
        public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_evento, parent, false); // ← Usa item_evento.xml
            return new EventoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
            Evento evento = listaEventos.get(position);
            holder.tvNombreEvento.setText(evento.getNombre_evento());
            holder.tvFecha.setText(evento.getFecha());
            holder.tvAforo.setText("Aforo: " + evento.getAforo_maximo());

            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEventoClick(evento);
                }
            });
        }

        @Override
        public int getItemCount() {
            return listaEventos.size();
        }

        public static class EventoViewHolder extends RecyclerView.ViewHolder {
            TextView tvNombreEvento, tvFecha, tvAforo;

            public EventoViewHolder(@NonNull View itemView) {
                super(itemView);
                tvNombreEvento = itemView.findViewById(R.id.tvNombreEvento);
                tvFecha = itemView.findViewById(R.id.tvFecha);
                tvAforo = itemView.findViewById(R.id.tvAforo);
            }
        }
    }