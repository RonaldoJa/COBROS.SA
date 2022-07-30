package controlador;

import Vista.FrmConsultarEmpleado;
import Vista.FrmEditarEmpleado;
import configSQL.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Empleado;

public class EmpleadoDB {

    private static PreparedStatement sentencia_preparada;
    private Connection nuevaConexion;
    private Statement statement;
    private ResultSet resultSet;
    private Empleado empleado;
    private ResultSet resultado;

    ConexionDB cnx = new ConexionDB();
    Connection conexion = cnx.conectar();
    ArrayList<Empleado> arrayEmpleado;

    public EmpleadoDB() {
        arrayEmpleado = new ArrayList();
    }

    public ArrayList<Empleado> ListEmpleado() {
        arrayEmpleado.clear();
        int i = 0;
        try {
            Connection cnx = ConexionDB.conectar();
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM ADMIN_COBROS.EMPLEADO");

            while (rs.next()) {
                System.out.println(i);
                Empleado empleado = new Empleado();
                empleado.setIdEmpleado(rs.getString("ID_EMPLEADO"));
                empleado.setCedula(rs.getString("CEDULA"));
                empleado.setNombre(rs.getString("NOMBRE"));
                empleado.setCorreo(rs.getString("CORREO"));
                empleado.setContrasenia(rs.getString("CONTRASENIA"));
                empleado.setTelefono(rs.getString("TELEFONO"));
                empleado.setDireccion(rs.getString("DIRECCION"));
                empleado.setFechaNacimiento(rs.getDate("FECHA_NAC"));
                empleado.setIdTrabajo(rs.getString("ID_TRABAJO"));
                arrayEmpleado.add(empleado);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("Error en listado");
        }
        return arrayEmpleado;
    }

        public String guardar(Empleado empleado) {
        String resultado = "Todo bien";
        Connection nuevaConexion = null;

        Statement addEmp;
        if(empleado.getCedula() != null ) {
            String addEmpeado = "INSERT INTO ADMIN_COBROS.EMPLEADO(ID_EMPLEADO, CEDULA, NOMBRE, CORREO, CONTRASENIA, TELEFONO, DIRECCION, FECHA_NAC, ID_TRABAJO)\n"
                    + "VALUES('" + empleado.getIdEmpleado() +  "','" + empleado.getCedula()+ "','" + 
                    empleado.getNombre() + "','" + empleado.getCorreo() + "','" + empleado.getContrasenia() + 
                    "','" + empleado.getTelefono() + "','" + empleado.getDireccion() 
                    + "', TO_DATE('" + empleado.getFechaNacimiento() + "','YYYY-MM-DD'),'" 
                    + empleado.getIdTrabajo() + "')";
            try {
                nuevaConexion = ConexionDB.conectar();
                addEmp = nuevaConexion.createStatement();
                addEmp.executeQuery(addEmpeado);
                System.out.println("Insertando datos...");
                nuevaConexion.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            return resultado;
        } else {
            String addEmpeado = "INSERT INTO ADMIN_COBROS.EMPLEADO(ID_EMPLEADO, CEDULA, NOMBRE, CORREO, CONTRASENIA, TELEFONO, DIRECCION, FECHA_NAC, ID_TRABAJO, ID_ADMINISTRADOR)\n"
                    + "VALUES('" + empleado.getIdEmpleado() + "','" + empleado.getCedula() + "','" + 
                    empleado.getNombre() + "','" + empleado.getCorreo() + "','" + empleado.getContrasenia() + 
                    "','" + empleado.getTelefono() + "','" + empleado.getDireccion() + "', TO_DATE('" + 
                    empleado.getFechaNacimiento() + "','YYYY-MM-DD'),'" + empleado.getIdTrabajo() +"')";
            try {
                nuevaConexion = ConexionDB.conectar();
                System.out.println(empleado.getFechaNacimiento());
                addEmp = nuevaConexion.createStatement();
                addEmp.executeQuery(addEmpeado);
                System.out.println("Insertando datos...");
                nuevaConexion.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            return resultado;
        }
    }
    public void llenarEditarEmpleado(JTable tablaEmpleados, FrmEditarEmpleado ventanaEmpleado) {
        Connection nuevaConexion = null;
        int fila = tablaEmpleados.getSelectedRow();
        System.out.println(fila);
        String CEDULA = tablaEmpleados.getValueAt(fila, 0).toString();
        try {
            nuevaConexion = ConexionDB.conectar();
            String sentencia_buscar = ("SELECT * FROM ADMIN_COBROS.EMPLEADO WHERE CEDULA=" + CEDULA);
            sentencia_preparada = nuevaConexion.prepareStatement(sentencia_buscar);
            resultado = sentencia_preparada.executeQuery();
            if (resultado.next()) {
                String ID_EMPLEADO = resultado.getString("ID_EMPLEADO");
                String NOMBRE = resultado.getString("NOMBRE");
                String CORREO = resultado.getString("CORREO");
                String TELEFONO = resultado.getString("TELEFONO");
                String DIRECCION = resultado.getString("DIRECCION");
                Date FECHA_NAC = resultado.getDate("FECHA_NAC");
                String ID_TRABAJO = resultado.getString("ID_TRABAJO");

                ventanaEmpleado.txtCedula.setText(CEDULA);
                ventanaEmpleado.txtCorreo.setText(CORREO);
                ventanaEmpleado.txtDireccion.setText(DIRECCION);
                ventanaEmpleado.txtNombre.setText(NOMBRE);
                ventanaEmpleado.txtTelefono.setText(TELEFONO);
            }
            nuevaConexion.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void modificarEmpleado(FrmEditarEmpleado ventanaEmpleado) {

        String CEDULA = ventanaEmpleado.txtCedula.getText();
        String CORREO = ventanaEmpleado.txtCorreo.getText();
        String DIRECCION = ventanaEmpleado.txtDireccion.getText();
        String NOMBRE = ventanaEmpleado.txtNombre.getText();
        String TELEFONO = ventanaEmpleado.txtTelefono.getText();
        try {
            PreparedStatement act = conexion.prepareStatement("UPDATE ADMIN_COBROS.EMPLEADO SET CEDULA='" + CEDULA + "'," + "CORREO='" + CORREO + "',DIRECCION='" + DIRECCION + "',NOMBRE='" + NOMBRE + "',TELEFONO='" + TELEFONO + "'\n"
                    + "WHERE CEDULA ='" + CEDULA + "'");
            act.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + "No se logró actualizar");
        }
    }

    public void visualizarEmpleado(JTable tablaEmpleados, FrmConsultarEmpleado ventanaConsultarEmpleado) {
        Connection nuevaConexion = null;
        int fila = tablaEmpleados.getSelectedRow();
        System.out.println(fila);
        String CEDULA = tablaEmpleados.getValueAt(fila, 0).toString();

        try {
            nuevaConexion = ConexionDB.conectar();
            String sentencia_buscar = ("SELECT * FROM ADMIN_COBROS.EMPLEADO WHERE CEDULA=" + CEDULA);
            sentencia_preparada = nuevaConexion.prepareStatement(sentencia_buscar);
            resultado = sentencia_preparada.executeQuery();
            if (resultado.next()) {
                String ID_EMPLEADO = resultado.getString("ID_EMPLEADO");
                String NOMBRE = resultado.getString("NOMBRE");
                String CORREO = resultado.getString("CORREO");
                String TELEFONO = resultado.getString("TELEFONO");
                String DIRECCION = resultado.getString("DIRECCION");
                Date FECHA_NAC = resultado.getDate("FECHA_NAC");
                String ID_TRABAJO = resultado.getString("ID_TRABAJO");
                

                ventanaConsultarEmpleado.getLblCedula().setText(CEDULA);
                ventanaConsultarEmpleado.getLblCorreo().setText(CORREO);
                ventanaConsultarEmpleado.getLblDireccion().setText(DIRECCION);
                ventanaConsultarEmpleado.getLblNombre().setText(NOMBRE);
                ventanaConsultarEmpleado.getLblTelefono().setText(TELEFONO);
            }
            nuevaConexion.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void LimpiarFormulario(JTable tablaEmpleados) {
        DefaultTableModel tb = (DefaultTableModel) tablaEmpleados.getModel();
        tb.getDataVector().removeAllElements();
        tb.fireTableDataChanged();
        System.out.println("Si funciono");
    }
    //////////////////////////////////////////////////////////////////////////////////////

    public Empleado obtenerEmpleado(String cedula) {
        empleado = new Empleado();
        try {
            nuevaConexion = ConexionDB.conectar();
            statement = nuevaConexion.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ADMIN_COBROS.EMPLEADO WHERE CEDULA=" + cedula);
            while (resultSet.next()) {
                empleado.setIdEmpleado(resultSet.getString("ID_EMPLEADO"));
                empleado.setCedula(cedula);
                empleado.setNombre(resultSet.getString("NOMBRE"));
                empleado.setCorreo(resultSet.getString("CORREO"));
                empleado.setContrasenia(resultSet.getString("CONTRASENIA"));
                empleado.setTelefono(resultSet.getString("TELEFONO"));
                empleado.setDireccion(resultSet.getString("DIRECCION"));
                empleado.setFechaNacimiento(resultSet.getDate("FECHA_NAC"));
                empleado.setIdTrabajo(resultSet.getString("ID_TRABAJO"));
            }
            statement.close();
            nuevaConexion.close();
            resultSet.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Empleado no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return empleado;

    }
    public int obternerIdMaximo(){
        String ID_EMPLEADO ="0";
        try {
            nuevaConexion = ConexionDB.conectar();
            String sentencia_buscar = ("SELECT max(ID_EMPLEADO) FROM ADMIN_COBROS.EMPLEADO");
            sentencia_preparada = nuevaConexion.prepareStatement(sentencia_buscar);
            resultado = sentencia_preparada.executeQuery();
            if (resultado.next()) {
                ID_EMPLEADO = resultado.getString("MAX(ID_EMPLEADO)");
            }
            nuevaConexion.close();
            
        } catch (Exception e) {
            System.out.println(e);
        }
        return Integer.parseInt(ID_EMPLEADO);
    }
    public String verificarSiExisteEmpleado(String cedula){
        try {
            nuevaConexion= ConexionDB.conectar();
            statement = nuevaConexion.createStatement();
            resultSet = statement.executeQuery("SELECT CEDULA FROM ADMIN_COBROS.EMPLEADO WHERE CEDULA="+ cedula);
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
    
    public String obtenerNombreRol(int id_rol){
        String nombreRol="";
        try {
            nuevaConexion= ConexionDB.conectar();
            statement = nuevaConexion.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT NOMBRE FROM ADMIN_COBROS.TRABAJO WHERE ID_TRABAJO="
                    + id_rol);
            while (resultSet.next()) {
                nombreRol=resultSet.getString("NOMBRE");
            }  
            statement.close();
            nuevaConexion.close();
            resultSet.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return nombreRol;
    }

}
