package server;

import database.Database;
import database.DatabaseImpl;
import entity.UserData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class LoginScreen extends JFrame implements ActionListener{
    private JTextField usernameTxt;
    private JLabel imageLabel;
    private JLabel jLabel1;
    private JLabel jLabel3;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JPanel jPanel1;
    private JSeparator jSeparator1;
    private JButton loginButton;
    private JPasswordField passwordTxt;
    private JButton resetButton;
    private ServerUtilityGui utility;
    
    public LoginScreen(){
        utility = new ServerUtilityGui();
        
        initGUI();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == loginButton){
            String username = usernameTxt.getText();
            String password = String.valueOf(passwordTxt.getPassword());
            
            String controlResult = utility.controlLoginResult(username, password);
            
            if(controlResult == null){
                //TODO Aggiungere controlli accesso con DB
                Database dbReference = DatabaseImpl.getDatabase();
                UserData administrator = dbReference.getUser(username);
                if(administrator != null){
                    this.setVisible(false);
                    ServerMain.showHome();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Credenziali Errate!","Login Amministratore", JOptionPane.ERROR_MESSAGE);
                    usernameTxt.setText("");
                    passwordTxt.setText("");
                }
            }
            else{
                JOptionPane.showMessageDialog(null, controlResult,"Login Amministratore", JOptionPane.ERROR_MESSAGE);
            }
        }
        if(event.getSource() == resetButton){
            usernameTxt.setText("");
            passwordTxt.setText("");
        }
    }

    private void initGUI() {
        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        jSeparator1 = new JSeparator();
        imageLabel = new JLabel();
        jLabel3 = new JLabel();
        passwordTxt = new JPasswordField();
        usernameTxt = new JTextField();
        jLabel5 = new JLabel();
        jLabel6 = new JLabel();
        loginButton = new JButton();
        resetButton = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(54, 150, 155));

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 36)); // NOI18N
        jLabel1.setText("Server Il Paroliere");

        imageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/server/icons/manager_96px.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel3.setText("Accedi al Server");

        passwordTxt.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        usernameTxt.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Username");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Password");

        loginButton.setBackground(new java.awt.Color(0, 250, 0));
        loginButton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        loginButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/server/icons/enter_24px.png"))); // NOI18N
        loginButton.setText("Login");
        loginButton.addActionListener(this);

        resetButton.setBackground(new java.awt.Color(250, 0, 0));
        resetButton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        resetButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/server/icons/restore_page_24px.png"))); // NOI18N
        resetButton.setText("Reset");
        resetButton.addActionListener(this);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(249, 249, 249)
                .addComponent(imageLabel)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 118, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(usernameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(passwordTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabel3)))
                                .addGap(205, 205, 205))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(115, 115, 115))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(loginButton)
                                .addGap(37, 37, 37)
                                .addComponent(resetButton)
                                .addGap(186, 186, 186))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(imageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resetButton)
                    .addComponent(loginButton))
                .addContainerGap(78, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }
}
