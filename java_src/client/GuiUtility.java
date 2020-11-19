package client;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Edoardo
 */
public class GuiUtility {

    public static boolean isEmailCorrect(String parEmail) {
        boolean result = true;
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(parEmail);
        if (!matcher.matches()) {
            result = false;
        }
        return result;
    }

    public static boolean isPasswordMatching(JPasswordField parPsw1, JPasswordField parPsw2) {
        boolean result = true;
        if (!Arrays.equals(parPsw1.getPassword(), parPsw2.getPassword())) {
            result = false;
        }
        return result;
    }

    public static boolean isEmpty(JTextField input) {
        boolean result = false;
        if (input.getText().equals("")) {
            result = true;
        }
        return result;
    }

    public static boolean isEmpty(JPasswordField password) {
        boolean result = false;
        if (password.getPassword().equals("")) {
            result = true;
        }
        return result;
    }
}
