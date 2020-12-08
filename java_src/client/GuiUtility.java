package client;

import java.awt.Color;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

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

    public static void clearTable(DefaultTableModel model) {
        while (model.getRowCount() > 0) {
            for (int i = 0; i < model.getRowCount(); ++i) {
                model.removeRow(i);
            }
        }
    }

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
