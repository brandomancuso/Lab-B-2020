/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import entity.GameData;
import entity.UserData;
import java.awt.Color;
import java.awt.Component;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import server.game.ServerGameStub;

/**
 *
 * @author Edoardo
 */
public class GameWin extends javax.swing.JDialog {

    ServerGameStub gameStub;
    ClientGameImpl clientGame;
    DefaultListModel wordListModel;
    DefaultTableModel scoreTableModel;
    int sessionNum = 1;
    String gameName = "";
    Map<String, Integer> storePointPlayer;
    UserData loggedUser;

    /**
     * Creates new form GameWin
     */
    public GameWin(java.awt.Dialog parent, boolean modal, String gameName, UserData parLoggedUser) {
        super(parent, modal);
        storePointPlayer = new HashMap<>();
        wordListModel = new DefaultListModel<String>();
        scoreTableModel = GuiUtility.createCustomTableModel(2);
        initComponents();
        initScoreTable();
        this.gameName = gameName;
        this.loggedUser = parLoggedUser;
        this.jLabelGameName.setText("Partita: " + this.gameName);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanelLeft = new javax.swing.JPanel();
        btn_game_leave = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableScore = new javax.swing.JTable();
        jLabelGameScore = new javax.swing.JLabel();
        jLabelGameSession = new javax.swing.JLabel();
        jLabelGameName = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabelGameTime = new javax.swing.JLabel();
        btn_game_addWord = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList_gameWord = new javax.swing.JList<>();
        text_game_addWord = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel_timerValue = new javax.swing.JLabel();
        jPanelGrid = new javax.swing.JPanel();
        lableGrid1 = new javax.swing.JLabel();
        lableGrid2 = new javax.swing.JLabel();
        lableGrid3 = new javax.swing.JLabel();
        lableGrid4 = new javax.swing.JLabel();
        lableGrid5 = new javax.swing.JLabel();
        lableGrid6 = new javax.swing.JLabel();
        lableGrid7 = new javax.swing.JLabel();
        lableGrid8 = new javax.swing.JLabel();
        lableGrid9 = new javax.swing.JLabel();
        lableGrid10 = new javax.swing.JLabel();
        lableGrid11 = new javax.swing.JLabel();
        lableGrid12 = new javax.swing.JLabel();
        lableGrid13 = new javax.swing.JLabel();
        lableGrid14 = new javax.swing.JLabel();
        lableGrid15 = new javax.swing.JLabel();
        lableGrid16 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanelLeft.setBackground(new java.awt.Color(176, 157, 189));

        btn_game_leave.setBackground(new java.awt.Color(232, 17, 35));
        btn_game_leave.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        btn_game_leave.setForeground(new java.awt.Color(255, 255, 255));
        btn_game_leave.setText("ABBANDONA");
        btn_game_leave.setBorder(null);
        btn_game_leave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_game_leaveActionPerformed(evt);
            }
        });

        jTableScore.setModel(scoreTableModel);
        jScrollPane2.setViewportView(jTableScore);

        jLabelGameScore.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        jLabelGameScore.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelGameScore.setText("Giocatori e Punteggi");

        jLabelGameSession.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabelGameSession.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelGameSession.setText("Sessione Corrente: #1");

        jLabelGameName.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabelGameName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelGameName.setText("Partita: sampleGame");

        javax.swing.GroupLayout jPanelLeftLayout = new javax.swing.GroupLayout(jPanelLeft);
        jPanelLeft.setLayout(jPanelLeftLayout);
        jPanelLeftLayout.setHorizontalGroup(
            jPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLeftLayout.createSequentialGroup()
                .addGroup(jPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLeftLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(btn_game_leave, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 34, Short.MAX_VALUE))
                    .addGroup(jPanelLeftLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jLabelGameName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabelGameSession, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabelGameScore, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelLeftLayout.setVerticalGroup(
            jPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLeftLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelGameName, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabelGameSession, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelGameScore, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_game_leave, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jSeparator7.setForeground(new java.awt.Color(79, 36, 107));
        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(79, 36, 107), 60));
        jSeparator7.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jSeparator7.setPreferredSize(new java.awt.Dimension(50, 20));

        jPanel3.setBackground(new java.awt.Color(137, 109, 156));

        jLabel2.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Il Paroliere");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabelGameTime.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabelGameTime.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelGameTime.setText("Tempo Rimanente:");

        btn_game_addWord.setBackground(new java.awt.Color(53, 184, 53));
        btn_game_addWord.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        btn_game_addWord.setForeground(new java.awt.Color(255, 255, 255));
        btn_game_addWord.setText("AGGIUNGI");
        btn_game_addWord.setBorder(null);
        btn_game_addWord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_game_addWordActionPerformed(evt);
            }
        });

        jList_gameWord.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        jList_gameWord.setModel(wordListModel);
        jList_gameWord.setEnabled(false);
        jScrollPane1.setViewportView(jList_gameWord);

        text_game_addWord.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        text_game_addWord.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        text_game_addWord.setText("parola");
        text_game_addWord.setBorder(null);
        text_game_addWord.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                text_addWordMouseClicked(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel_timerValue.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        jLabel_timerValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_timerValue.setText("30");

        jPanelGrid.setBackground(new java.awt.Color(215, 206, 222));
        jPanelGrid.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(79, 36, 107)));
        jPanelGrid.setLayout(new java.awt.GridLayout(4, 4, 5, 5));

        lableGrid1.setBackground(new java.awt.Color(255, 255, 255));
        lableGrid1.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        lableGrid1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lableGrid1.setText("A");
        jPanelGrid.add(lableGrid1);

        lableGrid2.setBackground(new java.awt.Color(255, 255, 255));
        lableGrid2.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        lableGrid2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lableGrid2.setText("B");
        jPanelGrid.add(lableGrid2);

        lableGrid3.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        lableGrid3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lableGrid3.setText("C");
        jPanelGrid.add(lableGrid3);

        lableGrid4.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        lableGrid4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lableGrid4.setText("D");
        jPanelGrid.add(lableGrid4);

        lableGrid5.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        lableGrid5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lableGrid5.setText("E");
        jPanelGrid.add(lableGrid5);

        lableGrid6.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        lableGrid6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lableGrid6.setText("F");
        jPanelGrid.add(lableGrid6);

        lableGrid7.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        lableGrid7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lableGrid7.setText("G");
        jPanelGrid.add(lableGrid7);

        lableGrid8.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        lableGrid8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lableGrid8.setText("H");
        jPanelGrid.add(lableGrid8);

        lableGrid9.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        lableGrid9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lableGrid9.setText("I");
        jPanelGrid.add(lableGrid9);

        lableGrid10.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        lableGrid10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lableGrid10.setText("J");
        jPanelGrid.add(lableGrid10);

        lableGrid11.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        lableGrid11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lableGrid11.setText("K");
        jPanelGrid.add(lableGrid11);

        lableGrid12.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        lableGrid12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lableGrid12.setText("L");
        jPanelGrid.add(lableGrid12);

        lableGrid13.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        lableGrid13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lableGrid13.setText("M");
        jPanelGrid.add(lableGrid13);

        lableGrid14.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        lableGrid14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lableGrid14.setText("N");
        jPanelGrid.add(lableGrid14);

        lableGrid15.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        lableGrid15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lableGrid15.setText("O");
        jPanelGrid.add(lableGrid15);

        lableGrid16.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        lableGrid16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lableGrid16.setText("P");
        jPanelGrid.add(lableGrid16);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanelLeft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabelGameTime)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel_timerValue, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanelGrid, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(text_game_addWord)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addComponent(btn_game_addWord, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(text_game_addWord, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanelGrid, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_game_addWord, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelGameTime, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_timerValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(15, 15, 15))
                    .addComponent(jSeparator7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelLeft, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void openResultWindow() {
        ResultWin guiResult = new ResultWin(this, true, this.gameName, this.loggedUser);
        guiResult.setClientGameStub(clientGame);
        guiResult.setServerGameStub(gameStub);
        guiResult.setSessionNum(sessionNum);
        this.clientGame.setGuiResult(guiResult);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                guiResult.setVisible(true);
            }
        });
    }

    private void initScoreTable() {
        //TABLE settings
        DefaultTableCellRenderer headerBgRender = new DefaultTableCellRenderer();
        headerBgRender.setBackground(Color.decode("#FFFFFF"));
        headerBgRender.setForeground(Color.decode("#FFFFFF"));
        DefaultTableCellRenderer rightAlignmentRender = new DefaultTableCellRenderer();
        rightAlignmentRender.setHorizontalAlignment(JLabel.RIGHT);

        this.jTableScore.getTableHeader().getColumnModel().getColumn(0).setHeaderRenderer(headerBgRender);
        this.jTableScore.getTableHeader().getColumnModel().getColumn(1).setHeaderRenderer(headerBgRender);
        this.jTableScore.getColumnModel().getColumn(1).setCellRenderer(rightAlignmentRender);
        this.jTableScore.getColumnModel().getColumn(0).setPreferredWidth(350);
        this.jTableScore.getColumnModel().getColumn(1).setPreferredWidth(50);
        this.jScrollPane2.getViewport().setBackground(Color.white);
    }

    public void setSessionNum(int parSession) {
        this.sessionNum = parSession;
        updateSessionNum();
    }

    public void setClientGameStub(ClientGameImpl clientGameImpl) {
        this.clientGame = clientGameImpl;
    }

    public void setServerGameStub(ServerGameStub serverGameStub) {
        this.gameStub = serverGameStub;
    }

    public void setPlayerList(List<String> parPlayer) {
        for (String tmp : parPlayer) {
            this.storePointPlayer.put(tmp, 0);
        }
    }

    public void disableInput() {
        this.btn_game_addWord.setEnabled(false);
        this.btn_game_leave.setEnabled(false);
    }

    private void updateSessionNum() {
        this.jLabelGameSession.setText("Sessione Corrente: #" + String.valueOf(sessionNum));
    }

    public void updateTimer(int value) {
        this.jLabel_timerValue.setText(value + "");
    }

    public List<String> getPlayerWords() {
        List<String> playerWords = new ArrayList<String>();

        for (int i = 0; i < wordListModel.getSize(); i++) {
            playerWords.add(String.valueOf(wordListModel.getElementAt(i)));
        }

        return playerWords;
    }

    public void fillGameGrid(String[] grid) {
        int cnt = 0;
        for (Component tmp : this.jPanelGrid.getComponents()) {
            if (tmp instanceof JLabel) {
                JLabel a = (JLabel) tmp;
                a.setText(grid[cnt]);
                cnt++;
            }

        }
    }

    private void btn_game_addWordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_game_addWordActionPerformed

        if (!this.text_game_addWord.getText().equals("")) {
            wordListModel.addElement(this.text_game_addWord.getText());
        }
    }//GEN-LAST:event_btn_game_addWordActionPerformed

    private void text_addWordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_text_addWordMouseClicked
        // TODO add your handling code here:
        this.text_game_addWord.setText("");
    }//GEN-LAST:event_text_addWordMouseClicked

    private void btn_game_leaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_game_leaveActionPerformed
        try {
            this.gameStub.leaveGame(this.loggedUser.getNickname());
        } catch (RemoteException ex) {
            Logger.getLogger(GameWin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btn_game_leaveActionPerformed

    //UTILITY
    public void fillScoreTable() {
        //va chimamato alla creazione? ma se nn la ricreo ogni volta....
        Object rowData[] = new Object[2];
        GuiUtility.clearTable(scoreTableModel);
        if (this.sessionNum != 1) {
            this.storePointPlayer = this.clientGame.getStorePointPlayer();
        }
        if (!this.storePointPlayer.isEmpty()) {
            for (Map.Entry<String, Integer> tmp : storePointPlayer.entrySet()) {
                if (tmp != null) {
                    rowData[0] = tmp.getKey();
                    rowData[1] = String.valueOf(tmp.getValue());
                    scoreTableModel.addRow(rowData);
                }
            }
        }
    }

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
            java.util.logging.Logger.getLogger(GameWin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameWin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameWin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameWin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GameWin dialog = new GameWin(new javax.swing.JDialog(), true, "", null);
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
    private javax.swing.JButton btn_game_addWord;
    private javax.swing.JButton btn_game_leave;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelGameName;
    private javax.swing.JLabel jLabelGameScore;
    private javax.swing.JLabel jLabelGameSession;
    private javax.swing.JLabel jLabelGameTime;
    private javax.swing.JLabel jLabel_timerValue;
    private javax.swing.JList<String> jList_gameWord;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelGrid;
    private javax.swing.JPanel jPanelLeft;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JTable jTableScore;
    private javax.swing.JLabel lableGrid1;
    private javax.swing.JLabel lableGrid10;
    private javax.swing.JLabel lableGrid11;
    private javax.swing.JLabel lableGrid12;
    private javax.swing.JLabel lableGrid13;
    private javax.swing.JLabel lableGrid14;
    private javax.swing.JLabel lableGrid15;
    private javax.swing.JLabel lableGrid16;
    private javax.swing.JLabel lableGrid2;
    private javax.swing.JLabel lableGrid3;
    private javax.swing.JLabel lableGrid4;
    private javax.swing.JLabel lableGrid5;
    private javax.swing.JLabel lableGrid6;
    private javax.swing.JLabel lableGrid7;
    private javax.swing.JLabel lableGrid8;
    private javax.swing.JLabel lableGrid9;
    private javax.swing.JTextField text_game_addWord;
    // End of variables declaration//GEN-END:variables
}
