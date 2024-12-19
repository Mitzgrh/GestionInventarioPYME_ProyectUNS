package modelos;

import java.util.Date;

public class Venta {
    private int id;
    private Producto producto;
    private int cantidad;
    private double precioTotal;
    private Date fecha;

    public Venta(int id, Producto producto, int cantidad, double precioTotal, Date fecha) {
        this.id = id;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

     public double getPrecioTotal() {
        return precioTotal;
    }

    public Date getFecha() {
        return fecha;
    }
}