package servicios;

import modelos.*;
import java.util.ArrayList;
import java.util.List;

public class ServicioVenta {
    private List<Venta> ventas;
    private ServicioInventario inventarioServicio;

    public ServicioVenta(ServicioInventario inventarioServicio) {
        this.ventas = new ArrayList<>();
        this.inventarioServicio = inventarioServicio;
    }

    public void registrarVenta(Venta venta) {
        Producto producto = venta.getProducto();
        int cantidad = venta.getCantidad();

        // Verificar si el producto existe en inventario
        Producto productoEnInventario = inventarioServicio.buscarPorId(producto.getId());
        if (productoEnInventario == null) {
            System.out.println("El producto no existe en el inventario.");
            return;
        }

        inventarioServicio.registrarMovimiento(new Salida(utilidades.GeneradorID.generarID(), new java.util.Date(),producto, cantidad));
        ventas.add(venta);
        System.out.println("Venta registrada exitosamente: " + producto.getNombre() + ", cantidad: " + cantidad);
    }

    public List<Venta> obtenerVentas() {
        return ventas;
    }
}