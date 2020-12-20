package database;

import entity.GameData;
import entity.SessionData;
import entity.StatsData;
import entity.UserData;
import entity.WordData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
            System.out.println("L'utente esiste già");
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
            conn = connManager.getConnection();
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
        StatsData stats = new StatsData();
        stats.setBestPlayerGameScore(queryForBestPlayerGameScore());
        stats.setBestPlayerSessionScore(queryForPlayerSessionScore());
        stats.setPlayerWithMoreSessions(queryForPlayerWithMoreSessions());
        stats.setBestAverageSessionScore(queryForBestAverageSessionScore());
        stats.setBestAverageGameScore(queryForBestAverageGameScore());
        stats.setPlayerWithMoreDuplicates(queryForPlayerWithMoreDuplicates());
        stats.setPlayerWithMoreErrors(queryForPlayerWithMoreErrors());
        stats.setOccurrencyWordsLeaderboard(queryForOccurrencyWordsLeaderboard());
        stats.setWordsBestScore(queryForWordsBestScore());
        return stats;
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
    private List<SessionData> getSessions(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void addPartecipate(Integer id, String player, int points) {
        String sql = "INSERT INTO partecipate (game_key, user_key, total_points) VALUES (?, ?, ?)";
        Connection conn = null;
        try {
            conn = connManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, player);
            stmt.setInt(3, points);
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
    }

    private void addSession(Integer id, SessionData s) {
        String sql = "INSERT INTO manche (id, grid, game_key) VALUES (?, ?, ?)";
        StringBuilder grid = new StringBuilder();
        for(int i = 0; i<s.getGrid().length; i++){
            grid.append(s.getGrid()[i]).append(i < s.getGrid().length-1 ? ",":"");
        }
        Connection conn = null;
        try {
            conn = connManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, SESSION_ID++);
            stmt.setString(2, grid.toString());
            stmt.setInt(3, id);
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
        
        addFoundWords(SESSION_ID-1, s.getFoundWords());
        addRequests(SESSION_ID-1, s.getRequestedWords());
        addPlays(SESSION_ID-1, s.getPlayers());
    }
    
    private void addFoundWords(int session_id, Map<String, List<WordData>> foundWords) {
        Set<String> players = foundWords.keySet();
        for(String player : players){
            List<WordData> wordsList = foundWords.get(player);
            for(WordData word : wordsList){
                WordData stored = getWordFromDb(word.getWord());
                if(stored == null) {
                    stored = addWordToDb(word);
                }
                addFindRecord(session_id, player, stored);
            }
        }
    }

    private void addRequests(int session_id, Map<String, List<WordData>> requestedWords) {
        Set<String> players = requestedWords.keySet();
        for(String player : players){
            List<WordData> wordsList = requestedWords.get(player);
            for(WordData word : wordsList){
                WordData stored = getWordFromDb(word.getWord());
                if(stored == null) {
                    throw new IllegalArgumentException("This word was never found in any session!");
                }
                addRequestRecord(session_id, player, stored);
            }
        }
    }
    
    private void addPlays(int session_id, Set<String> players) {
        for(String player : players){
            List<WordData> words = getFoundWords(session_id, player);
            int points = 0;
            for(WordData word : words){
                if(word.isCorrect() && !word.isDuplicate()) {
                    points += word.getPoints();
                }
            }
            addPlayRecord(session_id, player, points);
        }
    }
    
    private WordData getWordFromDb(String word) {
        String sql = "SELECT w.id, w.points FROM word w WHERE w.word = ?";
        WordData ret = null;
        Connection conn = null;
        try {
            conn = connManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, word);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                ret = new WordData();
                ret.setWord(word);
                ret.setId(rs.getInt(1));
                ret.setPoints(rs.getInt(2));
            }
            rs.close();
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
        return ret;
    }
    
    private WordData addWordToDb(WordData word) {
        String sql = "INSERT INTO word (id, word, points) VALUES (?, ?, ?)";
        word.setId(WORD_ID++);
        Connection conn = null;
        try {
            conn = connManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, word.getId());
            stmt.setString(2, word.getWord());
            stmt.setInt(3, word.getPoints());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseImpl.class.getName()).log(Level.SEVERE, null, ex);
            word = null;
        } finally {
            try {
                if(conn != null) conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        return word;
    }
    
    private void addFindRecord(int session_id, String player, WordData word) {
        String sql = "INSERT INTO find (user_key, word_key, manche_key, duplicate, correct) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = connManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, player);
            stmt.setInt(2, word.getId());
            stmt.setInt(3, session_id);
            stmt.setBoolean(4, word.isDuplicate());
            stmt.setBoolean(5, word.isCorrect());
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
    }
    
    private void addRequestRecord(int session_id, String player, WordData word) {
        String sql = "INSERT INTO def_req (user_key, word_key, manche_key) VALUES (?, ?, ?)";
        Connection conn = null;
        try {
            conn = connManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, player);
            stmt.setInt(2, word.getId());
            stmt.setInt(3, session_id);
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
    }
    
    private List<WordData> getFoundWords(int session_id, String player) {
        String sql = "SELECT id, word, points, duplicate, correct FROM word INNER JOIN find ON id = word_key "
                + "WHERE user_key = ? AND manche_key = ?";
        List<WordData> foundWords = new ArrayList<>();
        Connection conn = null;
        try {
            conn = connManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, player);
            stmt.setInt(2, session_id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                WordData word = new WordData();
                word.setId(rs.getInt("id"));
                word.setWord(rs.getString("word"));
                word.setPoints(rs.getInt("points"));
                word.setDuplicate(rs.getBoolean("duplicate"));
                word.setCorrect(rs.getBoolean("correct"));
                foundWords.add(word);   
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
        return foundWords;
    }
    
    private void addPlayRecord(int session_id, String player, int points) {
        String sql = "INSERT INTO play (user_key, manche_key, points) VALUES (?, ?, ?)";
        Connection conn = null;
        try {
            conn = connManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, player);
            stmt.setInt(2, session_id);
            stmt.setInt(3, points);
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
    }
    // </editor-fold>

    private Pair<String, Integer> queryForBestPlayerGameScore() {
        Integer points = 0;
        StringBuilder player = new StringBuilder();
        String sql = "SELECT user_key, total_points FROM partecipate WHERE total_points IN (SELECT MAX(total_points) FROM partecipate)";
        Connection c = null;
        try {
            c = connManager.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            boolean more = false;
            while(rs.next()){
                points = rs.getInt("total_points");
                player.append(more ? "," : "").append(rs.getString("user_key"));
                more = true;
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
        Pair<String, Integer> result = new Pair<>(player.toString(), points);
        return result;
    }

    private Pair<String, Integer> queryForPlayerSessionScore() {
        Integer points = 0;
        StringBuilder player = new StringBuilder();
        String sql = "SELECT user_key, points FROM play WHERE points IN (SELECT MAX(points) FROM play)";
        Connection c = null;
        try {
            c = connManager.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            boolean more = false;
            while(rs.next()){
                points = rs.getInt("points");
                player.append(more ? "," : "").append(rs.getString("user_key"));
                more = true;
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
        Pair<String, Integer> result = new Pair<>(player.toString(), points);
        return result;
    }

    private Pair<String, Integer> queryForPlayerWithMoreSessions() {
        Integer count = 0;
        StringBuilder player = new StringBuilder();
        String sql = "SELECT user_key, COUNT(*) AS num_sessions FROM play WHERE num_sessions IN "
                + "(SELECT MAX(COUNT(*)) FROM play GROUP BY user_key) GROUP BY user_key";
        Connection c = null;
        try {
            c = connManager.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            boolean more = false;
            while(rs.next()){
                count = rs.getInt("num_sessions");
                player.append(more ? "," : "").append(rs.getString("user_key"));
                more = true;
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
        Pair<String, Integer> result = new Pair<>(player.toString(), count);
        return result;
    }

    private Pair<String, Integer> queryForBestAverageSessionScore() {
        Integer score = 0;
        StringBuilder player = new StringBuilder();
        String sql = "SELECT user_key, AVG(points) AS avg_score FROM play WHERE avg_score IN "
                + "(SELECT MAX(AVG(points)) FROM play GROUP BY user_key) GROUP BY user_key";
        Connection c = null;
        try {
            c = connManager.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            boolean more = false;
            while(rs.next()){
                score = rs.getInt("avg_score");
                player.append(more ? "," : "").append(rs.getString("user_key"));
                more = true;
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
        Pair<String, Integer> result = new Pair<>(player.toString(), score);
        return result;
    }

    private Pair<String, Integer> queryForBestAverageGameScore() {
        Integer score = 0;
        StringBuilder player = new StringBuilder();
        String sql = "SELECT user_key, AVG(total_points) AS avg_score FROM partecipate WHERE avg_score IN "
                + "(SELECT MAX(AVG(total_points)) FROM partecipate GROUP BY user_key) GROUP BY user_key";
        Connection c = null;
        try {
            c = connManager.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            boolean more = false;
            while(rs.next()){
                score = rs.getInt("avg_score");
                player.append(more ? "," : "").append(rs.getString("user_key"));
                more = true;
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
        Pair<String, Integer> result = new Pair<>(player.toString(), score);
        return result;
    }

    private Pair<String, Integer> queryForPlayerWithMoreDuplicates() {
        Integer num_duplicates = 0;
        StringBuilder player = new StringBuilder();
        String sql = "SELECT user_key, COUNT(*) AS num_duplicates FROM find WHERE duplicate = 'true' AND num_duplicates IN "
                + "(SELECT MAX(COUNT(*)) FROM find WHERE duplicate = 'true' GROUP BY user_key) GROUP BY user_key";
        Connection c = null;
        try {
            c = connManager.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            boolean more = false;
            while(rs.next()){
                num_duplicates = rs.getInt("num_duplicates");
                player.append(more ? "," : "").append(rs.getString("user_key"));
                more = true;
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
        Pair<String, Integer> result = new Pair<>(player.toString(), num_duplicates);
        return result;
    }

    private Pair<String, Integer> queryForPlayerWithMoreErrors() {
        Integer num_errors = 0;
        StringBuilder player = new StringBuilder();
        String sql = "SELECT user_key, COUNT(*) AS num_errors FROM find WHERE duplicate = 'true' AND num_errors IN "
                + "(SELECT MAX(COUNT(*)) FROM find WHERE duplicate = 'true' GROUP BY user_key) GROUP BY user_key";
        Connection c = null;
        try {
            c = connManager.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            boolean more = false;
            while(rs.next()){
                num_errors = rs.getInt("num_errors");
                player.append(more ? "," : "").append(rs.getString("user_key"));
                more = true;
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
        Pair<String, Integer> result = new Pair<>(player.toString(), num_errors);
        return result;
    }

    private List<Pair<String, Integer>> queryForOccurrencyWordsLeaderboard() {
        List<Pair<String, Integer>> leaderboard = new ArrayList<>();
        String sql = "SELECT word, COUNT(*) AS num_occ FROM find INNER JOIN word ON word_key = id "
                + "GROUP BY word ORDER BY num_occ ASC";
        Connection c = null;
        try {
            c = connManager.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                leaderboard.add(new Pair<>(rs.getString("word"), rs.getInt("num_occ")));
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
        return leaderboard;
    }

    private List<Pair<String, String>> queryForWordsBestScore() {
        List<Pair<String, String>> leaderboard = new ArrayList<>();
        String sql = "SELECT word, points FROM word INNER JOIN find ON id = word_key "
                + "WHERE points IN (SELECT MAX(points) FROM word) AND duplicate = 'false' AND correct = 'true'";
        Connection c = null;
        try {
            c = connManager.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                leaderboard.add(new Pair<>(rs.getString("word"), "" + rs.getInt("points")));
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
        return leaderboard;
    }

}
