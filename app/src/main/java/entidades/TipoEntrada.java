package entidades;

/**
 * Clase que representa un Tipo de Entrada para un evento.
 * Contiene información sobre el nombre, precio y descripción de la entrada.
 */
public class TipoEntrada {

    /** ID único del tipo de entrada en la base de datos */
    private int id_tipo_entrada;

    /** Nombre del tipo de entrada */
    private String nombre_tipo;

    /** Precio del tipo de entrada */
    private double precio;

    /** Descripción del tipo de entrada */
    private String descripcion;

    /**
     * Constructor vacío necesario para ciertos procesos de la base de datos y adaptadores.
     */
    public TipoEntrada() {}

    /**
     * Constructor que inicializa un tipo de entrada con sus datos.
     *
     * @param nombre_tipo Nombre del tipo de entrada
     * @param precio Precio de la entrada
     * @param descripcion Descripción del tipo de entrada
     */
    public TipoEntrada(String nombre_tipo, double precio, String descripcion) {
        this.nombre_tipo = nombre_tipo;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    // Getters y Setters

    /** @return ID del tipo de entrada */
    public int getId_tipo_entrada() { return id_tipo_entrada; }

    /** @param id_tipo_entrada ID a asignar al tipo de entrada */
    public void setId_tipo_entrada(int id_tipo_entrada) { this.id_tipo_entrada = id_tipo_entrada; }

    /** @return Nombre del tipo de entrada */
    public String getNombre_tipo() { return nombre_tipo; }

    /** @param nombre_tipo Nombre a asignar al tipo de entrada */
    public void setNombre_tipo(String nombre_tipo) { this.nombre_tipo = nombre_tipo; }

    /** @return Precio del tipo de entrada */
    public double getPrecio() { return precio; }

    /** @param precio Precio a asignar al tipo de entrada */
    public void setPrecio(double precio) { this.precio = precio; }

    /** @return Descripción del tipo de entrada */
    public String getDescripcion() { return descripcion; }

    /** @param descripcion Descripción a asignar al tipo de entrada */
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
