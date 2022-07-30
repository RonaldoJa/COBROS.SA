package controlador.listenerGestionPropietario;

import Vista.FrmConsultarPropietario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Esta clase agraga los listener a los botones del formulario consultar
 * propietario haciendo uso de la interfaz ActionListener .
 *
 * @param cs {@link ActionListener } clase que permite agregar a los
 * escuchadores a los botones.
 * @author Grupo E
 */
public class ListenerFrmConsultarPropietario implements ActionListener {

    private FrmConsultarPropietario frmConsultarPropietario;

    public ListenerFrmConsultarPropietario(FrmConsultarPropietario frmConsultarPropietario) {
        this.frmConsultarPropietario = frmConsultarPropietario;
    }

    /**
     * Este metodo recibe el evento del boton presionado y lo compara para 
     * tomar un desicion.
     * 
     * @param cs {@link ActionEvent } captura el evento que se causo al 
     * presionar un boton y ejecuta la acciones definidas.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frmConsultarPropietario.getBtnSalir()) {
            frmConsultarPropietario.dispose();
        }
    }

}
