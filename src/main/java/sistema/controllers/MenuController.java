package sistema.controllers;


import sistema.FacturaController;
import sistema.FacturaModel;
import sistema.views.model.MenuModel;
import sistema.views.FacturaView;
import sistema.views.MenuPrincipalView;
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
public class MenuController implements ActionListener {
    MenuPrincipalView vista;
    MenuModel modelo;

    public MenuController(MenuPrincipalView vista, MenuModel modelo) {
        this.vista = vista;
        this.modelo = modelo;
        vista.menuFacturacion.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object p = e.getSource();
        
        if (p.equals(vista.menuFacturacion)) {
            FacturaView vistaFactura = new FacturaView();
            FacturaModel modeloFactura = new FacturaModel(vistaFactura);
            FacturaController controladorFactura = new FacturaController(vistaFactura, modeloFactura);
            modeloFactura.inicio();
            vistaFactura.txtIdUsuario.setText(vista.txtIdUsuario.getText());
            vistaFactura.txtUsuario.setText(vista.txtUsuario.getText());
            vistaFactura.setLocationRelativeTo(null);
            vistaFactura.setVisible(true);            
        }
    }
        
}
