/*VERSION DE WINDOWS CRUDS COMPLETOS*/

package vista;

import java.awt.Image;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author pedrohdez
 */
public class PantallaCrearTutor extends javax.swing.JFrame {

    /**
     * Creates new form PantallaCrearTutor
     */
     Connection con;
     Statement stmt;
     
     String Teléfonos[];
     File fotoTutor;
     String NombreDeFoto;
     boolean error = false;
     CamConRec camara;
     String linkbd = "jdbc:mysql://localhost:3306/VISTA?useTimezone=true&serverTimezone=UTC";
     
    public PantallaCrearTutor() {
        setSize(1000,800);
        setLocation(0,-25);
        initComponents();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(PantallaRegistrarNiño.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void IniciarPantalla(){
        //SE LIMPIAN TODOS LOS TEXT FIELDS
        tfNombres.setText("");
        tfApellidoPaterno.setText("");
        tfApellidoMaterno.setText("");
        tfTeléfono.setText("");
        lblFoto.setIcon(null);
        lblFoto.setText("SIN FOTO");
        fotoTutor = null;
        camara = new CamConRec();
        
        //SE EXTRAEN TODOS LOS TELÉFONOS DE LA BD PARA QUE AL MOMENTO DE AJUSTAR LA INFORMACIÓN NO SE REPITAN LOS TUTORES/AUTORIZADOS
        try {
            con = DriverManager.getConnection(linkbd, "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs, rs2;
            rs = stmt.executeQuery("SELECT COUNT(*) FROM Tutores");
            rs.first();
            
            Teléfonos = new String[Integer.parseInt(rs.getObject(1).toString())];
            System.out.println("TAMAÑO DEL ARREGLO: "+Teléfonos.length);
            
            rs2 = stmt.executeQuery("SELECT Telefono FROM Tutores");
            rs2.first();
            
            for(int i = 0; i < Teléfonos.length ; i++){
                Teléfonos[i] = rs2.getObject(1).toString();
                rs2.next();
            }
            
            for(int i = 0 ; i < Teléfonos.length ; i++){
                System.out.println(Teléfonos[i]);
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNombres = new javax.swing.JLabel();
        lblApellidoPaterno = new javax.swing.JLabel();
        lblApellidoMaterno = new javax.swing.JLabel();
        lblTeléfono = new javax.swing.JLabel();
        tfNombres = new javax.swing.JTextField();
        tfApellidoPaterno = new javax.swing.JTextField();
        tfApellidoMaterno = new javax.swing.JTextField();
        tfTeléfono = new javax.swing.JTextField();
        lblFoto = new javax.swing.JLabel();
        bTomarFoto = new javax.swing.JButton();
        bGuardar = new javax.swing.JButton();
        bActualizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblNombres.setText("Nombres:");

        lblApellidoPaterno.setText("Apellido paterno:");

        lblApellidoMaterno.setText("Apellido materno:");

        lblTeléfono.setText("Teléfono:");

        lblFoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        bTomarFoto.setText("Tomar foto");
        bTomarFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTomarFotoActionPerformed(evt);
            }
        });

        bGuardar.setText("Guardar cambios");
        bGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bGuardarActionPerformed(evt);
            }
        });

        bActualizar.setText("Actualizar foto");
        bActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bActualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(255, 255, 255)
                .addComponent(bGuardar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNombres)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfNombres, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblApellidoPaterno)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfApellidoPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblApellidoMaterno)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfApellidoMaterno))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTeléfono)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfTeléfono, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(bTomarFoto)
                        .addGap(55, 55, 55)
                        .addComponent(bActualizar)))
                .addGap(63, 63, 63))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNombres)
                            .addComponent(tfNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblApellidoPaterno)
                            .addComponent(tfApellidoPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblApellidoMaterno)
                            .addComponent(tfApellidoMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTeléfono)
                            .addComponent(tfTeléfono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bTomarFoto)
                    .addComponent(bActualizar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(bGuardar)
                .addGap(148, 148, 148))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bGuardarActionPerformed
        
         for (String Teléfono : Teléfonos) {
             if ( tfTeléfono.getText().equals(Teléfono) ) {
                 JOptionPane.showMessageDialog(null, "El tutor con teléfono "+tfTeléfono.getText()+" ya existe", "ERROR", 1);
                 error = true;
                 break;
             }
         }
        
        if(tfNombres.getText().equals("") || tfApellidoPaterno.getText().equals("") || tfApellidoMaterno.getText().equals("") ||
           tfTeléfono.getText().equals("")){
            JOptionPane.showMessageDialog(null, "La información es incompleta. Revise e inténtelo de nuevo", "ERROR", 1);
            error = true;
            
        }
        
        if(!error){
             //Se introduce al niño a la base de datos
                try {
                    con = DriverManager.getConnection(linkbd, "root", "");
                    stmt = con.createStatement();
                    stmt.executeUpdate("INSERT INTO Tutores VALUES ('"+tfTeléfono.getText()+"','"+tfNombres.getText()+"','"+tfApellidoPaterno.getText()+"','"+
                            tfApellidoMaterno.getText()+"','" +(fotoTutor == null ? "No" : "Fotos Tutores/"+fotoTutor.getName())+"','null', 'Tutor')");
                } 
                catch (SQLException ex) {
                    Logger.getLogger(PantallaRegistrarNiño.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error en el registro del niño.\nIntente de nuevo.");
                }
                
                JOptionPane.showMessageDialog(null, "Se ha creado al tutor");
                dispose();
        }
        else error = false;
       
    }//GEN-LAST:event_bGuardarActionPerformed

    private void bTomarFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTomarFotoActionPerformed
        
        if(tfNombres.getText().equals("") || tfApellidoPaterno.getText().equals("")|| tfApellidoMaterno.getText().equals("") ||
              tfTeléfono.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Primero rellene el formulario del tutor", "ERROR", 1);
        }
        else{
            NombreDeFoto = "Fotos Tutores/tutor"+tfTeléfono.getText();
            JOptionPane.showMessageDialog(null, "Asegúrese de tomar la foto correctamente(Intento único)\n"
                    + "Guarde la imagen con el mismo nombre y teléfono asignados al tutor", "ADVERTENCIA", 1);
            camara.IniciaCamaraPro("tutor"+tfTeléfono.getText(), "Fotos Tutores");
        }
           
    }//GEN-LAST:event_bTomarFotoActionPerformed

    private void bActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bActualizarActionPerformed
        fotoTutor = new File("Fotos Tutores/tutor"+tfTeléfono.getText()+".jpg");
        Image foto = getToolkit().getImage(fotoTutor.getAbsolutePath());
        foto = foto.getScaledInstance(260, 260, 260);
        lblFoto.setIcon(new ImageIcon(foto));
    }//GEN-LAST:event_bActualizarActionPerformed

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
            java.util.logging.Logger.getLogger(PantallaCrearTutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaCrearTutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaCrearTutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaCrearTutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaCrearTutor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bActualizar;
    private javax.swing.JButton bGuardar;
    private javax.swing.JButton bTomarFoto;
    private javax.swing.JLabel lblApellidoMaterno;
    private javax.swing.JLabel lblApellidoPaterno;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JLabel lblNombres;
    private javax.swing.JLabel lblTeléfono;
    private javax.swing.JTextField tfApellidoMaterno;
    private javax.swing.JTextField tfApellidoPaterno;
    private javax.swing.JTextField tfNombres;
    private javax.swing.JTextField tfTeléfono;
    // End of variables declaration//GEN-END:variables
}
