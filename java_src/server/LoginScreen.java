package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class LoginScreen extends JFrame implements ActionListener{
    private JTextField emailTxt;
    private JButton registerButton, loginButton, resetButton;
    private JLabel jLabel1, jLabel2, jLabel3, jLabel4;
    private JSeparator jSeparator1;
    private JPasswordField passwordTxt;
    
    public LoginScreen(){
        
        initGUI();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == loginButton){
            new HomeScreen().setVisible(true);
            this.setVisible(false);
        }
        if(event.getSource() == resetButton){
            emailTxt.setText("");
            passwordTxt.setText("");
        }
        if(event.getSource() == registerButton){
            new RegisterScreen().setVisible(true);
            this.setVisible(false);
        }
    }

    private void initGUI() {
        jLabel1 = new JLabel();
        jSeparator1 = new JSeparator();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        emailTxt = new JTextField();
        passwordTxt = new JPasswordField();
        loginButton = new JButton();
        resetButton = new JButton();
        registerButton = new JButton();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server Il Paroliere");
        setBackground(new java.awt.Color(0, 51, 255));

        jLabel1.setFont(new java.awt.Font("Arial", 3, 36)); // NOI18N
        jLabel1.setText("Server Il Paroliere");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel2.setText("Accedi come Amministratore");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Email");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Password");

        emailTxt.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        emailTxt.setPreferredSize(new java.awt.Dimension(77, 23));

        passwordTxt.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        loginButton.setBackground(new java.awt.Color(51, 204, 0));
        loginButton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        loginButton.setText("Accedi");

        resetButton.setBackground(new java.awt.Color(204, 0, 0));
        resetButton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        resetButton.setText("Reset");

        registerButton.setBackground(new java.awt.Color(0, 204, 204));
        registerButton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        registerButton.setText("Registrati");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(142, 142, 142)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4))
                                        .addGap(27, 27, 27)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(emailTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                            .addComponent(passwordTxt)))
                                    .addComponent(jLabel2)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(136, 136, 136)
                                .addComponent(jLabel1)))
                        .addGap(0, 146, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(registerButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(passwordTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(79, 79, 79)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginButton)
                    .addComponent(registerButton)
                    .addComponent(resetButton))
                .addContainerGap(107, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }
}
