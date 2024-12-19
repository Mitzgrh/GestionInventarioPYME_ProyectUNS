package servicios;

import utilidades.Observador;
import utilidades.ManagerNotificaciones;
import java.util.ArrayList;
import java.util.List;

public class ServicioNotificacion implements Observador {

    private final List<String> notificaciones = new ArrayList<>();
    private final ManagerNotificaciones manejador;

    public ServicioNotificacion(ManagerNotificaciones manejador) {
        this.manejador = manejador;
        manejador.suscribir(this);
    }

    @Override
    public void update(String mensaje) {
        notificaciones.add("NOTIFICACION: " + mensaje);
        System.out.println("NOTIFICACION: " + mensaje);
    }

    public void enviarNotificacionStockBajo(String producto, int stockActual) {
        String mensaje = "ALERTA: El producto " + producto + " tiene stock bajo. Stock actual: " + stockActual;
        notificaciones.add(mensaje);
        System.out.println(mensaje);
    }

    public void enviarNotificacionProductoNuevo(String producto) {
        String mensaje = "NOTIFICACION: Nuevo producto añadido - " + producto;
        notificaciones.add(mensaje);
        System.out.println(mensaje);
    }

    public List<String> obtenerNotificaciones() {
        return notificaciones;
    }

    public void limpiarNotificaciones() {
        notificaciones.clear();
    }
}