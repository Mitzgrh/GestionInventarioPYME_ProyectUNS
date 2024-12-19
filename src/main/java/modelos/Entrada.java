package modelos;

import java.util.Date;

public class Entrada extends Movimiento {
    public Entrada(int id, Date fecha, Producto producto, int cantidad) {
        super(id, fecha, producto, cantidad);
    }

    @Override
    public String getTipoMovimiento() {
        return "Entrada";
    }
}