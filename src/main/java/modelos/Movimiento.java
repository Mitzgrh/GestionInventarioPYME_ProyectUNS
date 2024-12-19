package modelos;

import java.util.Date;

public abstract class Movimiento {
    private int id;
    private Date fecha;
    private Producto producto;
    private int cantidad;

    public Movimiento(int id, Date fecha, Producto producto, int cantidad) {
        this.id = id;
        this.fecha = fecha;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public Date getFecha() {
        return fecha;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public abstract String getTipoMovimiento();
}