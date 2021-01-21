package client;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * classe di utility
 * @author Edoardo
 */
public class GuiUtility {

    /**
     * controlla il formato di una email
     * @param parEmail
     * @return 
     */
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

    /**
     * controlla che due campi password contengano lo stesso testo
     * @param parPsw1
     * @param parPsw2
     * @return 
     */
    public static boolean isPasswordMatching(JPasswordField parPsw1, JPasswordField parPsw2) {
        boolean result = true;
        if (!Arrays.equals(parPsw1.getPassword(), parPsw2.getPassword())) {
            result = false;
        }
        return result;
    }

    /**
     * controlla che un campo di testo non sia vuoto
     * @param input
     * @return 
     */
    public static boolean isEmpty(JTextField input) {
        boolean result = false;
        if (input.getText().equals("")) {
            result = true;
        }
        return result;
    }

    /**
     * controlla che un campo password non sia vuoto
     * @param password
     * @return 
     */
    public static boolean isEmpty(JPasswordField password) {
        boolean result = false;
        if (password.getPassword().equals("")) {
            result = true;
        }
        return result;
    }

    /**
     * cancella tutte le righe di un table model
     * @param model 
     */
    public static void clearTable(DefaultTableModel model) {
        while (model.getRowCount() > 0) {
            for (int i = 0; i < model.getRowCount(); ++i) {
                model.removeRow(i);
            }
        }
    }

    /**
     * crea un table model di n colonne non editabile dall'utente
     * @param parNumCols
     * @return 
     */
    public static DefaultTableModel createCustomTableModel(int parNumCols) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (int i = 0; i < parNumCols; i++) {
            model.addColumn("");
        }

        return model;
    }
}
