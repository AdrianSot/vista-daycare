/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebasvista;

import java.io.*;
import java.awt.Image;
import java.io.File;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pedrohdez
 */
public class PantallaConsultarNiño extends javax.swing.JInternalFrame {

    /**
     * Creates new form PantallaConsultarNiño
     */
    
    Connection con;
    File archivo;
    Image foto, conversion, tamaño;
    ImageIcon fin, iconoBoton, iconoEquis;
    int buscar;
    PantallaActualizarNiño pantallaActualizar;
    //Se crean los "botones" de actualizar y eliminar
        JLabel editar = new JLabel();
        JLabel eliminar = new JLabel();
    
    public PantallaConsultarNiño() {
        initComponents();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PantallaConsultarNiño.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        editar.setSize(10,10);
        eliminar.setSize(10,10);
        
        //Se introducen los íconos a los "botones"
        foto = getToolkit().getImage(System.getProperty("user.dir")+"/Iconos/lapiz.png");
        foto = foto.getScaledInstance(260, 260, 260);
        iconoBoton = new ImageIcon(foto);
        conversion = iconoBoton.getImage();
        tamaño = conversion.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        fin = new ImageIcon(tamaño);
        editar.setIcon(fin);
        editar.setHorizontalAlignment(JLabel.CENTER);
        editar.setVerticalAlignment(JLabel.CENTER);
        editar.setName("editar");
        
        foto = getToolkit().getImage(System.getProperty("user.dir")+"/Iconos/icon.png");
        foto = foto.getScaledInstance(260, 260, 260);
        iconoBoton = new ImageIcon(foto);
        conversion = iconoBoton.getImage();
        tamaño = conversion.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        fin = new ImageIcon(tamaño);
        eliminar.setIcon(fin);
        eliminar.setHorizontalAlignment(JLabel.CENTER);
        eliminar.setVerticalAlignment(JLabel.CENTER);
        eliminar.setName("eliminar");
        pantallaActualizar =  new PantallaActualizarNiño();
        
    } 
    
    public void borrarTabla(){
        DefaultTableModel tabla = (DefaultTableModel)tbTabla.getModel();
        tabla.setRowCount(0);
    }
    
    public void IniciarVentana(){
        borrarTabla();
        
        buscar = 0;
        archivo = null;
        foto = conversion = tamaño = null;
        fin = null;
        
        lblNombres.setVisible(false);
        lblID.setVisible(false);
        lblApellidoPaterno.setVisible(false);
        lblApellidoMaterno.setVisible(false);
        
        tfNombres.setVisible(false);
        tfApellidoPaterno.setVisible(false);
        tfApellidoMaterno.setVisible(false);
        
        tfNombres.setText("");
        tfApellidoPaterno.setText("");
        tfApellidoMaterno.setText("");
        cbTodo.setSelected(false);
        cbBuscar.setSelectedIndex(0);
        
        bBuscar.setVisible(false);
    }
    
    public int BuscarIDNiño(String direccion, String ID){
        System.out.println("METODO LLAMADO");
        String texto = "";
        String aux = "";
        String bfRead;
        int vacio = 0;
        
        //Se lee el texto
        try{
            BufferedReader bf = new BufferedReader(new FileReader(direccion));
            
            //Se copia el texto en una variable
            while((bfRead = bf.readLine() )!= null){
                aux = aux+bfRead+"\n";
            }
            texto = aux;
            
            //Se borra el ID del archivo
            texto = texto.replace(ID+"\n","");
            
            //Si el texto no queda vacío se actualiza el archivo.
            if(texto.isBlank()){
                vacio = 1;
                File archivo = new File(direccion);
                archivo.delete();
                
            }
            else{
                BufferedWriter bw = new BufferedWriter(new FileWriter(direccion));
                FileWriter fw = new FileWriter(direccion);
                bw.write(texto);
                bw.close();
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error con la lectura del archivo.");
        }
        return vacio;
    }
    
    public void Actualizar(){
        cbTodo.doClick();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbTabla = new javax.swing.JTable();
        lblTítulo = new javax.swing.JLabel();
        cbTodo = new javax.swing.JCheckBox();
        cbBuscar = new javax.swing.JComboBox<>();
        lblNombres = new javax.swing.JLabel();
        tfNombres = new javax.swing.JTextField();
        lblApellidoPaterno = new javax.swing.JLabel();
        tfApellidoPaterno = new javax.swing.JTextField();
        tfApellidoMaterno = new javax.swing.JTextField();
        lblApellidoMaterno = new javax.swing.JLabel();
        bBuscar = new javax.swing.JButton();
        lblID = new javax.swing.JLabel();
        bActualizar = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(800, 500));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Foto", "Nombres", "Apellido paterno", "Apellido materno"
            }
        ));
        tbTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbTabla);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 179, 770, 210));

        lblTítulo.setText("Consulta de niños");
        getContentPane().add(lblTítulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(318, 12, -1, -1));

        cbTodo.setText("Mostrar todo");
        cbTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTodoActionPerformed(evt);
            }
        });
        getContentPane().add(cbTodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(176, 50, -1, -1));

        cbBuscar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Buscar por:", "ID", "Nombre" }));
        cbBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbBuscarActionPerformed(evt);
            }
        });
        getContentPane().add(cbBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(443, 48, -1, -1));

        lblNombres.setText("Nombres:");
        getContentPane().add(lblNombres, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, 50));
        getContentPane().add(tfNombres, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, 130, -1));

        lblApellidoPaterno.setText("Apellido Paterno:");
        getContentPane().add(lblApellidoPaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 88, -1, 30));
        getContentPane().add(tfApellidoPaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 130, -1));
        getContentPane().add(tfApellidoMaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 90, 130, 30));

        lblApellidoMaterno.setText("Apellido Materno:");
        getContentPane().add(lblApellidoMaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 90, -1, 30));

        bBuscar.setText("Buscar");
        bBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBuscarActionPerformed(evt);
            }
        });
        getContentPane().add(bBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(361, 143, -1, -1));

        lblID.setText("ID:");
        getContentPane().add(lblID, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 88, -1, 30));

        bActualizar.setText("jButton1");
        bActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bActualizarActionPerformed(evt);
            }
        });
        getContentPane().add(bActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTodoActionPerformed
        
        borrarTabla();
        IniciarVentana();
        cbTodo.setSelected(true);
        tbTabla.setDefaultRenderer(Object.class, new TablaImagen());
        
        //Se selecciona el modelo de la tabla
        DefaultTableModel tabla = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
        };
        
        tabla.addColumn("ID");
        tabla.addColumn("Foto");
        tabla.addColumn("Nombres");
        tabla.addColumn("Apellido Paterno");
        tabla.addColumn("Apellido Materno");
        tabla.addColumn("");
        tabla.addColumn("");
        
        
        //SE EXTRAE LA INFORMACIÓN DE LA TABLA DE LOS NIÑOS
         try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VISTA", "root", "");    
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT * FROM Ninos"); 
            rs.first();
             
            do{
                Object[] aux = new Object[]  {1, 2, 3, 4, 5, 6, 7}; 
                Object[] fila = new Object[7];
                
                //Se crea la foto para mostrarla en la tabla
                fila[0] = rs.getObject(1);
                foto = getToolkit().getImage(rs.getObject(5).toString());
                foto = foto.getScaledInstance(260, 260, 260);
                ImageIcon icono = new ImageIcon(foto);
                conversion = icono.getImage();
                tamaño = conversion.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
                fin = new ImageIcon(tamaño);
                
                
                fila[1] = new JLabel(fin);
                fila[2] = rs.getObject(2);
                fila[3] = rs.getObject(3);
                fila[4] = rs.getObject(4);
                fila[5] = editar;
                fila[6] = eliminar;
                
                //Se introduce una fila auxiliar para separar los datos
                tabla.addRow(fila);
            }while(rs.next());  
            //Se introduce toda la info a la tabla
            tbTabla.setModel(tabla);
            tbTabla.setRowHeight(90);
            
            //Cada columna se ajusta a un determinado tamaño
            tbTabla.getColumnModel().getColumn(0).setPreferredWidth(10);
            tbTabla.getColumnModel().getColumn(1).setPreferredWidth(90);
            tbTabla.getColumnModel().getColumn(2).setPreferredWidth(100);
            tbTabla.getColumnModel().getColumn(3).setPreferredWidth(80);
            tbTabla.getColumnModel().getColumn(4).setPreferredWidth(80);
            tbTabla.getColumnModel().getColumn(5).setPreferredWidth(10);
            tbTabla.getColumnModel().getColumn(6).setPreferredWidth(10);
            
        } 
        catch (SQLException ex) {
            Logger.getLogger(PantallaConsultarNiño.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "No existe ningún niño registrado en la base de datos.");
            cbTodo.setSelected(false);
        }
    }//GEN-LAST:event_cbTodoActionPerformed

    private void cbBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBuscarActionPerformed

        borrarTabla();
        if(cbBuscar.getSelectedIndex() == 1){
            IniciarVentana();
            cbBuscar.setSelectedIndex(1);
            buscar = 1;
            
            lblNombres.setVisible(false);
            lblID.setVisible(true);
            tfNombres.setVisible(true);
            tfNombres.setText("");
            bBuscar.setVisible(true);
            
        }
        else if(cbBuscar.getSelectedIndex() == 2){
            IniciarVentana();
            buscar = 2;
            cbBuscar.setSelectedIndex(2);
            
            lblNombres.setVisible(true);
            lblID.setVisible(false);
            tfNombres.setVisible(true);
            tfNombres.setText("");
            lblApellidoPaterno.setVisible(true);
            tfApellidoPaterno.setVisible(true);
            tfApellidoPaterno.setText("");
            lblApellidoMaterno.setVisible(true);
            tfApellidoMaterno.setVisible(true);
            tfApellidoMaterno.setText("");
            bBuscar.setVisible(true);
        }
        else{
            IniciarVentana();
            buscar = 3;
            
        }
    }//GEN-LAST:event_cbBuscarActionPerformed

    private void bBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBuscarActionPerformed

        if(buscar == 1){
            if(tfNombres.getText().equals("") ){
                JOptionPane.showMessageDialog(null, "Error. Datos incompletos\nInténtelo de nuevo.");
            }
            else{
                borrarTabla();
                tbTabla.setDefaultRenderer(Object.class, new TablaImagen());
                DefaultTableModel tabla = new DefaultTableModel();
                
                tabla.addColumn("ID");
                tabla.addColumn("Foto");
                tabla.addColumn("Nombres");
                tabla.addColumn("Apellido Paterno");
                tabla.addColumn("Apellido Materno");
                tabla.addColumn("");
                tabla.addColumn("");
                
                try {
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VISTA", "root", "");
                    Statement stmt = con.createStatement();
                    ResultSet rs;
                    rs = stmt.executeQuery("SELECT * FROM Ninos WHERE ID = "+tfNombres.getText());
                    rs.first();

                    Object[] fila = new Object[7];

                    fila[0] = rs.getObject(1);
                    foto = getToolkit().getImage(rs.getObject(5).toString());
                    foto = foto.getScaledInstance(260, 260, 260);
                    ImageIcon icono = new ImageIcon(foto);
                    conversion = icono.getImage();
                    tamaño = conversion.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    fin = new ImageIcon(tamaño);

                    fila[1] = new JLabel(fin);
                    fila[2] = rs.getObject(2);
                    fila[3] = rs.getObject(3);
                    fila[4] = rs.getObject(4);
                    fila[5] = editar;
                    fila[6] = eliminar;

                    tabla.addRow(fila);
                     //Se introduce toda la info a la tabla
                tbTabla.setModel(tabla);
                tbTabla.setRowHeight(90);

                //Cada columna se ajusta a un determinado tamaño
                tbTabla.getColumnModel().getColumn(0).setPreferredWidth(10);
                tbTabla.getColumnModel().getColumn(1).setPreferredWidth(90);
                tbTabla.getColumnModel().getColumn(2).setPreferredWidth(100);
                tbTabla.getColumnModel().getColumn(3).setPreferredWidth(80);
                tbTabla.getColumnModel().getColumn(4).setPreferredWidth(80);
                tbTabla.getColumnModel().getColumn(5).setPreferredWidth(10);
                tbTabla.getColumnModel().getColumn(6).setPreferredWidth(10);
                } catch (SQLException ex) {
                    Logger.getLogger(PantallaConsultarNiño.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "El niño con ID: "+tfNombres.getText()+" no existe");
                }

            }
        }
        else if(buscar == 2){
            if(tfNombres.getText().equals("") || tfApellidoPaterno.getText().equals("") || tfApellidoMaterno.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Error. Datos incompletos\nInténtelo de nuevo.");
            }
            else{
                borrarTabla();
                tbTabla.setDefaultRenderer(Object.class, new TablaImagen());
                DefaultTableModel tabla = new DefaultTableModel();
                tabla.addColumn("ID");
                tabla.addColumn("Foto");
                tabla.addColumn("Nombres");
                tabla.addColumn("Apellido Paterno");
                tabla.addColumn("Apellido Materno");
                tabla.addColumn("");
                tabla.addColumn("");
                try {
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VISTA", "root", "");
                    Statement stmt = con.createStatement();
                    ResultSet rs;
                    rs = stmt.executeQuery("SELECT * FROM Ninos WHERE Nombres = '"+tfNombres.getText()+"' AND "
                        + "Apellido_paterno = '"+tfApellidoPaterno.getText()+"' AND Apellido_materno = '"+tfApellidoMaterno.getText()+"'");
                    rs.first();

                    Object[] fila = new Object[7];

                    fila[0] = rs.getObject(1);
                    foto = getToolkit().getImage(rs.getObject(5).toString());
                    foto = foto.getScaledInstance(260, 260, 260);
                    ImageIcon icono = new ImageIcon(foto);
                    conversion = icono.getImage();
                    tamaño = conversion.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    fin = new ImageIcon(tamaño);

                    fila[1] = new JLabel(fin);
                    fila[2] = rs.getObject(2);
                    fila[3] = rs.getObject(3);
                    fila[4] = rs.getObject(4);
                    fila[5] = editar;
                    fila[6] = eliminar;

                    tabla.addRow(fila);

                    tbTabla.setModel(tabla);
                    tbTabla.setRowHeight(90);

                    //Cada columna se ajusta a un determinado tamaño
                    tbTabla.getColumnModel().getColumn(0).setPreferredWidth(10);
                    tbTabla.getColumnModel().getColumn(1).setPreferredWidth(90);
                    tbTabla.getColumnModel().getColumn(2).setPreferredWidth(100);
                    tbTabla.getColumnModel().getColumn(3).setPreferredWidth(80);
                    tbTabla.getColumnModel().getColumn(4).setPreferredWidth(80);
                    tbTabla.getColumnModel().getColumn(5).setPreferredWidth(10);
                    tbTabla.getColumnModel().getColumn(6).setPreferredWidth(10);
                } catch (SQLException ex) {
                    Logger.getLogger(PantallaConsultarNiño.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "El niño: "+tfNombres.getText()+" "+tfApellidoPaterno.getText()+" "+tfApellidoMaterno.getText()+
                        " no existe");
                }

            }
        }
    }//GEN-LAST:event_bBuscarActionPerformed

    private void tbTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTablaMouseClicked
        int column = tbTabla.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = evt.getY()/tbTabla.getRowHeight();
        int ren;
        String IDNiño = null;
        
        if(row < tbTabla.getRowCount() && row >= 0 && column < tbTabla.getColumnCount() && column >= 0){
            Object value = tbTabla.getValueAt(row, column);
            if(value instanceof JLabel){
                JLabel lbl = (JLabel) value;
                
                if(lbl.getName().equals("editar")){
                    
                    ren = tbTabla.getSelectedRow();
                    //Se extrae el ID del niño 
                    IDNiño = tbTabla.getValueAt(ren, 0).toString();
                    
                    pantallaActualizar.setLocation(this.getLocationOnScreen());
                    pantallaActualizar.setVisible(true);
                    pantallaActualizar.setTitle("EDITAR NIÑO");
                    pantallaActualizar.MostrarPantalla(IDNiño);
                    
                }
                else if (lbl.getName().equals("eliminar")){
                    System.out.println(pantallaActualizar.isActive());
                    if(JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar al niño?") == 0){
                        
                    
                        ren = tbTabla.getSelectedRow();

                        //Se extrae el ID del niño 
                        IDNiño = tbTabla.getValueAt(ren, 0).toString();

                        /*Se extrae la información de la foto, el tutor y los telAutorizados del niño para posteriormente
                          decidir si se pueden eliminar por completo ó no.
                        */
                        //Info del niño, archivo de los niños del tutor, foto del tutor
                        ResultSet rs = null, rs2 = null, rs3 = null;
                        String[] infoNiño = new String[3];
                        String archivoTutor;
                        String[] fotos = new String[5];

                        try {
                            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VISTA", "root", "");
                            Statement stmt = con.createStatement();

                            //Se extrae la información necesaria de la tabla del niño.
                            rs = stmt.executeQuery("SELECT Foto, Tutor, Autorizados FROM Ninos WHERE ID = "+IDNiño);
                            rs.first();

                            //Se almacena la información del niño
                            infoNiño[0] = rs.getObject(1).toString();
                            infoNiño[1] = rs.getObject(2).toString();
                            infoNiño[2] = rs.getObject(3).toString();

                            //Se extrae el archivo con los niños del tutor.
                            rs2 = stmt.executeQuery("SELECT Ninos FROM Tutores WHERE Telefono = '"+rs.getObject(2).toString()+"'");
                            rs2.first();

                            //Se guarda el archivo con los niños del tutor.
                            archivoTutor = rs2.getObject(1).toString();


                            //Si el tutor sólo tenía a ese niño asignado, entonces se elimina su información de los registros.
                            if( BuscarIDNiño(archivoTutor, IDNiño) == 1 ){

                                try{
                                    fotos[1] = stmt.executeQuery("SELECT Foto FROM Tutores WHERE Telefono = '"+infoNiño[1]+"'").toString(); //Guardas la foto del tutor para luego eliminarla
                                    stmt.executeUpdate("DELETE FROM Tutores WHERE Telefono = '"+infoNiño[1]+"'");

                                }
                                catch(SQLException e){
                                    Logger.getLogger(PantallaConsultarNiño.class.getName()).log(Level.SEVERE, null, e);
                                }

                            }

                            //Se lee el archivo de los telAutorizados del niño para extraer el teléfono de cada uno.
                            if(!infoNiño[2].equals("null")){ //Si el archivo es null, entonces no tiene telAutorizados.
                                int numAutorizado = 0;
                                String aux;
                                String[] telAutorizados = new String[3];
                                String[] archivosAutorizados = new String[3];
                                try{
                                    BufferedReader bf = new BufferedReader(new FileReader(infoNiño[2]));
                                    while((aux = bf.readLine()) != null){
                                        telAutorizados[numAutorizado] = aux;
                                        numAutorizado++;
                                    }

                                    for(String a : telAutorizados){
                                        System.out.println(a);
                                    }
                                }

                                catch(IOException e){
                                    JOptionPane.showMessageDialog(null, "Error con la lectura del archivo de los autorizados del niño.");
                                }
                                numAutorizado = 0;

                                File archivo = new File(infoNiño[2]);
                                archivo.delete();

                                //Se obtienen los archivos de los autorizados para borrar al niño de ellos.
                                ResultSet r4 = null, r5 = null, r6 = null;
                                for(int i = 0 ; i < 3 ; i++){
                                   if(i == 0 && telAutorizados[i] != null){
                                    r4 = stmt.executeQuery("SELECT Ninos FROM Tutores WHERE Telefono = '"+telAutorizados[i]+"'");
                                    r4.first();
                                    archivosAutorizados[0] = r4.getObject(1).toString();
                                   }
                                   if(i == 1 && telAutorizados[i] != null){
                                     r5 = stmt.executeQuery("SELECT Ninos FROM Tutores WHERE Telefono = '"+telAutorizados[i]+"'");  
                                     r5.first();
                                     archivosAutorizados[1] = r5.getObject(1).toString();
                                   }
                                   if(i == 2 && telAutorizados[i] != null){
                                     r6 = stmt.executeQuery("SELECT Ninos FROM Tutores WHERE Telefono = '"+telAutorizados[i]+"'");  
                                     r6.first();
                                     archivosAutorizados[2] = r6.getObject(1).toString();
                                   }
                                }

                                for(String a : archivosAutorizados){
                                    System.out.println(a);
                                }

                                //Por cada archivo se revisa si el tutor puede ser eliminado o no.
                                numAutorizado = 0;
                                for(String ruta: archivosAutorizados){
                                    if(ruta != null){
                                        if( BuscarIDNiño(ruta, IDNiño) == 1 ){

                                        try{
                                            //GUARDAR LAS FOTOS!
                                           // rs3 = stmt.executeQuery("SELECT Foto FROM Tutores WHERE Telefono = '"+telAutorizados[numAutorizado]+"'");
                                            stmt.executeUpdate("DELETE FROM Tutores WHERE Telefono = '"+telAutorizados[numAutorizado]+"'");

                                        }
                                        catch(SQLException e){
                                            Logger.getLogger(PantallaConsultarNiño.class.getName()).log(Level.SEVERE, null, e);
                                        }

                                        }  
                                        numAutorizado++;
                                    }  
                                }
                            }

                            //Se elimina al niño de la base de datos
                            stmt.executeUpdate("DELETE FROM Ninos WHERE ID = "+IDNiño);
                            JOptionPane.showMessageDialog(null, "SE HA ELIMINADO AL NIÑO");

                            //Se actualiza la tabla
                            cbTodo.doClick();

                        } catch (SQLException ex) {
                            Logger.getLogger(PantallaConsultarNiño.class.getName()).log(Level.SEVERE, null, ex);
                        }




                    }
                }
            }
        }
    }//GEN-LAST:event_tbTablaMouseClicked

    private void bActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bActualizarActionPerformed
        Actualizar();
    }//GEN-LAST:event_bActualizarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bActualizar;
    private javax.swing.JButton bBuscar;
    private javax.swing.JComboBox<String> cbBuscar;
    private javax.swing.JCheckBox cbTodo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblApellidoMaterno;
    private javax.swing.JLabel lblApellidoPaterno;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblNombres;
    private javax.swing.JLabel lblTítulo;
    private javax.swing.JTable tbTabla;
    private javax.swing.JTextField tfApellidoMaterno;
    private javax.swing.JTextField tfApellidoPaterno;
    private javax.swing.JTextField tfNombres;
    // End of variables declaration//GEN-END:variables
}
