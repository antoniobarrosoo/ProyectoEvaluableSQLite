package entidades;


public class Reserva {
    private int id_reserva;
    private int id_cliente;
    private int id_evento;
    private int id_tipo_entrada;

    public Reserva() {}

    public Reserva(int id_cliente, int id_evento, int id_tipo_entrada) {
        this.id_cliente = id_cliente;
        this.id_evento = id_evento;
        this.id_tipo_entrada = id_tipo_entrada;
    }

    public int getId_reserva() { return id_reserva; }
    public void setId_reserva(int id_reserva) { this.id_reserva = id_reserva; }

    public int getId_cliente() { return id_cliente; }
    public void setId_cliente(int id_cliente) { this.id_cliente = id_cliente; }

    public int getId_evento() { return id_evento; }
    public void setId_evento(int id_evento) { this.id_evento = id_evento; }

    public int getId_tipo_entrada() { return id_tipo_entrada; }
    public void setId_tipo_entrada(int id_tipo_entrada) { this.id_tipo_entrada = id_tipo_entrada; }
}