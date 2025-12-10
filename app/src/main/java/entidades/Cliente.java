package entidades;

/**
 * Clase que representa a un Cliente en la aplicación.
 * Contiene información básica como nombre, email y teléfono.
 */
public class Cliente {

    /** ID único del cliente en la base de datos */
    private int id_cliente;

    /** Nombre del cliente */
    private String nombre;

    /** Correo electrónico del cliente */
    private String email;

    /** Teléfono del cliente */
    private String telefono;

    /**
     * Constructor vacío necesario para ciertos procesos de la base de datos y adaptadores.
     */
    public Cliente() {}

    /**
     * Constructor que inicializa un cliente con sus datos.
     *
     * @param nombre Nombre del cliente
     * @param email Correo electrónico del cliente
     * @param telefono Teléfono del cliente
     */
    public Cliente(String nombre, String email, String telefono) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    // Getters y Setters

    /**
     * Obtiene el ID del cliente.
     * @return ID del cliente
     */
    public int getId_cliente() { return id_cliente; }

    /**
     * Establece el ID del cliente.
     * @param id_cliente ID a asignar
     */
    public void setId_cliente(int id_cliente) { this.id_cliente = id_cliente; }

    /**
     * Obtiene el nombre del cliente.
     * @return Nombre del cliente
     */
    public String getNombre() { return nombre; }

    /**
     * Establece el nombre del cliente.
     * @param nombre Nombre a asignar
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Obtiene el correo electrónico del cliente.
     * @return Email del cliente
     */
    public String getEmail() { return email; }

    /**
     * Establece el correo electrónico del cliente.
     * @param email Email a asignar
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Obtiene el teléfono del cliente.
     * @return Teléfono del cliente
     */
    public String getTelefono() { return telefono; }

    /**
     * Establece el teléfono del cliente.
     * @param telefono Teléfono a asignar
     */
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
