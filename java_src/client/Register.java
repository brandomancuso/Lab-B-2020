/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author Edoardo
 */
public class Register extends javax.swing.JPanel {

    /**
     * Creates new form Register1
     */
    public Register() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        jPanel1 = new javax.swing.JPanel();
        jSeparator7 = new javax.swing.JSeparator();
        label_verifica = new javax.swing.JLabel();
        text_verificationCode = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        btn_verify = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.GridLayout(1, 2));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        label_registrati.setBackground(new java.awt.Color(137, 109, 156));
        label_registrati.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        label_registrati.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_registrati.setText("1. Registrati");

        text_name.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        text_name.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        text_name.setText("nome");
        text_name.setBorder(null);
        text_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_nameActionPerformed(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));

        text_surname.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        text_surname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        text_surname.setText("cognome");
        text_surname.setBorder(null);
        text_surname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_surnameActionPerformed(evt);
            }
        });

        jSeparator4.setBackground(new java.awt.Color(0, 0, 0));

        text_username.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        text_username.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        text_username.setText("username");
        text_username.setBorder(null);
        text_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_usernameActionPerformed(evt);
            }
        });

        jSeparator5.setBackground(new java.awt.Color(0, 0, 0));

        text_email.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        text_email.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        text_email.setText("email");
        text_email.setBorder(null);
        text_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_emailActionPerformed(evt);
            }
        });

        jSeparator6.setBackground(new java.awt.Color(0, 0, 0));

        text_password.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        text_password.setText("jPasswordField1");
        text_password.setBorder(null);

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));

        text_repeatPassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        text_repeatPassword.setText("jPasswordField1");
        text_repeatPassword.setBorder(null);

        jSeparator3.setBackground(new java.awt.Color(0, 0, 0));

        btn_register.setBackground(new java.awt.Color(79, 36, 107));
        btn_register.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        btn_register.setForeground(new java.awt.Color(255, 255, 255));
        btn_register.setText("REGISTRATI");
        btn_register.setBorder(null);

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
                        .addGap(30, 30, 30)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator4)
                            .addComponent(text_surname)
                            .addComponent(jSeparator1)
                            .addComponent(jSeparator3)
                            .addComponent(text_repeatPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                            .addComponent(jSeparator2)
                            .addComponent(text_password)
                            .addComponent(jSeparator6)
                            .addComponent(text_email)
                            .addComponent(jSeparator5)
                            .addComponent(text_username)
                            .addComponent(text_name)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(btn_register, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(59, Short.MAX_VALUE))
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

        add(jPanel3);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

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
        text_verificationCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_verificationCodeActionPerformed(evt);
            }
        });

        jSeparator8.setBackground(new java.awt.Color(0, 0, 0));

        btn_verify.setBackground(new java.awt.Color(79, 36, 107));
        btn_verify.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        btn_verify.setForeground(new java.awt.Color(255, 255, 255));
        btn_verify.setText("VERIFICA");
        btn_verify.setBorder(null);
        btn_verify.setEnabled(false);

        jLabel1.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Immetti il codice che ti abbiamo inviato");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(label_verifica, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(text_verificationCode, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jSeparator8))
                                .addGap(61, 61, 61))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(btn_verify, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(label_verifica, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(116, 116, 116)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(text_verificationCode, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_verify, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 289, Short.MAX_VALUE))
        );

        add(jPanel1);
    }// </editor-fold>//GEN-END:initComponents

    private void text_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_nameActionPerformed

    private void text_surnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_surnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_surnameActionPerformed

    private void text_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_usernameActionPerformed

    private void text_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_emailActionPerformed

    private void text_verificationCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_verificationCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_verificationCodeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_register;
    private javax.swing.JButton btn_verify;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
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
