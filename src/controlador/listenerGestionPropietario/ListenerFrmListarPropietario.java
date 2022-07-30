package controlador.listenerGestionPropietario;

import Vista.FrmConsultarPropietario;
import Vista.FrmEditarPropietario;
import Vista.FrmListarPropietario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Esta clase agraga los listener a los botones del formulario listar propietario
 *  haciendo uso de la interfaz ActionListener .
 *
 * @param cs {@link ActionListener } clase que permite agregar a los
 * escuchadores a los botones.
 * @author Grupo E
 */
public class ListenerFrmListarPropietario implements ActionListener {

    private final FrmListarPropietario frmListarPropietario;

    public ListenerFrmListarPropietario(FrmListarPropietario frmListarPropietario) {
        this.frmListarPropietario = frmListarPropietario;
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
        if (e.getSource() == frmListarPropietario.getBtnConsultar()) {
            FrmConsultarPropietario frmConsultarPropietario = new FrmConsultarPropietario();
            frmListarPropietario.validarCedulaCosultar(frmConsultarPropietario, frmListarPropietario.obtenerCedula());
        } else if (e.getSource() == frmListarPropietario.getBtnModificar()) {
            FrmEditarPropietario frmEditarPropietario = new FrmEditarPropietario();
            frmListarPropietario.validarCedulaModificar(frmEditarPropietario, frmListarPropietario.obtenerCedula());
        } else if (e.getSource() == frmListarPropietario.getBtnRefrescar()) {
            frmListarPropietario.LimpiarFormulario();
            frmListarPropietario.llenarTablaPropietario();
        } else if (e.getSource() == frmListarPropietario.getBtnBuscar()) {
            frmListarPropietario.Filtrar();
        }
    }

}
