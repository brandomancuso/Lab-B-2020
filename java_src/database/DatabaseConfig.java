package database;

public class DatabaseConfig {
    String host;
    String user;
    String pswd;
    
    public DatabaseConfig(){
        
    }

    public DatabaseConfig setHost(String host) {
        this.host = host;
        return this;
    }

    public DatabaseConfig setUser(String user) {
        this.user = user;
        return this;
    }

    public DatabaseConfig setPswd(String pswd) {
        this.pswd = pswd;
        return this;
    } 
}
