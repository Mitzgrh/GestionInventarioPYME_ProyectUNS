package servicios;

import modelos.Categoria;
import java.util.*;

public class ServicioCategoria implements IServicio<Categoria>{
    private List<Categoria> categorias;

    public ServicioCategoria() {
        this.categorias = new ArrayList<>();
    }

    @Override
     public void agregarE(Categoria categoria) {
        if (categorias.stream().anyMatch(c -> c.getNombre().equalsIgnoreCase(categoria.getNombre()))) {
            System.out.println("Ya existe una categoria con este nombre.");
            utilidades.UtilidadLogger.registrarLog("Intento de agregar categoria duplicada: " + categoria.getNombre());
              return;
        }
        categorias.add(categoria);
    }

    @Override
    public void eliminarE(int id) {
        categorias.removeIf(c -> c.getId() == id);
    }

    @Override
    public List<Categoria> obtenerEntidades() {
        return categorias;
    }

    @Override
    public Categoria buscarPorId(int id) {
        return categorias.stream().filter(categoria -> categoria.getId() == id).findFirst().orElse(null);
    }
}