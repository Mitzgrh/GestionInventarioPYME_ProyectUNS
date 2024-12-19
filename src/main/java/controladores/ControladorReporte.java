package controladores;

import servicios.*;
import modelos.Producto;
import modelos.Venta;
import modelos.Proveedor;
import modelos.RegistroAuditoria;
import java.util.Scanner;
import java.util.List;

public class ControladorReporte {
    private ServicioInventario inventarioServicio;
    private ServicioVenta servicioVenta;
    private ServicioProveedor proveedorServicio;
    private ServicioAuditoria auditoriaServicio;
    private Scanner scanner;

    public ControladorReporte(ServicioInventario inventarioServicio, ServicioVenta servicioVenta, ServicioProveedor proveedorServicio, ServicioAuditoria auditoriaServicio, Scanner scanner) {
        this.inventarioServicio = inventarioServicio;
        this.servicioVenta = servicioVenta;
        this.proveedorServicio = proveedorServicio;
        this.auditoriaServicio = auditoriaServicio;
        this.scanner = scanner;
    }

    public void generarInformes() {
        System.out.println("\n=== Generacion de Informes ===");
        System.out.println("1. Informe de Inventario");
        System.out.println("2. Informe de Ventas");
        System.out.println("3. Informe de Proveedores");
        System.out.println("4. Informe de Auditoria");
        System.out.println("5. Valor total del inventario");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opcion: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1:
                System.out.println("--- Informe de Inventario ---");
                 List<Producto> productos = inventarioServicio.obtenerEntidades();
                 if (productos.isEmpty()) {
                       System.out.println("No hay productos en el inventario.");
                 }else{
                     for (Producto producto : productos) {
                       System.out.println("ID: " + producto.getId() + ", Nombre: " + producto.getNombre() + ", Stock: " + producto.getStock() + ", Categoria: " + producto.getCategoria());
                     }
                 }
                break;
            case 2:
                System.out.println("--- Informe de Ventas ---");
                List<Venta> ventas = servicioVenta.obtenerVentas();
                 if(ventas.isEmpty()){
                     System.out.println("No hay ventas registradas.");
                 }else{
                     for (Venta venta : ventas) {
                       System.out.println("ID: " + venta.getId() + ", Producto: " + venta.getProducto().getNombre() + ", Cantidad: " + venta.getCantidad() + ", Fecha: " + venta.getFecha());
                     }
                 }
                break;
            case 3:
                System.out.println("--- Informe de Proveedores ---");
                List<Proveedor> proveedores = proveedorServicio.obtenerEntidades();
                 if (proveedores.isEmpty()) {
                      System.out.println("No hay proveedores registrados.");
                }else{
                     for (Proveedor proveedor : proveedores) {
                       System.out.println("ID: " + proveedor.getId() + ", Nombre: " + proveedor.getNombre() + ", Contacto: " + proveedor.getContacto());
                    }
                 }
                break;
            case 4:
                System.out.println("--- Informe de Auditoria ---");
                List<RegistroAuditoria> registros = auditoriaServicio.obtenerRegistros();
                 if(registros.isEmpty()){
                     System.out.println("No hay registros de auditoria.");
                 }else{
                     auditoriaServicio.mostrarRegistros();
                 }
                break;
            case 5:
                generarValorTotalInventario();
                break;
            case 6:
                System.out.println("Saliendo de la generacion de informes.");
                break;
            default:
                System.out.println("Opcion invalida. Intente nuevamente.");
        }
    }

     private void generarValorTotalInventario() {
        System.out.println("--- Valor Total del Inventario ---");
          List<Producto> productos = inventarioServicio.obtenerEntidades();
         if(productos.isEmpty()){
              System.out.println("No hay productos en el inventario.");
         }else{
              double valorTotal = productos.stream()
             .mapToDouble(producto -> producto.getStock() * producto.getPrecio())
             .sum();
        System.out.println("Valor total del inventario: " + valorTotal);
         }

    }
}