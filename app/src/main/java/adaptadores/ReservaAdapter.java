package adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyectoevaluablesqlite.R;
import java.util.List;
import database.ClienteDAO;
import database.EventoDAO;
import database.TipoEntradaDAO;
import entidades.Cliente;
import entidades.Evento;
import entidades.Reserva;
import entidades.TipoEntrada;

/**
 * Adaptador para el RecyclerView de la lista de reservas.
 * Muestra información detallada de cada reserva, incluyendo
 * los nombres reales del cliente, evento y tipo de entrada
 * mediante búsquedas en las tablas relacionadas.
 */
public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder> {

    // Lista de reservas a mostrar en el RecyclerView
    private List<Reserva> listaReservas;
    // Contexto necesario para acceder a la base de datos a través de los DAOs
    private Context context;
    // Listener para manejar clics en los ítems
    private OnReservaClickListener listener;

    /**
     * Constructor del adaptador.
     * @param listaReservas Lista de objetos Reserva a mostrar.
     * @param context Contexto de la actividad (necesario para instanciar DAOs).
     * @param listener Interfaz de callback para notificar clics en los ítems.
     */
    public ReservaAdapter(List<Reserva> listaReservas, Context context, OnReservaClickListener listener) {
        this.listaReservas = listaReservas;
        this.context = context;
        this.listener = listener;
    }

    /**
     * Interfaz de callback para manejar el clic en una reserva del listado.
     */
    public interface OnReservaClickListener {
        /**
         * Se llama cuando el usuario pulsa sobre un ítem del RecyclerView.
         * @param reserva El objeto Reserva correspondiente al ítem pulsado.
         */
        void onReservaClick(Reserva reserva);
    }

    @NonNull
    @Override
    public ReservaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el layout de cada ítem (item_reserva_simple.xml)
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reserva_simple, parent, false);
        return new ReservaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaViewHolder holder, int position) {
        // Obtiene la reserva en la posición actual
        Reserva r = listaReservas.get(position);
        // Muestra el ID de la reserva
        holder.tvReservaId.setText("Reserva #" + r.getId_reserva());

        // === Carga el nombre del cliente ===
        ClienteDAO cDao = new ClienteDAO(context);
        String nombreCliente = "Cliente no encontrado";
        if (r.getId_cliente() != -1) {
            Cliente cliente = cDao.obtenerClientePorId(r.getId_cliente());
            if (cliente != null) {
                nombreCliente = cliente.getNombre();
            }
        }
        holder.tvClienteNombre.setText("Cliente: " + nombreCliente);

        // === Carga el nombre del evento ===
        EventoDAO eDao = new EventoDAO(context);
        String nombreEvento = "Evento no encontrado";
        if (r.getId_evento() != -1) {
            Evento evento = eDao.obtenerEventoPorId(r.getId_evento());
            if (evento != null) {
                nombreEvento = evento.getNombre_evento() + " (" + evento.getFecha() + ")";
            }
        }
        holder.tvEventoNombre.setText("Evento: " + nombreEvento);

        // === Carga el nombre del tipo de entrada ===
        TipoEntradaDAO tDao = new TipoEntradaDAO(context);
        String nombreTipo = "Tipo no encontrado";
        if (r.getId_tipo_entrada() != -1) {
            TipoEntrada tipo = tDao.obtenerPorId(r.getId_tipo_entrada());
            if (tipo != null) {
                nombreTipo = tipo.getNombre_tipo() + " (" + tipo.getPrecio() + "€)";
            }
        }
        holder.tvTipoNombre.setText("Tipo: " + nombreTipo);

        // Asigna el listener de clic al ítem completo (toda la tarjeta)
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onReservaClick(r);
            }
        });
    }

    @Override
    public int getItemCount() {
        // Devuelve el número total de reservas en la lista
        return listaReservas.size();
    }

    /**
     * ViewHolder que almacena las referencias a los componentes de la vista de un ítem.
     */
    public static class ReservaViewHolder extends RecyclerView.ViewHolder {
        // Referencias a los TextViews del layout item_reserva_simple.xml
        TextView tvReservaId, tvClienteNombre, tvEventoNombre, tvTipoNombre;

        /**
         * Constructor del ViewHolder.
         * @param itemView La vista del ítem inflada (item_reserva_simple.xml).
         */
        public ReservaViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializa las referencias a los componentes de la vista
            tvReservaId = itemView.findViewById(R.id.tvReservaId);
            tvClienteNombre = itemView.findViewById(R.id.tvClienteNombre);
            tvEventoNombre = itemView.findViewById(R.id.tvEventoNombre);
            tvTipoNombre = itemView.findViewById(R.id.tvTipoNombre);
        }
    }
}