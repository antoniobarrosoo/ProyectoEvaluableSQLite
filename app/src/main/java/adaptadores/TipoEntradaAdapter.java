package adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyectoevaluablesqlite.R;
import java.util.List;
import entidades.TipoEntrada;

/**
 * Adaptador para el RecyclerView de la lista de tipos de entrada.
 * Vincula los datos de la entidad TipoEntrada con el layout item_tipo_entrada.xml
 * y gestiona el evento de clic para editar un tipo de entrada.
 */
public class TipoEntradaAdapter extends RecyclerView.Adapter<TipoEntradaAdapter.TipoViewHolder> {

    // Lista de tipos de entrada a mostrar en el RecyclerView
    private List<TipoEntrada> lista;
    // Listener para manejar clics en los ítems
    private OnTipoClickListener listener;

    /**
     * Constructor del adaptador.
     * @param lista Lista de objetos TipoEntrada a mostrar.
     * @param listener Interfaz de callback para notificar clics en los ítems.
     */
    public TipoEntradaAdapter(List<TipoEntrada> lista, OnTipoClickListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    /**
     * Interfaz de callback para manejar el clic en un tipo de entrada del listado.
     */
    public interface OnTipoClickListener {
        /**
         * Se llama cuando el usuario pulsa sobre un ítem del RecyclerView.
         * @param tipo El objeto TipoEntrada correspondiente al ítem pulsado.
         */
        void onTipoClick(TipoEntrada tipo);
    }

    @NonNull
    @Override
    public TipoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el layout de cada ítem (item_tipo_entrada.xml) para crear una nueva vista
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tipo_entrada, parent, false);
        return new TipoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TipoViewHolder holder, int position) {
        // Obtiene el tipo de entrada en la posición actual
        TipoEntrada t = lista.get(position);
        // Vincula los datos con los TextViews de la tarjeta
        holder.tvNombre.setText(t.getNombre_tipo());
        holder.tvPrecio.setText("Precio: " + t.getPrecio() + " €");
        holder.tvDescripcion.setText(t.getDescripcion());

        // Asigna el listener de clic al ítem completo (toda la tarjeta)
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTipoClick(t);
            }
        });
    }

    @Override
    public int getItemCount() {
        // Devuelve el número total de tipos de entrada en la lista
        return lista.size();
    }

    /**
     * ViewHolder que almacena las referencias a los componentes de la vista de un ítem.
     */
    public static class TipoViewHolder extends RecyclerView.ViewHolder {
        // Referencias a los TextViews del layout item_tipo_entrada.xml
        TextView tvNombre, tvPrecio, tvDescripcion;

        /**
         * Constructor del ViewHolder.
         * @param itemView La vista del ítem inflada (item_tipo_entrada.xml).
         */
        public TipoViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializa las referencias a los componentes de la vista
            tvNombre = itemView.findViewById(R.id.tvNombreTipo);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
        }
    }
}