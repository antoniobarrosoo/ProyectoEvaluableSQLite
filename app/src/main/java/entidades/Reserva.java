package entidades;

/**
 * Clase que representa una Reserva en la aplicación.
 * Una reserva vincula un cliente con un evento y un tipo de entrada.
 */
public class Reserva {

    /** ID único de la reserva en la base de datos */
    private int id_reserva;

    /** ID del cliente que realiza la reserva */
    private int id_cliente;

    /** ID del evento para el que se realiza la reserva */
    private int id_evento;

    /** ID del tipo de entrada reservado */
    private int id_tipo_entrada;

    /**
     * Constructor vacío necesario para ciertos procesos de la base de datos y adaptadores.
     */
    public Reserva() {}

    /**
     * Constructor que inicializa una reserva con los IDs correspondientes.
     *
     * @param id_cliente ID del cliente
     * @param id_evento ID del evento
     * @param id_tipo_entrada ID del tipo de entrada
     */
    public Reserva(int id_cliente, int id_evento, int id_tipo_entrada) {
        this.id_cliente = id_cliente;
        this.id_evento = id_evento;
        this.id_tipo_entrada = id_tipo_entrada;
    }

    // Getters y Setters

    /** @return ID de la reserva */
    public int getId_reserva() { return id_reserva; }

    /** @param id_reserva ID a asignar a la reserva */
    public void setId_reserva(int id_reserva) { this.id_reserva = id_reserva; }

    /** @return ID del cliente */
    public int getId_cliente() { return id_cliente; }

    /** @param id_cliente ID del cliente a asignar */
    public void setId_cliente(int id_cliente) { this.id_cliente = id_cliente; }

    /** @return ID del evento */
    public int getId_evento() { return id_evento; }

    /** @param id_evento ID del evento a asignar */
    public void setId_evento(int id_evento) { this.id_evento = id_evento; }

    /** @return ID del tipo de entrada */
    public int getId_tipo_entrada() { return id_tipo_entrada; }

    /** @param id_tipo_entrada ID del tipo de entrada a asignar */
    public void setId_tipo_entrada(int id_tipo_entrada) { this.id_tipo_entrada = id_tipo_entrada; }
}
