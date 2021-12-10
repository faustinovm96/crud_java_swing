package facturacion;

import facturacion.controladores.LoginControlador;


/**
 *
 * @author F996
 */
public class Arranque {
    public static void main(String[] args) {
        VistaLogin vistaLogin = new VistaLogin();
        ModeloLogin modeloLogin = new ModeloLogin(vistaLogin);
        LoginControlador controladro = new LoginControlador(vistaLogin, modeloLogin);
        vistaLogin.setLocationRelativeTo(null);
        vistaLogin.setVisible(true);
    }
}
