package modelos;

import java.util.Date;

public class Salida extends Movimiento {
    public Salida(int id, Date fecha, Producto producto, int cantidad) {
        super(id, fecha, producto, cantidad);
    }

    @Override
    public String getTipoMovimiento() {
        return "Salida";
    }
}