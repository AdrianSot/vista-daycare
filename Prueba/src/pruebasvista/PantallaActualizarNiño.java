/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebasvista;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author pedrohdez
 */

public class PantallaActualizarNiño extends javax.swing.JFrame {

    /**
     * Creates new form PantallaActualizarNiño
     */
    String ID, nombresNiño, apellidoPaternoNiño, apellidoMaternoNiño; //Info del niño
    
    String nombresTutor, apellidoPaternoTutor, apellidoMaternoTutor, teléfonoTutor; //Info del tutor
    
    String nombresAutorizados[] = {"","",""}; //Nombres de los autorizados
    String apellidosPaternosAutorizados[] = {"","",""}; //Apellidos paternos de los autorizados
    String apellidosMaternosAutorizados[] = {"","",""}; // Apellidos maternos de los autorizados
    String teléfonosAutorizados[] = {"","",""}; // Teléfonos de los autorizados
    
    //Archivos
    String archivoNiñosTutor, archivoTutor, archivoAutorizados;
    
    //Imágenes
    Image fotoNiño, fotoTutor;
    Image fotosAutorizados[] ={null,null,null}; 
    
    //Íconos
    ImageIcon iconoNiño, iconoTutor;
    ImageIcon iconosAutorizados[] = {null,null,null};
    
    
    Connection con;
    Statement stmt;
    
    String Teléfonos[];
    
    public PantallaActualizarNiño() {
        initComponents();
        
        IniciarVentana();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(PantallaRegistrarNiño.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void IniciarVentana(){
        //DISEÑO DE LAS DIVISIONES
        this.lbl.setBackground(Color.black);
        this.lbl.setOpaque(true);
        this.lbl1.setBackground(Color.black);
        this.lbl1.setOpaque(true);
        
        //DESHABILITACIÓN DE LOS BOTONES PARA TOMAR FOTOS
        bTomarFotoNiño.setEnabled(false);
        bTomarFotoTutor.setEnabled(false);
        bTomarFotoAut1.setEnabled(false);
        bTomarFotoAut2.setEnabled(false);
        bTomarFotoAut3.setEnabled(false);
        
        //DESHABILITACIÓN DE LA EDICIÓN DE LOS CAMPOS DE TEXTO DE LOS NIÑOS
        tfNombresNiño.setEditable(false);
        tfApellidoPaternoNiño.setEditable(false);
        tfApellidoMaternoNiño.setEditable(false);
        
        //CAMBIO DE COLOR AL TEXTO DESHABILITADO DE LOS CAMPOS DE TEXTO DE LOS NIÑOS
        tfNombresNiño.setForeground(Color.lightGray);
        tfApellidoPaternoNiño.setForeground(Color.lightGray);
        tfApellidoMaternoNiño.setForeground(Color.lightGray);
        
        //DESHABILITACIÓN DE LA EDICIÓN DE LOS CAMPOS DE TEXTO DEL TUTOR
        tfNombresTutor.setEditable(false);
        tfApellidoPaternoTutor.setEditable(false);
        tfApellidoMaternoTutor.setEditable(false);
        tfTeléfonoTutor.setEditable(false);
        
        //CAMBIO DE COLOR AL TEXTO DESHABILITADO DE LOS CAMPOS DE TEXTO DEL TUTOR
        tfNombresTutor.setForeground(Color.lightGray);
        tfApellidoPaternoTutor.setForeground(Color.lightGray);
        tfApellidoMaternoTutor.setForeground(Color.lightGray);
        tfTeléfonoTutor.setForeground(Color.lightGray);
        
        //DESHABILITACIÓN DE LA EDICIÓN DE LOS CAMPOS DE TEXTO DE LOS AUTORIZADOS
        tfNombresAut1.setEditable(false);
        tfApellidoPaternoAut1.setEditable(false);
        tfApellidoMaternoAut1.setEditable(false);
        tfTeléfonoAut1.setEditable(false);
        
        tfNombresAut2.setEditable(false);
        tfApellidoPaternoAut2.setEditable(false);
        tfApellidoMaternoAut2.setEditable(false);
        tfTeléfonoAut2.setEditable(false);
        
        tfNombresAut3.setEditable(false);
        tfApellidoPaternoAut3.setEditable(false);
        tfApellidoMaternoAut3.setEditable(false);
        tfTeléfonoAut3.setEditable(false);
        
        //CAMBIO DE COLOR AL TEXTO DESHABILITADO DE LOS CAMPOS DE TEXTO DE LOS AUTORIZADOS
        tfNombresAut1.setForeground(Color.lightGray);
        tfApellidoPaternoAut1.setForeground(Color.lightGray);
        tfApellidoMaternoAut1.setForeground(Color.lightGray);
        tfTeléfonoAut1.setForeground(Color.lightGray);
        
        tfNombresAut2.setForeground(Color.lightGray);
        tfApellidoPaternoAut2.setForeground(Color.lightGray);
        tfApellidoMaternoAut2.setForeground(Color.lightGray);
        tfTeléfonoAut2.setForeground(Color.lightGray);
        
        tfNombresAut3.setForeground(Color.lightGray);
        tfApellidoPaternoAut3.setForeground(Color.lightGray);
        tfApellidoMaternoAut3.setForeground(Color.lightGray);
        tfTeléfonoAut3.setForeground(Color.lightGray);
        
        ID = nombresNiño = apellidoPaternoNiño = apellidoMaternoNiño = ""; //Inicialización de la info del niño
        fotoNiño = null;
        
        nombresTutor = apellidoPaternoTutor = apellidoMaternoTutor = teléfonoTutor = ""; //Inicialización de la info del tutor
        fotoTutor = null;
        
        //Archivos
        archivoNiñosTutor = archivoTutor = archivoAutorizados = "";
        
        //Fotos
        fotoNiño = fotoTutor =null;
        
        //Iconos
        iconoNiño = iconoTutor = null;

        //Imágenes
        for(int i = 0 ; i < 3 ; i++){
            fotosAutorizados[i] = null;
            iconosAutorizados[i] = null;
            nombresAutorizados[i] = apellidosPaternosAutorizados[i] =
            apellidosMaternosAutorizados[i] = teléfonosAutorizados[i] = "";
        }
    }    
    
    
    public void MostrarPantalla(String id){
        IniciarVentana();
        ID = id;
        
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VISTA", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT * FROM Ninos WHERE ID = "+ID); //SE EXTRAE LA INFORMACIÓN DEL NIÑO
            rs.first();
            
            //SE GUARDA LA INFORMACIÓN EN LAS RESPECTIVAS VARIABLES
            nombresNiño = rs.getObject(2).toString();
            apellidoPaternoNiño = rs.getObject(3).toString();
            apellidoMaternoNiño = rs.getObject(4).toString();
            fotoNiño = getToolkit().getImage(rs.getObject(5).toString());
            fotoNiño = fotoNiño.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            iconoNiño = new ImageIcon(fotoNiño);
            teléfonoTutor = rs.getObject(6).toString();
            archivoAutorizados = rs.getObject(7).toString();
            
        } catch (SQLException ex) {
            Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //SE COLOCA LA INFORMACIÓN DEL NIÑO EN DONDE CORRESPONDE
        lblFotoNiño.setIcon(iconoNiño);
        tfNombresNiño.setText(nombresNiño);
        tfApellidoPaternoNiño.setText(apellidoPaternoNiño);
        tfApellidoMaternoNiño.setText(apellidoMaternoNiño);
        
        //SE OBTIENE LA INFORMACIÓN DEL TUTOR
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VISTA", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT * FROM Tutores WHERE Telefono = '"+teléfonoTutor+"'"); //SE EXTRAE LA INFORMACIÓN DEL NIÑO
            rs.first();
            
            //SE GUARDA LA INFORMACIÓN EN LAS RESPECTIVAS VARIABLES
            nombresTutor = rs.getObject(2).toString();
            apellidoPaternoTutor = rs.getObject(3).toString();
            apellidoMaternoTutor = rs.getObject(4).toString();
            fotoTutor = getToolkit().getImage(rs.getObject(5).toString());
            fotoTutor = fotoTutor.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            iconoTutor = new ImageIcon(fotoTutor);
            archivoNiñosTutor = rs.getObject(6).toString();
            
        } catch (SQLException ex) {
            Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //SE COLOCA LA INFORMACIÓN DEL TUTOR EN DONDE CORRESPONDE
        lblFotoTutor.setIcon(iconoTutor);
        tfNombresTutor.setText(nombresTutor);
        tfApellidoPaternoTutor.setText(apellidoPaternoTutor);
        tfApellidoMaternoTutor.setText(apellidoMaternoTutor);
        tfTeléfonoTutor.setText(teléfonoTutor);
        
        //SE LEE EL ARCHIVO DE LOS AUTORIZADOS DEL NIÑO
        String aux;
        int numAut = 0;
        try{
            BufferedReader bf = new BufferedReader(new FileReader(archivoAutorizados));
            while((aux = bf.readLine()) != null){ //Se guardan los teléfonos de los autorizados en un arreglo
                teléfonosAutorizados[numAut] = aux;
                numAut++;
            }

            for(String a : teléfonosAutorizados){
                System.out.println(a);
            }
            System.out.println("\n");
        }

        catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error con la lectura del archivo de los autorizados del niño.");
        }
        
        numAut = 0;
        for(String tel : teléfonosAutorizados){
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VISTA", "root", "");
                Statement stmt = con.createStatement();
                ResultSet rs;
                rs = stmt.executeQuery("SELECT * FROM Tutores WHERE Telefono = '"+tel+"'"); //Se extrae la info de cada autorizado
                rs.first();
                
                //SE GUARDA LA INFORMACIÓN EN LAS RESPECTIVAS VARIABLES
                if(rs.first()){
                    nombresAutorizados[numAut] = rs.getObject(2).toString();
                    apellidosPaternosAutorizados[numAut] = rs.getObject(3).toString();
                    apellidosMaternosAutorizados[numAut] = rs.getObject(4).toString();
                    fotosAutorizados[numAut] = getToolkit().getImage(rs.getObject(5).toString());
                    fotosAutorizados[numAut] = fotosAutorizados[numAut].getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    iconosAutorizados[numAut] = new ImageIcon(fotosAutorizados[numAut]);
                    numAut++;
                }
                
                
                
            } catch (SQLException ex) {
                Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
        
        //SE COLOCA LA INFORMACIÓN DE CADA AUTORIZADO EN DONDE CORRESPONDE

        //Autorizado 1
        lblFotoAut1.setIcon(iconosAutorizados[0]);
        tfNombresAut1.setText(nombresAutorizados[0]);
        tfApellidoPaternoAut1.setText(apellidosPaternosAutorizados[0]);
        tfApellidoMaternoAut1.setText(apellidosMaternosAutorizados[0]);
        tfTeléfonoAut1.setText(teléfonosAutorizados[0]);
        
        //Autorizado 2
        lblFotoAut2.setIcon(iconosAutorizados[1]);
        tfNombresAut2.setText(nombresAutorizados[1]);
        tfApellidoPaternoAut2.setText(apellidosPaternosAutorizados[1]);
        tfApellidoMaternoAut2.setText(apellidosMaternosAutorizados[1]);
        tfTeléfonoAut2.setText(teléfonosAutorizados[1]);
        
        //Autorizado 3
        lblFotoAut3.setIcon(iconosAutorizados[2]);
        tfNombresAut3.setText(nombresAutorizados[2]);
        tfApellidoPaternoAut3.setText(apellidosPaternosAutorizados[2]);
        tfApellidoMaternoAut3.setText(apellidosMaternosAutorizados[2]);
        tfTeléfonoAut3.setText(teléfonosAutorizados[2]);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblFotoNiño = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblApellidoPaterno = new javax.swing.JLabel();
        lblApellidoMaterno = new javax.swing.JLabel();
        tfNombresNiño = new javax.swing.JTextField();
        tfApellidoPaternoNiño = new javax.swing.JTextField();
        tfApellidoMaternoNiño = new javax.swing.JTextField();
        lblNiño = new javax.swing.JLabel();
        lbl = new javax.swing.JLabel();
        lblTutor = new javax.swing.JLabel();
        lblFotoTutor = new javax.swing.JLabel();
        lblNombreTutor = new javax.swing.JLabel();
        tfNombresTutor = new javax.swing.JTextField();
        lblApellidoPaternoTutor = new javax.swing.JLabel();
        tfApellidoPaternoTutor = new javax.swing.JTextField();
        lblApellidoMaternoTutor = new javax.swing.JLabel();
        tfApellidoMaternoTutor = new javax.swing.JTextField();
        lbl1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblTeléfonoTutor = new javax.swing.JLabel();
        tfTeléfonoTutor = new javax.swing.JTextField();
        lblFotoAut1 = new javax.swing.JLabel();
        lblNombreAut1 = new javax.swing.JLabel();
        tfNombresAut1 = new javax.swing.JTextField();
        lblApellidoPaternoAut1 = new javax.swing.JLabel();
        tfApellidoPaternoAut1 = new javax.swing.JTextField();
        lblApellidoMaternoAut1 = new javax.swing.JLabel();
        tfApellidoMaternoAut1 = new javax.swing.JTextField();
        lblTeléfonoAut1 = new javax.swing.JLabel();
        tfTeléfonoAut1 = new javax.swing.JTextField();
        lbl2 = new javax.swing.JLabel();
        lblFotoAut2 = new javax.swing.JLabel();
        lblNombreAut2 = new javax.swing.JLabel();
        tfNombresAut2 = new javax.swing.JTextField();
        lblApellidoPaternoAut2 = new javax.swing.JLabel();
        tfApellidoPaternoAut2 = new javax.swing.JTextField();
        lblApellidoMaternoAut2 = new javax.swing.JLabel();
        tfApellidoMaternoAut2 = new javax.swing.JTextField();
        lblTeléfonoAut2 = new javax.swing.JLabel();
        tfTeléfonoAut2 = new javax.swing.JTextField();
        lblFotoAut3 = new javax.swing.JLabel();
        lblNombreAut3 = new javax.swing.JLabel();
        tfNombresAut3 = new javax.swing.JTextField();
        lblApellidoPaternoAut3 = new javax.swing.JLabel();
        tfApellidoPaternoAut3 = new javax.swing.JTextField();
        lblApellidoMaternoAut3 = new javax.swing.JLabel();
        tfApellidoMaternoAut3 = new javax.swing.JTextField();
        lblTeléfonoAut3 = new javax.swing.JLabel();
        tfTeléfonoAut3 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        lbl3 = new javax.swing.JLabel();
        lbl4 = new javax.swing.JLabel();
        lbl5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        bTomarFotoNiño = new javax.swing.JButton();
        bTomarFotoTutor = new javax.swing.JButton();
        bTomarFotoAut1 = new javax.swing.JButton();
        bTomarFotoAut2 = new javax.swing.JButton();
        bTomarFotoAut3 = new javax.swing.JButton();
        cbAsignarTutor = new javax.swing.JCheckBox();
        cbCambiarTutor = new javax.swing.JCheckBox();
        cbEditarAut1 = new javax.swing.JCheckBox();
        cbELiminarAut1 = new javax.swing.JCheckBox();
        cbEditarAut2 = new javax.swing.JCheckBox();
        cbEliminarAut2 = new javax.swing.JCheckBox();
        cbEditarAut3 = new javax.swing.JCheckBox();
        cbEliminarAut3 = new javax.swing.JCheckBox();
        bGuardar = new javax.swing.JButton();
        cbEditarNiño = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblFotoNiño.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblNombre.setText("Nombres: ");

        lblApellidoPaterno.setText("Apellido paterno:");

        lblApellidoMaterno.setText("Apellido materno:");

        lblNiño.setText("NIÑO");

        lbl.setBackground(new java.awt.Color(17, 14, 12));
        lbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lbl.setOpaque(true);

        lblTutor.setText("TUTOR");

        lblFotoTutor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblNombreTutor.setText("Nombres: ");

        lblApellidoPaternoTutor.setText("Apellido paterno:");

        lblApellidoMaternoTutor.setText("Apellido materno:");

        lbl1.setBackground(new java.awt.Color(19, 15, 12));
        lbl1.setOpaque(true);

        jLabel1.setText("AUTORIZADOS");

        lblTeléfonoTutor.setText("Teléfono:");

        lblFotoAut1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblNombreAut1.setText("Nombres: ");

        lblApellidoPaternoAut1.setText("Apellido paterno:");

        lblApellidoMaternoAut1.setText("Apellido materno:");

        lblTeléfonoAut1.setText("Teléfono:");

        lbl2.setBackground(new java.awt.Color(19, 15, 12));
        lbl2.setOpaque(true);

        lblFotoAut2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblNombreAut2.setText("Nombres: ");

        lblApellidoPaternoAut2.setText("Apellido paterno:");

        lblApellidoMaternoAut2.setText("Apellido materno:");

        lblTeléfonoAut2.setText("Teléfono:");

        lblFotoAut3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblNombreAut3.setText("Nombres: ");

        lblApellidoPaternoAut3.setText("Apellido paterno:");

        lblApellidoMaternoAut3.setText("Apellido materno:");

        lblTeléfonoAut3.setText("Teléfono:");

        jLabel3.setText("AUTORIZADO 1");

        lbl3.setBackground(new java.awt.Color(17, 14, 12));
        lbl3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lbl3.setOpaque(true);

        lbl4.setBackground(new java.awt.Color(17, 14, 12));
        lbl4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lbl4.setOpaque(true);

        lbl5.setBackground(new java.awt.Color(19, 15, 12));
        lbl5.setOpaque(true);

        jLabel4.setText("AUTORIZADO 2");

        jLabel5.setText("AUTORIZADO3");

        bTomarFotoNiño.setText("Tomar foto");

        bTomarFotoTutor.setText("TomarFoto");

        bTomarFotoAut1.setText("Tomar foto");

        bTomarFotoAut2.setText("Tomar foto");

        bTomarFotoAut3.setText("Tomar foto");

        cbAsignarTutor.setText("Asignar tutor");
        cbAsignarTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAsignarTutorActionPerformed(evt);
            }
        });

        cbCambiarTutor.setText("Cambiar tutor");
        cbCambiarTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCambiarTutorActionPerformed(evt);
            }
        });

        cbEditarAut1.setText("Editar");
        cbEditarAut1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEditarAut1ActionPerformed(evt);
            }
        });

        cbELiminarAut1.setText("Eliminar");

        cbEditarAut2.setText("Editar");
        cbEditarAut2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEditarAut2ActionPerformed(evt);
            }
        });

        cbEliminarAut2.setText("Eliminar");

        cbEditarAut3.setText("Editar");
        cbEditarAut3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEditarAut3ActionPerformed(evt);
            }
        });

        cbEliminarAut3.setText("Eliminar");

        bGuardar.setText("Guardar cambios");
        bGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bGuardarActionPerformed(evt);
            }
        });

        cbEditarNiño.setText("Editar");
        cbEditarNiño.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEditarNiñoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(lbl5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(201, 201, 201)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFotoNiño, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(82, 82, 82)
                                        .addComponent(lblNiño)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbEditarNiño)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblNombre)
                                        .addGap(3, 3, 3)
                                        .addComponent(tfNombresNiño, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(lblApellidoMaterno)
                                            .addGap(3, 3, 3)
                                            .addComponent(tfApellidoMaternoNiño))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(lblApellidoPaterno)
                                            .addGap(1, 1, 1)
                                            .addComponent(tfApellidoPaternoNiño, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(87, 87, 87)
                                .addComponent(lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(87, 87, 87)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblFotoTutor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblNombreTutor)
                                                .addGap(3, 3, 3)
                                                .addComponent(tfNombresTutor, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblTeléfonoTutor)
                                                .addGap(3, 3, 3)
                                                .addComponent(tfTeléfonoTutor, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lblApellidoMaternoTutor)
                                                    .addComponent(lblApellidoPaternoTutor))
                                                .addGap(3, 3, 3)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(tfApellidoPaternoTutor)
                                                    .addComponent(tfApellidoMaternoTutor, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(89, 89, 89)
                                        .addComponent(lblTutor)
                                        .addGap(104, 104, 104)
                                        .addComponent(cbAsignarTutor)
                                        .addGap(38, 38, 38)
                                        .addComponent(cbCambiarTutor))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(264, 264, 264)
                                .addComponent(bTomarFotoNiño)
                                .addGap(536, 536, 536)
                                .addComponent(bTomarFotoTutor)))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(bTomarFotoAut1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblFotoAut1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(jLabel3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbEditarAut1)
                                .addGap(18, 18, 18)
                                .addComponent(cbELiminarAut1))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblApellidoPaternoAut1)
                                    .addComponent(lblApellidoMaternoAut1))
                                .addGap(3, 3, 3)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfApellidoPaternoAut1)
                                    .addComponent(tfApellidoMaternoAut1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblNombreAut1)
                                .addGap(3, 3, 3)
                                .addComponent(tfNombresAut1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTeléfonoAut1)
                                .addGap(3, 3, 3)
                                .addComponent(tfTeléfonoAut1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(bTomarFotoAut2))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lblFotoAut2, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addComponent(jLabel4)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbEditarAut2)
                                .addGap(9, 9, 9)
                                .addComponent(cbEliminarAut2))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblApellidoPaternoAut2)
                                    .addComponent(lblApellidoMaternoAut2))
                                .addGap(3, 3, 3)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfApellidoPaternoAut2)
                                    .addComponent(tfApellidoMaternoAut2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblNombreAut2)
                                .addGap(3, 3, 3)
                                .addComponent(tfNombresAut2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTeléfonoAut2)
                                .addGap(3, 3, 3)
                                .addComponent(tfTeléfonoAut2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addComponent(lbl4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(bTomarFotoAut3)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblFotoAut3, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(85, 85, 85)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbEditarAut3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbEliminarAut3))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblApellidoPaternoAut3)
                                    .addComponent(lblApellidoMaternoAut3))
                                .addGap(3, 3, 3)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfApellidoPaternoAut3)
                                    .addComponent(tfApellidoMaternoAut3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblNombreAut3)
                                .addGap(3, 3, 3)
                                .addComponent(tfNombresAut3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTeléfonoAut3)
                                .addGap(3, 3, 3)
                                .addComponent(tfTeléfonoAut3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(717, 717, 717)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(713, 713, 713)
                        .addComponent(bGuardar)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTutor)
                            .addComponent(cbAsignarTutor)
                            .addComponent(cbCambiarTutor))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFotoTutor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblNombreTutor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfNombresTutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblApellidoPaternoTutor)
                                    .addComponent(tfApellidoPaternoTutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblApellidoMaternoTutor)
                                    .addComponent(tfApellidoMaternoTutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblTeléfonoTutor)
                                    .addComponent(tfTeléfonoTutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNiño)
                            .addComponent(cbEditarNiño))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfNombresNiño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblApellidoPaterno)
                                    .addComponent(tfApellidoPaternoNiño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblApellidoMaterno)
                                    .addComponent(tfApellidoMaternoNiño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblFotoNiño, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(bTomarFotoNiño)
                                    .addComponent(bTomarFotoTutor))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(cbEditarAut1)
                                .addComponent(cbELiminarAut1))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(cbEditarAut2)
                                .addComponent(cbEliminarAut2))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(cbEditarAut3)
                                .addComponent(cbEliminarAut3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFotoAut1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblNombreAut1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfNombresAut1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblApellidoPaternoAut1)
                                    .addComponent(tfApellidoPaternoAut1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblApellidoMaternoAut1)
                                    .addComponent(tfApellidoMaternoAut1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblTeléfonoAut1)
                                    .addComponent(tfTeléfonoAut1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lblFotoAut2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblNombreAut2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfNombresAut2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblApellidoPaternoAut2)
                                    .addComponent(tfApellidoPaternoAut2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblApellidoMaternoAut2)
                                    .addComponent(tfApellidoMaternoAut2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblTeléfonoAut2)
                                    .addComponent(tfTeléfonoAut2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lblFotoAut3, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblNombreAut3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfNombresAut3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblApellidoPaternoAut3)
                                    .addComponent(tfApellidoPaternoAut3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblApellidoMaternoAut3)
                                    .addComponent(tfApellidoMaternoAut3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblTeléfonoAut3)
                                    .addComponent(tfTeléfonoAut3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(bTomarFotoAut1)
                                .addComponent(bTomarFotoAut2))
                            .addComponent(bTomarFotoAut3)))
                    .addComponent(lbl3, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl4, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(bGuardar)
                .addGap(220, 220, 220))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbEditarNiñoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEditarNiñoActionPerformed
        if(cbEditarNiño.isSelected()){
           tfNombresNiño.setForeground(Color.black);
           tfApellidoPaternoNiño.setForeground(Color.black);
           tfApellidoMaternoNiño.setForeground(Color.black);
           tfNombresNiño.setEditable(true);
           tfApellidoPaternoNiño.setEditable(true);
           tfApellidoMaternoNiño.setEditable(true);
           bTomarFotoNiño.setEnabled(true);
        }
        else{
           tfNombresNiño.setForeground(Color.lightGray);
           tfApellidoPaternoNiño.setForeground(Color.lightGray);
           tfApellidoMaternoNiño.setForeground(Color.lightGray);
           tfNombresNiño.setEditable(false);
           tfApellidoPaternoNiño.setEditable(false);
           tfApellidoMaternoNiño.setEditable(false);
           bTomarFotoNiño.setEnabled(false);
        }
    }//GEN-LAST:event_cbEditarNiñoActionPerformed

    private void cbCambiarTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCambiarTutorActionPerformed
        if(cbCambiarTutor.isSelected()){
            tfNombresTutor.setForeground(Color.black);
            tfApellidoPaternoTutor.setForeground(Color.black);
            tfApellidoMaternoTutor.setForeground(Color.black);
            tfTeléfonoTutor.setForeground(Color.black);
            cbAsignarTutor.setSelected(false);
            lblNombreTutor.setText("Nombre: ");
            
            tfNombresTutor.setText(nombresTutor);
            tfApellidoPaternoTutor.setText(apellidoPaternoTutor);
            tfApellidoMaternoTutor.setText(apellidoMaternoTutor);
            tfTeléfonoTutor.setText(teléfonoTutor);
            
            tfNombresTutor.setVisible(true);
            tfApellidoPaternoTutor.setVisible(true);
            tfApellidoMaternoTutor.setVisible(true);
            tfTeléfonoTutor.setVisible(true);
            
            tfNombresTutor.setEditable(true);
            tfApellidoPaternoTutor.setEditable(true);
            tfApellidoMaternoTutor.setEditable(true);
            tfTeléfonoTutor.setEditable(true);
            bTomarFotoTutor.setEnabled(true);
        }
        else{
            tfNombresTutor.setForeground(Color.lightGray);
            tfApellidoPaternoTutor.setForeground(Color.lightGray);
            tfApellidoMaternoTutor.setForeground(Color.lightGray);
            tfTeléfonoTutor.setForeground(Color.lightGray);
            
            tfNombresTutor.setText(nombresTutor);
            tfApellidoPaternoTutor.setText(apellidoPaternoTutor);
            tfApellidoMaternoTutor.setText(apellidoMaternoTutor);
            tfTeléfonoTutor.setText(teléfonoTutor);
            
            tfNombresTutor.setVisible(true);
            tfApellidoPaternoTutor.setVisible(true);
            tfApellidoMaternoTutor.setVisible(true);
            tfTeléfonoTutor.setVisible(true);
            
            tfNombresTutor.setEditable(false);
            tfApellidoPaternoTutor.setEditable(false);
            tfApellidoMaternoTutor.setEditable(false);
            tfTeléfonoTutor.setEditable(false);
            bTomarFotoTutor.setEnabled(false);
            
        }
    }//GEN-LAST:event_cbCambiarTutorActionPerformed

    private void cbAsignarTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAsignarTutorActionPerformed
        if(cbAsignarTutor.isSelected()){
            cbCambiarTutor.setSelected(false);
            lblNombreTutor.setText("Teléfono: ");
            tfNombresTutor.setText("");
            tfNombresTutor.setForeground(Color.black);
            tfNombresTutor.setEditable(true);

            lblApellidoPaternoTutor.setVisible(false);
            lblApellidoMaternoTutor.setVisible(false);
            lblTeléfonoTutor.setVisible(false);
            tfApellidoPaternoTutor.setVisible(false);
            tfApellidoMaternoTutor.setVisible(false);
            tfTeléfonoTutor.setVisible(false);
        }
        else{
            tfNombresTutor.setEditable(false);
            
            tfNombresTutor.setForeground(Color.lightGray);
            tfApellidoPaternoTutor.setForeground(Color.lightGray);
            tfApellidoMaternoTutor.setForeground(Color.lightGray);
            tfTeléfonoTutor.setForeground(Color.lightGray);
            
            lblNombreTutor.setText("Nombres: ");
            tfNombresTutor.setText(nombresTutor);
            tfApellidoPaternoTutor.setText(apellidoPaternoTutor);
            tfApellidoMaternoTutor.setText(apellidoMaternoTutor);
            tfTeléfonoTutor.setText(teléfonoTutor);
            
            lblApellidoPaternoTutor.setVisible(true);
            lblApellidoMaternoTutor.setVisible(true);
            lblTeléfonoTutor.setVisible(true);
            tfApellidoPaternoTutor.setVisible(true);
            tfApellidoMaternoTutor.setVisible(true);
            tfTeléfonoTutor.setVisible(true);
        }
    }//GEN-LAST:event_cbAsignarTutorActionPerformed

    private void cbEditarAut1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEditarAut1ActionPerformed
        if(cbEditarAut1.isSelected()){
            tfNombresAut1.setForeground(Color.black);
            tfApellidoPaternoAut1.setForeground(Color.black);
            tfApellidoMaternoAut1.setForeground(Color.black);
            tfTeléfonoAut1.setForeground(Color.black);
        
            tfNombresAut1.setEditable(true);
            tfApellidoPaternoAut1.setEditable(true);
            tfApellidoMaternoAut1.setEditable(true);
            tfTeléfonoAut1.setEditable(true);
            bTomarFotoAut1.setEnabled(true);
        }
        else{
            tfNombresAut1.setForeground(Color.lightGray);
            tfApellidoPaternoAut1.setForeground(Color.lightGray);
            tfApellidoMaternoAut1.setForeground(Color.lightGray);
            tfTeléfonoAut1.setForeground(Color.lightGray);
        
            tfNombresAut1.setEditable(false);
            tfApellidoPaternoAut1.setEditable(false);
            tfApellidoMaternoAut1.setEditable(false);
            tfTeléfonoAut1.setEditable(false);
            bTomarFotoAut1.setEnabled(false);
        }
    }//GEN-LAST:event_cbEditarAut1ActionPerformed

    private void cbEditarAut2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEditarAut2ActionPerformed
        if(cbEditarAut2.isSelected()){
            tfNombresAut2.setForeground(Color.black);
            tfApellidoPaternoAut2.setForeground(Color.black);
            tfApellidoMaternoAut2.setForeground(Color.black);
            tfTeléfonoAut2.setForeground(Color.black);
        
            tfNombresAut2.setEditable(true);
            tfApellidoPaternoAut2.setEditable(true);
            tfApellidoMaternoAut2.setEditable(true);
            tfTeléfonoAut2.setEditable(true);
            bTomarFotoAut2.setEnabled(true);
        }
        else{
            tfNombresAut2.setForeground(Color.lightGray);
            tfApellidoPaternoAut2.setForeground(Color.lightGray);
            tfApellidoMaternoAut2.setForeground(Color.lightGray);
            tfTeléfonoAut2.setForeground(Color.lightGray);
        
            tfNombresAut2.setEditable(false);
            tfApellidoPaternoAut2.setEditable(false);
            tfApellidoMaternoAut2.setEditable(false);
            tfTeléfonoAut2.setEditable(false);
            bTomarFotoAut2.setEnabled(false);
        }
    }//GEN-LAST:event_cbEditarAut2ActionPerformed

    private void cbEditarAut3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEditarAut3ActionPerformed
        if(cbEditarAut3.isSelected()){
            tfNombresAut3.setForeground(Color.black);
            tfApellidoPaternoAut3.setForeground(Color.black);
            tfApellidoMaternoAut3.setForeground(Color.black);
            tfTeléfonoAut3.setForeground(Color.black);
        
            tfNombresAut3.setEditable(true);
            tfApellidoPaternoAut3.setEditable(true);
            tfApellidoMaternoAut3.setEditable(true);
            tfTeléfonoAut3.setEditable(true);
            bTomarFotoAut3.setEnabled(true);
        }
        else{
            tfNombresAut3.setForeground(Color.lightGray);
            tfApellidoPaternoAut3.setForeground(Color.lightGray);
            tfApellidoMaternoAut3.setForeground(Color.lightGray);
            tfTeléfonoAut3.setForeground(Color.lightGray);
        
            tfNombresAut3.setEditable(false);
            tfApellidoPaternoAut3.setEditable(false);
            tfApellidoMaternoAut3.setEditable(false);
            tfTeléfonoAut3.setEditable(false);
            bTomarFotoAut3.setEnabled(false);
        }
    }//GEN-LAST:event_cbEditarAut3ActionPerformed

    private void bGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bGuardarActionPerformed
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VISTA", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs, rs2;
            rs = stmt.executeQuery("SELECT COUNT(*) FROM Tutores");
            rs.first();
            
            Teléfonos = new String[Integer.parseInt(rs.getObject(1).toString())];
            System.out.println("TAMAÑO DEL ARREGLO: "+Teléfonos.length);
            
            rs2 = stmt.executeQuery("SELECT Telefono FROM Tutores");
            
            rs2.first();
            while(rs2.next()){
                System.out.println(rs2);
            }
            
            for(int i = 0 ; i < Teléfonos.length ; i++){
                System.out.println(Teléfonos[i]);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }//GEN-LAST:event_bGuardarActionPerformed

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
            java.util.logging.Logger.getLogger(PantallaActualizarNiño.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaActualizarNiño.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaActualizarNiño.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaActualizarNiño.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaActualizarNiño().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bGuardar;
    private javax.swing.JButton bTomarFotoAut1;
    private javax.swing.JButton bTomarFotoAut2;
    private javax.swing.JButton bTomarFotoAut3;
    private javax.swing.JButton bTomarFotoNiño;
    private javax.swing.JButton bTomarFotoTutor;
    private javax.swing.JCheckBox cbAsignarTutor;
    private javax.swing.JCheckBox cbCambiarTutor;
    private javax.swing.JCheckBox cbELiminarAut1;
    private javax.swing.JCheckBox cbEditarAut1;
    private javax.swing.JCheckBox cbEditarAut2;
    private javax.swing.JCheckBox cbEditarAut3;
    private javax.swing.JCheckBox cbEditarNiño;
    private javax.swing.JCheckBox cbEliminarAut2;
    private javax.swing.JCheckBox cbEliminarAut3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lbl;
    private javax.swing.JLabel lbl1;
    private javax.swing.JLabel lbl2;
    private javax.swing.JLabel lbl3;
    private javax.swing.JLabel lbl4;
    private javax.swing.JLabel lbl5;
    private javax.swing.JLabel lblApellidoMaterno;
    private javax.swing.JLabel lblApellidoMaternoAut1;
    private javax.swing.JLabel lblApellidoMaternoAut2;
    private javax.swing.JLabel lblApellidoMaternoAut3;
    private javax.swing.JLabel lblApellidoMaternoTutor;
    private javax.swing.JLabel lblApellidoPaterno;
    private javax.swing.JLabel lblApellidoPaternoAut1;
    private javax.swing.JLabel lblApellidoPaternoAut2;
    private javax.swing.JLabel lblApellidoPaternoAut3;
    private javax.swing.JLabel lblApellidoPaternoTutor;
    private javax.swing.JLabel lblFotoAut1;
    private javax.swing.JLabel lblFotoAut2;
    private javax.swing.JLabel lblFotoAut3;
    private javax.swing.JLabel lblFotoNiño;
    private javax.swing.JLabel lblFotoTutor;
    private javax.swing.JLabel lblNiño;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNombreAut1;
    private javax.swing.JLabel lblNombreAut2;
    private javax.swing.JLabel lblNombreAut3;
    private javax.swing.JLabel lblNombreTutor;
    private javax.swing.JLabel lblTeléfonoAut1;
    private javax.swing.JLabel lblTeléfonoAut2;
    private javax.swing.JLabel lblTeléfonoAut3;
    private javax.swing.JLabel lblTeléfonoTutor;
    private javax.swing.JLabel lblTutor;
    private javax.swing.JTextField tfApellidoMaternoAut1;
    private javax.swing.JTextField tfApellidoMaternoAut2;
    private javax.swing.JTextField tfApellidoMaternoAut3;
    private javax.swing.JTextField tfApellidoMaternoNiño;
    private javax.swing.JTextField tfApellidoMaternoTutor;
    javax.swing.JTextField tfApellidoPaternoAut1;
    private javax.swing.JTextField tfApellidoPaternoAut2;
    private javax.swing.JTextField tfApellidoPaternoAut3;
    private javax.swing.JTextField tfApellidoPaternoNiño;
    private javax.swing.JTextField tfApellidoPaternoTutor;
    private javax.swing.JTextField tfNombresAut1;
    private javax.swing.JTextField tfNombresAut2;
    javax.swing.JTextField tfNombresAut3;
    private javax.swing.JTextField tfNombresNiño;
    private javax.swing.JTextField tfNombresTutor;
    private javax.swing.JTextField tfTeléfonoAut1;
    private javax.swing.JTextField tfTeléfonoAut2;
    private javax.swing.JTextField tfTeléfonoAut3;
    private javax.swing.JTextField tfTeléfonoTutor;
    // End of variables declaration//GEN-END:variables
}
