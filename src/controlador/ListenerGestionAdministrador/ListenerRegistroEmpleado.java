
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
 
    public ListenerRegistroEmpleado(){}

    public ListenerRegistroEmpleado(PnlRegistroEmpleado ventanaRegistroEmpleado) {
        this.ventanaRegistroEmpleado = ventanaRegistroEmpleado;
        arrayEmpleado = new ArrayList();
        empleadoDB = new EmpleadoDB();
        addActionListeners();
        addKeyListeners();
    }


    private void addActionListeners(){ 
        ventanaRegistroEmpleado.getBtnRegistrar().addActionListener(this);
        ventanaRegistroEmpleado.getBtnLimpiarRegistro().addActionListener(this);
        ventanaRegistroEmpleado.getBtnCancelar().addActionListener(this);
    }
    
    private void addKeyListeners(){
        ventanaRegistroEmpleado.getTxtCedula().addKeyListener(this);
        ventanaRegistroEmpleado.getTxtTelefono().addKeyListener(this);        
    }
    
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
    
    private void addArrayListEmpleado(Empleado empleado){       
        arrayEmpleado.add(empleado);
         limpiarCampos();
    }
    
    private String generarIdEmpleado(){
        System.out.println("IdEmpleado" +empleadoDB.obternerIdMaximo());
        return String.valueOf(empleadoDB.obternerIdMaximo()+1);  
    }
    
    private String generarIdAdministrador(){
        if( ventanaRegistroEmpleado.getCmbRolEmpleado().getSelectedIndex() == 1){
            return String.valueOf(generadorAdministrador++);
        }
        else{
            return null;
        }
    }
    
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

    public ArrayList<Empleado> getArrayEmpleado() {
        return arrayEmpleado;
    }
    
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