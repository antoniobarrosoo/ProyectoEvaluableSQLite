package entidades;

public class Evento {
    private int id_evento;
    private String nombre_evento;
    private String fecha;
    private int aforo_maximo;

    public Evento() {}

    public Evento(String nombre_evento, String fecha, int aforo_maximo) {
        this.nombre_evento = nombre_evento;
        this.fecha = fecha;
        this.aforo_maximo = aforo_maximo;
    }

    public int getId_evento() { return id_evento; }
    public void setId_evento(int id_evento) { this.id_evento = id_evento; }

    public String getNombre_evento() { return nombre_evento; }
    public void setNombre_evento(String nombre_evento) { this.nombre_evento = nombre_evento; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public int getAforo_maximo() { return aforo_maximo; }
    public void setAforo_maximo(int aforo_maximo) { this.aforo_maximo = aforo_maximo; }
}