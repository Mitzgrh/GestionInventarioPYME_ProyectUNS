package modelos;

public class Vendedor extends Usuario {
    public Vendedor(int id, String nombre, String email, String contrasena) {
        super(id, nombre, email, contrasena);
    }

    @Override
    public String getRol() {
        return "Vendedor";
    }
}