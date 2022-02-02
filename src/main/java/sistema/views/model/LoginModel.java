package sistema.views.model;

import sistema.views.MenuPrincipalView;
import sistema.views.LoginView;
import sistema.controllers.MenuController;
import javax.swing.JOptionPane;
import sistema.modelo.dao.IUsuarioDao;
import sistema.modelo.dao.IUsuarioDao;
import sistema.modelo.dao.UsuarioDaoJDBC;
import sistema.modelo.dao.UsuarioDaoJDBC;
import sistema.modelo.domain.Usuario;
import sistema.modelo.domain.Usuario;

/**
 *
 * @author Faustino
 */
public class LoginModel {

    private String usuario;
    private String clave;

    IUsuarioDao usuarioDao = new UsuarioDaoJDBC();

    LoginView vistaLogin;

    public LoginModel(LoginView vistaLogin) {
        this.vistaLogin = vistaLogin;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void acceder() {

        Usuario usuario1 = usuarioDao.login(usuario, clave);

        if (usuario1 == null) {
            JOptionPane.showMessageDialog(vistaLogin, "DATOS INCORRECTOS");
        } else if (usuario1.getEstado().equals("A")) {
            MenuPrincipalView vistaMenu = new MenuPrincipalView();
            MenuModel modeloMenu = new MenuModel(vistaMenu);
            MenuController c = new MenuController(vistaMenu, modeloMenu);
            vistaMenu.txtIdUsuario.setText(usuario1.getIdUsuario().toString());
            vistaMenu.txtUsuario.setText(usuario1.getUsuario());
            modeloMenu.controlPrivilegios();
            vistaMenu.setLocationRelativeTo(null);

            vistaMenu.setVisible(true);
            vistaLogin.dispose();
        }

    }

}
