package facturacion;

import facturacion.controladores.LoginController;


/**
 *
 * @author F996
 */
public class Arranque {
    public static void main(String[] args) {
        VistaLogin vistaLogin = new VistaLogin();
        LoginModel modeloLogin = new LoginModel(vistaLogin);
        LoginController controladro = new LoginController(vistaLogin, modeloLogin);
        vistaLogin.setLocationRelativeTo(null);
        vistaLogin.setVisible(true);
    }
}
