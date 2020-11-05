package database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import utils.Pair;


public class QueryExecutor {
    //Configuration data
    private DatabaseConfig configuration = new DatabaseConfig();
    //Static Field(s)
    private static final String DBNAME = "databaseIP";
    //Singleton instance
    private static QueryExecutor instance = null;
    //Private constructor - loads the jdbc driver
    private QueryExecutor(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            throw new Error("Impossible to find postgres driver");
        }
    }
    
    //Query methods
    public boolean executeUpdate(String query, Object... params){
        return false;
    }
    
    public <T> List<T> executeQuery(Class<T> type, T elem) {
        return null;
    }
    
    //Configuration and utility methods
    public void configure(DatabaseConfig config) {
        this.configuration = config;
    }
    
    public boolean checkAdminExistence() {
        boolean result = false;
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
        }else{
            createDatabase();
        }
        return result;
    }
    
    private Connection getConnection() throws SQLException{
        Connection c = DriverManager.getConnection(configuration.host + DBNAME, configuration.user, configuration.pswd);
        return c;
    }
    
    private boolean checkDatabaseExistence() {
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
    
    private void createDatabase() {
        Connection conn;
        BufferedReader reader;
        try {
            conn = DriverManager.getConnection(configuration.host, configuration.user, configuration.pswd);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE DATABASE " + DBNAME);
            conn.close();
            conn = getConnection();
            stmt = conn.createStatement();
            reader = new BufferedReader(new FileReader("dbsrc/db_src_ip.txt"));
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
                    stmt.executeUpdate(builder.toString());
                    builder = new StringBuilder();
                }
            }
            reader.close();
            conn.close();
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static QueryExecutor getExecutor() {
        if(instance == null){
            instance = new QueryExecutor();
        }
        return instance;
    }
    
}
