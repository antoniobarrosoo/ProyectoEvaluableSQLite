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


public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder> {


    private List<Cliente> listaClientes;

    private OnClienteClickListener listener;


    public ClienteAdapter(List<Cliente> listaClientes, OnClienteClickListener listener) {
        this.listaClientes = listaClientes;
        this.listener = listener;
    }


    public interface OnClienteClickListener {

        void onClienteClick(Cliente cliente);
    }

    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

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

        // Asigna el listener de clic al ítem completo
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