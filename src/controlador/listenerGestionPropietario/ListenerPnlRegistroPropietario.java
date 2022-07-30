package controlador.listenerGestionPropietario;

import Vista.PnlRegistroPropietario;
import controlador.PropietarioDB;
import controlador.ValidarCampos;
import controlador.VehiculoDB;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import javax.swing.JOptionPane;
import modelo.Propietario;

/**
 * Esta clase agraga los listener a los botones al panel  registrar
 * propietario haciendo uso de la interfaz ActionListener y extiende de la clase
 * KeyAdapter para agregar listener de tipeo a las cajas de texto.
 *
 * @param cs {@link ActionListener } clase que permite agregar a los
 * escuchadores a los botones.
 * @param cs {@link KeyAdapter } clase que implementa KeyListener.
 * @author Grupo E
 */
public class ListenerPnlRegistroPropietario extends KeyAdapter implements ActionListener {

    private final PnlRegistroPropietario pnlRegistroPropietario;
    private final PropietarioDB propietarioDB = new PropietarioDB();
    private final VehiculoDB vehiculoDB = new VehiculoDB();
    private final ValidarCampos validarCampos = new ValidarCampos();
    private  Propietario propietario;
    
    // Obtiene lel formulario que lo esta utilizando.
    public ListenerPnlRegistroPropietario(PnlRegistroPropietario pnlRegistroPropietario) {
        this.pnlRegistroPropietario = pnlRegistroPropietario;
    }

    /**
     * Este metodo recibe el evento del boton presionado y lo compara para tomar
     * un desicion.
     *
     * @param cs {@link ActionEvent } captura el evento que se causo al
     * presionar un boton y ejecuta la acciones definidas.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pnlRegistroPropietario.getBtnRegistrar()) {
            validarRegistro();
        } else if (e.getSource() == pnlRegistroPropietario.getBtnCancelar()) {
            pnlRegistroPropietario.limpiarCampos();
        }
    }

    /**
     * Este metodo recibe el evento de la caja de texto que esta siendo tipeado 
     * las compara para saber cual es y realiza las acciones predefinidas.
     *
     * @param cs {@link KeyEvent } captura el evento que se causo al
     * presionar un boton y ejecuta la acciones definidas.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == pnlRegistroPropietario.getTxtCedula()) {
            validarCampos.validarCedula(e, pnlRegistroPropietario.getTxtCedula());
            validarCampos.verificarSiExistePropietario(pnlRegistroPropietario.getTxtCedula());
        } else if (e.getSource() == pnlRegistroPropietario.getTxtNombre()) {
            validarCampos.validaLetras(e);
        } else if (e.getSource() == pnlRegistroPropietario.getTxtTelefono()) {
            validarCampos.validarTelefono(e, pnlRegistroPropietario.getTxtTelefono());
        } else if (e.getSource() == pnlRegistroPropietario.getTxtPlaca()) {
            validarCampos.verificarSiExistePlaca(pnlRegistroPropietario.getTxtPlaca());
        } else if (e.getSource() == pnlRegistroPropietario.getTxtAnioVehiculo()) {
            validarCampos.validarAnioVehiculo(e, pnlRegistroPropietario.getTxtAnioVehiculo());
        }
    }
    
    /*Este metodo verifica que la informacion ingreso corrcatmente*/
    public void registrarPropietario() {
        if (ingresarVehiculo() > 0 && ingresarPropietario() > 0) {
            JOptionPane.showMessageDialog(null, "Datos Guardados Correctamente");
            pnlRegistroPropietario.limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(null, "No se han podido guardar los datos");
        }
    }
    
    /*este metodo registra la informacion del vehiculo en la BD haciendo uso 
    del controlador VehiculoDB*/
    public int ingresarVehiculo() {
        return vehiculoDB.registrarVehiculo(
                pnlRegistroPropietario.getTxtPlaca().getText(),
                pnlRegistroPropietario.getTxtMarca().getText(),
                pnlRegistroPropietario.getTxtModelo().getText(),
                pnlRegistroPropietario.getTxtAnioVehiculo().getText(),
                pnlRegistroPropietario.getCmbTipoImpuesto().getSelectedIndex() + "");
    }
     /*este metodo registra la informacion del propietario en la BD haciendo uso 
    del controlador PropietarioDB*/
    public int ingresarPropietario() {
        propietario = new Propietario();
        propietario.setCedula(pnlRegistroPropietario.getTxtCedula().getText());
        propietario.setNombre(pnlRegistroPropietario.getTxtNombre().getText());
        propietario.setCorreo(pnlRegistroPropietario.getTxtCorreo().getText());
        propietario.setTelefono(pnlRegistroPropietario.getTxtTelefono().getText());
        propietario.setDireccion(pnlRegistroPropietario.getTxtDireccion().getText());
        propietario.setFechaNacimiento(
                new java.sql.Date(((Date) pnlRegistroPropietario.getDtcFechaNacimiento().getDate()).getTime()));
        propietario.setPlaca(pnlRegistroPropietario.getTxtPlaca().getText());
        propietario.setId_estado_propietario(pnlRegistroPropietario.getCmbEstadoPropietario().getSelectedIndex());
        return propietarioDB.registrarPropietario(propietario);
    }

    /*Valida que las cajas de texto no esten vacio y valida la edad*/
    public void validarRegistro() {
        if (validarCampos.validarCampoVacio(this.pnlRegistroPropietario) && validarEdadIngreso()) {
            registrarPropietario();
        }
    }
    /*Metodo que validad la edad haciendo uso del metodo validarEdad */
    public boolean validarEdadIngreso() {
        return validarCampos.validarEdad(pnlRegistroPropietario.getDtcFechaNacimiento().getDate());
    }
}
