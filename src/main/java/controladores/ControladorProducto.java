package controladores;

import servicios.ServicioInventario;
import servicios.ServicioAuditoria;
import servicios.ServicioCategoria;
import servicios.ServicioNotificacion;
import servicios.ServicioVenta;
import modelos.Producto;
import modelos.Salida;
import modelos.Usuario;
import modelos.Categoria;
import modelos.Entrada;
import modelos.Movimiento;

import java.util.List;
import java.util.Scanner;
import utilidades.UtilidadLogger;
import java.util.HashMap;
import java.util.Map;

public class ControladorProducto {

    private ServicioAuditoria auditoriaServicio;
    private Usuario usuarioActivo;
    private ServicioInventario inventarioServicio;
    private ServicioCategoria categoriaServicio;
    private ServicioNotificacion servicioNotificacion;
    private Scanner scanner;
    private Map<Integer, Double> historialPrecios;

    public ControladorProducto(ServicioInventario inventarioServicio, ServicioCategoria categoriaServicio,
            ServicioNotificacion servicioNotificacion, Scanner scanner,
            ServicioAuditoria auditoriaServicio, Usuario usuarioActivo) {
        this.inventarioServicio = inventarioServicio;
        this.categoriaServicio = categoriaServicio;
        this.servicioNotificacion = servicioNotificacion;
        this.scanner = scanner;
        this.auditoriaServicio = auditoriaServicio;
        this.usuarioActivo = usuarioActivo;
        this.historialPrecios = new HashMap<>();
    }

    public void agregarProducto() {
        System.out.println("\n--- Lista de Categorias Disponibles ---");
        List<Categoria> categorias = categoriaServicio.obtenerEntidades();
        if (categorias.isEmpty()) {
            System.out.println("No hay categorias registradas, agrega una para continuar.");
            return;
        }
        for (int i = 0; i < categorias.size(); i++) {
            System.out.println((i + 1) + ". " + categorias.get(i).getNombre());
        }
        System.out.print("Seleccione la categoria del producto: ");
        int categoriaSeleccionada = scanner.nextInt() - 1;
        scanner.nextLine();
        if (categoriaSeleccionada < 0 || categoriaSeleccionada >= categorias.size()) {
            System.out.println("Categoria no valida.");
        } else {
            System.out.print("Ingrese el nombre del producto: ");
            String nombre = scanner.nextLine();
            System.out.print("Ingrese el precio del producto: ");
            double precio = scanner.nextDouble();
            System.out.print("Ingrese la cantidad del producto: ");
            int stock = scanner.nextInt();
            scanner.nextLine();
            try {
                Producto nuevoProducto = new Producto(nombre, precio, stock, categorias.get(categoriaSeleccionada));
                inventarioServicio.agregarE(nuevoProducto);
                servicioNotificacion.enviarNotificacionProductoNuevo(nombre);
                historialPrecios.put(nuevoProducto.getId(), precio);
                System.out.println("Producto agregado exitosamente.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error al agregar el producto: " + e.getMessage());
                UtilidadLogger.registrarLog("Error al agregar producto: " + e.getMessage());
            }
        }
    }

    public void eliminarProducto() {
        List<Producto> productos = inventarioServicio.obtenerEntidades();
        if (productos.isEmpty()) {
            System.out.println("No hay productos para eliminar.");
            return;
        }
        System.out.print("Ingrese el ID del producto a eliminar: ");
        int idEliminar = scanner.nextInt();
        scanner.nextLine();
        Producto producto = inventarioServicio.buscarPorId(idEliminar);
        if (producto == null) {
            System.out.println("No se encontro ningún producto con el ID proporcionado.");
            return;
        }
        inventarioServicio.eliminarE(idEliminar);
        System.out.println("Producto eliminado exitosamente.");
        UtilidadLogger.registrarLog("Producto eliminado con ID: " + idEliminar);
        historialPrecios.remove(idEliminar);
    }

    public void verProductos() {
        List<Producto> productos = inventarioServicio.obtenerEntidades();
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        System.out.println("\n--- Lista de Productos ---");
        for (Producto producto : productos) {
            System.out.println("ID: " + producto.getId() + ", Nombre: " + producto.getNombre() + ", Stock: "
                    + producto.getStock() + ", Categoria: " + producto.getCategoria());
            if (historialPrecios.containsKey(producto.getId())) {
                System.out.println("   Precio actual: " + producto.getPrecio());
                System.out.println("   Historial de precios: " + historialPrecios.get(producto.getId()));
            }
        }
    }

    public void buscarProducto() {
        System.out.print("Ingrese el ID o nombre del producto a buscar: ");
        String criterio = scanner.nextLine();
        try {
            int id = Integer.parseInt(criterio);
            Producto producto = inventarioServicio.buscarPorId(id);
            if (producto != null) {
                System.out.println("ID: " + producto.getId() + ", Nombre: " + producto.getNombre() + ", Stock: "
                        + producto.getStock() + ", Categoria: " + producto.getCategoria());
                if (historialPrecios.containsKey(producto.getId())) {
                    System.out.println("   Precio actual: " + producto.getPrecio());
                    System.out.println("   Historial de precios: " + historialPrecios.get(producto.getId()));
                }
                modificarProducto(producto);
            } else {
                System.out.println("No se encontro ningún producto con el ID proporcionado.");
            }
        } catch (NumberFormatException e) {
            List<Producto> productos = inventarioServicio.obtenerEntidades();
            List<Producto> productosEncontrados = productos.stream()
                    .filter(producto -> producto.getNombre().equalsIgnoreCase(criterio))
                    .toList();
            if (!productosEncontrados.isEmpty()) {
                if (productosEncontrados.size() == 1) {
                    Producto productoEncontrado = productosEncontrados.get(0);
                    System.out.println("ID: " + productoEncontrado.getId() + ", Nombre: "
                            + productoEncontrado.getNombre() + ", Stock: " + productoEncontrado.getStock()
                            + ", Categoria: " + productoEncontrado.getCategoria());
                    if (historialPrecios.containsKey(productoEncontrado.getId())) {
                        System.out.println("   Precio actual: " + productoEncontrado.getPrecio());
                        System.out.println(
                                "   Historial de precios: " + historialPrecios.get(productoEncontrado.getId()));
                    }
                    modificarProducto(productoEncontrado);
                } else {
                    System.out.println("Se encontraron varios productos con ese nombre:");
                    for (Producto producto : productosEncontrados) {
                        System.out.println("ID: " + producto.getId() + ", Nombre: " + producto.getNombre() + ", Stock: "
                                + producto.getStock() + ", Categoria: " + producto.getCategoria());
                    }
                    System.out.println("Porfavor usa el ID si quieres buscar una en especifico.");
                }
            } else {
                System.out.println("No se encontro ningún producto con el nombre proporcionado.");
            }
        }
    }

    public void modificarProducto(Producto producto) {
        System.out.print("¿Desea modificar el producto? (s/n): ");
        String opcion = scanner.nextLine();
        if (opcion.equalsIgnoreCase("s")) {
            System.out.println("¿Que desea modificar?");
            System.out.println("1. Nombre");
            System.out.println("2. Precio");
            System.out.println("3. Stock");
            System.out.print("Seleccione una opcion: ");
            int opcionModificacion = scanner.nextInt();
            scanner.nextLine();

            switch (opcionModificacion) {
                case 1:
                    System.out.print("Ingrese el nuevo nombre del producto: ");
                    String nuevoNombre = scanner.nextLine();
                    Producto productoActualizadoNombre = new Producto(nuevoNombre, producto.getPrecio(),
                            producto.getStock(), producto.getCategoria());
                    historialPrecios.put(productoActualizadoNombre.getId(), producto.getPrecio());
                    inventarioServicio.eliminarE(producto.getId());
                    inventarioServicio.agregarE(productoActualizadoNombre);
                    System.out.println("Nombre del producto modificado exitosamente.");
                    UtilidadLogger.registrarLog(
                            "Producto modificado con ID: " + producto.getId() + " nuevo nombre " + nuevoNombre);
                    break;
                case 2:
                    System.out.print("Ingrese el nuevo precio del producto: ");
                    double nuevoPrecio = scanner.nextDouble();
                    scanner.nextLine();
                    historialPrecios.put(producto.getId(), producto.getPrecio());
                    producto.setPrecio(nuevoPrecio);
                    System.out.println("Precio del producto modificado exitosamente.");
                    UtilidadLogger.registrarLog(
                            "Producto modificado con ID: " + producto.getId() + " nuevo precio " + nuevoPrecio);
                    break;
                case 3:
                    System.out.print("Ingrese el nuevo stock del producto: ");
                    int nuevoStock = scanner.nextInt();
                    scanner.nextLine();
                    producto.setStock(nuevoStock);
                    System.out.println("Stock del producto modificado exitosamente.");
                    UtilidadLogger.registrarLog(
                            "Producto modificado con ID: " + producto.getId() + " nuevo stock " + nuevoStock);
                    break;
                default:
                    System.out.println("Opcion de modificacion no valida.");
            }
        }
    }

    public void registrarVenta(ServicioVenta servicioVenta) {
        List<Producto> productos = inventarioServicio.obtenerEntidades();
        if (productos.isEmpty()) {
            System.out.println("No hay productos para vender.");
            return;
        }
        System.out.print("Ingrese el ID del producto a vender: ");
        int idVenta = scanner.nextInt();
        System.out.print("Ingrese la cantidad a vender: ");
        int cantidad = scanner.nextInt();
        scanner.nextLine();

        Producto producto = inventarioServicio.buscarPorId(idVenta);
        if (producto == null) {
            System.out.println("Producto no encontrado con ID: " + idVenta);
            UtilidadLogger.registrarLog("Error al registrar la venta: Producto no encontrado con ID: " + idVenta);
            return;
        }
        if (producto.getStock() < cantidad) {
            System.out.println("Stock insuficiente para el producto: " + producto.getNombre());
            UtilidadLogger.registrarLog(
                    "Error al registrar la venta: Stock insuficiente para el producto: " + producto.getNombre());
            return;
        }
        servicios.ServicioVenta servicioVent = (servicios.ServicioVenta) servicioVenta;
        modelos.Venta venta = new modelos.Venta(utilidades.GeneradorID.generarID(), producto, cantidad,
                producto.getPrecio() * cantidad, new java.util.Date());
        servicioVent.registrarVenta(venta);
        servicioNotificacion.enviarNotificacionStockBajo(producto.getNombre(), producto.getStock());
        System.out.println("STOCK: " + producto.getStock());
        UtilidadLogger.registrarLog(
                "Venta registrada exitosamente, producto: " + producto.getNombre() + " cantidad: " + cantidad);
    }

    public void ajustarStock() {
        List<Producto> productos = inventarioServicio.obtenerEntidades();
        if (productos.isEmpty()) {
            System.out.println("No hay productos para ajustar stock.");
            return;
        }

        // Mostrar productos
        System.out.println("\n=== Lista de Productos ===");
        for (Producto producto : productos) {
            System.out.println("ID: " + producto.getId() + ", Nombre: " + producto.getNombre() +
                    ", Stock: " + producto.getStock() + ", Categoría: " + producto.getCategoria());
        }

        // Selección de producto
        System.out.print("\n¿Desea buscar el producto por ID o Nombre? (id/nombre): ");
        String criterioBusqueda = scanner.nextLine().trim().toLowerCase();
        Producto producto = null;

        if (criterioBusqueda.equals("id")) {
            System.out.print("Ingrese el ID del producto: ");
            int idProducto = scanner.nextInt();
            scanner.nextLine();
            producto = inventarioServicio.buscarPorId(idProducto);
        } else if (criterioBusqueda.equals("nombre")) {
            System.out.print("Ingrese el nombre del producto: ");
            String nombreProducto = scanner.nextLine().trim();
            producto = inventarioServicio.buscarProductoPorNombre(nombreProducto);
        } else {
            System.out.println("Criterio no válido.");
            return;
        }

        if (producto == null) {
            System.out.println("Producto no encontrado.");
            return;
        }

        // Registrar movimiento
        System.out.print("Ingrese la cantidad de ajuste (positivo para entrada, negativo para salida): ");
        int cantidadAjuste = scanner.nextInt();
        scanner.nextLine();

        if (cantidadAjuste == 0) {
            System.out.println("No se realizó ningún ajuste.");
            return;
        }

        Movimiento movimiento;
        if (cantidadAjuste > 0) {
            movimiento = new Entrada(utilidades.GeneradorID.generarID(), new java.util.Date(), producto,
                    cantidadAjuste);
        } else {
            movimiento = new Salida(utilidades.GeneradorID.generarID(), new java.util.Date(), producto,
                    Math.abs(cantidadAjuste));
        }

        inventarioServicio.registrarMovimiento(movimiento);

        String detalleMovimiento = "Stock de producto " + producto.getId() + " ajustado. Tipo: " +
                movimiento.getTipoMovimiento() + ", Cantidad: " + Math.abs(cantidadAjuste) + ", Nuevo stock: "
                + producto.getStock();
        auditoriaServicio.registrarAccion(detalleMovimiento, usuarioActivo);

        System.out.println("Stock de " + producto.getNombre() + " ajustado a: " + producto.getStock());
        UtilidadLogger.registrarLog(detalleMovimiento);
    }

    public void modificarPrecio() {
        List<Producto> productos = inventarioServicio.obtenerEntidades();
        if (productos.isEmpty()) {
            System.out.println("No hay productos para modificar precio.");
            return;
        }
        System.out.println("=== Modificar Precio del Producto ===");
        System.out.print("Ingrese el ID del producto a modificar: ");
        int idProducto = scanner.nextInt();
        scanner.nextLine();
        Producto producto = inventarioServicio.buscarPorId(idProducto);
        if (producto == null) {
            System.out.println("Producto no encontrado con ID: " + idProducto);
            return;
        }
        System.out.print("Ingrese el nuevo precio del producto: ");
        double nuevoPrecio = scanner.nextDouble();
        scanner.nextLine();
        historialPrecios.put(producto.getId(), producto.getPrecio());
        producto.setPrecio(nuevoPrecio);
        System.out
                .println("Precio del producto " + producto.getNombre() + " modificado a " + producto.getPrecio() + ".");
        UtilidadLogger
                .registrarLog("Precio del producto " + producto.getNombre() + " modificado a " + producto.getPrecio());
    }
}
