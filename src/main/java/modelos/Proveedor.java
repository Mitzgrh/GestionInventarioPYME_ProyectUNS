package modelos;

interface ProveedorBase {
    int getId();
    String getNombre();
    String getContacto();
}

public class Proveedor implements ProveedorBase {
    private int id;
    private String nombre;
    private String contacto;

    public Proveedor(int id, String nombre, String contacto) {
        this.id = id;
        this.nombre = nombre;
        this.contacto = contacto;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public String getContacto() {
        return contacto;
    }    
}