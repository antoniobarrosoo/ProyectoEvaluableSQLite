package adaptadores;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoevaluablesqlite.EditarClienteActivity;
import com.example.proyectoevaluablesqlite.R;
import java.util.List;

import entidades.Cliente;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder> {

    private List<Cliente> listaClientes;
    private OnClienteClickListener listener;

    // Constructor con listener
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
        Cliente cliente = listaClientes.get(position);
        holder.tvNombre.setText(cliente.getNombre());
        holder.tvEmail.setText(cliente.getEmail());
        holder.tvTelefono.setText(cliente.getTelefono());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClienteClick(cliente);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaClientes.size();
    }

    public static class ClienteViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvEmail, tvTelefono;

        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvTelefono = itemView.findViewById(R.id.tvTelefono);
        }
    }
}