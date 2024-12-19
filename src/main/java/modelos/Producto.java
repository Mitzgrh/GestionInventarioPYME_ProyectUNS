package modelos;

public class Producto {
    private final int id;//Para no modificar el ID unico creado para un producto
    private String nombre; //Nombre de producto
    private double precio; //Precio en double
    private int stock; //Stock del producto
    private Categoria categoria; //En que categoria se encontrara almacenado el producto

    public Producto(String nombre, double precio, int stock, Categoria categoria) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacio");
        } else if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        } else if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        } else {
            this.id = utilidades.GeneradorID.generarID();
            this.nombre = nombre;
            this.precio = precio;
            this.stock = stock;
            this.categoria = categoria;
        }
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}