package database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConnectionManager {
    //Configuration data
    private DatabaseConfig configuration;
    
    //Static Field(s)
    private static final String DBNAME = "databaseip";
    
    //Singleton instance
    private static ConnectionManager instance = null;
    
    //Private constructor - loads the jdbc driver
    private ConnectionManager(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            throw new Error("Impossible to find postgres driver");
        }
    }
    
    //Sets data for connection
    ConnectionManager configure(DatabaseConfig config) {
        this.configuration = config;
        return this;
    }

    Connection getConnection() throws SQLException{
        if(configuration == null){
            throw new NoConfigException("You need to configure the database first!");
        }
        Connection c = DriverManager.getConnection(configuration.host + DBNAME, configuration.user, configuration.pswd);
        return c;
    }
    
    boolean checkAdminExistence() {
        boolean result = false;
        if(configuration == null){
            throw new NoConfigException("You need to configure the database first!");
        }
        if(checkDatabaseExistence()) {
            String sql = "SELECT * FROM ip_user WHERE adminstrator = true";
            try {
                Connection c = this.getConnection();
                PreparedStatement stmt = c.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()) {
                    result = true;
                }
                rs.close();
                stmt.close();
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
     
    boolean checkDatabaseExistence() {
        if(configuration == null){
            throw new NoConfigException("You need to configure the database first!");
        }
        Connection connection;
        boolean result = false;
        try {
            connection = DriverManager.getConnection(configuration.host, configuration.user, configuration.pswd);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT datname FROM pg_database WHERE datistemplate = false;");
            while(rs.next()) {
                String name = rs.getString(1);
                if(name.equals(DBNAME)) {
                    result = true;
                    break;
                }
            }
            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    void createDatabase() {
        if(configuration == null){
            throw new NoConfigException("You need to configure the database first!");
        }
        Connection conn;
        BufferedReader reader;
        try {
            conn = DriverManager.getConnection(configuration.host, configuration.user, configuration.pswd);
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE DATABASE " + DBNAME);
            System.out.println("Database creato.");
            stmt.close();
            conn.close();
            conn = getConnection();
            reader = new BufferedReader(new FileReader("sql_src/db_src_ip.sql"));
            String line;
            StringBuilder builder = new StringBuilder();
            while(reader.ready()) {
                line = reader.readLine().trim();
                if(line.length() == 0) {
                    continue;
                }
                builder.append(" ").append(line);
                if(builder.charAt(builder.length() - 1) == ';') {
                    builder.deleteCharAt(builder.length() - 1);
                    stmt = conn.createStatement();
                    stmt.execute(builder.toString());
                    stmt.close();
                    System.out.println("Eseguito: " + builder.toString());
                    builder = new StringBuilder();
                }
            }
            reader.close();
            conn.close();
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
    }
    
    static ConnectionManager getConnectionManager() {
        if(instance == null){
            instance = new ConnectionManager();
        }
        return instance;
    }

    void deleteDatabase() {
        if(configuration == null){
            throw new NoConfigException("You need to configure the database first!");
        }
        Connection conn;
        try {
            conn = DriverManager.getConnection(configuration.host, configuration.user, configuration.pswd);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DROP DATABASE " + DBNAME);
            conn.close();
            System.out.println("Database cancellato!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private class NoConfigException extends RuntimeException {
        NoConfigException(String msg) {
            super(msg);
        }
    }
}
