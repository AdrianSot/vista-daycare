package vista;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class PantallaActualizarRecepcionista extends javax.swing.JInternalFrame {

    Connection con;
    boolean exito = true;
    
    public PantallaActualizarRecepcionista() {
        initComponents();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
                    } catch (ClassNotFoundException ex) {
            Logger.getLogger(PantallaActualizarRecepcionista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void IniciarVentana(){
        tfNombre.setText("");
        tfUsuario.setText("");
        tfUsuario2.setText("");
        tfApellido.setText("");
        tfContraseña.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LblTitulo = new javax.swing.JLabel();
        LblRecep = new javax.swing.JLabel();
        LblInfo = new javax.swing.JLabel();
        LblNombre = new javax.swing.JLabel();
        tfUsuario2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tfNombre = new javax.swing.JTextField();
        tfApellido = new javax.swing.JTextField();
        tfUsuario = new javax.swing.JTextField();
        tfContraseña = new javax.swing.JTextField();
        BActualizar = new javax.swing.JButton();

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        LblTitulo.setText("Actualizar información de recepcionistas");

        LblRecep.setText("Recepcionista a actualizar");

        LblInfo.setText("Información a actualizar");

        LblNombre.setText("Nombre de usuario:");

        jLabel1.setText("Nombre:");

        jLabel2.setText("Apellido:");

        jLabel3.setText("Usuario:");

        jLabel4.setText("Contraseña:");

        BActualizar.setText("Actualizar Información");
        BActualizar.setToolTipText("");
        BActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BActualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(LblTitulo)
                .addGap(134, 134, 134))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(LblRecep))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(LblNombre)
                                .addGap(3, 3, 3)
                                .addComponent(tfUsuario2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(90, 90, 90)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel1))
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(4, 4, 4)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(tfUsuario, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfApellido, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfNombre, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfContraseña, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(LblInfo))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(184, 184, 184)
                        .addComponent(BActualizar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(LblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LblRecep)
                    .addComponent(LblInfo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(LblNombre)
                    .addComponent(tfUsuario2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(tfUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(BActualizar)
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BActualizarActionPerformed
       
        
        if(tfUsuario2.getText().equals("")){
           JOptionPane.showMessageDialog(null, "Error. Por favor rellene el Campo: 'Nombre de usuario'.");
        }
        else{
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VISTA", "root", "");    
                Statement stmt = con.createStatement();
                    
                if(tfNombre.getText().isEmpty() && tfApellido.getText().isEmpty() && tfUsuario.getText().isEmpty() && !tfContraseña.getText().isEmpty()){
                    stmt.executeUpdate("UPDATE Recepcionistas SET Contrasena = '"+tfContraseña.getText()+"' WHERE Usuario = '"+tfUsuario2.getText()+"'");
                    exito = true;
                }
                if(tfNombre.getText().isEmpty() && tfApellido.getText().isEmpty() && !tfUsuario.getText().isEmpty() && tfContraseña.getText().isEmpty()){
                    stmt.executeUpdate("UPDATE Recepcionistas SET Usuario = '"+tfUsuario.getText()+"' WHERE Usuario = '"+tfUsuario2.getText()+"'");
                    exito = true;
                }
                if(tfNombre.getText().isEmpty() && tfApellido.getText().isEmpty() && !tfUsuario.getText().isEmpty() && !tfContraseña.getText().isEmpty()){
                    stmt.executeUpdate("UPDATE Recepcionistas SET Usuario = '"+tfUsuario.getText()+"', Contrasena = '"+tfContraseña.getText()+
                                       "' WHERE Usuario = '"+tfUsuario2.getText()+"'");
                    exito = true;
                }
                if(tfNombre.getText().isEmpty() && !tfApellido.getText().isEmpty() && tfUsuario.getText().isEmpty() && tfContraseña.getText().isEmpty()){
                    stmt.executeUpdate("UPDATE Recepcionistas SET Apellido = '"+tfApellido.getText()+"' WHERE Usuario = '"+tfUsuario2.getText()+"'");
                    exito = true;
                }
                if(tfNombre.getText().isEmpty() && !tfApellido.getText().isEmpty() && tfUsuario.getText().isEmpty() && !tfContraseña.getText().isEmpty()){
                    stmt.executeUpdate("UPDATE Recepcionistas SET Apellido = '"+tfApellido.getText()+"', Contrasena = '"+tfContraseña.getText()+
                                       "' WHERE Usuario = '"+tfUsuario2.getText()+"'");
                    exito = true;
                }
                if(tfNombre.getText().isEmpty() && !tfApellido.getText().isEmpty() && !tfUsuario.getText().isEmpty() && tfContraseña.getText().isEmpty()){
                    stmt.executeUpdate("UPDATE Recepcionistas SET Apellido = '"+tfApellido.getText()+"', Usuario = '"+tfUsuario.getText()+
                                       "' WHERE Usuario = '"+tfUsuario2.getText()+"'");
                    exito = true;
                }
                if(tfNombre.getText().isEmpty() && !tfApellido.getText().isEmpty() && !tfUsuario.getText().isEmpty() && !tfContraseña.getText().isEmpty()){
                    stmt.executeUpdate("UPDATE Recepcionistas SET Contrasena = '"+tfContraseña.getText()+"', Apellido = '"+tfApellido.getText()+
                                       "', Usuario = '"+tfUsuario.getText()+"' WHERE Usuario = '"+tfUsuario2.getText()+"'");
                    exito = true;
                }
                if(!tfNombre.getText().isEmpty() && tfApellido.getText().isEmpty() && tfUsuario.getText().isEmpty() && tfContraseña.getText().isEmpty()){
                    stmt.executeUpdate("UPDATE Recepcionistas SET Nombre = '"+tfNombre.getText()+"' WHERE Usuario = '"+tfUsuario2.getText()+"'");
                    exito = true;
                }
                if(!tfNombre.getText().isEmpty() && tfApellido.getText().isEmpty() && tfUsuario.getText().isEmpty() && !tfContraseña.getText().isEmpty()){
                    stmt.executeUpdate("UPDATE Recepcionistas SET Nombre = '"+tfNombre.getText()+"', Contrasena = '"+tfContraseña.getText()+
                                       "' WHERE Usuario = '"+tfUsuario2.getText()+"'");
                    exito = true;
                }
                if(!tfNombre.getText().isEmpty() && tfApellido.getText().isEmpty() && !tfUsuario.getText().isEmpty() && tfContraseña.getText().isEmpty()){
                    stmt.executeUpdate("UPDATE Recepcionistas SET Nombre = '"+tfNombre.getText()+"', Usuario = '"+tfUsuario.getText()+
                                       "' WHERE Usuario = '"+tfUsuario2.getText()+"'");
                    exito = true;
                }
                if(!tfNombre.getText().isEmpty() && tfApellido.getText().isEmpty() && !tfUsuario.getText().isEmpty() && !tfContraseña.getText().isEmpty()){
                    stmt.executeUpdate("UPDATE Recepcionistas SET Nombre = '"+tfNombre.getText()+"', Usuario = '"+tfUsuario.getText()+
                                       "', Contrasena = '"+tfContraseña.getText()+"' WHERE Usuario = '"+tfUsuario2.getText()+"'");
                    exito = true;
                }
               if(!tfNombre.getText().isEmpty() && !tfApellido.getText().isEmpty() && tfUsuario.getText().isEmpty() && tfContraseña.getText().isEmpty()){
                    stmt.executeUpdate("UPDATE Recepcionistas SET Nombre = '"+tfNombre.getText()+"', Apellido = '"+tfApellido.getText()+
                                       "' WHERE Usuario = '"+tfUsuario2.getText()+"'");
                    exito = true;
                }
                if(!tfNombre.getText().isEmpty() && !tfApellido.getText().isEmpty() && tfUsuario.getText().isEmpty() && !tfContraseña.getText().isEmpty()){
                    stmt.executeUpdate("UPDATE Recepcionistas SET Nombre = '"+tfNombre.getText()+"', Apellido = '"+tfApellido.getText()+
                                       "', Contrasena = '"+tfContraseña.getText()+"' WHERE Usuario = '"+tfUsuario2.getText()+"'");
                    exito = true;
                }
                if(!tfNombre.getText().isEmpty() && !tfApellido.getText().isEmpty() && !tfUsuario.getText().isEmpty() && tfContraseña.getText().isEmpty()){
                    stmt.executeUpdate("UPDATE Recepcionistas SET Apellido = '"+tfApellido.getText()+"', Usuario = '"+tfUsuario.getText()+
                                       "', Nombre = '"+tfNombre.getText()+"' WHERE Usuario = '"+tfUsuario2.getText()+"'");
                    exito = true;
                }
                if(!tfNombre.getText().isEmpty() && !tfApellido.getText().isEmpty() && !tfUsuario.getText().isEmpty() && !tfContraseña.getText().isEmpty()){
                    stmt.executeUpdate("UPDATE Recepcionistas SET Nombre = '"+tfNombre.getText()+"', Apellido = '"+tfApellido.getText()+"', Usuario = '"+tfUsuario.getText()+
                                       "', Contrasena = '"+tfContraseña.getText()+"' WHERE Usuario = '"+tfUsuario2.getText()+"'");
                    exito = true;
                }
                if(tfNombre.getText().isEmpty() && tfApellido.getText().isEmpty() && tfUsuario.getText().isEmpty() && tfContraseña.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Por favor, rellene el formulario.");
                    exito = false;
                }
                
                if(stmt.getUpdateCount() == 0 && !(tfNombre.getText().isEmpty() && tfApellido.getText().isEmpty() && 
                   tfUsuario.getText().isEmpty() && tfContraseña.getText().isEmpty())){
                    JOptionPane.showMessageDialog(null, "Error. El recepcionista no existe. Intente de nuevo.");
                    exito = false;
                }
                if(exito){
                    JOptionPane.showMessageDialog(null, "Se han actualizado correctamente los datos");
                    tfApellido.setText("");
                    tfContraseña.setText("");
                    tfNombre.setText("");
                    tfUsuario.setText("");
                }
                
            } 
            catch (SQLException ex) {
                Logger.getLogger(PantallaActualizarRecepcionista.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error. Intente de nuevo.");
            }
            
        }
       
    }//GEN-LAST:event_BActualizarActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BActualizar;
    private javax.swing.JLabel LblInfo;
    private javax.swing.JLabel LblNombre;
    private javax.swing.JLabel LblRecep;
    private javax.swing.JLabel LblTitulo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField tfApellido;
    private javax.swing.JTextField tfContraseña;
    private javax.swing.JTextField tfNombre;
    private javax.swing.JTextField tfUsuario;
    private javax.swing.JTextField tfUsuario2;
    // End of variables declaration//GEN-END:variables
}