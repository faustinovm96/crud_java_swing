/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.modelo.dao;

import sistema.views.model.LoginModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import sistema.Conexion;
import sistema.modelo.domain.Usuario;

/**
 *
 * @author Faustino
 */
public class UsuarioDaoJDBC implements IUsuarioDao{

    @Override
    public Usuario login(String usuario, String clave) {
        Statement st = null;
        ResultSet rs = null;
                
        String sql = "select * from usuarios where nombre= '"+usuario+"' and clave='"+clave+"'";
        
        try {
            st = Conexion.stmt(st);
            rs = st.executeQuery(sql);
            rs.next();
            
            Usuario usuario1 = new Usuario();
            
            usuario1.setIdUsuario(rs.getInt("id_usuario"));
            usuario1.setUsuario(rs.getString("nombre"));
            usuario1.setClave(rs.getString("clave"));
            usuario1.setTipo(rs.getString("tipo"));
            usuario1.setEstado(rs.getString("estado"));
            
            if(rs.getRow() == 0){
                return null;
            }else if(rs.getString("estado").equals("A")){
                return usuario1;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
