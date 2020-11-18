package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class DbConnectScreen extends JFrame implements ActionListener{
    private JTextField dbHost;
    private JPasswordField dbPassword;
    private JTextField dbUser;
    private JLabel jLabel1;
    private JLabel imageLabel;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JPanel jPanel1;
    private JSeparator jSeparator1;
    private JButton loginDbButton;
    private JButton resetButton;
    
    public DbConnectScreen(){
        
        initGUI();
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == loginDbButton){
            //TODO Aggiungere controlli
            if(ServerMain.connectDatabase(dbUser.getText(), new String(dbPassword.getPassword()), dbHost.getText()))
                this.setVisible(false);
            else{
                
            }
        }
        if(event.getSource() == resetButton){
            dbUser.setText("");
            dbPassword.setText("");
            dbHost.setText("");
        }
    }

    private void initGUI() {
        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        jSeparator1 = new JSeparator();
        imageLabel = new JLabel();
        jLabel3 = new JLabel();
        dbHost = new JTextField();
        dbPassword = new JPasswordField();
        dbUser = new JTextField();
        jLabel4 = new JLabel();
        jLabel5 = new JLabel();
        jLabel6 = new JLabel();
        loginDbButton = new JButton();
        resetButton = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(54, 150, 155));

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 36)); // NOI18N
        jLabel1.setText("Database Il Paroliere");

        imageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/server/icons/search_database_96px.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel3.setText("Accedi al database");

        dbHost.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        dbPassword.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        dbUser.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Host");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("User");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Password");

        loginDbButton.setBackground(new java.awt.Color(0, 250, 0));
        loginDbButton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        loginDbButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/server/icons/enter_24px.png"))); // NOI18N
        loginDbButton.setText("Login");

        resetButton.setBackground(new java.awt.Color(250, 0, 0));
        resetButton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        resetButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/server/icons/restore_page_24px.png"))); // NOI18N
        resetButton.setText("Reset");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 85, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(91, 91, 91))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dbUser, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dbPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dbHost, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(204, 204, 204))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(249, 249, 249)
                        .addComponent(imageLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(184, 184, 184)
                        .addComponent(loginDbButton)
                        .addGap(32, 32, 32)
                        .addComponent(resetButton)))
                .addGap(0, 0, Short.MAX_VALUE))
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
                    .addComponent(dbHost, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dbUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dbPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginDbButton)
                    .addComponent(resetButton))
                .addContainerGap(30, Short.MAX_VALUE))
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
