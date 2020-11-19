package server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class ServerUtilityGui {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
    private static final Pattern DB_HOST_PATTERN = Pattern.compile("^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$");
    private static final Pattern DB_USER_PATTERN = Pattern.compile("");
    
    public ServerUtilityGui(){
        
    }
    
    public boolean checkEmail(String email){
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
    
    public boolean checkPassword(String password){
        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        return matcher.matches();
    }
    
    public boolean checkDbInfo(String dbHost, String dbUser, String dbPassword){
        if(checkDbHost(dbHost) && checkDbUser(dbUser) && checkPassword(dbPassword))
            return true;
        else
            return false;
    }
    
    public void showMessage(String message, String title){
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    private boolean checkDbHost(String dbHost){
        Matcher matcher = DB_HOST_PATTERN.matcher(dbHost);
        return matcher.matches();
    }
    
    private boolean checkDbUser(String dbUser){
        //Matcher matcher = DB_USER_PATTERN.matcher(dbUser);
        //return matcher.matches();
        return dbUser.isEmpty();
    }
}