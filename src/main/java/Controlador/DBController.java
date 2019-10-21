/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos Araújo González
 */
public final class DBController {
    private static Connection connection;
    private static String url;
    private static String user;
    private static String pass;
    private static Statement statement;
    private static PreparedStatement preparedStatement;
    private static String driver;
    
    public static void closeConnection(){
        try {
            connection.close();
            System.out.println("Conexión cerrada correctamente.");
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void closeStatement() throws SQLException{
        statement.close();
    }
    
    public static void closePreparedStatement() throws SQLException{
        preparedStatement.close();
    }
    
    public static void connect(){
        init();
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, pass);
            System.out.println("La conexión se ha realizado correctamente");
        } catch (ClassNotFoundException|SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public static void createStatement(){
        try {
            statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void createTables(){
        try {
            createTableInstituciones();
            createTableInvestigadores();
            createTablePublicaciones();
            createTablePublicacionesAutores();
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
        
    private static void createTableInstituciones() throws SQLException{
        String query = "CREATE TABLE IF NOT EXISTS instituciones ("+
                "id_institucion int(3) NOT NULL AUTO_INCREMENT,"+
                "nombre         varchar(50),"+
                "direccion      varchar(50),"+
                "ciudad         varchar(50),"+
                "pais           varchar(50),"+
                "PRIMARY KEY (id_institucion))";
        prepareExecuteCloseStatement(query);
    }
    
    private static void createTableInvestigadores() throws SQLException{
        String query = "CREATE TABLE IF NOT EXISTS investigadores ("+
                "id_investigador int(3) NOT NULL AUTO_INCREMENT,"+
                "borrado         boolean,"+
                "nombre          varchar(50),"+
                "apellido1       varchar(50),"+
                "apellido2       varchar(50),"+
                "correo          varchar(50),"+
                "puesto          varchar(20),"+
                "id_institucion  int(3),"+
                "password        varchar(20),"+
                "FOREIGN KEY (id_institucion) REFERENCES instituciones(id_institucion),"+
                "PRIMARY KEY (id_investigador))";   
        prepareExecuteCloseStatement(query);
    }
    
    private static void createTablePublicaciones() throws SQLException{
        String query = "CREATE TABLE IF NOT EXISTS publicaciones (" +
                "id_publicacion  int(3) NOT NULL AUTO_INCREMENT," +
                "titulo          varchar(50)," +
                "year            int(4)," +
                "first_page      int(3)," +
                "last_page       int(3)," +
                "id_autor_contacto int(3)," +
                "PRIMARY KEY (id_publicacion)," +
                "FOREIGN KEY (id_autor_contacto) REFERENCES investigadores(id_investigador))";  
        prepareExecuteCloseStatement(query);
    }
    
    private static void createTablePublicacionesAutores() throws SQLException{
        String query = "CREATE TABLE IF NOT EXISTS publicaciones_autores (" +
                "id_publicacion  int(3) NOT NULL," +
                "id_autor int(3) NOT NULL," +
                "PRIMARY KEY (id_publicacion, id_autor),"+
                "FOREIGN KEY (id_publicacion) REFERENCES publicaciones(id_publicacion)," +
                "FOREIGN KEY (id_autor) REFERENCES investigadores(id_investigador))";  
        prepareExecuteCloseStatement(query);
    }
    
    private ResultSet executeQuery(Statement stmt, String query) throws SQLException{
        return stmt.executeQuery(query);
    }
        
    private static void init(){
        url        = "jdbc:mysql://localhost:3306/proyecto";
        user       = "root";
        pass       = "";
        driver     = "com.mysql.jdbc.Driver";
    }
    
    public static void prepareExecuteCloseStatement(String query) throws SQLException{
        prepareStatement(query);
        preparedStatement.execute();
        preparedStatement.close();
    }
    
    public static void prepareStatement(String query) throws SQLException{
        preparedStatement = connection.prepareStatement(query);
    }

    public static Statement getStatement() {
        return statement;
    }

    public static void setStatement(Statement statement) {
        DBController.statement = statement;
    }

    public static String getDriver() {
        return DBController.driver;
    }

    public static void setDriver(String driver) {
        DBController.driver = driver;
    }

    public static Connection getConnection() {
        return connection;
    }
    
    public static void setConnection(Connection connection) {
        DBController.connection = connection;
    }

    public static PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public static void setPreparedStatement(PreparedStatement preparedStatement) {
        DBController.preparedStatement = preparedStatement;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        DBController.url = url;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        DBController.user = user;
    }

    public static String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        DBController.pass = pass;
    }
}
