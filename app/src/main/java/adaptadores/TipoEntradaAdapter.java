package adaptadores;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoevaluablesqlite.EditarTipoEntradaActivity;
import com.example.proyectoevaluablesqlite.R;

import java.util.List;

import entidades.TipoEntrada;

public class TipoEntradaAdapter extends RecyclerView.Adapter<TipoEntradaAdapter.TipoViewHolder> {

    private List<TipoEntrada> lista;
    private OnTipoClickListener listener;

    public TipoEntradaAdapter(List<TipoEntrada> lista, OnTipoClickListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    public interface OnTipoClickListener {
        void onTipoClick(TipoEntrada tipo);
    }

    @NonNull
    @Override
    public TipoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tipo_entrada, parent, false);
        return new TipoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TipoViewHolder holder, int position) {
        TipoEntrada t = lista.get(position);
        holder.tvNombre.setText(t.getNombre_tipo());
        holder.tvPrecio.setText("Precio: " + t.getPrecio() + " €");
        holder.tvDescripcion.setText(t.getDescripcion());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTipoClick(t);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class TipoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvPrecio, tvDescripcion;

        public TipoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreTipo);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
        }
    }
}