package sistema.controllers;


import sistema.views.model.LoginModel;
import sistema.views.LoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author Faustino
 */
public class LoginController /*implements ActionListener*/{
    
    private LoginView vistaLogin;
    private LoginModel modelo;

    public LoginController(LoginView vista, LoginModel modelo) {
        this.vistaLogin = vista;
        this.modelo = modelo;
        vista.btnIniciarSesion.addActionListener(e-> iniciarSesion(e)); //Implementación por medio de lambdas
    }
/*
    @Override
    public void actionPerformed(ActionEvent e) {
        Object p = e.getSource();
        
        if(p.equals(vistaLogin.btnIniciarSesion)){
            modelo.setUsuario(vistaLogin.txtUsuario1.getText());
            modelo.setClave(vistaLogin.txtPassword.getText());
            modelo.acceder();
        }
    }*/
    
    public void iniciarAplicacion(){
        this.vistaLogin.setVisible(true);
        this.vistaLogin.setLocationRelativeTo(null);
    }

    private void iniciarSesion(ActionEvent e) {
        Object p = e.getSource();
        if(p.equals(vistaLogin.btnIniciarSesion)){
            modelo.setUsuario(vistaLogin.txtUsuario1.getText());
            modelo.setClave(vistaLogin.txtPassword.getText());
            modelo.acceder();
        }
    }
    
    
}
