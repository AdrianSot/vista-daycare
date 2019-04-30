/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebasvista;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Image;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.ImageIcon;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author pedrohdez
 */
public class PantallaRegistrarNiño extends javax.swing.JInternalFrame {

    /**
     * Creates new form PantallaRegistrarNIño
     */
    String nombresNiño, apellidoPaternoNiño, apellidoMaternoNiño,nombresTutor, 
           apellidoPaternoTutor, apellidoMaternoTutor, telefonoTutor, nombresAutorizado,
           apellidoPaternoAutorizado, apellidoMaternoAutorizado, teléfonoAutorizado;
    String[] nombresAutorizados = {"","",""};
    String[] apellidosPaternosAutorizados = {"","",""};
    String[] apellidosMaternosAutorizados = {"","",""};
    String[] teléfonosAutorizados ={"","",""};
    
    Image fotoNiño, fotoTutor, fotoAutorizado;
    ImageIcon fotoNiño2, fotoTutor2, fotoAutorizado2;
    ImageIcon[] fotosAutorizados = {null,null,null};
    File archivoNiño, archivoTutor, archivoAutorizado1, archivoAutorizado2, archivoAutorizado3, file, file2, file3;
    String idNiño = null;
    int confirmacion;
    int numAutorizados = 0;
    boolean errorFinal = false, errorBaseDatos = false;
    enum Donde {niños, tutor, autorizados}
    Donde donde = Donde.niños;
    int autorizadoSeleccionado;
    Connection con;
    Statement stmt;
    String directorioRaiz;
    String ruta = null;
    String contenido = null;
    public PantallaRegistrarNiño() {
        initComponents();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(PantallaRegistrarNiño.class.getName()).log(Level.SEVERE, null, ex);
        }
        directorioRaiz = System.getProperty("user.dir");
        fotoTutor = null;
        bAnterior.setVisible(false);
        lblTeléfono.setVisible(false);
        tfTeléfono.setVisible(false);
        cbAutorizados.setVisible(false);
        nombresNiño = new String();
        apellidoPaternoNiño = new String();
        apellidoMaternoNiño = new String();
        nombresTutor = new String();
        apellidoPaternoTutor = new String();
        apellidoMaternoTutor = new String();
        telefonoTutor = new String();
        nombresAutorizado = new String();
        apellidoPaternoAutorizado = new String();
        apellidoMaternoAutorizado= new String();
        teléfonoAutorizado = new String();
        autorizadoSeleccionado = 0;
        cbAutorizados.setSelectedIndex(4);
        cbNumAutorizados.setSelectedIndex(3);
        lblNumAutorizados.setVisible(false);
        cbNumAutorizados.setVisible(false);
    }
    
    public void IniciarVentana(){
        
        tfNombres.setText("");
        tfApellidoPaterno.setText("");
        tfApellidoMaterno.setText("");
        tfTeléfono.setText("");
        
        
        for(int i = 0 ; i < 3 ; i++){
            nombresAutorizados[i] = "";
            apellidosPaternosAutorizados[i] = "";
            apellidosMaternosAutorizados[i] = "";
            teléfonosAutorizados[i] = "";
            fotosAutorizados[i] = null;
        }
        
        nombresNiño = apellidoPaternoNiño = apellidoMaternoNiño = nombresTutor = 
        apellidoPaternoTutor = apellidoMaternoTutor = telefonoTutor = nombresAutorizado
        = apellidoPaternoAutorizado = apellidoMaternoAutorizado = teléfonoAutorizado = "";
        
        fotoNiño = fotoTutor = fotoAutorizado = null;
        fotoNiño2 = fotoTutor2 = fotoAutorizado2 = null;
        file = file2 = file3 = null;
        directorioRaiz = System.getProperty("user.dir");   
        ruta = null;
        contenido = null;
        archivoNiño = archivoTutor = archivoAutorizado1 = archivoAutorizado2 = archivoAutorizado3 = null;
        idNiño = null;
        confirmacion = 999;
        numAutorizados = 0;
        errorFinal = errorBaseDatos = false;
        donde = Donde.niños;
        autorizadoSeleccionado = 0;
        con = null;
        stmt = null;
        
        bAnterior.setVisible(false);
        bSiguiente.setVisible(true);
        bSiguiente.setText("Siguiente");
        lblTeléfono.setVisible(false);
        tfTeléfono.setVisible(false);
        cbAutorizados.setVisible(false);
        cbAutorizados.setSelectedIndex(4);
        cbNumAutorizados.setSelectedIndex(3);
        lblNumAutorizados.setVisible(false);
        cbNumAutorizados.setVisible(false);
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
        lblInformacion = new javax.swing.JLabel();
        lblNombres = new javax.swing.JLabel();
        lblApellidoPaterno = new javax.swing.JLabel();
        tfNombres = new javax.swing.JTextField();
        tfApellidoPaterno = new javax.swing.JTextField();
        bTomarFoto = new javax.swing.JButton();
        imagen = new javax.swing.JLabel();
        bSiguiente = new javax.swing.JButton();
        lblApellidoMaterno = new javax.swing.JLabel();
        tfApellidoMaterno = new javax.swing.JTextField();
        bAnterior = new javax.swing.JButton();
        lblTeléfono = new javax.swing.JLabel();
        tfTeléfono = new javax.swing.JTextField();
        cbAutorizados = new javax.swing.JComboBox<>();
        cbNumAutorizados = new javax.swing.JComboBox<>();
        lblNumAutorizados = new javax.swing.JLabel();

        jLabel1.setText("Registrar niño");

        lblInformacion.setText("Información del niño");

        lblNombres.setText("Nombres:");

        lblApellidoPaterno.setText("Apellido paterno:");

        bTomarFoto.setText("Tomar foto");
        bTomarFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTomarFotoActionPerformed(evt);
            }
        });

        imagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imagen.setText("SIN FOTO");
        imagen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        bSiguiente.setText("Siguiente");
        bSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSiguienteActionPerformed(evt);
            }
        });

        lblApellidoMaterno.setText("Apellido materno:");

        bAnterior.setText("Anterior");
        bAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAnteriorActionPerformed(evt);
            }
        });

        lblTeléfono.setText("Teléfono:");

        cbAutorizados.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Autorizado 1", "Autorizado 2", "Autorizado 3", "Sin autorizados", "Lista de autorizados" }));
        cbAutorizados.setToolTipText("");
        cbAutorizados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAutorizadosActionPerformed(evt);
            }
        });

        cbNumAutorizados.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "..." }));

        lblNumAutorizados.setText("Número de autorizados:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(bAnterior)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bSiguiente)
                                .addGap(21, 21, 21))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblApellidoMaterno)
                                                .addGap(12, 12, 12)
                                                .addComponent(tfApellidoMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblTeléfono)
                                                .addGap(70, 70, 70)
                                                .addComponent(tfTeléfono, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblApellidoPaterno)
                                                .addGap(18, 18, 18)
                                                .addComponent(tfApellidoPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(cbAutorizados, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(lblNumAutorizados)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(cbNumAutorizados, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGap(32, 32, 32)
                                                        .addComponent(lblInformacion)))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(lblNombres)
                                                    .addGap(68, 68, 68)
                                                    .addComponent(tfNombres, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGap(159, 159, 159)
                                        .addComponent(imagen, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(436, 436, 436)
                                        .addComponent(bTomarFoto)))
                                .addGap(0, 12, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(297, 297, 297)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                        .addComponent(imagen, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(lblInformacion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbNumAutorizados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNumAutorizados))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbAutorizados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNombres))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfApellidoPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblApellidoPaterno))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(lblApellidoMaterno))
                            .addComponent(tfApellidoMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(lblTeléfono))
                            .addComponent(tfTeléfono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(12, 12, 12)
                .addComponent(bTomarFoto)
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bSiguiente)
                    .addComponent(bAnterior))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bTomarFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTomarFotoActionPerformed
        
        JFileChooser ficAbrirArchivo = new JFileChooser();
        ficAbrirArchivo.setFileFilter(new FileNameExtensionFilter("archivo de imagen", "jpg", "jpeg"));
        int respuesta=ficAbrirArchivo.showOpenDialog(this);
        
        if(respuesta==JFileChooser.APPROVE_OPTION ){
            if(!tfTeléfono.isVisible()){
                archivoNiño = ficAbrirArchivo.getSelectedFile();
                Image foto = getToolkit().getImage(archivoNiño.getAbsolutePath());
                foto = foto.getScaledInstance(260, 260, 260);
                imagen.setIcon(new ImageIcon(foto));
            }
            if(tfTeléfono.isVisible() && !cbAutorizados.isVisible()){
                archivoTutor = ficAbrirArchivo.getSelectedFile();
                Image foto = getToolkit().getImage(archivoTutor.getAbsolutePath());
                foto = foto.getScaledInstance(260, 260, 260);
                imagen.setIcon(new ImageIcon(foto));
            }
            if(cbAutorizados.isVisible()){
                if(autorizadoSeleccionado == 0){
                    archivoAutorizado1 = ficAbrirArchivo.getSelectedFile();
                    Image foto = getToolkit().getImage(archivoAutorizado1.getAbsolutePath());
                    foto = foto.getScaledInstance(260, 260, 260);
                    imagen.setIcon(new ImageIcon(foto));
                }
                if(autorizadoSeleccionado == 1){
                    archivoAutorizado2 = ficAbrirArchivo.getSelectedFile();
                    Image foto = getToolkit().getImage(archivoAutorizado2.getAbsolutePath());
                    foto = foto.getScaledInstance(260, 260, 260);
                    imagen.setIcon(new ImageIcon(foto));
                }
                if(autorizadoSeleccionado == 2){
                    archivoAutorizado3 = ficAbrirArchivo.getSelectedFile();
                    Image foto = getToolkit().getImage(archivoAutorizado3.getAbsolutePath());
                    foto = foto.getScaledInstance(260, 260, 260);
                    imagen.setIcon(new ImageIcon(foto));
                }
            }
        }
    }//GEN-LAST:event_bTomarFotoActionPerformed

    private void bSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSiguienteActionPerformed
   
        if(donde == Donde.niños){
            nombresNiño = tfNombres.getText();
            apellidoPaternoNiño = tfApellidoPaterno.getText();
            apellidoMaternoNiño = tfApellidoMaterno.getText();
            try{
                fotoNiño = getToolkit().getImage(archivoNiño.getAbsolutePath());
                fotoNiño2 = new ImageIcon(fotoNiño.getScaledInstance(260, 260, 260));
            }
            catch (Exception e){

            }
        }
        
            
        if( (nombresNiño.equals("") || apellidoPaternoNiño.equals("") || 
           apellidoMaternoNiño.equals("") || archivoNiño == null)){
            
            if(tfNombres.getText().equals("")){
                tfNombres.setBackground(Color.red);
                JOptionPane.showMessageDialog(null, "Datos incompletos. Por favor, ingrese \nlos nombres del niño.");
                tfNombres.setBackground(Color.white);
            }
            if(tfApellidoPaterno.getText().equals("")){
                tfApellidoPaterno.setBackground(Color.red);
                JOptionPane.showMessageDialog(null, "Datos incompletos. Por favor, ingrese el \n apellido paterno del niño.");
                tfApellidoPaterno.setBackground(Color.white);
            }
            if(tfApellidoMaterno.getText().equals("")){
                tfApellidoMaterno.setBackground(Color.red);
                JOptionPane.showMessageDialog(null, "Datos incompletos. Por favor, ingrese el \n apellido materno del niño.");
                tfApellidoMaterno.setBackground(Color.white);
                
            }
            if(archivoNiño == null){
                imagen.setForeground(Color.red);
                JOptionPane.showMessageDialog(null, "Datos incompletos. Por favor, ingrese una \n foto del niño.");
                imagen.setForeground(Color.black);
            }
            
        }
        
        else if(donde == Donde.niños){
            nombresNiño = tfNombres.getText();
            apellidoPaternoNiño = tfApellidoPaterno.getText();
            apellidoMaternoNiño = tfApellidoMaterno.getText();
            tfNombres.setText(nombresTutor);
            tfApellidoPaterno.setText(apellidoPaternoTutor);
            tfApellidoMaterno.setText(apellidoMaternoTutor);
            imagen.setIcon(fotoTutor2);
            imagen.setText("SIN FOTO");
            lblTeléfono.setVisible(true);
            tfTeléfono.setVisible(true);
            tfTeléfono.setText(telefonoTutor);
            lblInformacion.setText("Información del Tutor");
            bAnterior.setVisible(true);
            donde = Donde.tutor;
        }
        else if(donde == Donde.tutor){
            nombresTutor = tfNombres.getText();
            apellidoPaternoTutor = tfApellidoPaterno.getText();
            apellidoMaternoTutor = tfApellidoMaterno.getText();
            telefonoTutor = tfTeléfono.getText();
            if((nombresTutor.equals("") || apellidoPaternoTutor.equals("") || 
               apellidoMaternoTutor.equals("") || telefonoTutor.equals("") || archivoTutor == null)){
                
                if(tfNombres.getText().equals("")){
                    tfNombres.setBackground(Color.red);
                    JOptionPane.showMessageDialog(null, "Datos incompletos. Por favor, ingrese \nlos nombres del tutor.");
                    tfNombres.setBackground(Color.white);
                }
                if(tfApellidoPaterno.getText().equals("")){
                    tfApellidoPaterno.setBackground(Color.red);
                    JOptionPane.showMessageDialog(null, "Datos incompletos. Por favor, ingrese el \n apellido paterno del tutor.");
                    tfApellidoPaterno.setBackground(Color.white);
                }
                if(tfApellidoMaterno.getText().equals("")){
                    tfApellidoMaterno.setBackground(Color.red);
                    JOptionPane.showMessageDialog(null, "Datos incompletos. Por favor, ingrese el \n apellido materno del tutor.");
                    tfApellidoMaterno.setBackground(Color.white);

                }
                if(tfTeléfono.getText().equals("")){
                    tfTeléfono.setBackground(Color.red);
                    JOptionPane.showMessageDialog(null, "Datos incompletos. Por favor, ingrese el \n teléfono del tutor.");
                    tfTeléfono.setBackground(Color.white);

                }
                if(archivoTutor == null){
                    imagen.setForeground(Color.red);
                    JOptionPane.showMessageDialog(null, "Datos incompletos. Por favor, ingrese una \n foto del tutor.");
                    imagen.setForeground(Color.black);
                }
            }
            else{
                imagen.setText("SIN FOTO");
                lblInformacion.setText("Información de las personas autorizadas");
                bSiguiente.setText("Finalizar registro");
                bAnterior.setVisible(true);
                donde = Donde.autorizados;
                cbAutorizados.setVisible(true);
                lblNumAutorizados.setVisible(true);
                cbNumAutorizados.setVisible(true);
                
                if(autorizadoSeleccionado == 0){
                    tfNombres.setText(nombresAutorizados[0]);
                    tfApellidoPaterno.setText(apellidosPaternosAutorizados[0]);
                    tfApellidoMaterno.setText(apellidosMaternosAutorizados[0]);
                    tfTeléfono.setText(teléfonosAutorizados[0]);
                    imagen.setIcon(fotosAutorizados[0]);
                    
                }
                if(autorizadoSeleccionado == 1){
                    tfNombres.setText(nombresAutorizados[1]);
                    tfApellidoPaterno.setText(apellidosPaternosAutorizados[1]);
                    tfApellidoMaterno.setText(apellidosMaternosAutorizados[1]);
                    tfTeléfono.setText(teléfonosAutorizados[1]);
                    imagen.setIcon(fotosAutorizados[1]);
                }
                if(autorizadoSeleccionado == 2){
                    tfNombres.setText(nombresAutorizados[2]);
                    tfApellidoPaterno.setText(apellidosPaternosAutorizados[2]);
                    tfApellidoMaterno.setText(apellidosMaternosAutorizados[2]);
                    tfTeléfono.setText(teléfonosAutorizados[2]);
                    imagen.setIcon(fotosAutorizados[2]);
                }
                if(autorizadoSeleccionado == 4){
                    tfNombres.setText("");
                    tfApellidoPaterno.setText("");
                    tfApellidoMaterno.setText("");
                    tfTeléfono.setText("");
                    imagen.setIcon(null);
                }
            }
            
        }
        else if (donde == Donde.autorizados){
            cbAutorizados.actionPerformed(evt);
            if(cbAutorizados.getSelectedIndex() == 3){
                
                if(JOptionPane.showConfirmDialog(null, "¿Está seguro de que no desea agregar ningún autorizado?") == 0){
                    numAutorizados = 0;
                     confirmacion = JOptionPane.showConfirmDialog(null, "Niño: "+nombresNiño+" "+apellidoPaternoNiño+" "+apellidoMaternoNiño+"\n"+
                            "Tutor: "+nombresTutor+" "+apellidoPaternoTutor+" "+apellidoMaternoTutor+" ; Teléfono: "+telefonoTutor,
                            "CONFIRME LOS DATOS",1);
                }
            }
            else{
                cbNumAutorizados.actionPerformed(evt);
                if(cbNumAutorizados.getSelectedIndex() == 0) numAutorizados = 1;
                if(cbNumAutorizados.getSelectedIndex() == 1) numAutorizados = 2;
                if(cbNumAutorizados.getSelectedIndex() == 2) numAutorizados = 3;
                if(cbNumAutorizados.getSelectedIndex() == 3) numAutorizados = 0;
                
                try{
                    for(int i = 0 ; i < numAutorizados ; i++){
                        if(nombresAutorizados[i].equals("") || apellidosPaternosAutorizados[i].equals("") 
                           || apellidosMaternosAutorizados[i].equals("") || teléfonosAutorizados[i].equals("")){

                            JOptionPane.showMessageDialog(null, "Datos incompletos. Por favor, revise la información e inténtelo de nuevo.");
                            errorFinal = true;
                            break;
                        }
                        errorFinal = false;
                    }
                    if((cbAutorizados.getSelectedIndex() == 4 && nombresAutorizados[0].equals("") 
                       && nombresAutorizados[1].equals("") && nombresAutorizados[2].equals("") && 
                       apellidosPaternosAutorizados[0].equals("") && apellidosPaternosAutorizados[1].equals("") 
                       && apellidosPaternosAutorizados[2].equals("") && apellidosMaternosAutorizados[0].equals("")
                       && apellidosMaternosAutorizados[1].equals("") && apellidosMaternosAutorizados[2].equals("") 
                       && teléfonosAutorizados[0].equals("") && teléfonosAutorizados[1].equals("") && teléfonosAutorizados[2].equals(""))
                       || cbNumAutorizados.getSelectedIndex()==3){
                        errorFinal = true;
                    }
                    
                }
                catch(HeadlessException e){
                    
                }
                
                if(!errorFinal){
                
                    if(numAutorizados == 3){
                        confirmacion = JOptionPane.showConfirmDialog(null, "Niño: "+nombresNiño+" "+apellidoPaternoNiño+" "+apellidoMaternoNiño+"\n"+
                            "Tutor: "+nombresTutor+" "+apellidoPaternoTutor+" "+apellidoMaternoTutor+" ; Teléfono: "+telefonoTutor+"\n"+
                            "Autorizado 1: "+nombresAutorizados[0]+" "+apellidosPaternosAutorizados[0]+" "+apellidosMaternosAutorizados[0]+
                            " ; Teléfono: "+teléfonosAutorizados[0]+" ; Foto: "+(fotosAutorizados[0] == null ? "No" : "Sí" )+"\n"+"Autorizado 2: "+
                            nombresAutorizados[1]+" "+apellidosPaternosAutorizados[1]+" "+apellidosMaternosAutorizados[1]+" ; Teléfono: "+
                            teléfonosAutorizados[1]+" ; Foto: "+(fotosAutorizados[1] == null ? "No" : "Sí" )+"\n"+"Autorizado 3: "+nombresAutorizados[2]+
                            " "+apellidosPaternosAutorizados[2]+" "+apellidosMaternosAutorizados[2]+" ; Teléfono: "+teléfonosAutorizados[2]+" ; Foto: "+
                            (fotosAutorizados[2] == null ? "No" : "Sí" ), "CONFIRME LOS DATOS",0);
                    }
                    if(numAutorizados == 2){
                        confirmacion = JOptionPane.showConfirmDialog(null, "Niño: "+nombresNiño+" "+apellidoPaternoNiño+" "+apellidoMaternoNiño+"\n"+
                            "Tutor: "+nombresTutor+" "+apellidoPaternoTutor+" "+apellidoMaternoTutor+" ; Teléfono: "+telefonoTutor+"\n"+
                            "Autorizado 1: "+nombresAutorizados[0]+" "+apellidosPaternosAutorizados[0]+" "+apellidosMaternosAutorizados[0]+" "+
                            "Teléfono: "+teléfonosAutorizados[0]+" ; Foto: "+(fotosAutorizados[0] == null ? "No" : "Sí" )+"\n"+"Autorizado 2: "+
                            nombresAutorizados[1]+" "+apellidosPaternosAutorizados[1]+" "+apellidosMaternosAutorizados[1]+" "+"Teléfono: "+
                            teléfonosAutorizados[1]+" ; Foto: "+(fotosAutorizados[1] == null ? "No" : "Sí" ), "CONFIRME LOS DATOS",0);
                    }
                    if(numAutorizados == 1){
                        confirmacion = JOptionPane.showConfirmDialog(null, "Niño: "+nombresNiño+" "+apellidoPaternoNiño+" "+apellidoMaternoNiño+"\n"+
                            "Tutor: "+nombresTutor+" "+apellidoPaternoTutor+" "+apellidoMaternoTutor+" ; Teléfono: "+telefonoTutor+"\n"+
                            "Autorizado 1: "+nombresAutorizados[0]+" "+apellidosPaternosAutorizados[0]+" "+apellidosMaternosAutorizados[0]+" "+
                            "Teléfono: "+teléfonosAutorizados[0]+" ; Foto: "+(fotosAutorizados[0] == null ? "No" : "Sí" ), "CONFIRME LOS DATOS",0);
                    }
                }
            }
            
            //COMIENZA EL REGISTRO EN LA BASE DE DATOS
            if(confirmacion == 0 && !errorFinal){
                
                try {
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VISTA", "root", "");
                    stmt = con.createStatement();
                    stmt.executeUpdate("INSERT INTO Ninos (Nombres,Apellido_paterno,Apellido_materno,Foto,Tutor,Autorizados) VALUES ('"+nombresNiño+"','"+apellidoPaternoNiño+"','"+apellidoMaternoNiño+"','"+archivoNiño.getAbsolutePath()+"','"+telefonoTutor+"','a');");
                } 
                catch (SQLException ex) {
                    Logger.getLogger(PantallaRegistrarNiño.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error en el registro del niño.\nIntente de nuevo.");
                    errorBaseDatos = true;
                }
                
                //Se extrae el ID del niño
                if(!errorBaseDatos){
                    try {
                        stmt = con.createStatement();
                        ResultSet rs;
                        rs = stmt.executeQuery("SELECT ID FROM Ninos WHERE Nombres = '"+nombresNiño+"' AND Apellido_paterno = '"+apellidoPaternoNiño+"' AND Apellido_materno = '"+apellidoMaternoNiño+"' AND Foto = '"+archivoNiño.getAbsolutePath()+"' AND Autorizados = 'a'");
                        rs.first();
                        idNiño =  rs.getString(1);
                    } catch (SQLException ex) {
                        Logger.getLogger(PantallaRegistrarNiño.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error en el registro del niño.\nIntente de nuevo.");
                        errorBaseDatos = true;
                    }
                }
                
                if(!errorBaseDatos){
                    //Se crea el contenido del archivo con las llaves primarias de los autorizados  
                    ruta = null;
                    contenido = null;
                    file = null;
                    switch (numAutorizados) {
                        case 1:
                            contenido = teléfonosAutorizados[0];
                            break;
                        case 2:
                            contenido = teléfonosAutorizados[0]+"\n"+teléfonosAutorizados[1];
                            break;
                        case 3:
                            contenido = teléfonosAutorizados[0]+"\n"+teléfonosAutorizados[1]+"\n"+teléfonosAutorizados[2];
                            break;
                        default:
                            break;
                    }

                    //Se crea el archivo que contiene a todos los autorizados
                    if(numAutorizados != 0){
                        try {
                            ruta = directorioRaiz+"/Autorizados/Autorizados"+idNiño+".txt";
                            file = new File(ruta);
                            // Si el archivo no existe es creado
                            if (!file.exists()) {
                                file.createNewFile();
                            }
                            FileWriter fw = new FileWriter(file);
                            BufferedWriter bw = new BufferedWriter(fw);
                            bw.write(contenido);
                            bw.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    //Se agrega a la información del niño la ruta del archivo que contiene a sus autorizados
                    try {
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VISTA", "root", "");
                        stmt = con.createStatement();
                        stmt.executeUpdate("UPDATE Ninos SET Autorizados = '"+ruta+"' WHERE ID = "+idNiño);
                    } 
                    catch (SQLException ex) {
                        Logger.getLogger(PantallaRegistrarNiño.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error en el registro del niño.\nIntente de nuevo.");
                        try{
                            file.delete();
                        }
                        catch(HeadlessException e){
                            
                        }
                        
                        try {
                            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VISTA", "root", "");
                            stmt = con.createStatement();
                            stmt.executeUpdate("DELETE FROM Ninos WHERE ID = "+idNiño);
                        } 
                        catch (SQLException ex2) {
                            Logger.getLogger(PantallaRegistrarNiño.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, "Ha ocurrido un error en el registro del niño.\nIntente de nuevo.");
                            errorBaseDatos = true;
                        }
                        errorBaseDatos = true;
                    }
                    
                }
                
                
                //Se agrega el tutor a la base de datos
                if(!errorBaseDatos){
                    try {
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VISTA", "root", "");
                        stmt = con.createStatement();
                        stmt.executeUpdate("INSERT INTO Tutores VALUES ('"+telefonoTutor+"','"+nombresTutor+"','"+apellidoPaternoTutor+"','"+apellidoMaternoTutor+"','"+archivoTutor+"','a','Tutor')");
                    } 
                    catch (SQLException ex) {
                        Logger.getLogger(PantallaRegistrarNiño.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error en el registro del tutor. (TELÉFONO REPETIDO)\nIntente de nuevo.");
                        try{
                            file.delete();
                        }
                        catch(HeadlessException e){
                            
                        }
                        try {
                            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VISTA", "root", "");
                            stmt = con.createStatement();
                            stmt.executeUpdate("DELETE FROM Ninos WHERE ID = "+idNiño);
                        } 
                        catch (SQLException ex2) {
                            Logger.getLogger(PantallaRegistrarNiño.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, "Ha ocurrido un error en el registro del niño.\nIntente de nuevo.");
                            errorBaseDatos = true;
                        }
                         errorBaseDatos = true;
                    }
                    
                }
                
                
                if(!errorBaseDatos){
                    //Se crea el archivo con los niños del tutor.
                    try {
                        ruta = directorioRaiz+"/Niños(tutores)/NiñosTutor_"+telefonoTutor+".txt";
                        System.out.println(ruta);
                        contenido = idNiño;
                        file2 = new File(ruta);
                        // Si el archivo no existe es creado
                        if (!file2.exists()) {
                            file2.createNewFile();
                        }
                        FileWriter fw = new FileWriter(file2);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write(contenido);
                        bw.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //Se agrega a la info del tutor la ruta del archivo con los niños de éste
                    try {
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VISTA", "root", "");
                        stmt = con.createStatement();
                        stmt.executeUpdate("UPDATE Tutores SET Ninos ='"+ruta+"' WHERE Telefono = '"+telefonoTutor+"'");
                    } 
                    catch (SQLException ex) {
                        Logger.getLogger(PantallaRegistrarNiño.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error en el registro del tutor.\nIntente de nuevo.");
                        
                        try{
                            file.delete();
                            file2.delete();
                        }
                        catch(HeadlessException e){
                            
                        }
                        try {
                            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VISTA", "root", "");
                            stmt = con.createStatement();
                            stmt.executeUpdate("DELETE FROM Ninos WHERE ID = "+idNiño);
                        } 
                        catch (SQLException ex2) {
                            Logger.getLogger(PantallaRegistrarNiño.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, "Ha ocurrido un error en el registro del niño.\nIntente de nuevo.");
                            errorBaseDatos = true;
                        }
                        
                        errorBaseDatos = true;
                    }
                    
                }
                
                
                if(!errorBaseDatos){
                    
                    //Se crean los archivos de los autorizados
                    for(int i = 0 ; i < numAutorizados ; i++){
                    
                        try {
                            ruta = directorioRaiz+"/Niños(autorizados)/NiñosAutorizados_"+teléfonosAutorizados[i]+".txt";
                            contenido = idNiño;
                            file3 = new File(ruta);
                            // Si el archivo no existe es creado
                            if (!file3.exists()) {
                                file3.createNewFile();
                                try {
                                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VISTA", "root", "");
                                    stmt = con.createStatement();
                                    if(i == 0) stmt.executeUpdate("INSERT INTO Tutores VALUES ('"+teléfonosAutorizados[i]+"','"+nombresAutorizados[i]+"','"+apellidosPaternosAutorizados[i]+"','"+apellidosMaternosAutorizados[i]+"','"+(archivoAutorizado1 == null ? "No" : archivoAutorizado1)+"','"+ruta+"','Autorizado')");
                                    if(i == 1) stmt.executeUpdate("INSERT INTO Tutores VALUES ('"+teléfonosAutorizados[i]+"','"+nombresAutorizados[i]+"','"+apellidosPaternosAutorizados[i]+"','"+apellidosMaternosAutorizados[i]+"','"+(archivoAutorizado2 == null ? "No" : archivoAutorizado2)+"','"+ruta+"','Autorizado')");
                                    if(i == 2) stmt.executeUpdate("INSERT INTO Tutores VALUES ('"+teléfonosAutorizados[i]+"','"+nombresAutorizados[i]+"','"+apellidosPaternosAutorizados[i]+"','"+apellidosMaternosAutorizados[i]+"','"+(archivoAutorizado3 == null ? "No" : archivoAutorizado3)+"','"+ruta+"','Autorizado')");

                                } 
                                catch (SQLException ex) {
                                    Logger.getLogger(PantallaRegistrarNiño.class.getName()).log(Level.SEVERE, null, ex);
                                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error en el registro de los autorizados. (TELÉFONO REPETIDO)\nIntente de nuevo.");
                                }
                            }
                            else{
                                errorBaseDatos = true;
                                JOptionPane.showMessageDialog(null, "Ha ocurrido un error con el registro de los autorizados (TELÉFONO REPETIDO)\nIntente de nuevo.");
                                try{
                                    file.delete();
                                    file2.delete();
                                }
                                catch(HeadlessException e){
                                }
                                
                                try {
                                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VISTA", "root", "");
                                    stmt = con.createStatement();
                                    stmt.executeUpdate("DELETE FROM Ninos WHERE ID = "+idNiño);
                                    stmt.executeUpdate("DELETE FROM Tutores WHERE Telefono = '"+telefonoTutor+"'");
                                    
                                    if(i == 0){
                                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error en el registro del niño.\nIntente de nuevo.");
                                        errorBaseDatos = true;
                                        break;
                                    }
                                    
                                    if(i == 1){
                                        
                                        ruta = directorioRaiz+"/Niños(autorizados)/NiñosAutorizados_"+teléfonosAutorizados[0]+".txt";
                                        file3 = new File(ruta);
                                        try{
                                            file3.delete();
                                        }catch(HeadlessException e){
                                            
                                        }
                                        try {
                                            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VISTA", "root", "");
                                            stmt = con.createStatement();
                                            stmt.executeUpdate("DELETE FROM Tutores WHERE Telefono = '"+teléfonosAutorizados[0]+"'");
                                        } 
                                        catch (SQLException ex2) {
                                            Logger.getLogger(PantallaRegistrarNiño.class.getName()).log(Level.SEVERE, null, ex2);
                                            JOptionPane.showMessageDialog(null, "Ha ocurrido un error en el registro del niño.\nIntente de nuevo.");
                                            errorBaseDatos = true;
                                        }
                                        
                                        break;
                                        
                                    }
                                    if(i == 2){
                                        ruta = directorioRaiz+"/Niños(autorizados)/NiñosAutorizados_"+teléfonosAutorizados[1]+".txt";
                                        file3 = new File(ruta);
                                        try{
                                            file3.delete();
                                        }catch(HeadlessException e){
                                            
                                        }
                                        ruta = directorioRaiz+"/Niños(autorizados)/NiñosAutorizados_"+teléfonosAutorizados[0]+".txt";
                                        file3 = new File(ruta);
                                        try{
                                            file3.delete();
                                        }catch(HeadlessException e){
                                            
                                        }
                                        
                                         try {
                                            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VISTA", "root", "");
                                            stmt = con.createStatement();
                                            stmt.executeUpdate("DELETE FROM Tutores WHERE Telefono = '"+teléfonosAutorizados[1]+"'");
                                        } 
                                        catch (SQLException ex2) {
                                            Logger.getLogger(PantallaRegistrarNiño.class.getName()).log(Level.SEVERE, null, ex2);
                                            JOptionPane.showMessageDialog(null, "Ha ocurrido un error en el registro del niño.\nIntente de nuevo.");
                                            errorBaseDatos = true;
                                        }
                                        try {
                                            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VISTA", "root", "");
                                            stmt = con.createStatement();
                                            stmt.executeUpdate("DELETE FROM Tutores WHERE Telefono = '"+teléfonosAutorizados[0]+"'");
                                        } 
                                        catch (SQLException ex2) {
                                            Logger.getLogger(PantallaRegistrarNiño.class.getName()).log(Level.SEVERE, null, ex2);
                                            JOptionPane.showMessageDialog(null, "Ha ocurrido un error en el registro del niño.\nIntente de nuevo.");
                                            errorBaseDatos = true;
                                        }
                                    }
                                   
                                } 
                                catch (SQLException ex2) {
                                    Logger.getLogger(PantallaRegistrarNiño.class.getName()).log(Level.SEVERE, null, ex2);
                                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error en el registro del niño.\nIntente de nuevo.");
                                    errorBaseDatos = true;
                                }
                                break;
                            }
                            FileWriter fw = new FileWriter(file3);
                            BufferedWriter bw = new BufferedWriter(fw);
                            bw.write(contenido);
                            bw.close();

                        } catch (HeadlessException | IOException e) {
                        }


                    }
                  
                }
                if(!errorBaseDatos){
                    JOptionPane.showMessageDialog(null, "REGISTRO EXITOSO");
                    IniciarVentana();
                }
                else{
                    errorBaseDatos = false;
                    JOptionPane.showMessageDialog(null, "No fue posible completar el registro.\nRevise la información e inténtelo de nuevo");
                }
                
            }
        }
        
        
    }//GEN-LAST:event_bSiguienteActionPerformed

    private void bAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAnteriorActionPerformed

        if(archivoTutor != null){
            fotoTutor = getToolkit().getImage(archivoTutor.getAbsolutePath());
            fotoTutor2 = new ImageIcon(fotoTutor.getScaledInstance(260, 260, 260));
        }
        
        if(donde == Donde.tutor){
            nombresTutor = tfNombres.getText();
            apellidoPaternoTutor = tfApellidoPaterno.getText();
            apellidoMaternoTutor = tfApellidoMaterno.getText();
            telefonoTutor = tfTeléfono.getText();
            
            tfNombres.setText(nombresNiño);
            tfApellidoPaterno.setText(apellidoPaternoNiño);
            tfApellidoMaterno.setText(apellidoMaternoNiño);
            imagen.setIcon(fotoNiño2);
            imagen.setText(null);
            lblTeléfono.setVisible(false);
            tfTeléfono.setVisible(false);
            lblInformacion.setText("Información del niño");
            bSiguiente.setText("Siguiente");
            bAnterior.setVisible(false);
            donde = Donde.niños;
        }
        else if(donde == Donde.autorizados){
            
            if(autorizadoSeleccionado == 0){
                nombresAutorizados[0] = tfNombres.getText();
                apellidosPaternosAutorizados[0] = tfApellidoPaterno.getText();
                apellidosMaternosAutorizados[0] = tfApellidoMaterno.getText();
                teléfonosAutorizados[0] = tfTeléfono.getText();
                try{
                    fotoAutorizado = getToolkit().getImage(archivoAutorizado1.getAbsolutePath());
                    fotoAutorizado2 = new ImageIcon(fotoAutorizado.getScaledInstance(260, 260, 260));
                    fotosAutorizados[0] = fotoAutorizado2;
                }
                catch (Exception e){

                }
            }
            
            if(autorizadoSeleccionado == 1){
                nombresAutorizados[1] = tfNombres.getText();
                apellidosPaternosAutorizados[1] = tfApellidoPaterno.getText();
                apellidosMaternosAutorizados[1] = tfApellidoMaterno.getText();
                teléfonosAutorizados[1] = tfTeléfono.getText();
                try{
                    fotoAutorizado = getToolkit().getImage(archivoAutorizado2.getAbsolutePath());
                    fotoAutorizado2 = new ImageIcon(fotoAutorizado.getScaledInstance(260, 260, 260));
                    fotosAutorizados[1] = fotoAutorizado2;
                }
                catch (Exception e){

                }
            }
            
            if(autorizadoSeleccionado == 2){
                nombresAutorizados[2] = tfNombres.getText();
                apellidosPaternosAutorizados[2] = tfApellidoPaterno.getText();
                apellidosMaternosAutorizados[2] = tfApellidoMaterno.getText();
                teléfonosAutorizados[2] = tfTeléfono.getText();
                try{
                    fotoAutorizado = getToolkit().getImage(archivoAutorizado3.getAbsolutePath());
                    fotoAutorizado2 = new ImageIcon(fotoAutorizado.getScaledInstance(260, 260, 260));
                    fotosAutorizados[2] = fotoAutorizado2;
                }
                catch (Exception e){

                }
            }
            
            donde = Donde.tutor;
            tfNombres.setText(nombresTutor);
            tfApellidoPaterno.setText(apellidoPaternoTutor);
            tfApellidoMaterno.setText(apellidoMaternoTutor);
            imagen.setIcon(fotoTutor2);
            imagen.setText("SIN FOTO");
            tfTeléfono.setText(telefonoTutor);
            lblInformacion.setText("Información del Tutor");
            bSiguiente.setText("Siguiente");
            bAnterior.setVisible(true);
            donde = Donde.tutor; 
            cbAutorizados.setVisible(false);
            lblNumAutorizados.setVisible(false);
            cbNumAutorizados.setVisible(false);
        }
    }//GEN-LAST:event_bAnteriorActionPerformed

    private void cbAutorizadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAutorizadosActionPerformed
    
        if(cbAutorizados.getSelectedIndex() == 0){
            
            if(autorizadoSeleccionado == 0){
                try{
                    fotoAutorizado = getToolkit().getImage(archivoAutorizado1.getAbsolutePath());
                    fotoAutorizado2 = new ImageIcon(fotoAutorizado.getScaledInstance(260, 260, 260));
                    fotosAutorizados[0] = fotoAutorizado2;
                }
                catch (Exception e){

                }
                nombresAutorizados[0] = tfNombres.getText();
                apellidosPaternosAutorizados[0] = tfApellidoPaterno.getText();
                apellidosMaternosAutorizados[0] = tfApellidoMaterno.getText();
                teléfonosAutorizados[0] = tfTeléfono.getText();
            }
            if(autorizadoSeleccionado == 1){
                try{
                    fotoAutorizado = getToolkit().getImage(archivoAutorizado2.getAbsolutePath());
                    fotoAutorizado2 = new ImageIcon(fotoAutorizado.getScaledInstance(260, 260, 260));
                    fotosAutorizados[1] = fotoAutorizado2;
                }
                catch (Exception e){

                }
                nombresAutorizados[1] = tfNombres.getText();
                apellidosPaternosAutorizados[1] = tfApellidoPaterno.getText();
                apellidosMaternosAutorizados[1] = tfApellidoMaterno.getText();
                teléfonosAutorizados[1] = tfTeléfono.getText();
            }
            if(autorizadoSeleccionado == 2){
                try{
                    fotoAutorizado = getToolkit().getImage(archivoAutorizado3.getAbsolutePath());
                    fotoAutorizado2 = new ImageIcon(fotoAutorizado.getScaledInstance(260, 260, 260));
                    fotosAutorizados[2] = fotoAutorizado2;
                }
                catch (Exception e){

                }
                nombresAutorizados[2] = tfNombres.getText();
                apellidosPaternosAutorizados[2] = tfApellidoPaterno.getText();
                apellidosMaternosAutorizados[2] = tfApellidoMaterno.getText();
                teléfonosAutorizados[2] = tfTeléfono.getText();
            }
            
            tfNombres.setText(nombresAutorizados[0]);
            tfApellidoPaterno.setText(apellidosPaternosAutorizados[0]);
            tfApellidoMaterno.setText(apellidosMaternosAutorizados[0]);
            tfTeléfono.setText(teléfonosAutorizados[0]);
            imagen.setIcon(fotosAutorizados[0]);
            autorizadoSeleccionado = 0;   
        }
        
        if(cbAutorizados.getSelectedIndex() == 1){
            
            if(autorizadoSeleccionado == 0){
                try{
                    fotoAutorizado = getToolkit().getImage(archivoAutorizado1.getAbsolutePath());
                    fotoAutorizado2 = new ImageIcon(fotoAutorizado.getScaledInstance(260, 260, 260));
                    fotosAutorizados[0] = fotoAutorizado2;
                }
                catch (Exception e){

                }
                nombresAutorizados[0] = tfNombres.getText();
                apellidosPaternosAutorizados[0] = tfApellidoPaterno.getText();
                apellidosMaternosAutorizados[0] = tfApellidoMaterno.getText();
                teléfonosAutorizados[0] = tfTeléfono.getText();
            }
            if(autorizadoSeleccionado == 1){
                try{
                    fotoAutorizado = getToolkit().getImage(archivoAutorizado2.getAbsolutePath());
                    fotoAutorizado2 = new ImageIcon(fotoAutorizado.getScaledInstance(260, 260, 260));
                    fotosAutorizados[1] = fotoAutorizado2;
                }
                catch (Exception e){

                }
                nombresAutorizados[1] = tfNombres.getText();
                apellidosPaternosAutorizados[1] = tfApellidoPaterno.getText();
                apellidosMaternosAutorizados[1] = tfApellidoMaterno.getText();
                teléfonosAutorizados[1] = tfTeléfono.getText();
            }
            if(autorizadoSeleccionado == 2){
                try{
                    fotoAutorizado = getToolkit().getImage(archivoAutorizado3.getAbsolutePath());
                    fotoAutorizado2 = new ImageIcon(fotoAutorizado.getScaledInstance(260, 260, 260));
                    fotosAutorizados[2] = fotoAutorizado2;
                }
                catch (Exception e){

                }
                nombresAutorizados[2] = tfNombres.getText();
                apellidosPaternosAutorizados[2] = tfApellidoPaterno.getText();
                apellidosMaternosAutorizados[2] = tfApellidoMaterno.getText();
                teléfonosAutorizados[2] = tfTeléfono.getText();
            }
            
            tfNombres.setText(nombresAutorizados[1]);
            tfApellidoPaterno.setText(apellidosPaternosAutorizados[1]);
            tfApellidoMaterno.setText(apellidosMaternosAutorizados[1]);
            tfTeléfono.setText(teléfonosAutorizados[1]);
            imagen.setIcon(fotosAutorizados[1]);
            autorizadoSeleccionado = 1;   
        }
        
        if(cbAutorizados.getSelectedIndex() == 2){
            if(autorizadoSeleccionado == 0){
                try{
                    fotoAutorizado = getToolkit().getImage(archivoAutorizado1.getAbsolutePath());
                    fotoAutorizado2 = new ImageIcon(fotoAutorizado.getScaledInstance(260, 260, 260));
                    fotosAutorizados[0] = fotoAutorizado2;
                }
                catch (Exception e){

                }
                nombresAutorizados[0] = tfNombres.getText();
                apellidosPaternosAutorizados[0] = tfApellidoPaterno.getText();
                apellidosMaternosAutorizados[0] = tfApellidoMaterno.getText();
                teléfonosAutorizados[0] = tfTeléfono.getText();
            }
            if(autorizadoSeleccionado == 1){
                try{
                    fotoAutorizado = getToolkit().getImage(archivoAutorizado2.getAbsolutePath());
                    fotoAutorizado2 = new ImageIcon(fotoAutorizado.getScaledInstance(260, 260, 260));
                    fotosAutorizados[1] = fotoAutorizado2;
                }
                catch (Exception e){

                }
                nombresAutorizados[1] = tfNombres.getText();
                apellidosPaternosAutorizados[1] = tfApellidoPaterno.getText();
                apellidosMaternosAutorizados[1] = tfApellidoMaterno.getText();
                teléfonosAutorizados[1] = tfTeléfono.getText();
            }
            if(autorizadoSeleccionado == 2){
                try{
                    fotoAutorizado = getToolkit().getImage(archivoAutorizado3.getAbsolutePath());
                    fotoAutorizado2 = new ImageIcon(fotoAutorizado.getScaledInstance(260, 260, 260));
                    fotosAutorizados[2] = fotoAutorizado2;
                }
                catch (Exception e){

                }
                nombresAutorizados[2] = tfNombres.getText();
                apellidosPaternosAutorizados[2] = tfApellidoPaterno.getText();
                apellidosMaternosAutorizados[2] = tfApellidoMaterno.getText();
                teléfonosAutorizados[2] = tfTeléfono.getText();
            }
            
            tfNombres.setText(nombresAutorizados[2]);
            tfApellidoPaterno.setText(apellidosPaternosAutorizados[2]);
            tfApellidoMaterno.setText(apellidosMaternosAutorizados[2]);
            tfTeléfono.setText(teléfonosAutorizados[2]);
            imagen.setIcon(fotosAutorizados[2]);
            autorizadoSeleccionado = 2;   
        }
        if(cbAutorizados.getSelectedIndex() == 4){
            
            if(autorizadoSeleccionado == 0){
                try{
                    fotoAutorizado = getToolkit().getImage(archivoAutorizado1.getAbsolutePath());
                    fotoAutorizado2 = new ImageIcon(fotoAutorizado.getScaledInstance(260, 260, 260));
                    fotosAutorizados[0] = fotoAutorizado2;
                }
                catch (Exception e){

                }
                nombresAutorizados[0] = tfNombres.getText();
                apellidosPaternosAutorizados[0] = tfApellidoPaterno.getText();
                apellidosMaternosAutorizados[0] = tfApellidoMaterno.getText();
                teléfonosAutorizados[0] = tfTeléfono.getText();
            }
            if(autorizadoSeleccionado == 1){
                try{
                    fotoAutorizado = getToolkit().getImage(archivoAutorizado2.getAbsolutePath());
                    fotoAutorizado2 = new ImageIcon(fotoAutorizado.getScaledInstance(260, 260, 260));
                    fotosAutorizados[1] = fotoAutorizado2;
                }
                catch (Exception e){

                }
                nombresAutorizados[1] = tfNombres.getText();
                apellidosPaternosAutorizados[1] = tfApellidoPaterno.getText();
                apellidosMaternosAutorizados[1] = tfApellidoMaterno.getText();
                teléfonosAutorizados[1] = tfTeléfono.getText();
            }
            if(autorizadoSeleccionado == 2){
                try{
                    fotoAutorizado = getToolkit().getImage(archivoAutorizado3.getAbsolutePath());
                    fotoAutorizado2 = new ImageIcon(fotoAutorizado.getScaledInstance(260, 260, 260));
                    fotosAutorizados[2] = fotoAutorizado2;
                }
                catch (Exception e){

                }
                nombresAutorizados[2] = tfNombres.getText();
                apellidosPaternosAutorizados[2] = tfApellidoPaterno.getText();
                apellidosMaternosAutorizados[2] = tfApellidoMaterno.getText();
                teléfonosAutorizados[2] = tfTeléfono.getText();
            }
            
            tfNombres.setText("");
            tfApellidoPaterno.setText("");
            tfApellidoMaterno.setText("");
            tfTeléfono.setText("");
            imagen.setIcon(null);
            autorizadoSeleccionado = 4;   
        }
        
    }//GEN-LAST:event_cbAutorizadosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAnterior;
    private javax.swing.JButton bSiguiente;
    private javax.swing.JButton bTomarFoto;
    private javax.swing.JComboBox<String> cbAutorizados;
    private javax.swing.JComboBox<String> cbNumAutorizados;
    private javax.swing.JLabel imagen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblApellidoMaterno;
    private javax.swing.JLabel lblApellidoPaterno;
    private javax.swing.JLabel lblInformacion;
    private javax.swing.JLabel lblNombres;
    private javax.swing.JLabel lblNumAutorizados;
    private javax.swing.JLabel lblTeléfono;
    private javax.swing.JTextField tfApellidoMaterno;
    private javax.swing.JTextField tfApellidoPaterno;
    private javax.swing.JTextField tfNombres;
    private javax.swing.JTextField tfTeléfono;
    // End of variables declaration//GEN-END:variables
}
