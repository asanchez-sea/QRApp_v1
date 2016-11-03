package sea.co.cr.qrapp.Model;

/**
 * Created by asanchezo on 1/11/2016.
 */

public class Asistente {
    private String nombre;
    private String correoElectronico;
    private String empresa;
    private String estado;
    private String asistente;
    int confirmacion;
    private static Asistente asistenteSingleton;
    private Asistente(){

        //ToDo here

    }
    public static Asistente getAsistenteSingleton() {
        if (asistenteSingleton == null)
        {
            asistenteSingleton = new Asistente();
        }
        return asistenteSingleton;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getConfirmacion() {
        return confirmacion;
    }

    public void setConfirmacion(int confirmacion) {
        this.confirmacion = confirmacion;
    }

    public String getAsistente() {
        return asistente;
    }

    public void setAsistente(String asistente) {
        this.asistente = asistente;
    }
    public void limpiaAsistente(){
        getAsistenteSingleton().setEstado("");
        getAsistenteSingleton().setAsistente("");
        getAsistenteSingleton().setEmpresa("");
        getAsistenteSingleton().setCorreoElectronico("");
        getAsistenteSingleton().setNombre("");
        getAsistenteSingleton().setConfirmacion(2);
    }
}
