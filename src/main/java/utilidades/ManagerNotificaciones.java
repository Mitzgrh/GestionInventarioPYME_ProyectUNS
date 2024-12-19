package utilidades;

import java.util.ArrayList;
import java.util.List;

public class ManagerNotificaciones {
    private final List<Observador> observadores = new ArrayList<>();

    public void suscribir(Observador observador) {
        observadores.add(observador);
    }

    public void desuscribir(Observador observador) {
        observadores.remove(observador);
    }

    public void notificar(String mensaje) {
        for (Observador observador : observadores) {
            observador.update(mensaje);
        }
    }
}