
package controlador.ListenerGestionAdministrador;


import Vista.FrmAdministrador;
import Vista.FrmListarEmpleado;
import Vista.PnlEstadoSolicitud;
import Vista.PnlIngresoSolicitudes;
import Vista.PnlListarSolicitud;
import Vista.PnlRegistroEmpleado;
import controlador.EmpleadoDB;
import controlador.ListenerSolicitudes.ListenerPnlIngresoSolicitudes;
import controlador.ListenerSolicitudes.ListenerPnlListarSolicitud;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;


public class ListenerFrmAdministrador implements ActionListener {
    
    private FrmAdministrador frmAdministrador;
    private PnlRegistroEmpleado pnlRegistroEmpleado;
    private ListenerRegistroEmpleado listenerRegistroEmpleado;
    private FrmListarEmpleado frmListarEmpleado;
    private ListenerListarEmpleado controlListarEmpleado;
    
    private ListenerPnlIngresoSolicitudes controlIngresarSolicitudes;
    private PnlIngresoSolicitudes pnlIngresoSolicitudes;
    private ListenerPnlListarSolicitud controlListarSolicitudes;
    private PnlListarSolicitud pnlListarSolicitudes;
    private PnlEstadoSolicitud pnlEstadoSolicitud;
    
    EmpleadoDB empdb = new EmpleadoDB();

    
    public ListenerFrmAdministrador(){}

    public ListenerFrmAdministrador(FrmAdministrador oFrmAdministrador) {
        this.frmAdministrador = oFrmAdministrador;
        pnlRegistroEmpleado= new PnlRegistroEmpleado();
        listenerRegistroEmpleado = new ListenerRegistroEmpleado(pnlRegistroEmpleado);
        frmListarEmpleado = new FrmListarEmpleado();
        controlListarEmpleado = new ListenerListarEmpleado(frmListarEmpleado);
        
        pnlIngresoSolicitudes = new PnlIngresoSolicitudes();
        controlIngresarSolicitudes = new ListenerPnlIngresoSolicitudes(pnlIngresoSolicitudes);
        pnlListarSolicitudes = new PnlListarSolicitud();
        controlListarSolicitudes = new ListenerPnlListarSolicitud(pnlListarSolicitudes);
        pnlEstadoSolicitud = new PnlEstadoSolicitud();
        
        addListeners();
    }   
    
    private void addListeners(){
        frmAdministrador.getMniRegistrarEmpleado().addActionListener(this);
        frmAdministrador.getMniListarEmpleado().addActionListener(this);
        
        frmAdministrador.getMniIngresoSolicitudes().addActionListener(this);
        frmAdministrador.getMniListarSolicitudes().addActionListener(this);
        frmAdministrador.getMniEstadoSolicitudes().addActionListener(this);
    }
    
    private void mostrarPanel(JPanel panel){
        panel.setSize(853,363);
        panel.setLocation(0,0);
        frmAdministrador.getPanelAdmin().removeAll();
        frmAdministrador.getPanelAdmin().add(panel, BorderLayout.CENTER);
        frmAdministrador.getPanelAdmin().revalidate();
        frmAdministrador.getPanelAdmin().repaint();
     }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == frmAdministrador.getMniRegistrarEmpleado()){
            mostrarPanel(pnlRegistroEmpleado);
        }
        
        else if(e.getSource() == frmAdministrador.getMniListarEmpleado()){ 
            empdb.LimpiarFormulario(frmListarEmpleado.getTablaEmpleados());
            controlListarEmpleado.mostrarDatosTabla();
            mostrarPanel(frmListarEmpleado);  
        }
        
        else if(e.getSource() == frmAdministrador.getMniIngresoSolicitudes()){
            mostrarPanel(pnlIngresoSolicitudes);
        }
        
        else if(e.getSource() == frmAdministrador.getMniListarSolicitudes()){
            mostrarPanel(pnlListarSolicitudes);
        }
        
        else if(e.getSource() == frmAdministrador.getMniEstadoSolicitudes()){
            mostrarPanel(pnlEstadoSolicitud);
        }
    }
}

