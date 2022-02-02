package sistema.views.model;


import sistema.views.MenuPrincipalView;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import sistema.Conexion;

/**
 *
 * @author Faustino
 */
public class MenuModel {
    MenuPrincipalView vista;
    Statement st;
    ResultSet rs;

    public MenuModel(MenuPrincipalView vista) {
        this.vista = vista;
    }
    
    public void controlPrivilegios(){
        String sql = "select * from usuarios where id_usuario = '"+vista.txtIdUsuario.getText()+"'";
        String tipo;
        try {
            st = Conexion.stmt(st);
            rs = st.executeQuery(sql);
            rs.next();
            tipo = rs.getString("tipo");
            rs.close();
            st.close();
            
            if(tipo.equals("INVITADO")){
                vista.subMenuSalir.setEnabled(false);
                vista.menuFacturacion.setEnabled(false);
            }
            
            if(tipo.equals("ADMINISTRADOR")){

            }
            
            if(tipo.equals("CAJERO")){

            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MenuModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
