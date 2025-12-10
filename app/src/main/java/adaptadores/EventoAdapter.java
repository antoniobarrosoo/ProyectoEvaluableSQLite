package adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyectoevaluablesqlite.R;
import java.util.List;
import entidades.Evento;

/**
 * Adaptador para el RecyclerView de la lista de eventos.
 * Vincula los datos de la entidad Evento con el layout item_evento.xml
 * y gestiona el evento de clic para editar un evento.
 */
public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.EventoViewHolder> {

    // Lista de eventos a mostrar en el RecyclerView
    private List<Evento> listaEventos;
    // Listener para manejar clics en los ítems
    private OnEventoClickListener listener;

    /**
     * Constructor del adaptador.
     * @param listaEventos Lista de objetos Evento a mostrar.
     * @param listener Interfaz de callback para notificar clics en los ítems.
     */
    public EventoAdapter(List<Evento> listaEventos, OnEventoClickListener listener) {
        this.listaEventos = listaEventos;
        this.listener = listener;
    }

    /**
     * Interfaz de callback para manejar el clic en un evento del listado.
     */
    public interface OnEventoClickListener {
        /**
         * Se llama cuando el usuario pulsa sobre un ítem del RecyclerView.
         * @param evento El objeto Evento correspondiente al ítem pulsado.
         */
        void onEventoClick(Evento evento);
    }

    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el layout de cada ítem (item_evento.xml) para crear una nueva vista
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_evento, parent, false);
        return new EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
        // Obtiene el evento en la posición actual
        Evento evento = listaEventos.get(position);
        // Vincula los datos del evento con los TextViews de la tarjeta
        holder.tvNombreEvento.setText(evento.getNombre_evento());
        holder.tvFecha.setText(evento.getFecha());
        holder.tvAforo.setText("Aforo: " + evento.getAforo_maximo());

        // Asigna el listener de clic al ítem completo (toda la tarjeta)
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEventoClick(evento);
            }
        });
    }

    @Override
    public int getItemCount() {
        // Devuelve el número total de eventos en la lista
        return listaEventos.size();
    }

    /**
     * ViewHolder que almacena las referencias a los componentes de la vista de un ítem.
     */
    public static class EventoViewHolder extends RecyclerView.ViewHolder {
        // Referencias a los TextViews del layout item_evento.xml
        TextView tvNombreEvento, tvFecha, tvAforo;

        /**
         * Constructor del ViewHolder.
         * @param itemView La vista del ítem inflada (item_evento.xml).
         */
        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializa las referencias a los componentes de la vista
            tvNombreEvento = itemView.findViewById(R.id.tvNombreEvento);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvAforo = itemView.findViewById(R.id.tvAforo);
        }
    }
}