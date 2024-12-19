package main;

import servicios.*;
import modelos.*;
import utilidades.*;
import controladores.*;
import java.util.*;

public class SistemaGestionInventario {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Inicializacion de servicios
        ServicioInventario inventarioServicio = new ServicioInventario();
        ServicioCategoria categoriaServicio = new ServicioCategoria();
        ServicioProveedor proveedorServicio = new ServicioProveedor();
        ServicioVenta servicioVenta = new ServicioVenta(inventarioServicio);
        ManagerNotificaciones ManagerNotificaciones = new ManagerNotificaciones();
        ServicioNotificacion servicioNotificacion = new ServicioNotificacion(ManagerNotificaciones);
        ServicioAuditoria auditoriaServicio = new ServicioAuditoria();

        // Usuarios de prueba
        Usuario administrador = new Administrador(1, "Admin", "admin@empresa.com", "admin123");
        Usuario vendedor = new Vendedor(2, "Juan", "juan@empresa.com", "vendedor123");
        Usuario usuarioActivo = iniciarSesion(scanner, administrador, vendedor);
        
        // Inicializacion de controladores
        ControladorProducto controladorProducto = new ControladorProducto(inventarioServicio, categoriaServicio,
        servicioNotificacion, scanner, auditoriaServicio, usuarioActivo);
        ControladorCategoria controladorCategoria = new ControladorCategoria(categoriaServicio, inventarioServicio, scanner);
        ControladorProveedor controladorProveedor = new ControladorProveedor(proveedorServicio, scanner);
        ControladorReporte controladorReporte = new ControladorReporte(inventarioServicio, servicioVenta, proveedorServicio, auditoriaServicio, scanner);

        while (true) {
            System.out.println("\n--- Sistema de Gestion de Inventario ---");
            System.out.println("1. Gestion de Productos");
            System.out.println("2. Gestion de Categorias");
            System.out.println("3. Gestion de Proveedores");
            System.out.println("4. Generacion de Informes");
            System.out.println("5. Notificaciones");
            System.out.println("6. Auditoria");
            System.out.println("7. Configuracion");
             System.out.println("8. Ajustar Stock");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opcion: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    auditoriaServicio.registrarAccion("Acceso a Gestion de Productos", usuarioActivo);
                    gestionarProductos(scanner, controladorProducto, servicioVenta);
                    break;
                case 2:
                    auditoriaServicio.registrarAccion("Acceso a Gestion de Categorias", usuarioActivo);
                    gestionarCategorias(scanner, controladorCategoria);
                    break;
                case 3:
                    auditoriaServicio.registrarAccion("Acceso a Gestion de Proveedores", usuarioActivo);
                    gestionarProveedores(scanner, controladorProveedor);
                    break;
                case 4:
                    auditoriaServicio.registrarAccion("Generacion de Informes", usuarioActivo);
                    controladorReporte.generarInformes();
                    break;
                case 5:
                    auditoriaServicio.registrarAccion("Acceso a Notificaciones", usuarioActivo);
                    mostrarNotificaciones(scanner, servicioNotificacion);
                    break;
                case 6:
                    auditoriaServicio.mostrarRegistros();
                    break;
                case 7:
                    configurarSistema(scanner, usuarioActivo, administrador, vendedor);
                    break;
                case 8:
                    controladorProducto.ajustarStock();
                    break;
                case 9:
                    System.out.println("Saliendo del sistema...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opcion invalida. Intente nuevamente.");
            }
        }
    }

    private static Usuario iniciarSesion(Scanner scanner, Usuario admin, Usuario vendedor) {
        System.out.println("\n--- Inicio de Sesion ---");
        while (true) {
            System.out.print("Ingrese nombre de usuario: ");
            String usuario = scanner.nextLine();
            System.out.print("Ingrese contraseña: ");
            String password = scanner.nextLine();

            if (usuario.equals("Admin") && password.equals("admin123")) {
                System.out.println("Bienvenido Administrador.");
                return admin;
            } else if (usuario.equals("Vendedor") && password.equals("vendedor123")) {
                System.out.println("Bienvenido Vendedor.");
                return vendedor;
            } else {
                System.out.println("Credenciales invalidas. Intente nuevamente.");
            }
        }
    }

    private static void configurarSistema(Scanner scanner, Usuario usuarioActivo, Usuario admin, Usuario vendedor) {
        System.out.println("\n--- Configuracion del Sistema ---");
        if (usuarioActivo instanceof Administrador) {
            System.out.println("1. Cambiar contraseña de Administrador");
            System.out.println("2. Cambiar contraseña de Vendedor");
            System.out.print("Seleccione una opcion: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese la nueva contraseña de Administrador: ");
                    admin.setContrasena(scanner.nextLine());
                    System.out.println("Contraseña de Administrador actualizada.");
                    break;
                case 2:
                    System.out.print("Ingrese la nueva contraseña de Vendedor: ");
                    vendedor.setContrasena(scanner.nextLine());
                    System.out.println("Contraseña de Vendedor actualizada.");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } else {
            System.out.println("Solo el Administrador puede acceder a esta configuracion.");
        }
    }

    private static void gestionarProductos(Scanner scanner, ControladorProducto controladorProducto, ServicioVenta servicioVenta) {
          while (true) {
            System.out.println("\n--- Gestion de Productos ---");
            System.out.println("1. Agregar Producto");
            System.out.println("2. Eliminar Producto");
            System.out.println("3. Ver Productos");
            System.out.println("4. Buscar Producto");
            System.out.println("5. Registrar Venta");
             System.out.println("6. Modificar Precio");
             System.out.println("7. Volver al Menu Principal");
            System.out.print("Seleccione una opcion: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    controladorProducto.agregarProducto();
                    break;
                case 2:
                    controladorProducto.eliminarProducto();
                    break;
                case 3:
                    controladorProducto.verProductos();
                    break;
                case 4:
                    controladorProducto.buscarProducto();
                    break;
                case 5:
                   controladorProducto.registrarVenta(servicioVenta);
                    break;
                case 6:
                     controladorProducto.modificarPrecio();
                     break;
                  case 7:
                    return;
                default:
                    System.out.println("Opcion invalida en Gestion de Productos.");
            }
        }
    }

    private static void gestionarCategorias(Scanner scanner, ControladorCategoria controladorCategoria) {
       while (true) {
            System.out.println("\n--- Gestion de Categorias ---");
            System.out.println("1. Agregar Categoria");
            System.out.println("2. Eliminar Categoria");
            System.out.println("3. Ver Categorias");
            System.out.println("4. Buscar Categoria");
            System.out.println("5. Volver al Menu Principal");
            System.out.print("Seleccione una opcion: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    controladorCategoria.agregarCategoria();
                    break;
                case 2:
                    controladorCategoria.eliminarCategoria();
                    break;
                case 3:
                    controladorCategoria.verCategorias();
                    break;
                 case 4:
                     controladorCategoria.buscarCategoria();
                     break;
                 case 5:
                    return;
                default:
                    System.out.println("Opcion invalida en Gestion de Categorias.");
            }
        }
    }

    private static void gestionarProveedores(Scanner scanner, ControladorProveedor controladorProveedor) {
         while (true) {
            System.out.println("\n--- Gestion de Proveedores ---");
            System.out.println("1. Agregar Proveedor");
            System.out.println("2. Eliminar Proveedor");
            System.out.println("3. Ver Proveedores");
            System.out.println("4. Buscar Proveedor");
             System.out.println("5. Volver al Menu Principal");
            System.out.print("Seleccione una opcion: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion) {
                case 1:
                    controladorProveedor.agregarProveedor();
                    break;
                case 2:
                    controladorProveedor.eliminarProveedor();
                    break;
                case 3:
                    controladorProveedor.verProveedores();
                    break;
                 case 4:
                     controladorProveedor.buscarProveedor();
                     break;
                  case 5:
                    return;
                default:
                    System.out.println("Opcion invalida en Gestion de Proveedores.");
            }
        }
    }

    private static void mostrarNotificaciones(Scanner scanner, ServicioNotificacion servicioNotificacion) {
        System.out.println("\n--- Notificaciones ---");
        List<String> notificaciones = servicioNotificacion.obtenerNotificaciones();
        if (notificaciones.isEmpty()) {
            System.out.println("No hay notificaciones disponibles.");
        } else {
            for (int i = 0; i < notificaciones.size(); i++) {
                System.out.println((i + 1) + ". " + notificaciones.get(i));
            }
            System.out.println("¿Desea limpiar las notificaciones? (s/n)");
            String opcion = scanner.nextLine();
            if (opcion.equalsIgnoreCase("s")) {
                servicioNotificacion.limpiarNotificaciones();
                System.out.println("Notificaciones limpiadas con exito uwu.");
            }
        }
    }
}