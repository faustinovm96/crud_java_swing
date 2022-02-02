package sistema;

import sistema.views.model.LoginModel;
import sistema.views.LoginView;
import sistema.controllers.LoginController;


/**
 *
 * @author F996
 */
public class MainApp {
    public static void main(String[] args) {
        LoginView vistaLogin = new LoginView();
        LoginModel modeloLogin = new LoginModel(vistaLogin);
        LoginController controlador = new LoginController(vistaLogin, modeloLogin);
        controlador.iniciarAplicacion();
    }
}
