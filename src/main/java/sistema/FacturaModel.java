package sistema;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import sistema.views.FacturaView;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author F996
 */
public class FacturaModel {

    private String id;
    private String codigoCliente;
    private String usuario;
    private String condicion;
    private String fecha;
    private String numero;
    private String estado;

    FacturaView vista;
    DefaultTableModel modeloTablaCliente;
    DefaultTableModel modeloTablaProductos;
    DefaultTableModel modeloTablaDetalleFactura;
    DefaultTableModel modeloTablaFacturas;

    Statement st;
    ResultSet rs;

    public FacturaModel(FacturaView vista) {
        this.vista = vista;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public FacturaView getVista() {
        return vista;
    }

    public void setVista(FacturaView vista) {
        this.vista = vista;
    }

    public void bloquearObjetos(boolean a) {
        vista.txtNumeroFactura.setEnabled(a);
        vista.contadoRadio.setEnabled(a);
        vista.creditoRadio.setEnabled(a);
        vista.txtCodigoProducto.setEnabled(a);
        vista.txtCantidadProducto.setEnabled(a);
        vista.btnBuscarCliente.setEnabled(a);
        vista.btnAgregarProducto.setEnabled(a);
        vista.btnBorrarProducto.setEnabled(a);
        vista.txtFechaFactura.setEnabled(a);
        vista.txtCodigoFactura.setEnabled(a);
    }
    
    public void limpiar() {
        vista.txtCodigoFactura.setText("");
        vista.txtNumeroFactura.setText("");
        vista.txtFechaFactura.setText("");
        vista.contadoRadio.setEnabled(true);
        vista.txtCodigoProducto.setText("");
        vista.txtCantidadProducto.setText("");
        vista.txtEstado.setText("PENDIENTE");
        vista.codigoClienteTxt.setText("");
        vista.nombreClienteTxt.setText("");
        vista.apellidoClienteTxt.setText("");
        vista.rucClienteTxt.setText("");
        vista.txtCodigoProducto.setText("");
        modeloTablaDetalleFactura.setRowCount(0);
        totales();
        
    }

    public void activarBotones(boolean n, boolean g, boolean i, boolean b, boolean a) {
        vista.btnNuevo.setEnabled(n);
        vista.btnGuardar.setEnabled(g);
        vista.btnImprimir.setEnabled(i);
        vista.btnBuscar.setEnabled(b);
        vista.btnAnular.setEnabled(a);
    }

    public void inicio() {
        bloquearObjetos(false);
        activarBotones(true, false, false, true, false);
        modeloTablaCliente = (DefaultTableModel) vista.tablaBuscarCliente.getModel();
        modeloTablaProductos = (DefaultTableModel) vista.tablaBuscarProductos.getModel();
        modeloTablaDetalleFactura = (DefaultTableModel) vista.tablaDetallesProductos.getModel();
        modeloTablaFacturas = (DefaultTableModel) vista.tablaBusquedaFacturas.getModel();
    }

    public void llamarBuscadorCliente() {
        vista.buscadorClientes.setSize(685, 441);
        vista.buscadorClientes.setLocationRelativeTo(vista);
        vista.buscadorClientes.setVisible(true);
    }

    public void cargarTablaClientes(String filtro) {
        //Olvidaste agregar el porcentaje para indicar ue era un patron idiota
        String sql = "SELECT * FROM clientes WHERE apellidos LIKE '" + filtro + "%'";
        try {
            modeloTablaCliente.setRowCount(0);
            st = Conexion.stmt(st);
            rs = st.executeQuery(sql);

            System.out.println("REsultado:" + rs);

            while (rs.next()) {
                modeloTablaCliente.addRow(new Object[]{
                    rs.getString("id_cliente"),
                    rs.getString("ruc"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getString("direccion"),
                    rs.getString("email")
                });
                //System.out.println(modeloTablaCliente.getColumnCount());
            }

            vista.tablaBuscarCliente.setModel(modeloTablaCliente);
            st.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("ERROR");
            Logger.getLogger(FacturaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buscarProductoPorCodigo(String cod) {
        String sql = "SELECT * FROM productos WHERE id_producto = '" + cod + "'";

        try {
            st = Conexion.stmt(st);
            rs = st.executeQuery(sql);
            rs.next();

            //Que no retorno nada
            if (rs.getRow() == 0) {
                vista.nombreProductoLabel.setText("Producto No Encontrado");
            } else {
                vista.nombreProductoLabel.setText(rs.getString("nombre"));
                vista.txtCantidadProducto.requestFocus();
            }

            st.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(FacturaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void transferirCliente() {
        Integer fila = vista.tablaBuscarCliente.getSelectedRow();
        vista.codigoClienteTxt.setText(vista.tablaBuscarCliente.getValueAt(fila, 0).toString());
        vista.nombreClienteTxt.setText(vista.tablaBuscarCliente.getValueAt(fila, 1).toString());
        vista.apellidoClienteTxt.setText(vista.tablaBuscarCliente.getValueAt(fila, 2).toString());
        vista.rucClienteTxt.setText(vista.tablaBuscarCliente.getValueAt(fila, 3).toString());

        vista.buscadorClientes.setVisible(false);
        vista.txtCodigoProducto.requestFocus();
    }

    public void llamarBuscadorProductosDialogo() {
        vista.buscadorProductos.setSize(780, 390);
        vista.buscadorProductos.setLocationRelativeTo(vista);
        vista.buscadorProductos.setVisible(true);
        vista.txtFiltroProducto.requestFocus();
    }

    public void cargarTablaProductos(String filtro) {
        String sql = "SELECT * FROM productos WHERE nombre like '" + filtro + "%'";

        try {
            modeloTablaProductos.setRowCount(0);
            st = Conexion.stmt(st);
            rs = st.executeQuery(sql);

            while (rs.next()) {
                modeloTablaProductos.addRow(new Object[]{
                    rs.getString("id_producto"),
                    rs.getString("nombre"),
                    rs.getString("precio"),
                    rs.getString("cantidad")
                });
            }

            vista.tablaBuscarProductos.setModel(modeloTablaProductos);
            rs.close();
            st.close();

        } catch (SQLException ex) {
            Logger.getLogger(FacturaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void transferirProducto() {
        Integer fila = vista.tablaBuscarProductos.getSelectedRow();

        vista.txtCodigoProducto.setText(vista.tablaBuscarProductos.getValueAt(fila, 0).toString());
        vista.nombreProductoLabel.setText(vista.tablaBuscarProductos.getValueAt(fila, 1).toString());
        vista.buscadorProductos.setVisible(false);
        vista.txtCantidadProducto.requestFocus();
    }

    public void agregarProductoDetalle(String codigo) {
        String sql = "SELECT * FROM productos WHERE id_producto = '" + codigo + "'";
        try {
            st = Conexion.stmt(st);
            rs = st.executeQuery(sql);
            rs.next();

            if (rs.getRow() == 0) {
                JOptionPane.showMessageDialog(null, "Producto No Encontrado");
            } else {
                String iva = rs.getString("iva");
                if (iva.equals("10")) {
                    modeloTablaDetalleFactura.addRow(new Object[]{
                        rs.getString("id_producto"),
                        rs.getString("nombre"),
                        rs.getString("precio"),
                        vista.txtCantidadProducto.getText(),
                        "",
                        "",
                        Integer.parseInt(vista.txtCantidadProducto.getText()) * Integer.parseInt(rs.getString("precio"))
                    });
                } else if (iva.equals("5")) {
                    modeloTablaDetalleFactura.addRow(new Object[]{
                        rs.getString("id_producto"),
                        rs.getString("nombre"),
                        rs.getString("precio"),
                        vista.txtCantidadProducto.getText(),
                        "",
                        Integer.parseInt(vista.txtCantidadProducto.getText()) * Integer.parseInt(rs.getString("precio")),
                        ""
                    });
                } else if (iva.equals("EXENTA")) {
                    modeloTablaDetalleFactura.addRow(new Object[]{
                        rs.getString("id_producto"),
                        rs.getString("nombre"),
                        rs.getString("precio"),
                        vista.txtCantidadProducto.getText(),
                        Integer.parseInt(vista.txtCantidadProducto.getText()) * Integer.parseInt(rs.getString("precio")),
                        "",
                        ""
                    });
                }
            }
            st.close();
            rs.close();
            vista.tablaDetallesProductos.setModel(modeloTablaDetalleFactura);
        } catch (SQLException ex) {
            Logger.getLogger(FacturaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void eliminarProducto(){
        int fila = vista.tablaDetallesProductos.getSelectedRow();
        
        if(fila == -1){
            JOptionPane.showMessageDialog(null, "Seleccione un producto por favor");
        }else{
            modeloTablaDetalleFactura.removeRow(fila);
        }
    }
    
    public void totales(){
        int cantidadFilas = vista.tablaDetallesProductos.getRowCount();
        int sumaExenta = 0, suma5 = 0, suma10 = 0;
        
        for(int i = 0; i < cantidadFilas; i++){
            if(!vista.tablaDetallesProductos.getValueAt(i, 4).equals("")){
                sumaExenta = sumaExenta + Integer.parseInt(vista.tablaDetallesProductos.getValueAt(i, 4).toString());
            }
            
            if(!vista.tablaDetallesProductos.getValueAt(i, 5).equals("")){
                suma5 = suma5 + Integer.parseInt(vista.tablaDetallesProductos.getValueAt(i, 5).toString());
            }
            
            if(!vista.tablaDetallesProductos.getValueAt(i, 6).equals("")){
                suma10 = suma10 + Integer.parseInt(vista.tablaDetallesProductos.getValueAt(i, 6).toString());
            }
        }
        
        vista.txtExenta.setText(Integer.toString(sumaExenta));
        vista.txt10.setText(Integer.toString(suma10));
        vista.txt5.setText(Integer.toString(suma5));
        
        vista.txtTotalFactura.setText(Integer.toString(sumaExenta + suma10 + suma5));
        
        vista.txtLiq5.setText(Integer.toString(suma5/21));
        vista.txtLiq10.setText(Integer.toString(suma10/11));
        vista.txtTotalIVa.setText(Integer.toString(Integer.parseInt(vista.txtLiq5.getText()) + 
                Integer.parseInt(vista.txtLiq10.getText())));
    }
    
    public void generarCodigo(){
        String sql = "select ifnull(max(id_factura),0) + 1 as codigo from facturas";
        
        try {
            st = Conexion.stmt(st);
            rs = st.executeQuery(sql);
            rs.next();
            vista.txtCodigoFactura.setText(rs.getString("codigo"));
            vista.txtNumeroFactura.requestFocus();
            st.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(FacturaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void guardarFactura(){
        String sql = "insert into facturas (id_factura,"
                + "clientes_id_cliente,usuarios_id_usuario, "
                + "condicion, fecha, numero, estado) values ('"
                +id+"','"+codigoCliente+"','"+usuario+"','"
                +condicion+"','"+fecha+"','"+numero+"','"+estado+"');";
        
        try {
            st = Conexion.stmt(st);
            st.executeUpdate(sql);
            st.close();
            
            JOptionPane.showMessageDialog(null, "CABECERA GUARDADA");
        } catch (SQLException ex) {
            Logger.getLogger(FacturaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void guardarDetalleFactura(){
        String sql;
        Integer numeroItems = vista.tablaDetallesProductos.getRowCount();
        
        for (int i = 0; i < numeroItems; i++) {
            //Error logico se utilizo += en vez de = con lo cual solo insertaba 1 vez
            sql = "insert into detalles_factura (facturas_id_factura, productos_id_producto,"
                    + "cantidad, precio) values ('"+id+"','"+vista.tablaDetallesProductos.getValueAt(i, 0)
                    +"','"+vista.tablaDetallesProductos.getValueAt(i, 3)
                    +"','"+vista.tablaDetallesProductos.getValueAt(i, 2)+"');";
            
            try {
                st = Conexion.stmt(st);
                st.executeUpdate(sql);
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(FacturaModel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        JOptionPane.showMessageDialog(vista, "DEtalle Garudado");
    }
    
    public void diminuirStock(){
        String sql;
        
        Integer cantidadItems = vista.tablaDetallesProductos.getRowCount();
        
        for (int i = 0; i < cantidadItems; i++) {
            sql = "update productos set cantidad = cantidad - '"+vista.tablaDetallesProductos.getValueAt(i, 3)+"' where id_producto = '"+vista.tablaDetallesProductos.getValueAt(i, 0)+"'";
            
            try {
                st = Conexion.stmt(st);
                st.executeUpdate(sql);
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(FacturaModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void incrementarStock(){
        String sql;
        
        Integer cantidadItems = vista.tablaDetallesProductos.getRowCount();
        
        for (int i = 0; i < cantidadItems; i++) {
            sql = "update productos set cantidad = cantidad - '"+vista.tablaDetallesProductos.getValueAt(i, 3)+"' where id_producto = '"+vista.tablaDetallesProductos.getValueAt(i, 0)+"'";
            
            try {
                st = Conexion.stmt(st);
                st.executeUpdate(sql);
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(FacturaModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void llamarInforme(){
        Connection conn = null;
        String RUTALOCAL = System.getProperty("user.dir");
        try {
            conn = Conexion.conectar(conn);
            HashMap parametros = new HashMap();
            parametros.put("factura", vista.txtCodigoFactura.getText());
            parametros.put("iva_10", vista.txt10.getText());
            parametros.put("iva_5", vista.txt5.getText());
            parametros.put("liq_0", vista.txtExenta.getText());
            parametros.put("liq_5", vista.txtLiq5.getText());
            parametros.put("liq_10", vista.txtLiq10.getText());
            parametros.put("total", vista.txtTotalFactura.getText());
            parametros.put("t_iva", vista.txtTotalIVa.getText());
            parametros.put("letras", null);
            
            JasperPrint jp = JasperFillManager.fillReport(RUTALOCAL + "/src/main/java/sistema/reportes/factura.jasper", parametros, conn);
            JasperViewer view = new JasperViewer(jp, false);
            view.setTitle("FACTURA");
            //view.setExtendedState(Frame.MAXIMIZED_BOTH);
            view.setVisible(true);
            
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    public void llamarBuscadorFactura(){
        vista.buscadorFacturas.setSize(750, 500);
        vista.buscadorFacturas.setLocationRelativeTo(vista);
        vista.buscadorFacturas.setVisible(true);
        vista.txtFechaInicio.requestFocus();
    }
    
    public void cargarFacturas(){
        String sql = "select * from facturas f inner join clientes c on f.clientes_id_cliente = c.id_cliente where f.fecha between '"+vista.txtFechaInicio.getText()+"' and '"+vista.txtFechaFin.getText()+"'";
               
        try {
            st = Conexion.stmt(st);
            rs = st.executeQuery(sql);
            modeloTablaFacturas.setRowCount(0);
            while(rs.next()){
                modeloTablaFacturas.addRow(new Object[]{
                    rs.getString("id_factura"),
                    rs.getString("numero"),
                    rs.getString("condicion"),
                    rs.getString("fecha"),
                    rs.getString("estado"),
                    rs.getString("id_cliente"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getString("ruc")
                });
            }
            
            vista.tablaBusquedaFacturas.setModel(modeloTablaFacturas);
            rs.close();
            st.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(FacturaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void transferirFactura(){
        int fila = vista.tablaBusquedaFacturas.getSelectedRow();
        
        vista.txtCodigoFactura.setText(vista.tablaBusquedaFacturas.getValueAt(fila, 0).toString());
        vista.txtNumeroFactura.setText(vista.tablaBusquedaFacturas.getValueAt(fila, 1).toString());
        
        if(vista.tablaBusquedaFacturas.getValueAt(fila, 2).toString().equals("CONTADO")){
            vista.contadoRadio.setSelected(true);
        }else{
            vista.creditoRadio.setSelected(true);
        }
        
        vista.txtFechaFactura.setText(vista.tablaBusquedaFacturas.getValueAt(fila, 3).toString());
       
        if(vista.tablaBusquedaFacturas.getValueAt(fila, 4).toString().equals("P")){
            vista.txtEstado.setText("PENDIENTE");
        }
        
        if(vista.tablaBusquedaFacturas.getValueAt(fila, 4).toString().equals("A")){
            vista.txtEstado.setText("ANULADO");
        }
        
        if(vista.tablaBusquedaFacturas.getValueAt(fila, 4).toString().equals("C")){
            vista.txtEstado.setText("COBRADO");
        }
        
        vista.codigoClienteTxt.setText(vista.tablaBusquedaFacturas.getValueAt(fila, 5).toString());
        vista.nombreClienteTxt.setText(vista.tablaBusquedaFacturas.getValueAt(fila, 6).toString());
        vista.apellidoClienteTxt.setText(vista.tablaBusquedaFacturas.getValueAt(fila, 7).toString());
        vista.rucClienteTxt.setText(vista.tablaBusquedaFacturas.getValueAt(fila, 8).toString());
        
        String sql = "select d.facturas_id_factura, d.productos_id_producto, d.cantidad, d.precio,  ";
        
        sql += "p.nombre, if(p.iva = 'exenta', d.cantidad * d.precio, '') as exenta, ";
        sql += "if(p.iva = '5', d.cantidad * d.precio, '') as cinco, ";
        sql += "if(p.iva = '10', d.cantidad * d.precio, '') as diez ";
        
        sql += "from detalles_factura d inner join productos p on d.productos_id_producto = p.id_producto where facturas_id_factura = '"+vista.tablaBusquedaFacturas.getValueAt(fila, 0).toString()+"'";
        
        modeloTablaDetalleFactura.setRowCount(0);
        
        try {
            st = Conexion.stmt(st);
            rs = st.executeQuery(sql);
            while(rs.next()){
                modeloTablaDetalleFactura.addRow(new Object[]{
                    rs.getString("d.productos_id_producto"),
                    rs.getString("p.nombre"),
                    rs.getString("d.precio"),
                    rs.getString("d.cantidad"),
                    rs.getString("exenta"),
                    rs.getString("cinco"),
                    rs.getString("diez")
                });
            }
            
            vista.tablaDetallesProductos.setModel(modeloTablaDetalleFactura);
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(FacturaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        totales();
        vista.buscadorFacturas.setEnabled(false);
        activarBotones(false, false, true, true, true);
    }
    
    public void anularFactura(){
        if (vista.txtEstado.getText().equals("PENDIENTE")) {
            String sql = "update facturas set estado = 'A' where id_factura = '"+vista.txtCodigoFactura.getText()+"'";
            
            try {
                st = Conexion.stmt(st);
                st.executeUpdate(sql);
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(FacturaModel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //aumentar stock
            incrementarStock();
            JOptionPane.showMessageDialog(null, "ANULADO");
            vista.txtEstado.setText("ANULADO");
        }
    }
    
    public void cancelarFactura(){
        bloquearObjetos(true);
        limpiar();
    }
    
}
