package facturacion;


import facturacion.controladores.MenuControlador;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Faustino
 */
public class ModeloLogin {
    private String usuario;
    private String clave;
    
    Statement st;
    ResultSet rs;
    
    VistaLogin vistaLogin;

    public ModeloLogin(VistaLogin vistaLogin) {
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
    
    public void acceder(){
        String sql = "select * from usuarios where nombre= '"+usuario+"' and clave='"+clave+"'";
        
        try {
            st = Conexion.stmt(st);
            rs = st.executeQuery(sql);
            rs.next();
            
            if(rs.getRow() == 0){
                JOptionPane.showMessageDialog(vistaLogin, "DATOS INCORRECTOS");
            }else if(rs.getString("estado").equals("A")){
                VistaMenuPrincipal vistaMenu = new VistaMenuPrincipal();
                ModeloMenu modeloMenu = new ModeloMenu(vistaMenu);
                MenuControlador c = new MenuControlador(vistaMenu, modeloMenu);
                vistaMenu.txtIdUsuario.setText(rs.getString("id_usuario"));
                vistaMenu.txtUsuario.setText(rs.getString("nombre"));
                modeloMenu.controlPrivilegios();
                vistaMenu.setLocationRelativeTo(null);
                
                vistaMenu.setVisible(true);
                vistaLogin.dispose();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ModeloLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
