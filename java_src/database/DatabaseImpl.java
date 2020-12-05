package database;

import entity.GameData;
import entity.SessionData;
import entity.StatsData;
import entity.UserData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Pair;

public class DatabaseImpl implements Database{

    private static DatabaseImpl instance;
    
    private static int GAME_ID = 0;
    private static int SESSION_ID = 0;
    private static int WORD_ID = 0;
    
    private ConnectionManager connManager;
    
    private DatabaseImpl() {
        connManager = ConnectionManager.getConnectionManager();
    }
    
    public static Database getDatabase() {
        if(instance == null) {
            instance = new DatabaseImpl();
        }
        return instance;
    }
    
    // <editor-fold defaultstate="collapsed" desc="user-methods">
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
                ret.setPassword(rs.getString("password"));
                ret.setFirstName(rs.getString("name"));
                ret.setLastName(rs.getString("surname"));
                ret.setAdmin(rs.getBoolean("administrator"));
                ret.setActive(rs.getBoolean("active"));
                ret.setActivationCode(rs.getString("activation_code"));
            }
            rs.close();
            stmt.close();
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
                usr.setPassword(rs.getString("password"));
                usr.setFirstName(rs.getString("name"));
                usr.setLastName(rs.getString("surname"));
                usr.setAdmin(rs.getBoolean("administrator"));
                usr.setActive(rs.getBoolean("active"));
                usr.setActivationCode(rs.getString("activation_code"));
                if(password.equals(usr.getPassword())){
                    result = new Pair<>(usr, null);
                } else {
                    result = new Pair<>(null, 1);
                }
            } else {
                result = new Pair<>(null, 0);
            }
            rs.close();
            stmt.close();
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
        String sql = "INSERT INTO ip_user (nickname, name, surname, email, password, activation_code, administrator, active) "
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
            stmt.setBoolean(8, user.getActive());
            stmt.executeUpdate();
            stmt.close();
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
        if(old == null || user == null) throw new IllegalArgumentException();
        boolean comma = false;
        int req = 1;
        StringBuilder sql = new StringBuilder("UPDATE ip_user SET ");
        if(user.getNickname()!= null) {
            sql.append("nickname = ? ");
            req++;
            comma = true;
        }
        if(user.getFirstName()!= null) {
            sql.append(comma ? ", " : "").append("name = ? ");
            req++;
            comma = true;
        }
        if(user.getLastName()!= null) {
            sql.append(comma ? ", " : "").append("surname = ? ");
            req++;
            comma = true;
        }
        if(user.getEmail()!= null) {
            sql.append(comma ? ", " : "").append("email = ? ");
            req++;
            comma = true;
        }
        if(user.getPassword()!= null) {
            sql.append(comma ? ", " : "").append("password = ? ");
            req++;
            comma = true;
        }
        if(user.getActive()!= null) {
            sql.append(comma ? ", " : "").append("active = ? ");
            req++;
        }
        sql.append("WHERE nickname = ?");
        if(req == 1) throw new IllegalArgumentException("At least one field of user has to be non-null");
        Connection c = null;
        try {
            c = connManager.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql.toString());
            stmt.setString(req--, old);
            if(user.getActive()!= null) stmt.setBoolean(req--, user.getActive());
            if(user.getPassword()!= null) stmt.setString(req--, user.getPassword());
            if(user.getEmail()!= null) stmt.setString(req--, user.getEmail());
            if(user.getLastName()!= null) stmt.setString(req--, user.getLastName());
            if(user.getFirstName()!= null) stmt.setString(req--, user.getFirstName());
            if(user.getNickname()!= null) stmt.setString(req, user.getNickname());
            stmt.executeUpdate();
            stmt.close();
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
                stmt.close();
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
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="game-methods">
    @Override
    public GameData addGame(GameData gameData) {
        String sql = "INSERT INTO game (id, name) VALUES (?, ?)";
        gameData.setId(GAME_ID++);
        Connection conn = null;
        try {
            conn = connManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, gameData.getId());
            stmt.setString(2, gameData.getName());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(conn != null) conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return gameData;
    }

    @Override
    public GameData getGame(int gameId) {
        String sql = "SELECT * FROM game WHERE id = ?";
        GameData gameData = null;
        Connection conn = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, gameId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                gameData = new GameData();
                gameData.setId(rs.getInt("id"));
                gameData.setName(rs.getString("name"));
                rs.close();
                stmt.close();
                gameData.setSessions(getSessions(gameData.getId()));
                
            } else {
                rs.close();
                stmt.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(conn != null) try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return gameData;
    }

    @Override
    public boolean updateGame(GameData gameData) {
        for(String player : gameData.getPlayersList()) {
            addPartecipate(gameData.getId(), player, gameData.getPoints(player));
        }
        for(SessionData s : gameData.getSessions()) {
            addSession(gameData.getId(), s);
        }
        return true;
    }
    
    @Override
    public void removeGame(int gameId) {
        Connection conn = null;
        String sql = "DELETE FROM game WHERE id = ?";
        try {
            conn = connManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, gameId);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(conn != null) try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="stats-methods">
    
    @Override
    public StatsData getStats() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    // </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="config-methods">
    @Override
    public Database configure(DatabaseConfig config) {
        connManager.configure(config);
        updateIds();
        return this;
    }
    
    private void updateIds() {
        Connection conn = null;
        String sql = "SELECT id FROM ";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = connManager.getConnection();
            stmt = conn.prepareStatement(sql + "game ORDER BY id DESC");
            rs = stmt.executeQuery();
            if(rs.next()){
                int val = rs.getInt(1);
                GAME_ID = ++val;
            }
            rs.close();
            stmt.close();
            stmt = conn.prepareStatement(sql + "manche ORDER BY id DESC");
            rs = stmt.executeQuery();
            if(rs.next()){
                int val = rs.getInt(1);
                SESSION_ID = ++val;
            }
            rs.close();
            stmt.close();
            stmt = conn.prepareStatement(sql + "word ORDER BY id DESC");
            rs = stmt.executeQuery();
            if(rs.next()){
                int val = rs.getInt(1);
                WORD_ID = ++val;
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(conn != null) try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    // </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="utility-methods"> Utility methods for first start and/or testing purpose
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

    // </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="private-methods">
    private List<SessionData> getSessions(Integer gameId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void addPartecipate(Integer id, String player, int points) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void addSession(Integer id, SessionData s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    // </editor-fold>

}
