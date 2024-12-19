package controladores;

import servicios.ServicioCategoria;
import servicios.ServicioInventario;
import modelos.Categoria;
import modelos.Producto;

import java.util.List;
import java.util.Scanner;
import utilidades.UtilidadLogger;

public class ControladorCategoria {
    private ServicioCategoria categoriaServicio;
    private ServicioInventario inventarioServicio;
    private Scanner scanner;

    public ControladorCategoria(ServicioCategoria categoriaServicio, ServicioInventario inventarioServicio, Scanner scanner) {
        this.categoriaServicio = categoriaServicio;
        this.inventarioServicio = inventarioServicio;
        this.scanner = scanner;
    }

    public void agregarCategoria() {
        System.out.print("Ingrese el nombre de la categoria: ");
        String nombreCategoria = scanner.nextLine();
        categoriaServicio.agregarE(new Categoria(utilidades.GeneradorID.generarID(), nombreCategoria));
        System.out.println("Categoria agregada exitosamente.");
        UtilidadLogger.registrarLog("Categoria agregada: " + nombreCategoria);
    }

    public void eliminarCategoria() {
        List<Categoria> categorias = categoriaServicio.obtenerEntidades();
        if (categorias.isEmpty()) {
            System.out.println("No hay categorias para eliminar.");
            return;
        }
        System.out.print("Ingrese el ID de la categoria a eliminar: ");
        int idCategoria = scanner.nextInt();
        scanner.nextLine();
        Categoria categoria = categoriaServicio.buscarPorId(idCategoria);
        if (categoria == null) {
            System.out.println("No se encontro ninguna categoria con el ID proporcionado.");
            return;
        }
        categoriaServicio.eliminarE(idCategoria);
        System.out.println("Categoria eliminada exitosamente.");
        UtilidadLogger.registrarLog("Categoria eliminada con ID: " + idCategoria);
    }

    public void verCategorias() {
        List<Categoria> categorias = categoriaServicio.obtenerEntidades();
        if (categorias.isEmpty()) {
            System.out.println("No hay categorias registradas.");
            return;
        }
        System.out.println("\n--- Lista de Categorias ---");
        for (Categoria categoria : categorias) {
            System.out.println("ID: " + categoria.getId() + ", Nombre: " + categoria.getNombre());
        }
    }

    public void buscarCategoria() {
        System.out.print("Ingrese el ID o nombre de la categoria a buscar: ");
        String criterio = scanner.nextLine();
        try {
            int id = Integer.parseInt(criterio);
            Categoria categoria = categoriaServicio.buscarPorId(id);
            if (categoria != null) {
                System.out.println("ID: " + categoria.getId() + ", Nombre: " + categoria.getNombre());
                modificarCategoria(categoria);
            } else {
                System.out.println("No se encontro ninguna categoria con el ID proporcionado.");
            }
        } catch (NumberFormatException e) {
            List<Categoria> categorias = categoriaServicio.obtenerEntidades();
            List<Categoria> categoriasEncontradas = categorias.stream()
                    .filter(categoria -> categoria.getNombre().equalsIgnoreCase(criterio))
                    .toList();
            if (!categoriasEncontradas.isEmpty()) {
                if (categoriasEncontradas.size() == 1) {
                    Categoria categoriaEncontrada = categoriasEncontradas.get(0);
                    System.out.println(
                            "ID: " + categoriaEncontrada.getId() + ", Nombre: " + categoriaEncontrada.getNombre());
                    modificarCategoria(categoriaEncontrada);
                } else {
                    System.out.println("Se encontraron varias categorias con ese nombre:");
                    for (Categoria categoria : categoriasEncontradas) {
                        System.out.println("ID: " + categoria.getId() + ", Nombre: " + categoria.getNombre());
                    }
                    System.out.println("Porfavor usa el ID si quieres buscar una en especifico.");
                }
            } else {
                System.out.println("No se encontro ninguna categoria con el nombre proporcionado.");
            }
        }
    }

    public void modificarCategoria(Categoria categoria) {
        System.out.print("¿Desea modificar la categoría? (s/n): ");
        String opcion = scanner.nextLine();
        if (opcion.equalsIgnoreCase("s")) {
            System.out.print("Ingrese el nuevo nombre de la categoría: ");
            String nuevoNombre = scanner.nextLine();
            categoria.setNombre(nuevoNombre);
            List<Producto> productos = inventarioServicio.obtenerEntidades();
            for (Producto producto : productos) {
                if (producto.getCategoria().getId() == categoria.getId()) {
                    producto.setCategoria(categoria);
                }
            }
            System.out.println("Categoría modificada exitosamente.");
            UtilidadLogger.registrarLog(
                    "Categoría modificada con ID: " + categoria.getId() + ", nuevo nombre: " + nuevoNombre);
        }
    }
}