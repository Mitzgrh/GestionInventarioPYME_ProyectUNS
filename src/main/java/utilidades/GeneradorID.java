package utilidades;

import java.util.concurrent.atomic.AtomicInteger;

public class GeneradorID {
    private static final AtomicInteger contador = new AtomicInteger(0);

    public static int generarID() {
        return contador.incrementAndGet();
    }
}