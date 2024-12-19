package servicios;

import modelos.RegistroAuditoria;
import modelos.Usuario;
import java.util.*;

public class ServicioAuditoria {
    private final List<RegistroAuditoria> registros;

    public ServicioAuditoria() {
        this.registros = new ArrayList<>();
    }

    public void registrarAccion(String accion, Usuario usuario) {
        RegistroAuditoria registro = new RegistroAuditoria(utilidades.GeneradorID.generarID(), accion + " por: " + usuario.getNombre(), new Date());
        registros.add(registro);
    }
    
     public void mostrarRegistros() {
         System.out.println("---- Registros de Auditoria ----");
         for (RegistroAuditoria registro : registros) {
             System.out.println("ID: " + registro.getId() + ", Accion: " + registro.getAccion() + ", Fecha: " + registro.getFechaRegistro());
         }
     }
    
     public List<RegistroAuditoria> obtenerRegistros() {
        return new ArrayList<>(registros);
    }
}