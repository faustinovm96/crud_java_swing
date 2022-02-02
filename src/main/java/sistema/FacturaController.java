package sistema;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import sistema.views.FacturaView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;

/**
 *
 * @author F996
 */
public class FacturaController implements ActionListener, KeyListener{
    
    public FacturaView vista;
    public FacturaModel modelo;

    public FacturaController(FacturaView vista, FacturaModel modelo) {
        this.vista = vista;
        this.modelo = modelo;
        vista.btnNuevo.addActionListener(this);
        vista.btnBuscarCliente.addActionListener(this);
        vista.txtFiltroCliente.addKeyListener(this);
        vista.txtCodigoProducto.addActionListener(this);
        vista.btnAceptarBuscarCliente.addActionListener(this);
        vista.txtFiltroProducto.addKeyListener(this);
        vista.btnAceptarBuscarProducto.addActionListener(this);
        vista.btnAgregarProducto.addActionListener(this);
        vista.btnBorrarProducto.addActionListener(this);
        vista.txtFechaFactura.addActionListener(this);
        vista.contadoRadio.addActionListener(this);
        vista.contadoRadio.addKeyListener(this);
        vista.creditoRadio.addActionListener(this);
        vista.txtCantidadProducto.addActionListener(this);
        vista.txtNumeroFactura.addActionListener(this);
        vista.btnGuardar.addActionListener(this);
        vista.btnImprimir.addActionListener(this);
        vista.btnBuscar.addActionListener(this);
        vista.txtFechaFin.addActionListener(this);
        vista.btnAceptarBuscarFactura.addActionListener(this);
        vista.btnAnular.addActionListener(this);
        vista.btnCancelar.addActionListener(this);
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        Object p = e.getSource();
       
        if (p.equals(vista.btnNuevo)) {
            modelo.bloquearObjetos(true);
            modelo.activarBotones(false, true, false, false, false);
            modelo.generarCodigo();
        }
        
        if(p.equals(vista.txtNumeroFactura)){
            if(vista.txtNumeroFactura.getText().equals("")){
                JOptionPane.showMessageDialog(vista, "Completar Numero de Factura");
                vista.txtNumeroFactura.requestFocus();
                return;
            }
            vista.txtFechaFactura.requestFocus();
        }
        
        if(p.equals(vista.btnBuscarCliente)){
            modelo.llamarBuscadorCliente();
            vista.txtFiltroCliente.requestFocus();
            modelo.cargarTablaClientes("");
        }
        
        if(p.equals(vista.txtCodigoProducto)){
            if (vista.txtCodigoProducto.getText().trim().length() == 0) {
                modelo.llamarBuscadorProductosDialogo();
                modelo.cargarTablaProductos("");
            }else {
                modelo.buscarProductoPorCodigo(vista.txtCodigoProducto.getText());
            }
            
        }
        
        if (p.equals(vista.btnAceptarBuscarCliente)) {
            modelo.transferirCliente();
        }
        
        if(p.equals(vista.btnAceptarBuscarProducto)){
            modelo.transferirProducto();
        } 
        
        if (p.equals(vista.btnAgregarProducto)) {
            modelo.agregarProductoDetalle(vista.txtCodigoProducto.getText());
            modelo.totales();
        }
        
        if(p.equals(vista.btnBorrarProducto)){
            modelo.eliminarProducto();
            modelo.totales();
        }
        
        if(p.equals(vista.txtFechaFactura)){
            vista.contadoRadio.requestFocus();
        }
        
        if(p.equals(vista.contadoRadio)){
            vista.creditoRadio.requestFocus();
        }
        
        if(p.equals(vista.creditoRadio)){
            vista.btnBuscarCliente.requestFocus();
        }
        
        if(p.equals(vista.txtCantidadProducto)){
            if(vista.txtCantidadProducto.getText().equals("")){
                JOptionPane.showMessageDialog(vista, "ERROR");
                vista.txtCantidadProducto.requestFocus();
                return;
            }
            vista.btnAgregarProducto.requestFocus();
        }
        
        if(p.equals(vista.txtNumeroFactura)){
            vista.txtFechaFactura.requestFocus();
        }
        
        if(p.equals(vista.btnGuardar)){
            modelo.setId(vista.txtCodigoFactura.getText());
            modelo.setCodigoCliente(vista.codigoClienteTxt.getText());
            modelo.setUsuario(vista.txtIdUsuario.getText());
            
            String condicion;
            
            if(vista.contadoRadio.isSelected() == true){
                condicion = "CONTADO";
            }else{
                condicion = "CREDITO";
            }
            
            modelo.setCondicion(condicion);
            modelo.setFecha(vista.txtFechaFactura.getText());
            modelo.setNumero(vista.txtNumeroFactura.getText());
            modelo.setEstado(vista.txtEstado.getText());
            
            modelo.guardarFactura();
            modelo.guardarDetalleFactura();
            modelo.diminuirStock();
            modelo.vista.btnImprimir.setEnabled(true);
        }
        
        if(p.equals(vista.btnImprimir)){
            modelo.llamarInforme();
        }
        
        if(p.equals(vista.btnBuscar)){
            modelo.llamarBuscadorFactura();
        }
        
        if(p.equals(vista.txtFechaFin)){
            modelo.cargarFacturas();
        }
        
        if(p.equals(vista.btnAceptarBuscarFactura)){
            //modelo.setId(vista.txtCodigoFactura.getText());
            modelo.transferirFactura();
            //vista.buscadorFacturas.setVisible(false);  
        }
        
        if(p.equals(vista.btnAnular)){
            modelo.anularFactura();
        }
        
        if(p.equals(vista.btnCancelar)){
            modelo.cancelarFactura();
        }
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        Object p = e.getSource();
        
        if(p.equals(vista.contadoRadio)){
            vista.btnBuscarCliente.requestFocus();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Object p = e.getSource();
        if (p.equals(vista.txtFiltroCliente)) {
            modelo.cargarTablaClientes(vista.txtFiltroCliente.getText());
        }
        
        if (p.equals(vista.txtFiltroProducto)) {
            modelo.cargarTablaProductos(vista.txtFiltroProducto.getText());
        }
    }
    
}
