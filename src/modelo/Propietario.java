package modelo;
/**
     * Esta es una clase extiende de la clase absatracta Persona y se la utiliza para instanciar objetos de Propietario
     * y guardar información.
     * @author Grupo E
*/
import java.util.Date;

public class Propietario extends Persona{
    private String placa;
    private int id_estado_propietario;
 /*Constructores*/ 
    public Propietario(){};
    
    public Propietario( String id_vehiculo, int id_estado_propietario, String cedula, 
            String nombre, String direccion, String correo, Date fechaNacimiento, 
            String telefono) {
        super(cedula, nombre, direccion, correo, fechaNacimiento, telefono);
        this.placa = id_vehiculo;
        this.id_estado_propietario = id_estado_propietario;

    }
/*Getter and Setter*/
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getId_estado_propietario() {
        return id_estado_propietario;
    }

    public void setId_estado_propietario(int id_estado_propietario) {
        this.id_estado_propietario = id_estado_propietario;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


   
}
