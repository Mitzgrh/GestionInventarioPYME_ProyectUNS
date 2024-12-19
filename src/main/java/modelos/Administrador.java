package modelos;

public class Administrador extends Usuario {
    public Administrador(int id, String nombre, String email, String contrasena) {
        super(id, nombre, email, contrasena);
    }

    @Override
    public String getRol() {
        return "Administrador";
    }
}