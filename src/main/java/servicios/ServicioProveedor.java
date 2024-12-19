package servicios;

import modelos.Proveedor;
import java.util.*;

public class ServicioProveedor implements IServicio<Proveedor> {
    private List<Proveedor> proveedores;

    public ServicioProveedor() {
        this.proveedores = new ArrayList<>();
    }

    @Override
    public void agregarE(Proveedor proveedor) {
        if (proveedores.stream().anyMatch(p -> p.getNombre().equalsIgnoreCase(proveedor.getNombre()))) {
              System.out.println("Ya existe un proveedor con este nombre.");
                utilidades.UtilidadLogger.registrarLog("Intento de agregar proveedor duplicado: " + proveedor.getNombre());
            return;
        }
        proveedores.add(proveedor);
    }

    @Override
    public void eliminarE(int id) {
        proveedores.removeIf(p -> p.getId() == id);
    }

    @Override
    public List<Proveedor> obtenerEntidades() {
        return proveedores;
    }

    @Override
    public Proveedor buscarPorId(int id) {
        return proveedores.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }
}