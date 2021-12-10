package facturacion.controladores;


import facturacion.ControladorFactura;
import facturacion.ModeloFactura;
import facturacion.ModeloMenu;
import facturacion.VistaFactura;
import facturacion.VistaMenuPrincipal;
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
public class MenuControlador implements ActionListener {
    VistaMenuPrincipal vista;
    ModeloMenu modelo;

    public MenuControlador(VistaMenuPrincipal vista, ModeloMenu modelo) {
        this.vista = vista;
        this.modelo = modelo;
        vista.menuFacturacion.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object p = e.getSource();
        
        if (p.equals(vista.menuFacturacion)) {
            VistaFactura vistaFactura = new VistaFactura();
            ModeloFactura modeloFactura = new ModeloFactura(vistaFactura);
            ControladorFactura controladorFactura = new ControladorFactura(vistaFactura, modeloFactura);
            modeloFactura.inicio();
            vistaFactura.txtIdUsuario.setText(vista.txtIdUsuario.getText());
            vistaFactura.txtUsuario.setText(vista.txtUsuario.getText());
            vistaFactura.setLocationRelativeTo(null);
            vistaFactura.setVisible(true);            
        }
    }
        
}
