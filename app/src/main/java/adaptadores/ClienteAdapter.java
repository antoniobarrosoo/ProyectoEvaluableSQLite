package adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyectoevaluablesqlite.R;
import java.util.List;
import entidades.Cliente;

/**
 * Adaptador para el RecyclerView de la lista de clientes.
 * Enlaza los datos de la entidad Cliente con la vista item_cliente.xml
 * y gestiona el evento de clic para editar un cliente.
 */
public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder> {

    // Lista de clientes a mostrar en el RecyclerView
    private List<Cliente> listaClientes;
    // Listener para manejar clics en los ítems
    private OnClienteClickListener listener;

    /**
     * Constructor del adaptador.
     * @param listaClientes Lista de objetos Cliente a mostrar.
     * @param listener Interfaz de callback para notificar clics en los ítems.
     */
    public ClienteAdapter(List<Cliente> listaClientes, OnClienteClickListener listener) {
        this.listaClientes = listaClientes;
        this.listener = listener;
    }

    /**
     * Interfaz de callback para manejar el clic en un cliente del listado.
     */
    public interface OnClienteClickListener {
        /**
         * Se llama cuando el usuario pulsa sobre un ítem del RecyclerView.
         * @param cliente El objeto Cliente correspondiente al ítem pulsado.
         */
        void onClienteClick(Cliente cliente);
    }

    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el layout de cada ítem (item_cliente.xml) para crear una nueva vista
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cliente, parent, false);
        return new ClienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteViewHolder holder, int position) {
        // Obtiene el cliente en la posición actual
        Cliente cliente = listaClientes.get(position);
        // Vincula los datos del cliente con los TextViews de la tarjeta
        holder.tvNombre.setText(cliente.getNombre());
        holder.tvEmail.setText(cliente.getEmail());
        holder.tvTelefono.setText(cliente.getTelefono());

        // Asigna el listener de clic al ítem completo (toda la tarjeta)
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClienteClick(cliente);
            }
        });
    }

    @Override
    public int getItemCount() {
        // Devuelve el número total de elementos en la lista de clientes
        return listaClientes.size();
    }

    /**
     * ViewHolder que almacena las referencias a los componentes de la vista de un ítem.
     */
    public static class ClienteViewHolder extends RecyclerView.ViewHolder {
        // Referencias a los TextViews del layout item_cliente.xml
        TextView tvNombre, tvEmail, tvTelefono;

        /**
         * Constructor del ViewHolder.
         * @param itemView La vista del ítem inflada (item_cliente.xml).
         */
        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializa las referencias a los componentes de la vista
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvTelefono = itemView.findViewById(R.id.tvTelefono);
        }
    }
}