package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

public class HomeScreen extends JFrame implements ActionListener{
    private JButton exitButton;
    private JLabel jLabel2;
    private JLabel jLabel8;
    private JPanel jPanel1;
    private JScrollPane jScrollPane2;
    private JSeparator jSeparator1;
    private static JTextArea serverOutput;
    private JButton startButton;
    private static ServerServiceImpl server;
    
    public HomeScreen(){
        try{
            server = new ServerServiceImpl();
            initGUI();
        }
        catch(Exception e){
            
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == startButton){
            try{
                String result = server.startServer();
                serverOutput.append(result + "\n");
                startButton.setEnabled(false);
            }
            catch(Exception e){
                serverOutput.append("Errore:\t"+e.getMessage()+"\n");
            }
        }
        if(event.getSource() == exitButton){
            try{
                server.shutDown();
                startButton.setEnabled(true);
                this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSED));
            }
            catch(Exception e){
                serverOutput.append("Errore! "+ e.getMessage() + "\n");
            }
            finally{
                
            }
        }
    }
    
    private void initGUI() {
        jPanel1 = new JPanel();
        jLabel2 = new JLabel();
        jLabel8 = new JLabel();
        jSeparator1 = new JSeparator();
        startButton = new JButton();
        exitButton = new JButton();
        jScrollPane2 = new JScrollPane();
        serverOutput = new JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(54, 150, 155));

        jLabel8.setFont(new java.awt.Font("Arial Black", 0, 36)); // NOI18N
        jLabel8.setText("Benvenuto Amministratore");

        startButton.setBackground(new java.awt.Color(0, 250, 0));
        startButton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        startButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/server/icons/play_24px.png"))); // NOI18N
        startButton.setText("Avvia Server");
        startButton.addActionListener(this);

        exitButton.setBackground(new java.awt.Color(250, 0, 0));
        exitButton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        exitButton.setText("Esci");
        exitButton.addActionListener(this);

        serverOutput.setColumns(20);
        serverOutput.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        serverOutput.setRows(5);
        serverOutput.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        serverOutput.setEnabled(false);
        jScrollPane2.setViewportView(serverOutput);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(startButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(exitButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jSeparator1)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel8)
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startButton)
                    .addComponent(exitButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }
    
    protected static void stampEvent(String event){
        serverOutput.append(event+"\n");
    }
    
}
