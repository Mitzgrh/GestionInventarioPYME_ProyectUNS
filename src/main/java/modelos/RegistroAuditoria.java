package modelos;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistroAuditoria {
    private int id;
    private String accion;
    private Date fechaRegistro;

    public RegistroAuditoria(int id, String accion, Date fechaRegistro) {
        this.id = id;
        this.accion = accion;
        this.fechaRegistro = fechaRegistro;
    }

    public int getId() {
        return id;
    }

    public String getAccion() {
        return accion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return "ID: " + id + ", Accion: " + accion + ", Fecha: " + sdf.format(fechaRegistro);
    }
}
