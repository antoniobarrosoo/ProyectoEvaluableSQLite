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

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder> {

    private List<Reserva> listaReservas;
    private Context context;
    private OnReservaClickListener listener;

    // Constructor con listener (el que usarás)
    public ReservaAdapter(List<Reserva> listaReservas, Context context, OnReservaClickListener listener) {
        this.listaReservas = listaReservas;
        this.context = context;
        this.listener = listener;
    }

    public interface OnReservaClickListener {
        void onReservaClick(Reserva reserva);
    }

    @NonNull
    @Override
    public ReservaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reserva_simple, parent, false);
        return new ReservaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaViewHolder holder, int position) {
        Reserva r = listaReservas.get(position);

        holder.tvReservaId.setText("Reserva #" + r.getId_reserva());

        // Cliente
        ClienteDAO cDao = new ClienteDAO(context);
        String nombreCliente = "Cliente no encontrado";
        if (r.getId_cliente() != -1) {
            Cliente cliente = cDao.obtenerClientePorId(r.getId_cliente());
            if (cliente != null) {
                nombreCliente = cliente.getNombre();
            }
        }
        holder.tvClienteNombre.setText("Cliente: " + nombreCliente);

        // Evento
        EventoDAO eDao = new EventoDAO(context);
        String nombreEvento = "Evento no encontrado";
        if (r.getId_evento() != -1) {
            Evento evento = eDao.obtenerEventoPorId(r.getId_evento());
            if (evento != null) {
                nombreEvento = evento.getNombre_evento() + " (" + evento.getFecha() + ")";
            }
        }
        holder.tvEventoNombre.setText("Evento: " + nombreEvento);

        // Tipo de entrada
        TipoEntradaDAO tDao = new TipoEntradaDAO(context);
        String nombreTipo = "Tipo no encontrado";
        if (r.getId_tipo_entrada() != -1) {
            TipoEntrada tipo = tDao.obtenerPorId(r.getId_tipo_entrada());
            if (tipo != null) {
                nombreTipo = tipo.getNombre_tipo() + " (" + tipo.getPrecio() + "€)";
            }
        }
        holder.tvTipoNombre.setText("Tipo: " + nombreTipo);

        // ✅ AÑADIDO: Listener de clic para editar
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onReservaClick(r);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaReservas.size();
    }

    public static class ReservaViewHolder extends RecyclerView.ViewHolder {
        TextView tvReservaId, tvClienteNombre, tvEventoNombre, tvTipoNombre;

        public ReservaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReservaId = itemView.findViewById(R.id.tvReservaId);
            tvClienteNombre = itemView.findViewById(R.id.tvClienteNombre);
            tvEventoNombre = itemView.findViewById(R.id.tvEventoNombre);
            tvTipoNombre = itemView.findViewById(R.id.tvTipoNombre);
        }
    }
}