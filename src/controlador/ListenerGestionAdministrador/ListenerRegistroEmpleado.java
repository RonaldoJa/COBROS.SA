
package controlador.ListenerGestionAdministrador;


import Vista.PnlRegistroEmpleado;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import modelo.Empleado;
import controlador.EmpleadoDB;


public class ListenerRegistroEmpleado extends KeyAdapter implements ActionListener{
    
    private PnlRegistroEmpleado ventanaRegistroEmpleado;
    private Empleado empleado;
    private ArrayList<Empleado> arrayEmpleado;
    private EmpleadoDB empleadoDB;
    static int generadorEmpleado = 1;
    static int generadorAdministrador = 1;
 
    /**
     * Constructor vacio para el control de la ventana RegistroEmpleado.java
     */
    public ListenerRegistroEmpleado(){}

    /**
     * Constructor para el control de la ventana RegistroEmpleado.java
     *
     * @param ventanaRegistroEmpleado El parámetro ventanaRegistroEmpleado recibe el frame a
     * controlar
     */
    public ListenerRegistroEmpleado(PnlRegistroEmpleado ventanaRegistroEmpleado) {
        this.ventanaRegistroEmpleado = ventanaRegistroEmpleado;
        arrayEmpleado = new ArrayList();
        empleadoDB = new EmpleadoDB();
        addActionListeners();
        addKeyListeners();
    }

    /**
     * Metodo que asigna los ActionListeners correspondientes
     */
    private void addActionListeners(){ 
        ventanaRegistroEmpleado.getBtnRegistrar().addActionListener(this);
        ventanaRegistroEmpleado.getBtnLimpiarRegistro().addActionListener(this);
        ventanaRegistroEmpleado.getBtnCancelar().addActionListener(this);
    }
    
    /**
     * Metodo que asigna los KeyListeners correspondientes
     */
    private void addKeyListeners(){
        ventanaRegistroEmpleado.getTxtCedula().addKeyListener(this);
        ventanaRegistroEmpleado.getTxtTelefono().addKeyListener(this);        
    }
    
    /**
     * Metodo que crea un objeto empleado obteniendo los datos de los campos 
     * textfield de la clase RegistrooEmpleado.java 
     */
    private void crearEmpleado(){
        String idEmpleado = generarIdEmpleado();
        String nombre  = ventanaRegistroEmpleado.getTxtNombre().getText();
        //String idAdministrador = generarIdAdministrador();
        String cedula = ventanaRegistroEmpleado.getTxtCedula().getText();
        //String id_Trabajo = String.valueOf(ventanaRegistroEmpleado.getCmbIdTrabajo().getSelectedItem());
       
        String direccion  = ventanaRegistroEmpleado.getTxtDireccion().getText();  
        String correo    = ventanaRegistroEmpleado.getTxtCorreo().getText();
        Date fechaNacimiento = new java.sql.Date(((Date) ventanaRegistroEmpleado.getDtcFechaNacimiento().getDate()).getTime());
        String  telefono = ventanaRegistroEmpleado.getTxtTelefono().getText();
        String contrasenia= new String(ventanaRegistroEmpleado.getjPasswordField().getPassword());
        String idTrabajo = (ventanaRegistroEmpleado.getCmbRolEmpleado().getSelectedIndex()+1)+"";
        empleado = new Empleado(idEmpleado, nombre, direccion,correo,telefono, 
            fechaNacimiento,cedula,contrasenia,idTrabajo );
        addArrayListEmpleado(empleado);
        empleadoDB.guardar(empleado);
    }
    
    /**
     * Metodo que guarda un objeto de tipo Empleado a un arraylist
     */
    private void addArrayListEmpleado(Empleado empleado){       
        arrayEmpleado.add(empleado);
         limpiarCampos();
    }
    
     /**
     * Metodo que genera un IdEmpleado
     */
    private String generarIdEmpleado(){
        System.out.println("IdEmpleado" +empleadoDB.obternerIdMaximo());
        return String.valueOf(empleadoDB.obternerIdMaximo()+1);  
    }
    
    /**
     * Metodo que genera un IdAdministrador
     */
    private String generarIdAdministrador(){
        if( ventanaRegistroEmpleado.getCmbRolEmpleado().getSelectedIndex() == 1){
            return String.valueOf(generadorAdministrador++);
        }
        else{
            return null;
        }
    }
    
    /**
     * Metodo que limpia los campos jTextField
     */
    private void limpiarCampos(){
        ventanaRegistroEmpleado.getTxtNombre().setText("");
        ventanaRegistroEmpleado.getTxtCedula().setText("");
        ventanaRegistroEmpleado.getTxtDireccion().setText("");
        ventanaRegistroEmpleado.getTxtTelefono().setText("");
        ventanaRegistroEmpleado.getTxtCorreo().setText("");
        ventanaRegistroEmpleado.getjPasswordField().setText("");
        ventanaRegistroEmpleado.getDtcFechaNacimiento().setDate(Date.from(Instant.now()));
    }
    
    private void ingresoSoloNumeros(char validar, KeyEvent e){
        if(validar >= 32 && validar <= 45 || validar == 47 || validar >= 58 && validar <= 8482) {
                e.consume();
            }
    }

    /**
     * Metodo que retorna el arraylist de tipo empleado
     */
    public ArrayList<Empleado> getArrayEmpleado() {
        return arrayEmpleado;
    }
    
    /**
     * Metodo que escucha los eventos segun los listeners asignados
     * @param e evento de tipo ActionListener recibido de un componente
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == ventanaRegistroEmpleado.getBtnRegistrar()) {
            
            crearEmpleado();
        }
        
        else if (e.getSource() == ventanaRegistroEmpleado.getBtnLimpiarRegistro()) {
            
            limpiarCampos();
        }
        
        else if (e.getSource() == ventanaRegistroEmpleado.getBtnCancelar()) {
            System.out.println("Volviendo ventana atras");
        }
    }
    
    /**
     * Metodo que escucha los eventos segun los listeners asignados
     * @param e evento de tipo KeyListener recibido de un componente
     */
    @Override
    public void keyTyped(KeyEvent e){
        if(e.getSource() == ventanaRegistroEmpleado.getTxtCedula()) {
            char validar = e.getKeyChar();	
            ingresoSoloNumeros(validar, e);
        }
        
        if(e.getSource() == ventanaRegistroEmpleado.getTxtTelefono()){
            char validad = e.getKeyChar();
            ingresoSoloNumeros(validad, e);
        }
    }

    @Override
    public String toString() {
        return "CtrRegistroEmpleado{" + "ventanaRegistroEmpleado=" + ventanaRegistroEmpleado + ", empleado=" + empleado + ", arrayEmpleado=" + arrayEmpleado + '}';
    }
}