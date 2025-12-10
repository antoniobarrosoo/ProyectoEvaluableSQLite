package entidades;

/**
 * Clase que representa un Evento en la aplicación.
 * Contiene información básica como nombre, fecha y aforo máximo.
 */
public class Evento {

    /** ID único del evento en la base de datos */
    private int id_evento;

    /** Nombre del evento */
    private String nombre_evento;

    /** Fecha del evento (formato String, p.ej. "dd/MM/yyyy") */
    private String fecha;

    /** Aforo máximo permitido para el evento */
    private int aforo_maximo;

    /**
     * Constructor vacío necesario para ciertos procesos de la base de datos y adaptadores.
     */
    public Evento() {}

    /**
     * Constructor que inicializa un evento con sus datos.
     *
     * @param nombre_evento Nombre del evento
     * @param fecha Fecha del evento
     * @param aforo_maximo Aforo máximo permitido
     */
    public Evento(String nombre_evento, String fecha, int aforo_maximo) {
        this.nombre_evento = nombre_evento;
        this.fecha = fecha;
        this.aforo_maximo = aforo_maximo;
    }

    // Getters y Setters

    /**
     * Obtiene el ID del evento.
     * @return ID del evento
     */
    public int getId_evento() { return id_evento; }

    /**
     * Establece el ID del evento.
     * @param id_evento ID a asignar
     */
    public void setId_evento(int id_evento) { this.id_evento = id_evento; }

    /**
     * Obtiene el nombre del evento.
     * @return Nombre del evento
     */
    public String getNombre_evento() { return nombre_evento; }

    /**
     * Establece el nombre del evento.
     * @param nombre_evento Nombre a asignar
     */
    public void setNombre_evento(String nombre_evento) { this.nombre_evento = nombre_evento; }

    /**
     * Obtiene la fecha del evento.
     * @return Fecha del evento
     */
    public String getFecha() { return fecha; }

    /**
     * Establece la fecha del evento.
     * @param fecha Fecha a asignar
     */
    public void setFecha(String fecha) { this.fecha = fecha; }

    /**
     * Obtiene el aforo máximo del evento.
     * @return Aforo máximo
     */
    public int getAforo_maximo() { return aforo_maximo; }

    /**
     * Establece el aforo máximo del evento.
     * @param aforo_maximo Aforo máximo a asignar
     */
    public void setAforo_maximo(int aforo_maximo) { this.aforo_maximo = aforo_maximo; }
}
