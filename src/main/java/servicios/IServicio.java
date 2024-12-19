package servicios;

import java.util.List;

public interface IServicio<S> {
    void agregarE(S entidad);
    void eliminarE(int id);
    List<S> obtenerEntidades();
    S buscarPorId(int id);
}