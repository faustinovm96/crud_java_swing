package facturacion;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author F996
 */
public class Conexion {
    private static Connection conexion = null;
    private static Statement statement = null;
    //private Connection connection;
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/fact?useSSL=false&useTimezone=true&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "holamundo96";
    
    public static org.apache.logging.log4j.Logger logger = LogManager.getLogger(Conexion.class);
    
    public static Connection conectar(Connection conexion) throws SQLException{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
            logger.debug("Creacion del objeto conexion");
        } catch (SQLException e) {
            logger.debug("ERROR");
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexion;
    }
    
    public static Statement stmt(Statement st) throws SQLException{
        conexion = conectar(conexion);
        statement = conexion.createStatement();
        return statement;
    }
    
}
