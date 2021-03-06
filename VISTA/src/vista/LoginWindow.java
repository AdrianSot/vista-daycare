package vista;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class LoginWindow extends javax.swing.JFrame {
    Connection con;
    public String username;
    public String password;
    public enum UserStat {AdminFailPass, RecepFailPass, RecepLogged, AdminLogged, Expired};
    public UserStat userStatus;
    public static Timer timer;
    
    public LoginWindow() {
        initComponents();
        setSize(650, 500);
        setLocation(350,150); 
        setTitle("VISTA");    
        userStatus = UserStat.AdminFailPass;
    }
 /*************************************************************************/
    
    public void Log(){
        username = tfUsername.getText();
        password = new String(pfPassword.getPassword());
        
        /* Username entre 7 y 20 caracteres sin dos caracteres especiales seguidos
        y que no empiece o termine con ellos */
        if(!username.matches("^(?=.{5,10}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$")){
            
           JOptionPane.showMessageDialog(this, "El nombre de usuario debe ser de al menos 5 "
                   + "caracteres alfanuméricos \ny puede incluir los caracteres especiales \"_\" o \".\".", 
                   "ERROR", JOptionPane.PLAIN_MESSAGE);
           tfUsername.setText("");
           pfPassword.setText("");
           
        //Mensaje para que no esté en blanco y validación para que no contenga espacios
        }else if(password.equals("")){
            JOptionPane.showMessageDialog(this, "Por favor introduzca una contraseña.", 
                    "ERROR", JOptionPane.PLAIN_MESSAGE);
        }else if(password.matches("\\s+")){
            JOptionPane.showMessageDialog(this, "La contraseña que ingresaste es incorrecta."
                    + "\nVuelve a intentarlo", "ERROR", JOptionPane.PLAIN_MESSAGE);
            pfPassword.setText("");
        }else{
            /* Consulta */
            try{
                 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VISTA?useTimezone=true&serverTimezone=UTC", "root", "");
                 Statement s = con.createStatement();

                 ResultSet UserSet = s.executeQuery("SELECT Usuario FROM Recepcionistas WHERE Usuario = '" + username + "' AND Contrasena = '"+ password + "';");
                 if(!UserSet.isBeforeFirst()){
                      userStatus = UserStat.RecepFailPass;
                     UserSet = s.executeQuery("SELECT Usuario FROM Administradores WHERE Usuario = '" + username + "' AND Contrasena = '"+ password + "';");
                     if(!UserSet.isBeforeFirst()){
                         userStatus = UserStat.AdminFailPass;
                         WarningWindow();
                         return;
                     }else{
                         //Loggeo el admin
                         try{
                            Main.w.CambiarAdmin();
                         }
                         catch(Exception e){
                             
                         }
                         
                         
                         userStatus = UserStat.AdminLogged;
                         Main.w = new MainWindow();
                         Main.w.CambiarAdmin();
                         Main.w.userStatus = MainWindow.UserStat.AdminLogged;
                         Main.w.drawingTimer.start();
                         Main.w.setVisible(true);
                         this.dispose();
                     }
                 }else{
                     //Loggeo recepcionista
                     try{
                        Main.w.CambiarRecep();
                     }
                     catch(Exception e){
                         
                     }
                     
                     userStatus = UserStat.RecepLogged;
                     Main.w = new MainWindow();
                     Main.w.CambiarRecep();
                     Main.w.userStatus = MainWindow.UserStat.RecepLogged;
                     Main.w.drawingTimer.start();
                     Main.w.setVisible(true);
                     this.dispose();
                 }

             }catch(SQLException e){
                 Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE,null, e);
                 JOptionPane.showMessageDialog(this, "Por favor intente de nuevo.", "ERROR", JOptionPane.PLAIN_MESSAGE);
            }
        }
       //userStatus = UserStat.RecepLogged; //Esto comenta porque es solo para pruebas jsjs
       //MainWindow.userStatus = MainWindow.UserStat.RecepLogged; //esto tambien
        
    }
    
    /*************************************************************************/
     public void WarningWindow()
    {
        if(userStatus == UserStat.AdminFailPass || userStatus == UserStat.RecepFailPass){
            JOptionPane.showMessageDialog(this, "La contraseña que ingresaste es incorrecta."
                    + "\nVuelve a intentarlo", "ERROR", JOptionPane.PLAIN_MESSAGE);
            pfPassword.setText("");
        }
    }
    
    /*************************************************************************/
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        LPass = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jbLog = new javax.swing.JButton();
        tfUsername = new javax.swing.JTextField();
        pfPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Apple SD Gothic Neo", 0, 36)); // NOI18N
        jLabel1.setText("VISTA");

        LPass.setFont(new java.awt.Font("Andale Mono", 0, 14)); // NOI18N
        LPass.setText("Contraseña: ");

        jLabel3.setFont(new java.awt.Font("Andale Mono", 0, 14)); // NOI18N
        jLabel3.setText("Usuario: ");

        jLabel4.setFont(new java.awt.Font("Andale Mono", 3, 14)); // NOI18N
        jLabel4.setText("Bienvenido a VISTA. Por favor inicie sesión para utilizar el software.");

        jbLog.setFont(new java.awt.Font("Andale Mono", 0, 14)); // NOI18N
        jbLog.setText("Iniciar Sesión");
        jbLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLogActionPerformed(evt);
            }
        });

        tfUsername.setFont(new java.awt.Font("Andale Mono", 0, 14)); // NOI18N
        tfUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfUsernameActionPerformed(evt);
            }
        });
        tfUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfUsernameKeyPressed(evt);
            }
        });

        pfPassword.setToolTipText("");
        pfPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pfPasswordActionPerformed(evt);
            }
        });
        pfPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pfPasswordKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(276, 276, 276)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(181, 181, 181)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LPass)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                            .addComponent(pfPassword)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(266, 266, 266)
                        .addComponent(jbLog)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(73, 73, 73)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LPass)
                    .addComponent(pfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addComponent(jbLog)
                .addContainerGap(163, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLogActionPerformed
        Log();
    }//GEN-LAST:event_jbLogActionPerformed

    private void tfUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfUsernameActionPerformed

    }//GEN-LAST:event_tfUsernameActionPerformed

    private void pfPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pfPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pfPasswordActionPerformed

    private void tfUsernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfUsernameKeyPressed
        if(evt.getKeyCode() == 10)  Log();
    }//GEN-LAST:event_tfUsernameKeyPressed

    private void pfPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pfPasswordKeyPressed
        if(evt.getKeyCode() == 10)  Log();
    }//GEN-LAST:event_pfPasswordKeyPressed

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
            java.util.logging.Logger.getLogger(LoginWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LPass;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JButton jbLog;
    private javax.swing.JPasswordField pfPassword;
    private javax.swing.JTextField tfUsername;
    // End of variables declaration//GEN-END:variables
}
