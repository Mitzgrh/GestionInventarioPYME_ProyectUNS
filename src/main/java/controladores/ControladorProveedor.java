package controladores;

import servicios.ServicioProveedor;
import modelos.Proveedor;
import java.util.List;
import java.util.Scanner;
import utilidades.UtilidadLogger;

public class ControladorProveedor {
    private ServicioProveedor proveedorServicio;
    private Scanner scanner;

    public ControladorProveedor(ServicioProveedor proveedorServicio, Scanner scanner) {
        this.proveedorServicio = proveedorServicio;
        this.scanner = scanner;
    }

    public void agregarProveedor() {
        System.out.print("Ingrese el nombre del proveedor: ");
        String nombreProveedor = scanner.nextLine();
        System.out.print("Ingrese el contacto del proveedor: ");
        String contactoProveedor = scanner.nextLine();
        proveedorServicio.agregarE(new Proveedor(utilidades.GeneradorID.generarID(), nombreProveedor, contactoProveedor));
        System.out.println("Proveedor agregado exitosamente.");
        UtilidadLogger.registrarLog("Proveedor agregado: " + nombreProveedor);
    }

    public void eliminarProveedor() {
        List<Proveedor> proveedores = proveedorServicio.obtenerEntidades();
        if (proveedores.isEmpty()) {
            System.out.println("No hay proveedores para eliminar.");
            return;
        }
        System.out.print("Ingrese el ID del proveedor a eliminar: ");
        int idProveedor = scanner.nextInt();
        scanner.nextLine();
        Proveedor proveedor = proveedorServicio.buscarPorId(idProveedor);
        if (proveedor == null) {
            System.out.println("No se encontro ningun proveedor con el ID proporcionado.");
            return;
        }
        proveedorServicio.eliminarE(idProveedor);
        System.out.println("Proveedor eliminado exitosamente.");
        UtilidadLogger.registrarLog("Proveedor eliminado con ID: " + idProveedor);
    }

    public void verProveedores() {
        List<Proveedor> proveedores = proveedorServicio.obtenerEntidades();
        if (proveedores.isEmpty()) {
            System.out.println("No hay proveedores registrados.");
            return;
        }
        System.out.println("\n--- Lista de Proveedores ---");
        for (Proveedor proveedor : proveedores) {
            System.out.println("ID: " + proveedor.getId() + ", Nombre: " + proveedor.getNombre() + ", Contacto: " + proveedor.getContacto());
        }
    }

    public void buscarProveedor() {
        System.out.print("Ingrese el ID o nombre del proveedor a buscar: ");
        String criterio = scanner.nextLine();
         try {
                int id = Integer.parseInt(criterio);
                Proveedor proveedor = proveedorServicio.buscarPorId(id);
                if (proveedor != null) {
                     System.out.println("ID: " + proveedor.getId() + ", Nombre: " + proveedor.getNombre() + ", Contacto: " + proveedor.getContacto());
                    modificarProveedor(proveedor);
                } else {
                     System.out.println("No se encontro ningun proveedor con el ID proporcionado.");
                }
            } catch (NumberFormatException e) {
                List<Proveedor> proveedores = proveedorServicio.obtenerEntidades();
                  List<Proveedor> proveedoresEncontrados = proveedores.stream()
                        .filter(proveedor -> proveedor.getNombre().equalsIgnoreCase(criterio))
                        .toList();
                if (!proveedoresEncontrados.isEmpty()) {
                    if(proveedoresEncontrados.size() == 1){
                           Proveedor proveedorEncontrado = proveedoresEncontrados.get(0);
                            System.out.println("ID: " + proveedorEncontrado.getId() + ", Nombre: " + proveedorEncontrado.getNombre() + ", Contacto: " + proveedorEncontrado.getContacto());
                            modificarProveedor(proveedorEncontrado);
                    }else{
                         System.out.println("Se encontraron varios proveedores con ese nombre:");
                         for (Proveedor proveedor : proveedoresEncontrados) {
                            System.out.println("ID: " + proveedor.getId() + ", Nombre: " + proveedor.getNombre() + ", Contacto: " + proveedor.getContacto());
                        }
                        System.out.println("Porfavor usa el ID si quieres buscar una en especifico.");
                    }
                } else {
                   System.out.println("No se encontro ningun proveedor con el nombre proporcionado.");
                }
            }
    }

    public void modificarProveedor(Proveedor proveedor) {
        System.out.print("¿Desea modificar el proveedor? (s/n): ");
        String opcion = scanner.nextLine();
        if (opcion.equalsIgnoreCase("s")) {
            System.out.println("¿Que desea modificar?");
            System.out.println("1. Nombre");
            System.out.println("2. Contacto");
            System.out.print("Seleccione una opcion: ");
            int opcionModificacion = scanner.nextInt();
            scanner.nextLine();

            switch (opcionModificacion) {
                case 1:
                    System.out.print("Ingrese el nuevo nombre del proveedor: ");
                    String nuevoNombre = scanner.nextLine();
                    Proveedor proveedorActualizadoNombre = new Proveedor(proveedor.getId(), nuevoNombre, proveedor.getContacto());
                    proveedorServicio.agregarE(proveedorActualizadoNombre);
                    System.out.println("Nombre del proveedor modificado exitosamente.");
                    UtilidadLogger.registrarLog("Proveedor modificado con ID: " + proveedor.getId() + " nuevo nombre " + nuevoNombre);
                    break;
                case 2:
                    System.out.print("Ingrese el nuevo contacto del proveedor: ");
                    String nuevoContacto = scanner.nextLine();
                    Proveedor proveedorActualizadoContacto = new Proveedor(proveedor.getId(), proveedor.getNombre(), nuevoContacto);
                    proveedorServicio.agregarE(proveedorActualizadoContacto);
                    System.out.println("Contacto del proveedor modificado exitosamente.");
                    UtilidadLogger.registrarLog("Proveedor modificado con ID: " + proveedor.getId() + " nuevo contacto " + nuevoContacto);
                    break;
                default:
                    System.out.println("Opcion de modificacion no valida.");
            }
        }
    }
}