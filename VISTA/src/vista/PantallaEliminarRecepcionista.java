/*VERSION DE WINDOWS CRUDS COMPLETOS*/

package vista;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedrohdez
 */
public class PantallaEliminarRecepcionista extends javax.swing.JInternalFrame {

    /**
     * Creates new form PantallaEliminarRecepcionista
     */
    Connection con;
    int confirmacion;
    String linkbd = "jdbc:mysql://localhost:3306/VISTA?useTimezone=true&serverTimezone=UTC";
    
    public PantallaEliminarRecepcionista() {
        setSize(1000,800);
        setLocation(0,-25);
        initComponents();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(PantallaEliminarRecepcionista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void IniciarVentana(){
        tfNombre.setText("");
        tfApellido.setText("");
        tfUsuario.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tfNombre = new javax.swing.JTextField();
        tfApellido = new javax.swing.JTextField();
        tfUsuario = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 2, true));

        jLabel1.setText("Eliminar Recepcionista");

        jLabel2.setText("Nombre:");

        jLabel3.setText("Apellido:");

        jLabel4.setText("Usuario:");

        jButton1.setText("Eliminar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfApellido)
                            .addComponent(tfUsuario)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfNombre)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(121, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(116, 116, 116))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(156, 156, 156))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(0, 107, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(tfNombre.getText().equals("") || tfApellido.getText().equals("") || tfUsuario.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Por favor, rellene el formulario completo.");
        }
        else{
            confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar a: "+tfNombre.getText()+" "+tfApellido.getText()+"\n"
                                                    +"de los registros de recepcionistas?");
            if(confirmacion == 0){
                try {
                    con = DriverManager.getConnection(linkbd, "root", "");
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate("DELETE FROM Recepcionistas WHERE Nombre = '"+tfNombre.getText()+"'AND Apellido = '"+tfApellido.getText()+"' AND Usuario = '"+tfUsuario.getText()+"'");

                    if(stmt.getUpdateCount() == 0){
                        JOptionPane.showMessageDialog(null, "El/La recepcionista no existe o los datos proporcionados son incorrectos\n"
                                                      + "Por favor, intente de nuevo.");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Recepcionista eliminado con éxito.");
                        tfNombre.setText("");
                        tfApellido.setText("");
                        tfUsuario.setText("");
                    }
                } 
                catch (SQLException ex) {
                    Logger.getLogger(PantallaEliminarRecepcionista.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error. Intente de nuevo.");
                }
            }
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField tfApellido;
    private javax.swing.JTextField tfNombre;
    private javax.swing.JTextField tfUsuario;
    // End of variables declaration//GEN-END:variables
}
