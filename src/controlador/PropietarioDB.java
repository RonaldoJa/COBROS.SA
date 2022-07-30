package controlador;
/**
 * Este es el modelo que maneja los datos de los propietarios que se encuentra 
 * la BD y la interfaz.
 *
 * @author Grupo E
 */
import configSQL.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import modelo.Propietario;

public class PropietarioDB {

    private static PreparedStatement sentencia_preparada;
    private Connection nuevaConexion;
    private Statement statement;
    private ResultSet resultSet;
    private ArrayList<Propietario> listaPropietarios;
    private Propietario propietario;
    private ValidarCampos validarCampos;
    private int resultado;
    private String ejecutarSentencia;
    
    
    public PropietarioDB() {};
    /*
    * Este metodo extrae la lista de los propietarios para presentarlo en la tabla listarEmpleado
    */
    public ArrayList<Propietario> listarPropietarios() { 
        listaPropietarios = new ArrayList();
        try {
            nuevaConexion = ConexionDB.conectar();
            statement = nuevaConexion.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ADMIN_COBROS.PROPIETARIO");
            while (resultSet.next()) {
                propietario = new Propietario();
                propietario.setCedula(resultSet.getString("CEDULA"));
                propietario.setNombre(resultSet.getString("NOMBRE"));
                propietario.setCorreo(resultSet.getString("CORREO"));
                propietario.setTelefono(resultSet.getString("TELEFONO"));
                propietario.setDireccion(resultSet.getString("DIRECCION"));
                propietario.setFechaNacimiento(resultSet.getDate("FECHA_NAC"));
                propietario.setPlaca(resultSet.getString("PLACA"));
                propietario.setId_estado_propietario(resultSet.getInt("ID_ESTADO_PROPIETARIO"));

                listaPropietarios.add(propietario);        
            }
            statement.close();
            nuevaConexion.close();
            resultSet.close();
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("Error en listado");
        }
        return listaPropietarios;
    }
    /*
    * Este metodo permite obtener el propietario apartir de la cedula.
    */
    public Propietario obtenerPropietario(String cedula) { 
        propietario = new Propietario();
        try {
            nuevaConexion= ConexionDB.conectar();
            statement = nuevaConexion.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ADMIN_COBROS.PROPIETARIO WHERE CEDULA="+ cedula);
            while (resultSet.next()) {
                propietario.setCedula(resultSet.getString("CEDULA"));
                propietario.setNombre(resultSet.getString("NOMBRE"));
                propietario.setCorreo(resultSet.getString("CORREO"));
                propietario.setTelefono(resultSet.getString("TELEFONO"));
                propietario.setDireccion(resultSet.getString("DIRECCION"));
                propietario.setFechaNacimiento(resultSet.getDate("FECHA_NAC"));
                propietario.setPlaca(resultSet.getString("PLACA"));
                propietario.setId_estado_propietario(resultSet.getInt("ID_ESTADO_PROPIETARIO"));
            }  
            statement.close();
            nuevaConexion.close();
            resultSet.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return propietario ;
    }
    /*
    * Este metodo permite registrar a los propietarios. en la BD
    */
    public int registrarPropietario( Propietario propietario) {
        resultado = 0;
        ejecutarSentencia = ("INSERT INTO ADMIN_COBROS.PROPIETARIO"
                + "(CEDULA, NOMBRE, CORREO, TELEFONO, DIRECCION, FECHA_NAC, "
                +  "PLACA, ID_ESTADO_PROPIETARIO) "
                + "VALUES(?,?,?,?,?,?,?,?)");
        try {
            nuevaConexion = ConexionDB.conectar();
            sentencia_preparada = nuevaConexion.prepareStatement(ejecutarSentencia);
            sentencia_preparada.setString(1, propietario.getCedula());
            sentencia_preparada.setString(2, propietario.getNombre());
            sentencia_preparada.setString(3, propietario.getCorreo());
            sentencia_preparada.setString(4, propietario.getTelefono());
            sentencia_preparada.setString(5, propietario.getDireccion());
            sentencia_preparada.setDate(6, (java.sql.Date) propietario.getFechaNacimiento());
            sentencia_preparada.setString(7, propietario.getPlaca());
            sentencia_preparada.setString(8, propietario.getId_estado_propietario()+"");
            resultado = sentencia_preparada.executeUpdate();
            sentencia_preparada.close();
            nuevaConexion.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return resultado;
    }
    public boolean validarEdad(Date date){
        validarCampos = new ValidarCampos();
        return validarCampos.validarEdad(date);
    }

    public int modificarPropietario(Propietario propietario) {
        resultado = 0;
        ejecutarSentencia ="UPDATE ADMIN_COBROS.PROPIETARIO SET "
            + "NOMBRE='" + propietario.getNombre()+"'"
            + ",CORREO='" + propietario.getCorreo()+"'"
            + ",TELEFONO='"+ propietario.getTelefono()+"'"
            + ",DIRECCION='" + propietario.getDireccion()+"'"
            + ",FECHA_NAC=TO_DATE('" + (java.sql.Date)propietario.getFechaNacimiento()
            +"', 'YYYY-MM-DD HH24:MI:SS')"
            + ",PLACA='" + propietario.getPlaca()+"'"
            + ",ID_ESTADO_PROPIETARIO='" +propietario.getId_estado_propietario()+"'"
            + " WHERE CEDULA=" + propietario.getCedula();
        
        try {
            nuevaConexion = ConexionDB.conectar();
            sentencia_preparada = nuevaConexion.prepareStatement(ejecutarSentencia);
            resultado = sentencia_preparada.executeUpdate();
            sentencia_preparada.close();
            nuevaConexion.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + "No se logró actualizar");
        }
        return resultado;
    }
    
    public String verificarSiExistePropietario(String cedula){
        try {
            nuevaConexion= ConexionDB.conectar();
            statement = nuevaConexion.createStatement();
            resultSet = statement.executeQuery("SELECT CEDULA FROM ADMIN_COBROS.PROPIETARIO WHERE CEDULA="+ cedula);
            cedula = null;
            while (resultSet.next()) {
                cedula = resultSet.getString("CEDULA");
            }  
            statement.close();
            nuevaConexion.close();
            resultSet.close();
        } catch (Exception e) {
            System.out.println(e);  
        }
        return cedula;
    }

    public void llenarCmbEstadoPropietario(JComboBox cmbEstadoPropietario){
        try {
            nuevaConexion = ConexionDB.conectar();
            statement = nuevaConexion.createStatement();
            resultSet = statement.executeQuery("SELECT NOMBRE_ESTADO FROM ESTADO_PROPIETARIO");
            while (resultSet.next()) {
                cmbEstadoPropietario.addItem(resultSet.getString("NOMBRE_ESTADO"));          
            }
            statement.close();
            nuevaConexion.close();
            resultSet.close();
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("Error en llenar estado propietario");
        }
    }

}
