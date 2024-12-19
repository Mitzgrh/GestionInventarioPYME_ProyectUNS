package servicios;

import modelos.*;
import java.util.List;

public class GeneradorReporte {
    protected List<Producto> productos;
    private List<Venta> ventas;
    private List<Proveedor> proveedores;
    private List<RegistroAuditoria> auditorias;
    
    public GeneradorReporte(List<Producto> productos) {
        this.productos = productos;
    }
    
    public GeneradorReporte(List<Producto> productos, List<Venta> ventas, List<Proveedor> proveedores, List<RegistroAuditoria> auditorias) {
        this.productos = productos;
        this.ventas = ventas;
        this.proveedores = proveedores;
        this.auditorias = auditorias;
    }

    public void generarInformeInventario() {
        System.out.println("---- Informe de Inventario ----");
        for (Producto producto : productos) {
            System.out.println("Categoria: " + producto.getCategoria() + "Producto: " + producto.getNombre() + 
                    ", Stock: " + producto.getStock());
        }
    }
    
    public void generarInformeVentas() {
        System.out.println("---- Informe de Ventas ----");
        for (Venta venta : ventas) {
            System.out.println("Venta: " + venta.getId() + ", Producto: " + venta.getProducto().getNombre() + ", Cantidad: " + venta.getCantidad());
        }
    }

    public void generarInformeProveedores() {
        System.out.println("---- Informe de Proveedores ----");
        for (Proveedor proveedor : proveedores) {
            System.out.println("Proveedor: " + proveedor.getNombre() + ", Contacto: " + proveedor.getContacto());
        }
    }

    public void generarInformeAuditoria() {
        System.out.println("---- Informe de Auditoría ----");
        for (RegistroAuditoria registro : auditorias) {
            System.out.println("Accion: " + registro.getAccion() + ", Fecha: " + registro.getFechaRegistro());
        }
    }
}