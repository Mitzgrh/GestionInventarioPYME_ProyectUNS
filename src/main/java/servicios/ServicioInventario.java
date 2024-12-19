package servicios;

import modelos.*;
import java.util.ArrayList;
import java.util.List;

public class ServicioInventario implements IServicio<Producto> {

    private List<Movimiento> movimientos;
    private List<Producto> productos;

    public ServicioInventario() {
        this.productos = new ArrayList<>();
        this.movimientos = new ArrayList<>();
    }

    @Override
    public void agregarE(Producto producto) {
        if (productos.stream().anyMatch(p -> p.getNombre().equalsIgnoreCase(producto.getNombre()))) {
            System.out.println("Ya existe un producto con este nombre.");
            utilidades.UtilidadLogger.registrarLog("Intento de agregar producto duplicado: " + producto.getNombre());
            return;
        }
        productos.add(producto);
    }

    public void registrarMovimiento(Movimiento movimiento) {
        movimientos.add(movimiento);
        actualizarStock(movimiento);
    }

    private void actualizarStock(Movimiento movimiento) {
        Producto producto = movimiento.getProducto();
        int cantidad = movimiento.getCantidad();

        if (movimiento instanceof Entrada) {
            producto.setStock(producto.getStock() + cantidad);
        } else if (movimiento instanceof Salida) {
            producto.setStock(producto.getStock() - cantidad);
        }
    }

    @Override
    public List<Producto> obtenerEntidades() {
        return productos;
    }

    public List<Movimiento> obtenerMovimientos() {
        return movimientos;
    }

    public void generarReporteInventario() {
        GeneradorReporte generadorReporte = new GeneradorReporte(productos);
        generadorReporte.generarInformeInventario();
    }

    @Override
    public Producto buscarPorId(int id) {
        return productos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Producto buscarProductoPorNombre(String nombre) {
        return productos.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void eliminarE(int id) {
        productos.removeIf(p -> p.getId() == id);
    }
}
