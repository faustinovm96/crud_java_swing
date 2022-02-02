package sistema;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
            logger.info("Conexion establecida");
        } catch (SQLException e) {
            logger.info("Error al conectar");
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            logger.info("Clase no encontrada");
        }
        return conexion;
    }
    
    public static Statement stmt(Statement st) throws SQLException{
        conexion = conectar(conexion);
        statement = conexion.createStatement();
        return statement;
    }
    
}
