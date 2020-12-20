/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import entity.UserData;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JTextField;
import server.ServerServiceStub;
import utils.CryptMD5;

/**
 *
 * @author Edoardo
 */
public class RegisterUser extends javax.swing.JDialog {

    private UserData newUser;
    private boolean isFieldLocked;
    ServerServiceStub stub;

    /**
     * Creates new form RegisterUser
     */
    public RegisterUser(java.awt.Frame parent, boolean modal, ServerServiceStub stub) {
        super(parent, modal);
        initComponents();
        isFieldLocked = false;
        this.stub = stub;
    }

    private RegisterUser(JFrame jFrame, boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        label_registrati = new javax.swing.JLabel();
        text_name = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        text_surname = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        text_username = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        text_email = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        text_password = new javax.swing.JPasswordField();
        jSeparator2 = new javax.swing.JSeparator();
        text_repeatPassword = new javax.swing.JPasswordField();
        jSeparator3 = new javax.swing.JSeparator();
        btn_register = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jSeparator7 = new javax.swing.JSeparator();
        label_verifica = new javax.swing.JLabel();
        text_verificationCode = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        btn_verify = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setLayout(new java.awt.GridLayout(1, 2));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        label_registrati.setBackground(new java.awt.Color(137, 109, 156));
        label_registrati.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        label_registrati.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_registrati.setText("1. Registrati");

        text_name.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        text_name.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        text_name.setText("nome");
        text_name.setToolTipText("");
        text_name.setBorder(null);
        text_name.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                text_nameMouseClicked(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));

        text_surname.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        text_surname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        text_surname.setText("cognome");
        text_surname.setBorder(null);
        text_surname.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                text_surnameMouseClicked(evt);
            }
        });

        jSeparator4.setBackground(new java.awt.Color(0, 0, 0));

        text_username.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        text_username.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        text_username.setText("username");
        text_username.setBorder(null);
        text_username.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                text_usernameMouseClicked(evt);
            }
        });

        jSeparator5.setBackground(new java.awt.Color(0, 0, 0));

        text_email.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        text_email.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        text_email.setText("email");
        text_email.setBorder(null);
        text_email.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                text_emailMouseClicked(evt);
            }
        });

        jSeparator6.setBackground(new java.awt.Color(0, 0, 0));

        text_password.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        text_password.setText("password");
        text_password.setBorder(null);
        text_password.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                text_passwordMouseClicked(evt);
            }
        });

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));

        text_repeatPassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        text_repeatPassword.setText("password");
        text_repeatPassword.setBorder(null);
        text_repeatPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                text_repeatPasswordMouseClicked(evt);
            }
        });

        jSeparator3.setBackground(new java.awt.Color(0, 0, 0));

        btn_register.setBackground(new java.awt.Color(79, 36, 107));
        btn_register.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        btn_register.setForeground(new java.awt.Color(255, 255, 255));
        btn_register.setText("REGISTRATI");
        btn_register.setBorder(null);
        btn_register.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(label_registrati, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(text_repeatPassword, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(text_password, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(text_email, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(text_username, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(text_surname, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(text_name, javax.swing.GroupLayout.Alignment.TRAILING))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(btn_register, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(51, 51, 51))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(label_registrati, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(text_name, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(text_surname, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(text_username, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(text_email, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(text_password, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(text_repeatPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_register, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jSeparator7.setForeground(new java.awt.Color(79, 36, 107));
        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(79, 36, 107), 60));
        jSeparator7.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jSeparator7.setPreferredSize(new java.awt.Dimension(50, 20));

        label_verifica.setBackground(new java.awt.Color(137, 109, 156));
        label_verifica.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        label_verifica.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_verifica.setText("2. Verifica");

        text_verificationCode.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        text_verificationCode.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        text_verificationCode.setText("Codice di Verifica");
        text_verificationCode.setBorder(null);
        text_verificationCode.setEnabled(false);
        text_verificationCode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                text_verificationCodeMouseClicked(evt);
            }
        });

        jSeparator8.setBackground(new java.awt.Color(0, 0, 0));

        btn_verify.setBackground(new java.awt.Color(79, 36, 107));
        btn_verify.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        btn_verify.setForeground(new java.awt.Color(255, 255, 255));
        btn_verify.setText("VERIFICA");
        btn_verify.setBorder(null);
        btn_verify.setEnabled(false);
        btn_verify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_verifyActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Immetti il codice che abbiamo inviato");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label_verifica, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(text_verificationCode, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                                    .addComponent(jSeparator8)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(btn_verify, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(label_verifica, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(115, 115, 115)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(text_verificationCode, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_verify, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 289, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1024, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1024, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void text_verificationCodeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_text_verificationCodeMouseClicked
        // TODO add your handling code here:
        this.text_verificationCode.setText("");
    }//GEN-LAST:event_text_verificationCodeMouseClicked

    private void btn_registerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registerActionPerformed
        boolean registered = false;
        //CHECK not empty
        if (GuiUtility.isEmpty(this.text_email) || GuiUtility.isEmpty(this.text_name) || GuiUtility.isEmpty(this.text_surname) || GuiUtility.isEmpty(this.text_username) || GuiUtility.isEmpty(this.text_password) || GuiUtility.isEmpty(this.text_repeatPassword)) {
            showMessageDialog(null, "Compilare tutti i campi");
            return;
        }
        //CHECK email
        if (GuiUtility.isEmailCorrect(this.text_email.getText()) == false) {
            showMessageDialog(null, "Formato Email errato");
            return;
        }
        //CHECK psw = repeatPsw
        if (GuiUtility.isPasswordMatching(this.text_password, this.text_password) == false) {
            showMessageDialog(null, "Le password non coincidono");
            return;
        }
        //LOCK fields
        this.isFieldLocked = true;
        //DISABLE fileds
        this.btn_register.setEnabled(false);
        this.text_email.setEnabled(false);
        this.text_name.setEnabled(false);
        this.text_surname.setEnabled(false);
        this.text_username.setEnabled(false);
        this.text_password.setEnabled(false);
        this.text_repeatPassword.setEnabled(false);
        //CREATE newUser Obj
        newUser = new UserData();
        newUser.setEmail(this.text_email.getText());
        newUser.setFirstName(this.text_name.getText());
        newUser.setLastName(this.text_surname.getText());
        newUser.setNickname(this.text_username.getText());
        newUser.setPassword(CryptMD5.crypt(String.valueOf(this.text_password.getPassword())));
        try {
            //CALL Register Method --> da sistemare
            registered = stub.register(newUser);
        } catch (RemoteException ex) {
            Logger.getLogger(RegisterUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        //CHECK registration
        if (registered) {
            //ENABLE verification part
            this.text_verificationCode.setEnabled(true);
            this.btn_verify.setEnabled(true);
        } else {
            //IF error, unlock fields so user can try again..
            showMessageDialog(null, "Errore durante la registrazione...");
            this.text_verificationCode.setEnabled(false);
            this.btn_verify.setEnabled(false);
            this.isFieldLocked = false;
            this.btn_register.setEnabled(true);
            this.text_email.setEnabled(true);
            this.text_name.setEnabled(true);
            this.text_surname.setEnabled(true);
            this.text_username.setEnabled(true);
            this.text_password.setEnabled(true);
            this.text_repeatPassword.setEnabled(true);

        }
    }//GEN-LAST:event_btn_registerActionPerformed

    private void btn_verifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_verifyActionPerformed
        boolean res = false;

        try {
            res = this.stub.verifyUser(this.text_verificationCode.getText(), this.text_username.getText());
        } catch (RemoteException ex) {
            Logger.getLogger(RegisterUser.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (res) {
            showMessageDialog(null, "Account verificato con successo!");
            this.setVisible(false);
            this.dispose();
        }
    }//GEN-LAST:event_btn_verifyActionPerformed

    private void text_nameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_text_nameMouseClicked
        // TODO add your handling code here:
        if (!isFieldLocked)
            this.text_name.setText("");
    }//GEN-LAST:event_text_nameMouseClicked

    private void text_surnameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_text_surnameMouseClicked
        // TODO add your handling code here:
        if (!isFieldLocked)
            this.text_surname.setText("");
    }//GEN-LAST:event_text_surnameMouseClicked

    private void text_usernameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_text_usernameMouseClicked
        // TODO add your handling code here:
        if (!isFieldLocked)
            this.text_username.setText("");
    }//GEN-LAST:event_text_usernameMouseClicked

    private void text_emailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_text_emailMouseClicked
        // TODO add your handling code here:
        if (!isFieldLocked)
            this.text_email.setText("");
    }//GEN-LAST:event_text_emailMouseClicked

    private void text_passwordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_text_passwordMouseClicked
        // TODO add your handling code here:
        if (!isFieldLocked)
            this.text_password.setText("");
    }//GEN-LAST:event_text_passwordMouseClicked

    private void text_repeatPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_text_repeatPasswordMouseClicked
        // TODO add your handling code here:
        if (!isFieldLocked)
            this.text_repeatPassword.setText("");
    }//GEN-LAST:event_text_repeatPasswordMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RegisterUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegisterUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegisterUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegisterUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                RegisterUser dialog = new RegisterUser(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_register;
    private javax.swing.JButton btn_verify;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JLabel label_registrati;
    private javax.swing.JLabel label_verifica;
    private javax.swing.JTextField text_email;
    private javax.swing.JTextField text_name;
    private javax.swing.JPasswordField text_password;
    private javax.swing.JPasswordField text_repeatPassword;
    private javax.swing.JTextField text_surname;
    private javax.swing.JTextField text_username;
    private javax.swing.JTextField text_verificationCode;
    // End of variables declaration//GEN-END:variables
}
