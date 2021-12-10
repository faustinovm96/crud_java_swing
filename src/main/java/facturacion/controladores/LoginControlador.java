package facturacion.controladores;


import facturacion.ModeloLogin;
import facturacion.VistaLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Faustino
 */
public class LoginControlador implements ActionListener{
    VistaLogin vistaLogin;
    ModeloLogin modelo;

    public LoginControlador(VistaLogin vista, ModeloLogin modelo) {
        this.vistaLogin = vista;
        this.modelo = modelo;
        vista.btnIniciarSesion.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object p = e.getSource();
        
        if(p.equals(vistaLogin.btnIniciarSesion)){
            modelo.setUsuario(vistaLogin.txtUsuario1.getText());
            modelo.setClave(vistaLogin.txtPassword.getText());
            modelo.acceder();
        }
    }
    
    
}
