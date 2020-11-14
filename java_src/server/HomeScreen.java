package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

public class HomeScreen extends JFrame implements ActionListener{
    private JButton startButton;
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JSeparator jSeparator1;
    private static JTextArea serverOutput;
    
    public HomeScreen(){
        
        initGUI();
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == startButton){
            ServerMain.activateServer();
        }
    }
    
    private void initGUI() {
        jLabel1 = new JLabel();
        jSeparator1 = new JSeparator();
        startButton = new JButton();
        jScrollPane1 = new JScrollPane();
        serverOutput = new JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server Il Paroliere");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel1.setText("Benvenuto Amministratore");

        startButton.setBackground(new java.awt.Color(51, 204, 0));
        startButton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        startButton.setText("Avvia Server");

        serverOutput.setColumns(20);
        serverOutput.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        serverOutput.setRows(5);
        jScrollPane1.setViewportView(serverOutput);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 63, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(68, 68, 68))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(startButton)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }
    
    protected static void stampEvent(String event){
        serverOutput.append(event+"\n");
    }
    
}
