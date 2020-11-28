package database;

import entity.GameData;
import entity.StatsData;
import entity.UserData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.Pair;

public class DatabaseImpl implements Database{

    private static DatabaseImpl instance;
    
    private ConnectionManager connManager;
    
    private DatabaseImpl() {
        connManager = ConnectionManager.getConnectionManager();
    }
    
    @Override
    public UserData getUser(String nickname) {
        UserData ret = null;
        String sql = "SELECT * FROM ip_user WHERE nickname = ?";
        Connection c = null;
        try {
            c = connManager.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, nickname);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                ret = new UserData();
                ret.setNickname(rs.getString("nickname"));
                ret.setEmail(rs.getString("email"));
                ret.setPassword("password");
                ret.setFirstName("name");
                ret.setLastName("surname");
                //add activation params
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if(c != null) c.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return ret;
    }

    @Override
    public Pair<UserData, Integer> getUser(String email, String password) {
        UserData usr = null;
        Pair<UserData, Integer> result = null;
        String sql = "SELECT * FROM ip_user WHERE email = ?";
        Connection c = null;
        try {
            c = connManager.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                usr = new UserData();
                usr.setNickname(rs.getString("nickname"));
                usr.setEmail(rs.getString("email"));
                usr.setPassword("password");
                usr.setFirstName("name");
                usr.setLastName("surname");
                //add activation params
                if(password.equals(usr.getPassword())){
                    result = new Pair<>(usr, null);
                } else {
                    result = new Pair<>(null, 1);
                }
            } else {
                result = new Pair<>(null, 0);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Just for not returning null");
        } finally {
            try {
                if(c != null) c.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public UserData addUser(UserData user) {
        String sql = "INSERT INTO ip_user (nickname, name, surname, email, password, activation_code, adminstrator, active) "
                + "VALUES (?,?,?,?,?,?,?,?)";
        Connection c = null;
        try {
            c = connManager.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, user.getNickname());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getActivationCode());
            stmt.setBoolean(7, user.isAdmin());
            stmt.setBoolean(8, user.isActive());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            user = null;
        } finally {
            try {
                if(c != null) c.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public UserData updateUser(UserData user, String old) {
        String sql = "UPDATE ip_user SET nickname = ? , name = ? , surname = ? ,"
                + " email = ? , password = ? , acrivation_code = ? , admin = ? , active = ? "
                + "WHERE nickname = ?";
        Connection c = null;
        try {
            c = connManager.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, user.getNickname());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getActivationCode());
            stmt.setBoolean(7, user.isAdmin());
            stmt.setBoolean(8, user.isActive());
            stmt.setString(9, old);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            user = null;
        } finally {
            try {
                if(c != null) c.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return user;
    }
    
    @Override
    public UserData removeUser(String nickname) {
        String sql = "DELETE FROM ip_user WHERE nickname = ?";
        UserData user = getUser(nickname);
        if(user != null) {
            Connection c = null;
            try {
                c = connManager.getConnection();
                PreparedStatement stmt = c.prepareStatement(sql);
                stmt.setString(1, nickname);
                stmt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
                user = null;
            } finally {
                try {
                    if(c != null) c.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return user;
    }

    @Override
    public GameData addGame(GameData gameData) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GameData getGame(int gameId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateGame(GameData gameData) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void removeGame(int gameId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public StatsData getStats() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Database configure(DatabaseConfig config) {
        connManager.configure(config);
        return this;
    }
    
    public static Database getDatabase() {
        if(instance == null) {
            instance = new DatabaseImpl();
        }
        return instance;
    }

    //Utility methods for first start and/or testing purpose
    @Override
    public boolean checkAdminExistence() {
        return connManager.checkAdminExistence();
    }

    @Override
    public boolean checkDatabaseExistence() {
        return connManager.checkDatabaseExistence();
    }

    @Override
    public void createDatabase() {
        connManager.createDatabase();
    }

    @Override
    public void deleteDatabase() {
        connManager.deleteDatabase();
    }

}
