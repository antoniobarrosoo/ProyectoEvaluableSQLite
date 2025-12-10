package entidades;


public class TipoEntrada {
    private int id_tipo_entrada;
    private String nombre_tipo;
    private double precio;
    private String descripcion;

    public TipoEntrada() {}

    public TipoEntrada(String nombre_tipo, double precio, String descripcion) {
        this.nombre_tipo = nombre_tipo;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public int getId_tipo_entrada() { return id_tipo_entrada; }
    public void setId_tipo_entrada(int id_tipo_entrada) { this.id_tipo_entrada = id_tipo_entrada; }

    public String getNombre_tipo() { return nombre_tipo; }
    public void setNombre_tipo(String nombre_tipo) { this.nombre_tipo = nombre_tipo; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}