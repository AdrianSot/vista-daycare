/*VERSION DE WINDOWS CRUDS COMPLETOS*/

package vista;

//jdbc:mysql://localhost:3306/VISTA?useTimezone=true&serverTimezone=UTC [root on Default schema]
import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

public class PantallaActualizarNiño extends javax.swing.JFrame {

    /**
     * Creates new form PantallaActualizarNiño
     */
    String ID, nombresNiño, apellidoPaternoNiño, apellidoMaternoNiño; //Info del niño
    
    String nombresTutor, apellidoPaternoTutor, apellidoMaternoTutor, teléfonoTutor; //Info del tutor
    
    String telTutor, telAut1, telAut2, telAut3; //Teléfonos auxiliares
    
    String nombresAutorizados[] = {"","",""}; //Nombres de los autorizados
    String apellidosPaternosAutorizados[] = {"","",""}; //Apellidos paternos de los autorizados
    String apellidosMaternosAutorizados[] = {"","",""}; // Apellidos maternos de los autorizados
    String teléfonosAutorizados[] = {"","",""}; // Teléfonos de los autorizados
    
    //Archivos
    String archivoNiñosTutor, archivoTutor, archivoAutorizados, archivoNiñosAut1, archivoNiñosAut2, archivoNiñosAut3;
    
    //Imágenes
    Image fotoNiño, fotoTutor;
    Image fotosAutorizados[] ={null,null,null}; 
    
    //Direcciones de las imágenes
    String dirFotoNiño, dirFotoTutor, dirFotoNiñoNueva, dirFotoTutorNueva, dirFotoAut1Nueva, dirFotoAut2Nueva, dirFotoAut3Nueva;
    String dirFotosAutorizados[] = {"No", "No", "No"};
    
    //Íconos
    ImageIcon iconoNiño, iconoTutor;
    ImageIcon iconosAutorizados[] = {null,null,null};
    
    //Fotos nuevas
    File nuevaFotoNiño, nuevaFotoTutor, nuevaFotoAut1, nuevaFotoAut2, nuevaFotoAut3, archTutor, archAutorizados, archNiño;
    
    Connection con;
    Statement stmt;
    
    //Teléfonos de tutor y autorizados para corroborar que los celulares no se repitan antes de ejecutar la actualización de la info
    String Teléfonos[];
    
    //Bandera para indicar si exite un error; si es así, entonces la actualización de la info no se realiza.
    //confirmacion: Confirmacion de que se quieren guardar los cambios.
    boolean error, confirmacion, datosIncompletos;
    String Estatus; //Bandera para saber si un tutor es autorizado o tutor.
    
    PantallaCrearTutor crearTutor;
    
    //Rutas
    String ruta, directorioRaiz;  
    
    CamConRec camara;
    String linkbd = "jdbc:mysql://localhost:3306/VISTA?useTimezone=true&serverTimezone=UTC";
    public PantallaActualizarNiño() {
        initComponents();
        setSize(1000,800);
        setLocation(0,-25);
        crearTutor = new PantallaCrearTutor();
        
        IniciarVentana();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(PantallaRegistrarNiño.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setExtendedState(MAXIMIZED_BOTH);
        
    }
    
    public void IniciarVentana(){
        camara = new CamConRec();
        //SE EXTRAEN TODOS LOS TELÉFONOS DE LA BD PARA QUE AL MOMENTO DE AJUSTAR LA INFORMACIÓN NO SE REPITAN LOS TUTORES/AUTORIZADOS
        try {
            con = DriverManager.getConnection(linkbd, "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs, rs2;
            rs = stmt.executeQuery("SELECT COUNT(*) FROM Tutores");
            rs.first();
            
            Teléfonos = new String[Integer.parseInt(rs.getObject(1).toString())];
            
            rs2 = stmt.executeQuery("SELECT Telefono FROM Tutores");
            rs2.first();
            
            for(int i = 0; i < Teléfonos.length ; i++){
                Teléfonos[i] = rs2.getObject(1).toString();
                rs2.next();
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
        }
        //DISEÑO DE LAS DIVISIONES
        this.lbl.setBackground(Color.black);
        this.lbl.setOpaque(true);
        this.lbl1.setBackground(Color.black);
        this.lbl1.setOpaque(true);
        
        //NOMBRES DE LAS ETIQUETAS
        lblNombreTutor.setText("Nombres: ");
        lblNombresAut1.setText("Nombres: ");
        lblNombresAut2.setText("Nombres: ");
        lblNombresAut3.setText("Nombres: ");
        
        //VISUALIZACIÓN DE LAS ETIQUETAS
        //Tutor
        lblNombreTutor.setVisible(true);
        lblApellidoPaternoTutor.setVisible(true);
        lblApellidoMaternoTutor.setVisible(true);
        lblTeléfonoTutor.setVisible(true);
        //Autorizado1
        lblNombresAut1.setVisible(true);
        lblApellidoPaternoAut1.setVisible(true);
        lblApellidoMaternoAut1.setVisible(true);
        lblTeléfonoAut1.setVisible(true);
        //Autorizado2
        lblNombresAut2.setVisible(true);
        lblApellidoPaternoAut2.setVisible(true);
        lblApellidoMaternoAut2.setVisible(true);
        lblTeléfonoAut2.setVisible(true);
        //Autorizado3
        lblNombresAut3.setVisible(true);
        lblApellidoPaternoAut3.setVisible(true);
        lblApellidoMaternoAut3.setVisible(true);
        lblTeléfonoAut3.setVisible(true);
        
        //DESSELECCIÓN DE LOS CHECKBOXES
        cbEditarNiño.setSelected(false);
        cbAsignarTutor.setSelected(false);
        cbEditarTutor.setSelected(false);
        cbCambiarTutor.setSelected(false);
        cbEditarAut1.setSelected(false);
        cbEliminarAut1.setSelected(false);
        cbAsignarAut1.setSelected(false);
        cbEditarAut2.setSelected(false);
        cbEliminarAut2.setSelected(false);
        cbAsignarAut2.setSelected(false);
        cbEditarAut3.setSelected(false);
        cbEliminarAut3.setSelected(false);
        cbAsignarAut3.setSelected(false);
        
        //DESHABILITACIÓN DE LOS BOTONES PARA TOMAR FOTOS
        bTomarFotoNiño.setEnabled(false);
        bTomarFotoTutor.setEnabled(false);
        bTomarFotoAut1.setEnabled(false);
        bTomarFotoAut2.setEnabled(false);
        bTomarFotoAut3.setEnabled(false);
        
        //DESHABILITACIÓN DE LOS BOTONES PARA INSERTAR UNA NUEVA FOTO
        bInsertarFotoNiño.setEnabled(false);
        bInsertarFotoTutor.setEnabled(false);
        bInsertarFotoAut1.setEnabled(false);
        bInsertarFotoAut2.setEnabled(false);
        bInsertarFotoAut3.setEnabled(false);
        
        
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
        
        //SE HABILITA LA VISUALIZACIÓN DE TODOS LOS CAMPOS DE TEXTO
        //Niños
        tfNombresNiño.setVisible(true);
        tfApellidoPaternoNiño.setVisible(true);
        tfApellidoMaternoNiño.setVisible(true);
        //tutor
        tfNombresTutor.setVisible(true);
        tfApellidoPaternoTutor.setVisible(true);
        tfApellidoMaternoTutor.setVisible(true);
        tfTeléfonoTutor.setVisible(true);
        //Autorizado1
        tfNombresAut1.setVisible(true);
        tfApellidoPaternoAut1.setVisible(true);
        tfApellidoMaternoAut1.setVisible(true);
        tfTeléfonoAut1.setVisible(true);
        //Autorizado2
        tfNombresAut2.setVisible(true);
        tfApellidoPaternoAut2.setVisible(true);
        tfApellidoMaternoAut2.setVisible(true);
        tfTeléfonoAut2.setVisible(true);
        //Autorizado3
        tfNombresAut3.setVisible(true);
        tfApellidoPaternoAut3.setVisible(true);
        tfApellidoMaternoAut3.setVisible(true);
        tfTeléfonoAut3.setVisible(true);
        
        
        
        ID = nombresNiño = apellidoPaternoNiño = apellidoMaternoNiño = ""; //Inicialización de la info del niño
        
        nombresTutor = apellidoPaternoTutor = apellidoMaternoTutor = teléfonoTutor = ""; //Inicialización de la info del tutor

        
        telTutor = telAut1 = telAut2 = telAut3 = ""; //Se inicializan los teléfonos auxiliares
        
        //Archivos
        archivoNiñosTutor = archivoTutor = archivoAutorizados = archivoNiñosAut1 = archivoNiñosAut2 = archivoNiñosAut3 = "";
        
        //Fotos
        fotoNiño = fotoTutor =null;
        
        //Dirección a las fotos
        dirFotoNiño = dirFotoTutor = "No";
        
        
        //Iconos
        iconoNiño = iconoTutor = null;
        
        //Archivos de fotos nuevas
        nuevaFotoNiño = nuevaFotoTutor = nuevaFotoAut1 = nuevaFotoAut2 = nuevaFotoAut3 = null;

        //Imágenes
        for(int i = 0 ; i < 3 ; i++){
            fotosAutorizados[i] = null;
            iconosAutorizados[i] = null;
            nombresAutorizados[i] = apellidosPaternosAutorizados[i] =
            apellidosMaternosAutorizados[i] = teléfonosAutorizados[i] = "";
        }
        
        //Banderas
        error = confirmacion = datosIncompletos = false;
        Estatus = "";
        
        //Rutas
        ruta = ""; 
        String aux = System.getProperty("user.dir");
        directorioRaiz = aux.replace("\\", "/");  
    }

    public void DefaultAutorizado1(){
        tfNombresAut1.setForeground(Color.lightGray);
        tfApellidoPaternoAut1.setForeground(Color.lightGray);
        tfApellidoMaternoAut1.setForeground(Color.lightGray);
        tfTeléfonoAut1.setForeground(Color.lightGray);

        tfNombresAut1.setEditable(false);
        tfApellidoPaternoAut1.setEditable(false);
        tfApellidoMaternoAut1.setEditable(false);
        tfTeléfonoAut1.setEditable(false);
        
        tfNombresAut1.setVisible(true);
        tfApellidoPaternoAut1.setVisible(true);
        tfApellidoMaternoAut1.setVisible(true);
        tfTeléfonoAut1.setVisible(true);

        lblNombresAut1.setText("Nombres: ");
        lblApellidoPaternoAut1.setVisible(true);
        lblApellidoMaternoAut1.setVisible(true);
        lblTeléfonoAut1.setVisible(true);

        tfNombresAut1.setText(nombresAutorizados[0]);
        tfApellidoPaternoAut1.setText(apellidosPaternosAutorizados[0]);
        tfApellidoMaternoAut1.setText(apellidosMaternosAutorizados[0]);
        tfTeléfonoAut1.setText(teléfonosAutorizados[0]);
        bTomarFotoAut1.setEnabled(false);
        bInsertarFotoAut1.setEnabled(false);
    }
    
    public void DefaultAutorizado2(){
        tfNombresAut2.setForeground(Color.lightGray);
        tfApellidoPaternoAut2.setForeground(Color.lightGray);
        tfApellidoMaternoAut2.setForeground(Color.lightGray);
        tfTeléfonoAut2.setForeground(Color.lightGray);

        tfNombresAut2.setEditable(false);
        tfApellidoPaternoAut2.setEditable(false);
        tfApellidoMaternoAut2.setEditable(false);
        tfTeléfonoAut2.setEditable(false);
        
        tfNombresAut2.setVisible(true);
        tfApellidoPaternoAut2.setVisible(true);
        tfApellidoMaternoAut2.setVisible(true);
        tfTeléfonoAut2.setVisible(true);

        lblNombresAut2.setText("Nombres: ");
        lblApellidoPaternoAut2.setVisible(true);
        lblApellidoMaternoAut2.setVisible(true);
        lblTeléfonoAut2.setVisible(true);

        tfNombresAut2.setText(nombresAutorizados[1]);
        tfApellidoPaternoAut2.setText(apellidosPaternosAutorizados[1]);
        tfApellidoMaternoAut2.setText(apellidosMaternosAutorizados[1]);
        tfTeléfonoAut2.setText(teléfonosAutorizados[1]);
        bTomarFotoAut2.setEnabled(false);
        bInsertarFotoAut2.setEnabled(false);
    }
    
    public void DefaultAutorizado3(){
        tfNombresAut3.setForeground(Color.lightGray);
        tfApellidoPaternoAut3.setForeground(Color.lightGray);
        tfApellidoMaternoAut3.setForeground(Color.lightGray);
        tfTeléfonoAut3.setForeground(Color.lightGray);

        tfNombresAut3.setEditable(false);
        tfApellidoPaternoAut3.setEditable(false);
        tfApellidoMaternoAut3.setEditable(false);
        tfTeléfonoAut3.setEditable(false);
        
        tfNombresAut3.setVisible(true);
        tfApellidoPaternoAut3.setVisible(true);
        tfApellidoMaternoAut3.setVisible(true);
        tfTeléfonoAut3.setVisible(true);

        lblNombresAut3.setText("Nombres: ");
        lblApellidoPaternoAut3.setVisible(true);
        lblApellidoMaternoAut3.setVisible(true);
        lblTeléfonoAut3.setVisible(true);

        tfNombresAut3.setText(nombresAutorizados[2]);
        tfApellidoPaternoAut3.setText(apellidosPaternosAutorizados[2]);
        tfApellidoMaternoAut3.setText(apellidosMaternosAutorizados[2]);
        tfTeléfonoAut3.setText(teléfonosAutorizados[2]);
        bTomarFotoAut3.setEnabled(false);
        bInsertarFotoAut3.setEnabled(false);
    }
    
    public boolean Tutor(String direccion, String telefono){
        String aux;
        boolean existe = false;
        
        try{
            BufferedReader bf = new BufferedReader(new FileReader(direccion));
            while((aux = bf.readLine()) != null){
                if(aux.equals(telefono)){
                    existe = true;
                    break;
                }
                else{
                    existe = false;
                }
            }
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error con la lectura del archivo");
        }
        return(existe);
    }
    
    public void AgregarIDNiño(String direccion, String ID){
        BufferedWriter bw = null;
        FileWriter fw = null;
        String aux;
        
        try{
            BufferedReader bf = new BufferedReader(new FileReader(direccion));
            while((aux = bf.readLine()) != null){
                if(aux.equals(ID) || aux.equals(ID+"\r\n")){
                    bf.close();
                    return;
                }
            }
            bf.close();
        }

        catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error con la lectura del archivo");
        }
        
        try {
            fw = new FileWriter(direccion, true);
            bw = new BufferedWriter(fw);
            bw.write(ID+"\r\n");
            //bw.newLine();
        } catch (IOException ex) {
            Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                            //Cierra instancias de FileWriter y BufferedWriter
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void MostrarPantalla(String id){
        IniciarVentana();
        ID = id;
        
        try {
            con = DriverManager.getConnection(linkbd, "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT * FROM Ninos WHERE ID = "+ID); //SE EXTRAE LA INFORMACIÓN DEL NIÑO
            rs.first();
            
            //SE GUARDA LA INFORMACIÓN EN LAS RESPECTIVAS VARIABLES
            nombresNiño = rs.getObject(2).toString();
            apellidoPaternoNiño = rs.getObject(3).toString();
            apellidoMaternoNiño = rs.getObject(4).toString();
            dirFotoNiño = rs.getObject(5).toString();
            fotoNiño = getToolkit().getImage(rs.getObject(5).toString());
            fotoNiño = fotoNiño.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            iconoNiño = new ImageIcon(fotoNiño);
            teléfonoTutor = ( rs.getObject(6).toString().equals("null") ? "" : rs.getObject(6).toString());
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
            con = DriverManager.getConnection(linkbd, "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT * FROM Tutores WHERE Telefono = '"+teléfonoTutor+"'"); //SE EXTRAE LA INFORMACIÓN DEL NIÑO
            rs.first();
            
            //SE GUARDA LA INFORMACIÓN EN LAS RESPECTIVAS VARIABLES
            nombresTutor = rs.getObject(2).toString();
            apellidoPaternoTutor = rs.getObject(3).toString();
            apellidoMaternoTutor = rs.getObject(4).toString();
            dirFotoTutor = rs.getObject(5).toString();
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
                if(!aux.equals(teléfonoTutor)){ //Se salta el teléfono del tutor
                    teléfonosAutorizados[numAut] = aux;
                    numAut++;
                }
                
            }
            bf.close();
            
        }

        catch(IOException e){
        }
        
        numAut = 0;
        for(String tel : teléfonosAutorizados){
            try {
                con = DriverManager.getConnection(linkbd, "root", "");
                Statement stmt = con.createStatement();
                ResultSet rs;
                rs = stmt.executeQuery("SELECT * FROM Tutores WHERE Telefono = '"+tel+"'"); //Se extrae la info de cada autorizado
                rs.first();
                
                //SE GUARDA LA INFORMACIÓN EN LAS RESPECTIVAS VARIABLES
                if(rs.first()){
                    nombresAutorizados[numAut] = rs.getObject(2).toString();
                    apellidosPaternosAutorizados[numAut] = rs.getObject(3).toString();
                    apellidosMaternosAutorizados[numAut] = rs.getObject(4).toString();
                    dirFotosAutorizados[numAut] = rs.getObject(5).toString();
                    fotosAutorizados[numAut] = getToolkit().getImage(rs.getObject(5).toString());
                    fotosAutorizados[numAut] = fotosAutorizados[numAut].getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    iconosAutorizados[numAut] = new ImageIcon(fotosAutorizados[numAut]);
                    if(numAut == 0) archivoNiñosAut1 = rs.getObject(6).toString();
                    else if(numAut == 1) archivoNiñosAut2 = rs.getObject(6).toString();
                    else if(numAut == 2) archivoNiñosAut3 = rs.getObject(6).toString();
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
        
        //SI EL NIÑO YA TIENE UN TUTOR, ENTONCES SE BLOQUEA LA OPCIÓN DE ASIGNARLE UNO, PERO LO PUEDE 
        if(tfNombresTutor.getText().equals("")){
            cbCambiarTutor.setEnabled(false);
            cbAsignarTutor.setEnabled(true);
            cbEditarTutor.setEnabled(false);
        }
        else{
            cbCambiarTutor.setEnabled(true);
            cbAsignarTutor.setEnabled(false);
            cbEditarTutor.setEnabled(true);
        }
        
        //SI EL NIÑO YA TIENE AL AUTORIZADO 1, ENTONCES SE BLOQUEA LA OPCIÓN DE ASIGNAR Y ELIMINAR
        if(tfNombresAut1.getText().equals("")){
            cbAsignarAut1.setEnabled(true);
            cbEditarAut1.setEnabled(false);
            cbEliminarAut1.setEnabled(false);
        }
        else{
            cbAsignarAut1.setEnabled(false);
            cbEditarAut1.setEnabled(true);
            cbEliminarAut1.setEnabled(true);
        }
        
        //SI EL NIÑO YA TIENE AL AUTORIZADO 2, ENTONCES SE BLOQUEA LA OPCIÓN DE ASIGNAR Y ELIMINAR
        if(tfNombresAut2.getText().equals("")){
            cbAsignarAut2.setEnabled(true);
            cbEditarAut2.setEnabled(false);
            cbEliminarAut2.setEnabled(false);
        }
        else{
            cbAsignarAut2.setEnabled(false);
            cbEditarAut2.setEnabled(true);
            cbEliminarAut2.setEnabled(true);
        }
        
        //SI EL NIÑO YA TIENE AL AUTORIZADO 2, ENTONCES SE BLOQUEA LA OPCIÓN DE ASIGNAR
        if(tfNombresAut3.getText().equals("")){
            cbAsignarAut3.setEnabled(true);
            cbEditarAut3.setEnabled(false);
            cbEliminarAut3.setEnabled(false);
        }
        else{
            cbAsignarAut3.setEnabled(false);
            cbEditarAut3.setEnabled(true);
            cbEliminarAut3.setEnabled(true);
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
        lblNombresAut1 = new javax.swing.JLabel();
        tfNombresAut1 = new javax.swing.JTextField();
        lblApellidoPaternoAut1 = new javax.swing.JLabel();
        tfApellidoPaternoAut1 = new javax.swing.JTextField();
        lblApellidoMaternoAut1 = new javax.swing.JLabel();
        tfApellidoMaternoAut1 = new javax.swing.JTextField();
        lblTeléfonoAut1 = new javax.swing.JLabel();
        tfTeléfonoAut1 = new javax.swing.JTextField();
        lbl2 = new javax.swing.JLabel();
        lblFotoAut2 = new javax.swing.JLabel();
        lblNombresAut2 = new javax.swing.JLabel();
        tfNombresAut2 = new javax.swing.JTextField();
        lblApellidoPaternoAut2 = new javax.swing.JLabel();
        tfApellidoPaternoAut2 = new javax.swing.JTextField();
        lblApellidoMaternoAut2 = new javax.swing.JLabel();
        tfApellidoMaternoAut2 = new javax.swing.JTextField();
        lblTeléfonoAut2 = new javax.swing.JLabel();
        tfTeléfonoAut2 = new javax.swing.JTextField();
        lblFotoAut3 = new javax.swing.JLabel();
        lblNombresAut3 = new javax.swing.JLabel();
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
        cbEditarTutor = new javax.swing.JCheckBox();
        cbEditarAut1 = new javax.swing.JCheckBox();
        cbEliminarAut1 = new javax.swing.JCheckBox();
        cbEditarAut2 = new javax.swing.JCheckBox();
        cbEliminarAut2 = new javax.swing.JCheckBox();
        cbEditarAut3 = new javax.swing.JCheckBox();
        cbEliminarAut3 = new javax.swing.JCheckBox();
        bGuardar = new javax.swing.JButton();
        cbEditarNiño = new javax.swing.JCheckBox();
        cbAsignarAut1 = new javax.swing.JCheckBox();
        cbAsignarAut2 = new javax.swing.JCheckBox();
        cbAsignarAut3 = new javax.swing.JCheckBox();
        cbCambiarTutor = new javax.swing.JCheckBox();
        bCrearTutor = new javax.swing.JButton();
        bInsertarFotoNiño = new javax.swing.JButton();
        bInsertarFotoTutor = new javax.swing.JButton();
        bInsertarFotoAut1 = new javax.swing.JButton();
        bInsertarFotoAut2 = new javax.swing.JButton();
        bInsertarFotoAut3 = new javax.swing.JButton();

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

        lblNombresAut1.setText("Nombres: ");

        lblApellidoPaternoAut1.setText("Apellido paterno:");

        lblApellidoMaternoAut1.setText("Apellido materno:");

        lblTeléfonoAut1.setText("Teléfono:");

        lbl2.setBackground(new java.awt.Color(19, 15, 12));
        lbl2.setOpaque(true);

        lblFotoAut2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblNombresAut2.setText("Nombres: ");

        lblApellidoPaternoAut2.setText("Apellido paterno:");

        lblApellidoMaternoAut2.setText("Apellido materno:");

        lblTeléfonoAut2.setText("Teléfono:");

        lblFotoAut3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblNombresAut3.setText("Nombres: ");

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
        bTomarFotoNiño.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTomarFotoNiñoActionPerformed(evt);
            }
        });

        bTomarFotoTutor.setText("Tomar Foto");
        bTomarFotoTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTomarFotoTutorActionPerformed(evt);
            }
        });

        bTomarFotoAut1.setText("Tomar foto");
        bTomarFotoAut1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTomarFotoAut1ActionPerformed(evt);
            }
        });

        bTomarFotoAut2.setText("Tomar foto");
        bTomarFotoAut2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTomarFotoAut2ActionPerformed(evt);
            }
        });

        bTomarFotoAut3.setText("Tomar foto");
        bTomarFotoAut3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTomarFotoAut3ActionPerformed(evt);
            }
        });

        cbAsignarTutor.setText("Asignar tutor");
        cbAsignarTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAsignarTutorActionPerformed(evt);
            }
        });

        cbEditarTutor.setText("Editar");
        cbEditarTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEditarTutorActionPerformed(evt);
            }
        });

        cbEditarAut1.setText("Editar");
        cbEditarAut1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEditarAut1ActionPerformed(evt);
            }
        });

        cbEliminarAut1.setText("Eliminar");
        cbEliminarAut1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEliminarAut1ActionPerformed(evt);
            }
        });

        cbEditarAut2.setText("Editar");
        cbEditarAut2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEditarAut2ActionPerformed(evt);
            }
        });

        cbEliminarAut2.setText("Eliminar");
        cbEliminarAut2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEliminarAut2ActionPerformed(evt);
            }
        });

        cbEditarAut3.setText("Editar");
        cbEditarAut3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEditarAut3ActionPerformed(evt);
            }
        });

        cbEliminarAut3.setText("Eliminar");
        cbEliminarAut3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEliminarAut3ActionPerformed(evt);
            }
        });

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

        cbAsignarAut1.setText("Asignar");
        cbAsignarAut1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAsignarAut1ActionPerformed(evt);
            }
        });

        cbAsignarAut2.setText("Asignar");
        cbAsignarAut2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAsignarAut2ActionPerformed(evt);
            }
        });

        cbAsignarAut3.setText("Asignar");
        cbAsignarAut3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAsignarAut3ActionPerformed(evt);
            }
        });

        cbCambiarTutor.setText("Cambiar");
        cbCambiarTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCambiarTutorActionPerformed(evt);
            }
        });

        bCrearTutor.setText("Crear tutor");
        bCrearTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCrearTutorActionPerformed(evt);
            }
        });

        bInsertarFotoNiño.setText("Insertar foto");
        bInsertarFotoNiño.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bInsertarFotoNiñoActionPerformed(evt);
            }
        });

        bInsertarFotoTutor.setText("Insertar foto");
        bInsertarFotoTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bInsertarFotoTutorActionPerformed(evt);
            }
        });

        bInsertarFotoAut1.setText("Insertar foto");
        bInsertarFotoAut1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bInsertarFotoAut1ActionPerformed(evt);
            }
        });

        bInsertarFotoAut2.setText("Insertar foto");
        bInsertarFotoAut2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bInsertarFotoAut2ActionPerformed(evt);
            }
        });

        bInsertarFotoAut3.setText("Insertar foto");
        bInsertarFotoAut3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bInsertarFotoAut3ActionPerformed(evt);
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
                        .addGap(201, 201, 201)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
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
                                .addComponent(lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(bTomarFotoNiño)
                                .addGap(18, 18, 18)
                                .addComponent(bInsertarFotoNiño)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(bTomarFotoTutor)
                                        .addGap(29, 29, 29)
                                        .addComponent(bInsertarFotoTutor))
                                    .addComponent(lblFotoTutor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(91, 91, 91)
                                .addComponent(lblTutor)))
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
                                    .addComponent(tfApellidoMaternoTutor, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(138, 138, 138)
                                .addComponent(cbAsignarTutor)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbEditarTutor)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbCambiarTutor)))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                .addComponent(cbEliminarAut1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbAsignarAut1))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblApellidoPaternoAut1)
                                    .addComponent(lblApellidoMaternoAut1))
                                .addGap(3, 3, 3)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfApellidoPaternoAut1)
                                    .addComponent(tfApellidoMaternoAut1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblNombresAut1)
                                .addGap(3, 3, 3)
                                .addComponent(tfNombresAut1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTeléfonoAut1)
                                .addGap(3, 3, 3)
                                .addComponent(tfTeléfonoAut1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(bTomarFotoAut1)
                        .addGap(41, 41, 41)
                        .addComponent(bInsertarFotoAut1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bTomarFotoAut2)
                                .addGap(27, 27, 27)
                                .addComponent(bInsertarFotoAut2))
                            .addComponent(lblFotoAut2, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbEditarAut2)
                        .addGap(9, 9, 9)
                        .addComponent(cbEliminarAut2)
                        .addGap(3, 3, 3)
                        .addComponent(cbAsignarAut2))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblApellidoPaternoAut2)
                            .addComponent(lblApellidoMaternoAut2))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfApellidoPaternoAut2)
                            .addComponent(tfApellidoMaternoAut2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNombresAut2)
                        .addGap(3, 3, 3)
                        .addComponent(tfNombresAut2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTeléfonoAut2)
                        .addGap(3, 3, 3)
                        .addComponent(tfTeléfonoAut2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(lbl4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(bTomarFotoAut3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bInsertarFotoAut3)
                        .addGap(48, 48, 48))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblFotoAut3, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbEditarAut3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbEliminarAut3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbAsignarAut3))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblApellidoPaternoAut3)
                            .addComponent(lblApellidoMaternoAut3))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfApellidoPaternoAut3)
                            .addComponent(tfApellidoMaternoAut3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNombresAut3)
                        .addGap(3, 3, 3)
                        .addComponent(tfNombresAut3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTeléfonoAut3)
                        .addGap(3, 3, 3)
                        .addComponent(tfTeléfonoAut3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(717, 717, 717)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(bCrearTutor)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bGuardar)
                .addGap(711, 711, 711))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cbAsignarTutor)
                                    .addComponent(cbEditarTutor)
                                    .addComponent(cbCambiarTutor))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                                    .addComponent(tfTeléfonoTutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
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
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblFotoNiño, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(lblFotoTutor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(bTomarFotoNiño)
                                            .addComponent(bTomarFotoTutor)
                                            .addComponent(bInsertarFotoNiño)
                                            .addComponent(bInsertarFotoTutor)))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTutor)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(cbEditarAut1)
                                .addComponent(cbEliminarAut1)
                                .addComponent(cbAsignarAut1))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(cbEditarAut2)
                                .addComponent(cbEliminarAut2)
                                .addComponent(cbAsignarAut2))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(cbEditarAut3)
                                .addComponent(cbEliminarAut3)
                                .addComponent(cbAsignarAut3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFotoAut1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblNombresAut1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                    .addComponent(lblNombresAut2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                    .addComponent(lblNombresAut3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bTomarFotoAut2))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(bTomarFotoAut3)
                                        .addComponent(bInsertarFotoAut3))
                                    .addComponent(bTomarFotoAut1)
                                    .addComponent(bInsertarFotoAut1)
                                    .addComponent(bInsertarFotoAut2)))))
                    .addComponent(lbl3, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl4, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bCrearTutor)
                .addGap(3, 3, 3)
                .addComponent(bGuardar)
                .addGap(201, 201, 201))
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
           bInsertarFotoNiño.setEnabled(true);
        }
        else{
           tfNombresNiño.setForeground(Color.lightGray);
           tfApellidoPaternoNiño.setForeground(Color.lightGray);
           tfApellidoMaternoNiño.setForeground(Color.lightGray);
           tfNombresNiño.setEditable(false);
           tfApellidoPaternoNiño.setEditable(false);
           tfApellidoMaternoNiño.setEditable(false);
           
           tfNombresNiño.setText(nombresNiño);
           tfApellidoPaternoNiño.setText(apellidoPaternoNiño);
           tfApellidoMaternoNiño.setText(apellidoMaternoNiño);
           bTomarFotoNiño.setEnabled(false);
           bInsertarFotoNiño.setEnabled(false);
           
           lblFotoNiño.setIcon(iconoNiño);
        }
    }//GEN-LAST:event_cbEditarNiñoActionPerformed

    private void cbEditarTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEditarTutorActionPerformed
        if(cbEditarTutor.isSelected()){
            tfNombresTutor.setForeground(Color.black);
            tfApellidoPaternoTutor.setForeground(Color.black);
            tfApellidoMaternoTutor.setForeground(Color.black);
            tfTeléfonoTutor.setForeground(Color.black);
            cbAsignarTutor.setSelected(false);
            cbCambiarTutor.setSelected(false);
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
            bInsertarFotoTutor.setEnabled(true);
            
            lblNombreTutor.setVisible(true);
            lblApellidoPaternoTutor.setVisible(true);
            lblApellidoMaternoTutor.setVisible(true);
            lblTeléfonoTutor.setVisible(true);
            
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
            bInsertarFotoTutor.setEnabled(false);
            
        }
    }//GEN-LAST:event_cbEditarTutorActionPerformed

    private void cbAsignarTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAsignarTutorActionPerformed
        if(cbAsignarTutor.isSelected()){
            cbEditarTutor.setSelected(false);
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
            cbEliminarAut1.setSelected(false);
            cbAsignarAut1.setSelected(false);
            DefaultAutorizado1();
            tfNombresAut1.setForeground(Color.black);
            tfApellidoPaternoAut1.setForeground(Color.black);
            tfApellidoMaternoAut1.setForeground(Color.black);
            tfTeléfonoAut1.setForeground(Color.black);
        
            tfNombresAut1.setEditable(true);
            tfApellidoPaternoAut1.setEditable(true);
            tfApellidoMaternoAut1.setEditable(true);
            tfTeléfonoAut1.setEditable(true);
            bTomarFotoAut1.setEnabled(true);
            bInsertarFotoAut1.setEnabled(true);
        }
        else{
            DefaultAutorizado1();
        }
    }//GEN-LAST:event_cbEditarAut1ActionPerformed

    private void cbEditarAut2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEditarAut2ActionPerformed
        if(cbEditarAut2.isSelected()){
            cbEliminarAut2.setSelected(false);
            cbAsignarAut2.setSelected(false);
            DefaultAutorizado2();
            tfNombresAut2.setForeground(Color.black);
            tfApellidoPaternoAut2.setForeground(Color.black);
            tfApellidoMaternoAut2.setForeground(Color.black);
            tfTeléfonoAut2.setForeground(Color.black);
        
            tfNombresAut2.setEditable(true);
            tfApellidoPaternoAut2.setEditable(true);
            tfApellidoMaternoAut2.setEditable(true);
            tfTeléfonoAut2.setEditable(true);
            bTomarFotoAut2.setEnabled(true);
            bInsertarFotoAut2.setEnabled(true);
        }
        else{
            DefaultAutorizado2();
        }
    }//GEN-LAST:event_cbEditarAut2ActionPerformed

    private void cbEditarAut3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEditarAut3ActionPerformed
        if(cbEditarAut3.isSelected()){
            cbEliminarAut3.setSelected(false);
            cbAsignarAut3.setSelected(false);
            DefaultAutorizado3();
            tfNombresAut3.setForeground(Color.black);
            tfApellidoPaternoAut3.setForeground(Color.black);
            tfApellidoMaternoAut3.setForeground(Color.black);
            tfTeléfonoAut3.setForeground(Color.black);
        
            tfNombresAut3.setEditable(true);
            tfApellidoPaternoAut3.setEditable(true);
            tfApellidoMaternoAut3.setEditable(true);
            tfTeléfonoAut3.setEditable(true);
            bTomarFotoAut3.setEnabled(true);
            bInsertarFotoAut3.setEnabled(true);
        }
        else{
            DefaultAutorizado3();
        }
    }//GEN-LAST:event_cbEditarAut3ActionPerformed

    private void bGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bGuardarActionPerformed
        
        if(JOptionPane.showConfirmDialog(null, "¿Está seguro que desea guardar los cambios?",
           "CONFIRME LOS DATOS",1) == 0){
            confirmacion = true;
        }
        
        /*****************************DETECCIÓN DE ERRORES ANTES DE GUARDAR LOS CAMBIOS***************************************************/
        
        //ANTES DE COMENZAR A HACER CAMBIOS SE CHECAN LOS TELÉFONOS
        /***********************************PENDIENTE************************/
        
        //CREO QUE ESTO NO ES NECESARIO
        /*
        for(int i = 0 ; i < Teléfonos.length ; i++){
            if(cbEditarAut1.isSelected() && tfTeléfonoAut1.getText().equals(Teléfonos[i])){
                JOptionPane.showMessageDialog(null, "El teléfono del Autorizado 1 se repite. (Ya existe en la base de datos)\nNo se guardarán los cambios", "ERROR", 1);
                error = true;
                break;
            }
            if(cbEditarAut2.isSelected() && tfTeléfonoAut2.getText().equals(Teléfonos[i])){
                JOptionPane.showMessageDialog(null, "El teléfono del Autorizado 2 se repite (Ya existe en la base de datos).\nNo se guardarán los cambios", "ERROR", 1);
                error = true;
                break;
            }
            if(cbEditarAut3.isSelected() && tfTeléfonoAut3.getText().equals(Teléfonos[i])){
                JOptionPane.showMessageDialog(null, "El teléfono del Autorizado 3 se repite.(Ya existe en la base de datos)\nNo se guardarán los cambios", "ERROR", 1);
                error = true;
                break;
            }
            
         /*   if(cbCambiarTutor.isSelected() && tfTeléfonoTutor.getText().equals(Teléfonos[i])){
                JOptionPane.showMessageDialog(null, "El teléfono del tutor se repite.(Ya existe en la base de datos)\nNo se guardarán los cambios", "ERROR", 1);
                error = true;
                break;
            }*/
        //} 
        
        /*EDITAR NIÑO*/
        //Si se está editando al niño y se deja un espacio en blanco, entonces se marca como error.
        if((tfNombresNiño.getText().equals("") || tfApellidoPaternoNiño.getText().equals("") || tfApellidoMaternoNiño.getText().equals("")) &&
            cbEditarNiño.isSelected()){
            JOptionPane.showMessageDialog(null, "Información del niño incompleta. Revise e inténtelo de nuevo.", "ERROR", 1);
            datosIncompletos = true;
        }
        
        /*ASIGNAR TUTOR*/
        //Si se está asociando a un tutor y el espacio queda en blanco, entonces se marca como error.
        if((tfNombresTutor.getText().equals("")) && cbAsignarTutor.isSelected()){
            JOptionPane.showMessageDialog(null, "El teléfono del tutor está en blanco\nRevise e inténtelo de nuevo.", "ERROR", 1);
            datosIncompletos = true;
        }
        
        /*ASIGNAR TUTOR*/
        //Si el teléfono del tutor asignado ya pertenece a un autorizado del niño, entonces se marca el error.
        if( ( (tfNombresTutor.getText().equals(tfTeléfonoAut1.getText()) && !tfNombresAut1.getText().equals("") )|| 
              (tfNombresTutor.getText().equals(tfTeléfonoAut2.getText()) && !tfNombresAut2.getText().equals("") )||  
              (tfNombresTutor.getText().equals(tfTeléfonoAut3.getText()) && !tfNombresAut3.getText().equals("") ) ) && cbAsignarTutor.isSelected()){
            JOptionPane.showMessageDialog(null, "El niño ya tiene asociada a una persona con el teléfono del tutor\n Revise e inténtelo de nuevo.");
            error = true;
        }
        
        /*EDITAR TUTOR*/
        //Si se cambia a un tutor y algún campo de texto queda en blanco, entonces se marca como error.
        if((tfNombresTutor.getText().equals("") || tfApellidoPaternoTutor.getText().equals("") || tfApellidoMaternoTutor.getText().equals("") ||
            tfTeléfonoTutor.getText().equals("")) && cbEditarTutor.isSelected()){
            JOptionPane.showMessageDialog(null, "Información del tutor incompleta.\nRevise e inténtelo de nuevo.", "ERROR", 1);
            datosIncompletos = true;
        }
        
        /*EDITAR TUTOR*/
        //Si el teléfono del tutor a editar ya pertenece a un autorizado del niño, entonces se marca el error.
        if( ( (tfTeléfonoTutor.getText().equals(tfTeléfonoAut1.getText()) && !tfTeléfonoTutor.getText().equals("") )|| 
              (tfTeléfonoTutor.getText().equals(tfTeléfonoAut2.getText()) && !tfTeléfonoTutor.getText().equals("") )||  
              (tfTeléfonoTutor.getText().equals(tfTeléfonoAut3.getText()) && !tfTeléfonoTutor.getText().equals("") ) ) && cbEditarTutor.isSelected()){
            JOptionPane.showMessageDialog(null, "El niño ya tiene asociada a una persona con el teléfono del tutor\n Revise e inténtelo de nuevo.");
            error = true;
        }
        
        /*CAMBIAR TUTOR*/
        //Si el teléfono del tutor a cambiar ya pertenece a un autorizado del niño, o al mismo tutor antiguo, entonces se marca como error
        if( ( (tfNombresTutor.getText().equals(tfTeléfonoAut1.getText()) && !tfNombresTutor.getText().equals("") )|| 
              (tfNombresTutor.getText().equals(tfTeléfonoAut2.getText()) && !tfNombresTutor.getText().equals("") )||  
              (tfNombresTutor.getText().equals(tfTeléfonoAut3.getText()) && !tfNombresTutor.getText().equals("") )||  
              (tfNombresTutor.getText().equals(teléfonoTutor) ) ) && cbCambiarTutor.isSelected()){
            JOptionPane.showMessageDialog(null, "El niño ya tiene asociada a una persona con el teléfono del tutor\n Revise e inténtelo de nuevo.");
            error = true;
        }
        /*CAMBIAR TUTOR*/
        //Si se está cambiando a un tutor y el espacio queda en blanco, entonces se marca como error.
        if((tfNombresTutor.getText().equals("")) && cbCambiarTutor.isSelected()){
            JOptionPane.showMessageDialog(null, "El teléfono del tutor está en blanco\nRevise e inténtelo de nuevo.", "ERROR", 1);
            datosIncompletos = true;
        }
        
        /*ASIGNAR AUTORIZADO 1*/
        //Si se está asociando a un tutor y el espacio queda en blanco, entonces se marca como error.
        if((tfNombresAut1.getText().equals("")) && cbAsignarAut1.isSelected()){
            JOptionPane.showMessageDialog(null, "El teléfono del autorizado 1 está en blanco\nRevise e inténtelo de nuevo.", "ERROR", 1);
            datosIncompletos = true;
        }
        
        
        
        /*ASIGNAR AUTORIZADO1*/
        //Si el teléfono del tutor asignado ya pertenece a un autorizado del niño, entonces se marca el error.
        if( ( (tfNombresAut1.getText().equals(tfTeléfonoTutor.getText()) && !tfNombresTutor.getText().equals("") )|| 
              (tfNombresAut1.getText().equals(tfTeléfonoAut2.getText()) && !tfNombresAut2.getText().equals("") )||  
              (tfNombresAut1.getText().equals(tfTeléfonoAut3.getText()) && !tfNombresAut3.getText().equals("") ) ) && cbAsignarAut1.isSelected()){
            JOptionPane.showMessageDialog(null, "El niño ya tiene asociada a una persona con el teléfono del autorizado 1\n Revise e inténtelo de nuevo.");
            error = true;
        }
        
        /*ASIGNAR AUTORIZADO 2*/
        //Si se está asociando a un tutor y el espacio queda en blanco, entonces se marca como error.
        if((tfNombresAut2.getText().equals("")) && cbAsignarAut2.isSelected()){
            JOptionPane.showMessageDialog(null, "El teléfono del autorizado 2 está en blanco\nRevise e inténtelo de nuevo.", "ERROR", 1);
            datosIncompletos = true;
        }
        
        /*ASIGNAR AUTORIZADO2*/
        //Si el teléfono del tutor asignado ya pertenece a un autorizado del niño, entonces se marca el error.
        if( ( (tfNombresAut2.getText().equals(tfTeléfonoTutor.getText()) && !tfNombresTutor.getText().equals("") )|| 
              (tfNombresAut2.getText().equals(tfTeléfonoAut1.getText()) && !tfNombresAut1.getText().equals("") )||  
              (tfNombresAut2.getText().equals(tfTeléfonoAut3.getText()) && !tfNombresAut3.getText().equals("") ) ) && cbAsignarAut2.isSelected()){
            JOptionPane.showMessageDialog(null, "El niño ya tiene asociada a una persona con el teléfono del autorizado 2\n Revise e inténtelo de nuevo.");
            error = true;
        }
        
        /*ASIGNAR AUTORIZADO 3*/
        //Si se está asociando a un tutor y el espacio queda en blanco, entonces se marca como error.
        if((tfNombresAut3.getText().equals("")) && cbAsignarAut3.isSelected()){
            JOptionPane.showMessageDialog(null, "El teléfono del autorizado 3 está en blanco\nRevise e inténtelo de nuevo.", "ERROR", 1);
            datosIncompletos = true;
        }
        
        /*ASIGNAR AUTORIZADO 3*/
        //Si el teléfono del tutor asignado ya pertenece a un autorizado del niño, entonces se marca el error.
        if( ( (tfNombresAut3.getText().equals(tfTeléfonoTutor.getText()) && !tfNombresTutor.getText().equals("") )|| 
              (tfNombresAut3.getText().equals(tfTeléfonoAut1.getText()) && !tfNombresAut1.getText().equals("") )||  
              (tfNombresAut3.getText().equals(tfTeléfonoAut2.getText()) && !tfNombresAut2.getText().equals("") ) ) && cbAsignarAut3.isSelected()){
            JOptionPane.showMessageDialog(null, "El niño ya tiene asociada a una persona con el teléfono del autorizado 3\n Revise e inténtelo de nuevo.");
            error = true;
        }
        
         /*EDITAR AUTORIZADO 1*/
        //Si se cambia al autorizado1 y algún campo de texto queda en blanco, entonces se marca como error.
        if((tfNombresAut1.getText().equals("") || tfApellidoPaternoAut1.getText().equals("") || tfApellidoMaternoAut1.getText().equals("") ||
            tfTeléfonoAut1.getText().equals("")) && cbEditarAut1.isSelected()){
            JOptionPane.showMessageDialog(null, "Información del autorizado 1 incompleta.\nRevise e inténtelo de nuevo.", "ERROR", 1);
            datosIncompletos = true;
        }
        
        /*EDITAR AUTORIZADO 1*/
        //Si el teléfono del tutor a editar ya pertenece a un autorizado del niño, entonces se marca el error.
        if( ( (tfTeléfonoAut1.getText().equals(tfTeléfonoTutor.getText()) && !tfTeléfonoAut1.getText().equals("") )|| 
              (tfTeléfonoAut1.getText().equals(tfTeléfonoAut2.getText()) && !tfTeléfonoAut1.getText().equals("") )||  
              (tfTeléfonoAut1.getText().equals(tfTeléfonoAut3.getText()) && !tfTeléfonoAut1.getText().equals("") ) ) && cbEditarAut1.isSelected()){
            JOptionPane.showMessageDialog(null, "El niño ya tiene asociada a una persona con el teléfono del autorizado 1\n Revise e inténtelo de nuevo.");
            error = true;
        }
        
        /*EDITAR AUTORIZADO 2*/
        //Si se cambia al autorizado1 y algún campo de texto queda en blanco, entonces se marca como error.
        if((tfNombresAut2.getText().equals("") || tfApellidoPaternoAut2.getText().equals("") || tfApellidoMaternoAut2.getText().equals("") ||
            tfTeléfonoAut2.getText().equals("")) && cbEditarAut2.isSelected()){
            JOptionPane.showMessageDialog(null, "Información del autorizado 2 incompleta.\nRevise e inténtelo de nuevo.", "ERROR", 1);
            datosIncompletos = true;
        }
        
        /*EDITAR AUTORIZADO 2*/
        //Si el teléfono del tutor a editar ya pertenece a un autorizado del niño, entonces se marca el error.
        if( ( (tfTeléfonoAut2.getText().equals(tfTeléfonoTutor.getText()) && !tfTeléfonoAut2.getText().equals("") )|| 
              (tfTeléfonoAut2.getText().equals(tfTeléfonoAut1.getText()) && !tfTeléfonoAut2.getText().equals("") )||  
              (tfTeléfonoAut2.getText().equals(tfTeléfonoAut3.getText()) && !tfTeléfonoAut2.getText().equals("") ) ) && cbEditarAut2.isSelected()){
            JOptionPane.showMessageDialog(null, "El niño ya tiene asociada a una persona con el teléfono del autorizado 2\n Revise e inténtelo de nuevo.");
            error = true;
        }
        
        /*EDITAR AUTORIZADO 3*/
        //Si se cambia al autorizado1 y algún campo de texto queda en blanco, entonces se marca como error.
        if((tfNombresAut3.getText().equals("") || tfApellidoPaternoAut3.getText().equals("") || tfApellidoMaternoAut3.getText().equals("") ||
            tfTeléfonoAut3.getText().equals("")) && cbEditarAut3.isSelected()){
            JOptionPane.showMessageDialog(null, "Información del autorizado 3 incompleta.\nRevise e inténtelo de nuevo.", "ERROR", 1);
            datosIncompletos = true;
        }
        
        /*EDITAR AUTORIZADO 3*/
        //Si el teléfono del tutor a editar ya pertenece a un autorizado del niño, entonces se marca el error.
        if( ( (tfTeléfonoAut3.getText().equals(tfTeléfonoTutor.getText()) && !tfTeléfonoAut3.getText().equals("") )|| 
              (tfTeléfonoAut3.getText().equals(tfTeléfonoAut1.getText()) && !tfTeléfonoAut3.getText().equals("") )||  
              (tfTeléfonoAut3.getText().equals(tfTeléfonoAut2.getText()) && !tfTeléfonoAut3.getText().equals("") ) ) && cbEditarAut2.isSelected()){
            JOptionPane.showMessageDialog(null, "El niño ya tiene asociada a una persona con el teléfono del autorizado 3\n Revise e inténtelo de nuevo.");
            error = true;
        }
        
        
        //SI EXISTE ALGUNA CLASE DE ERROR, ENTONCES LA PANTALLA SE REFRESCA INMEDIATAMENTE
        if(error || datosIncompletos){
            MostrarPantalla(ID);
        }
        
        /************************************SE COMIENZAN A GUARDAR TODOS LOS CAMBIOS************************************************************/
        
        
        //SE PROCEDE A CHECAR CADA CHECKBOX PARA SABER QUÉ INFORMACIÓN MODIFICAR
        if(cbEditarNiño.isSelected()){
            if(!error && !datosIncompletos && confirmacion){ //Si no hay error se procede con la actualización del niño
                //Actualización de la información del niño

                try {
                    con = DriverManager.getConnection(linkbd, "root", "");
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate("UPDATE Ninos SET Nombres = '"+tfNombresNiño.getText()+"', Apellido_paterno = '"+
                         tfApellidoPaternoNiño.getText()+"', Apellido_materno = '"+tfApellidoMaternoNiño.getText()+"', Foto = '"+
                          (nuevaFotoNiño == null ? dirFotoNiño : dirFotoNiñoNueva )+"' WHERE ID = "+ID);
                } catch (SQLException ex) {
                    Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(null, "Se han guardado los cambios con éxito");
            }
             MostrarPantalla(ID);
        }
        
        //Si la opción de asignar tutor está seleccionada, entonces se procede.
        if(cbAsignarTutor.isSelected()){
            /*
                Se obtiene el Estatus del tutor para saber donde buscar la información; además, 
                este bloque sirve como bandera para saber si el tutor que queremos asociar realmente
                existe en la base de datos
            */
            try {
                con = DriverManager.getConnection(linkbd, "root", "");
                Statement stmt = con.createStatement();
                ResultSet rs;
                rs = stmt.executeQuery("SELECT Estatus FROM Tutores WHERE Telefono = '"+tfNombresTutor.getText()+"'");
                rs.first();
                Estatus = rs.getObject(1).toString(); //Bandera que, en caso de que el tutor no exista lanza una excepción
            } catch (SQLException ex) {
                Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "El tutor con el teléfono ingresado no existe. Revise e inténte de nuevo","ERROR",1);
                error = true; //Si el tutor no existe se marca como error.

            }
            
            
            
            //Si no hay error de ningún tipo, entonces se procede a guardar al tutor asociado
            if(!error && !datosIncompletos && confirmacion){

                //Se inserta el teléfono del tutor en la información del niño en la base de datos.
                try {
                    con = DriverManager.getConnection(linkbd, "root", "");
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate("UPDATE Ninos SET Tutor = '"+tfNombresTutor.getText()+"' WHERE ID = "+ID);
                } catch (SQLException ex) {
                    Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                /* Se procede a averiguar si el niño tiene un archivo de autorizados, si no, se crea y se guarda al tutor ahí*/
                ruta = "Niños/TutoresDe"+ID+".txt";
                File bandera = new File(ruta);
                
                //Si el file no existe, entonces se crea
                if(!bandera.exists()){
                    try {
                        bandera.createNewFile();
                    } catch (IOException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Error al tratar de crear un file de autorizados para el niño","ERROR",1);
                    }
                    
                    //Como el niño no tenía autorizados, entonces ahora debe enlazarse este file al niño en la base de datos
                    try {
                        con = DriverManager.getConnection(linkbd, "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("UPDATE Ninos SET Autorizados = '"+ruta+"' WHERE ID = "+ID);
                    } catch (SQLException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Error al tratar de enlazar el archivo de los autorizados con el niño","ERROR",1);
                        error = true; //Si el tutor no existe se marca como error.
                    }   
                }
                //Se agrega el autorizado al archivo de autorizados del niño.
                AgregarIDNiño(ruta, tfNombresTutor.getText());
                
                
                
                /*Se procede a avegriguar si el tutor tiene un archivo de niños, si no, se crea y se guarda el id del niño*/
                ruta = "Tutores/NiñosDe"+tfNombresTutor.getText()+".txt";
                bandera = new File(ruta);
                if(!bandera.exists()){
                    try {
                        bandera.createNewFile();
                    } catch (IOException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Error al tratar de crear un file de niños para el tutor","ERROR",1);
                    }
                    
                    //Como el tutor no tenía niños, entonces ahora debe enlazarse este file al tutor en la base de datos
                    try {
                        con = DriverManager.getConnection(linkbd, "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("UPDATE Tutores SET Ninos = '"+ruta+"' WHERE Telefono = '"+tfNombresTutor.getText()+"'");
                    } catch (SQLException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Error al tratar de enlazar el archivo de los niños con el tutor","ERROR",1);
                        error = true; //Si el tutor no existe se marca como error.
                    }   
                }
                //Se agrega el niño al archivo de niños del tutor.
                AgregarIDNiño(ruta, ID);
                
                JOptionPane.showMessageDialog(null, "Se han guardado los cambios con éxito");
            }
           
        }
        
        //Si la opción de cambiar tutor está seleccionada, entonces se procede.
        if(cbEditarTutor.isSelected()){
            if(!error && !datosIncompletos && confirmacion){ //Si no hay error se procede con la actualización del tutor
                //CASO CUANDO EL TELÉFONO NO CAMBIA
                if(teléfonoTutor.equals(tfTeléfonoTutor.getText())){
                    try {
                        con = DriverManager.getConnection(linkbd, "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("UPDATE Tutores SET Nombres = '"+tfNombresTutor.getText()+"', Apellido_paterno = '"+
                         tfApellidoPaternoTutor.getText()+"', Apellido_materno = '"+tfApellidoMaternoTutor.getText()+"', Foto = '"+
                          (nuevaFotoTutor == null ? dirFotoTutor : dirFotoTutorNueva )+"' WHERE Telefono = '"+teléfonoTutor+"'");
                    } catch (SQLException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }//CASO CUANDO EL TELÉFONO SÍ CAMBIA
                
                else{
                    
                    //SE LE CAMBIA EL NOMBRE AL ARCHIVO
                    File f1 = new File(archivoNiñosTutor); //Archivo original
                    ruta = "Tutores/NiñosDe"+tfTeléfonoTutor.getText()+".txt"; //Nombre del nuevo archivo
                    
                    File f2 = new File(ruta); //Archivo nuevo
                    f1.renameTo(f2); //Se renombra el archivo
                    
                    f1.delete(); //Se borra el archivo viejo
                    
                    
                    //ACTUALIZO EL TUTOR DE CADA NIÑO
                    
                    int numNiños = 0; //Tamaño del arreglo
                    String cadena;
                    String[] niños;
                    FileReader f;
                    
                    //Se cuentan los niños
                    try {
                        f = new FileReader(f2);
                        BufferedReader b = new BufferedReader(f);
                        while((cadena = b.readLine())!=null) {
                            numNiños++;
                        }
                        b.close();
                        f.close();
                        
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    //Se crea el arreglo de los niños.
                    niños = new String[numNiños];
                    
                    //Se guardan los niños.
                    numNiños = 0;
                    try {
                        f = new FileReader(f2);
                        BufferedReader b = new BufferedReader(f);
                        while((cadena = b.readLine())!=null) {
                            niños[numNiños] = cadena;
                            numNiños++;
                        }
                        b.close();
                        f.close();
                        
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    //SE ACTUALIZA EL TUTOR DE CADA NIÑO Y EL PATH DEL ARCHIVO (PUES AHORA TIENE UN NOMBRE DIFERENTE)
                    //... TODO ESTO EN EL ARREGLO EN LA BASE DE DATOS 
                    for(String niño : niños){
                        
                        //CHECAR SI EL AUTORIZADO CAMBIADO ES TUTOR DE ALGÚN NIÑO EN LA BASE DE DATOS
                        String tutor = null;
                        //Se extrae el tutor de cada niño que forma parte del archivo de los niños del autorizado
                        //y se compara con el teléfono viejo del autorizado, si coinciden, entonces hay que actualizarle
                        //el tutor a ese niño
                        
                        /*Extraes el tutor del niño*/
                        try {
                            con = DriverManager.getConnection(linkbd, "root", "");
                            Statement stmt = con.createStatement();
                            ResultSet r;
                            r = stmt.executeQuery("SELECT Tutor FROM Ninos WHERE ID = "+niño);
                            r.first();
                            tutor = r.getObject(1).toString();
                        } catch (SQLException ex) {
                            Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        /*Comparas el número del autorizado viejo con el tutor del niño*/
                        if(teléfonoTutor.equals(tutor)){ //Si coniciden, le cambias el tutor.
                            try {
                                con = DriverManager.getConnection(linkbd, "root", "");
                                Statement stmt = con.createStatement();
                                stmt.executeUpdate("UPDATE Ninos SET Tutor = '"+tfTeléfonoTutor.getText()+"' WHERE ID = "+niño);
                            } catch (SQLException ex) {
                                Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    
                    //SE ACTUALIZA EL NÚMERO NUEVO EN CADA ARCHIVO DE AUTORIZADOS DE CADA NIÑO DEL TUTOR
                    File archAutorizados;
                    for(String niño : niños){
                        archAutorizados = new File("Niños/TutoresDe"+niño+".txt");
                        String texto = "";
                        String aux = "";
                        String bfRead;
                        
                        //Se lee el texto
                        try{
                            BufferedWriter bw = new BufferedWriter(new FileWriter(archAutorizados, true));
                            BufferedReader bf = new BufferedReader(new FileReader(archAutorizados));

                            //Se copia el texto en una variable
                            while((bfRead = bf.readLine() )!= null){
                                aux = aux+bfRead+"\r\n";
                            }
                            texto = aux;

                            //Se borra el ID del archivo
                            texto = texto.replace(teléfonoTutor+"\r\n",tfTeléfonoTutor.getText()+"\r\n");
                            FileWriter fw = new FileWriter(archAutorizados);
                            bw.write(texto);
                            bw.close();
                            bf.close();
                            fw.close();
                            
                        }
                        catch(Exception e){
                            JOptionPane.showMessageDialog(null, "Error al cambiar el teléfono del tutor en el archivo del niño."+niño);
                        }
                    }
                    
                    /*SE LE CAMBIA EL NOMBRE A LA FOTO DEL TUTOR PORQUE CONTIENE SU TELÉFONO EN ESE NOMBRE (Si es que tiene fotito)*/
                    File original = new File("Fotos Tutores/tutor"+teléfonoTutor+".jpg");
                    if(original.exists()){
                        File renombre = new File("Fotos Tutores/tutor"+tfTeléfonoTutor.getText()+".jpg");
                        original.renameTo(renombre);
                        dirFotoTutor = "Fotos Tutores/tutor"+tfTeléfonoTutor.getText()+".jpg";
                    }
                    
                    //SE ACTUALIZA LA DIRECCIÓN DEL ARCHIVO DE LOS NIÑOS DEL TUTOR
                    try {
                        con = DriverManager.getConnection(linkbd, "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("UPDATE Tutores SET Telefono = '"+tfTeléfonoTutor.getText()+"', Nombres = '"+tfNombresTutor.getText()+"', Apellido_paterno = '"+
                         tfApellidoPaternoTutor.getText()+"', Apellido_materno = '"+tfApellidoMaternoTutor.getText()+"', Foto = '"+
                          (nuevaFotoTutor == null ? dirFotoTutor : dirFotoTutorNueva )+"', Ninos = '"+"Tutores/"+f2.getName()+"' WHERE Telefono = '"+teléfonoTutor+"'");
                    } catch (SQLException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                JOptionPane.showMessageDialog(null, "Se han guardado los cambios con éxito");
            }
        }
        
        //Si la opción de cambiar tutor está seleccionada, entonces se procede
        if(cbCambiarTutor.isSelected()){
            /*
                Se obtiene el Estatus del tutor para saber donde buscar la información; además, 
                este bloque sirve como bandera para saber si el tutor que queremos asociar realmente
                existe en la base de datos
            */
            try {
                con = DriverManager.getConnection(linkbd, "root", "");
                Statement stmt = con.createStatement();
                ResultSet rs;
                rs = stmt.executeQuery("SELECT Estatus FROM Tutores WHERE Telefono = '"+tfNombresTutor.getText()+"'");
                rs.first();
                Estatus = rs.getObject(1).toString(); //Bandera que, en caso de que el tutor no exista lanza una excepción
            } catch (SQLException ex) {
                Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "El tutor con el teléfono ingresado no existe. Revise e inténte de nuevo","ERROR",1);
                error = true; //Si el tutor no existe se marca como error.

            }
            //Si no hay error de ningún tipo, entonces se procede a guardar al tutor asociado
            if(!error && !datosIncompletos && confirmacion){
                
                //SE CAMBIA EL TELÉFONO DEL **TUTOR** EN EL ARCHIVO DE TUTORES DEL NIÑO

                    File archAutorizados2 = new File("Niños/TutoresDe"+ID+".txt");
                    String texto = "";
                    String aux = "";
                    String bfRead;

                    //Se lee el texto
                    try{
                        BufferedWriter bw = new BufferedWriter(new FileWriter(archAutorizados2, true));
                        BufferedReader bf = new BufferedReader(new FileReader(archAutorizados2));

                        //Se copia el texto en una variable
                        while((bfRead = bf.readLine() )!= null){
                            aux = aux+bfRead+"\r\n";
                        }
                        texto = aux;

                        /* DEBERIA METER EL CONDICIONAL AQUÍ TAMBIEN???? */
                        //SE CAMBIA EL NÚMERO DEL TUTOR ANTIGUO POR EL DEL NUEVO TUTOR
                        texto = texto.replace(teléfonoTutor+"\r\n",tfNombresTutor.getText()+"\r\n");
                        FileWriter fw = new FileWriter(archAutorizados2);
                        bw.write(texto);
                        bw.close();
                        fw.close();
                        bf.close();

                    }
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null, "Error al cambiar el teléfono del tutor en el archivo del niño."+ID);
                    }
                    
                    //SE AGREGA EL ID DEL NIÑO AL ARCHIVO DE NIÑOS DEL NUEVO TUTOR
                    /* Se procede a averiguar si el autorizado tiene un archivo de niños, si no, se crea y se guarda al niño ahí*/
                    ruta = "Tutores/NiñosDe"+tfNombresTutor.getText()+".txt";
                    File bandera = new File(ruta);

                    //Si el file no existe, entonces se crea
                    if(!bandera.exists()){
                        try {
                            bandera.createNewFile();
                        } catch (IOException ex) {
                            Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, "Error al tratar de crear un file de autorizados para el niño","ERROR",1);
                        }

                        //Como el aturizado no tenía niños, entonces ahora debe enlazarse este file al autorizado en la base de datos
                        try {
                            con = DriverManager.getConnection(linkbd, "root", "");
                            Statement stmt = con.createStatement();
                            stmt.executeUpdate("UPDATE Tutores SET Ninos = '"+ruta+"' WHERE Telefono = '"+tfNombresTutor.getText()+"'");
                        } catch (SQLException ex) {
                            Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, "Error al tratar de enlazar el archivo de los autorizados con el niño","ERROR",1);
                            error = true; //Si el tutor no existe se marca como error.
                        }   
                    }
                    //Se agrega el ID al archivo de niños del autoriza.
                    AgregarIDNiño(ruta, ID);
                    
                    //SE BORRA EL ID DEL NIÑO EN EL ARCHIVO DEL TUTOR VIEJO
                    ruta = "Tutores/NiñosDe"+teléfonoTutor+".txt";
                    File archTutor;
                    
                    archTutor = new File(ruta);
                    texto = "";
                    aux = "";

                    //Se lee el texto
                    try{
                        BufferedWriter bw = new BufferedWriter(new FileWriter(archTutor, true));
                        BufferedReader bf = new BufferedReader(new FileReader(archTutor));

                        //Se copia el texto en una variable
                        while((bfRead = bf.readLine() )!= null){
                            aux = aux+bfRead+"\r\n";
                        }
                        texto = aux;

                        //SE ELIMINA EL ID DEL NIÑO EN EL ARCHIVO DEL TUTOR VIEJO
     /*DUDAAA*/         if(texto.contains("\r\n"+ID+"\r\n")) texto = texto.replace("\r\n"+ID+"\r\n","\r\n");
                        else if(texto.contains(ID+"\r\n")) texto = texto.replace(ID+"\r\n","");
                        else texto.replace(ID, "");
                        //Si el archivo se queda vacío, entonces se elimina junto con el tutor
                        if(texto.isBlank() || texto.equals("\r\n") || (!texto.contains("0") && !texto.contains("1") && 
                            !texto.contains("2") && !texto.contains("3") && !texto.contains("4") && !texto.contains("5") && 
                            !texto.contains("6") && !texto.contains("7") && !texto.contains("8") && !texto.contains("9") ) ){
                            bw.close();
                            bf.close();
                            File archivo = new File(ruta);
                            archivo.delete(); //Se elimina el archivo vacío
                            try{ //Se elimina al tutor
                               con = DriverManager.getConnection(linkbd, "root", "");
                               Statement stmt = con.createStatement();
                               stmt.executeUpdate("DELETE FROM Tutores WHERE Telefono = '"+teléfonoTutor+"'");

                            }
                            catch(SQLException e){
                                Logger.getLogger(PantallaConsultarNiño.class.getName()).log(Level.SEVERE, null, e);
                            }

                        }
                        else{
                            FileWriter fw = new FileWriter(archTutor);
                            bw.write(texto);
                            bw.close();
                            bf.close();
                            fw.close();
                        }
                        

                    }
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null, "Error al cambiar el teléfono del tutor en el archivo del niño."+ID);
                    }
                    
                    
                    //SE LE CAMBIA EL TUTOR AL NIÑO EN LA BASE DE DATOS
                    try {
                            con = DriverManager.getConnection(linkbd, "root", "");
                            Statement stmt = con.createStatement();
                                stmt.executeUpdate("UPDATE Ninos SET Tutor = '"+tfNombresTutor.getText()+"' WHERE ID = "+ID );
                        } catch (SQLException ex) {
                            Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, "Error al tratar de enlazar al nuevo tutor con el niño","ERROR",1);
                            error = true; //Si el tutor no existe se marca como error.
                        }  
            }
            
            
        }
        /*********************CRUD DE LOS AUTORIZADOS*************************************************/
        //------------------------------------------------ASIGNACIÓN----------------------------------/
        
        //AUTORIZADO 1
        //Si la opción de asignar tutor está seleccionada, entonces se procede.
        if(cbAsignarAut1.isSelected()){
            
            /*
                Se obtiene el Estatus del tutor para saber donde buscar la información; además, 
                este bloque sirve como bandera para saber si el tutor que queremos asociar realmente
                existe en la base de datos
            */
            try {
                con = DriverManager.getConnection(linkbd, "root", "");
                Statement stmt = con.createStatement();
                ResultSet rs;
                rs = stmt.executeQuery("SELECT Estatus FROM Tutores WHERE Telefono = '"+tfNombresAut1.getText()+"'");
                rs.first();
                Estatus = rs.getObject(1).toString(); //Bandera que, en caso de que el autorizado no exista lanza una excepción
            } catch (SQLException ex) {
                Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "La persona con el teléfono ingresado no existe. Revise e inténte de nuevo","ERROR",1);
                error = true; //Si el autorizado no existe se marca como error.

            }
            
            //Si no hay error de ningún tipo, entonces se procede a guardar al autorizado en el archivo de autorizados del niño
            if(!error && !datosIncompletos && confirmacion){
                
                ruta = "Niños/TutoresDe"+ID+".txt";
                /*
                    se procede a averiguar si el niño tiene un file de autorizados, si no, se crea y se guarda el autorizado.
                */
                File bandera = new File(ruta); 
                //Si el file no existe, entonces se crea
                if(!bandera.exists()){
                    try {
                        bandera.createNewFile();
                    } catch (IOException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Error al tratar de crear un file de autorizados para el niño","ERROR",1);
                    }
                    
                    //Como el niño no tenía autorizados, entonces ahora debe enlazarse este file al niño en la base de datos
                    try {
                        con = DriverManager.getConnection(linkbd, "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("UPDATE Ninos SET Autorizados = '"+ruta+"' WHERE ID = "+ID);
                    } catch (SQLException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Error al tratar de enlazar el archivo de los autorizados con el niño","ERROR",1);
                        error = true; //Si el tutor no existe se marca como error.
                    }   
                }
                
                //Se agrega al tutor al archivo de los autorizados del niño.
                AgregarIDNiño(ruta, tfNombresAut1.getText());
                
                
                /* Se procede a averiguar si el autorizado tiene un archivo de niños, si no, se crea y se guarda al niño ahí*/
                ruta = "Tutores/NiñosDe"+tfNombresAut1.getText()+".txt";
                bandera = new File(ruta);
                
                //Si el file no existe, entonces se crea
                if(!bandera.exists()){
                    try {
                        bandera.createNewFile();
                    } catch (IOException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Error al tratar de crear un file de autorizados para el niño","ERROR",1);
                    }
                    
                    //Como el aturizado no tenía niños, entonces ahora debe enlazarse este file al autorizado en la base de datos
                    try {
                        con = DriverManager.getConnection(linkbd, "root", "");
                        Statement stmt = con.createStatement();
                            stmt.executeUpdate("UPDATE Tutores SET Ninos = '"+ruta+"' WHERE Telefono = '"+tfNombresAut1.getText()+"'");
                    } catch (SQLException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Error al tratar de enlazar el archivo de los autorizados con el niño","ERROR",1);
                        error = true; //Si el tutor no existe se marca como error.
                    }   
                }
                //Se agrega el ID al archivo de niños del autoriza.
                AgregarIDNiño(ruta, ID);
                
                

                JOptionPane.showMessageDialog(null, "Se han guardado los cambios con éxito");
            }
           
        }
        
        if(cbEliminarAut1.isSelected()){
            if(!error && !datosIncompletos && confirmacion){
            
                //SE ELIMINA EL TELÉFONO DEL **AUTORIZADO** EN EL ARCHIVO DE TUTORES DEL NIÑO
                File archAutorizados = new File("Niños/TutoresDe"+ID+".txt");
                String texto = "";
                String aux = "";
                String bfRead;

                //Se lee el texto
                try{
                    BufferedWriter bw = new BufferedWriter(new FileWriter(archAutorizados, true));
                    BufferedReader bf = new BufferedReader(new FileReader(archAutorizados));

                    //Se copia el texto en una variable
                    while((bfRead = bf.readLine() )!= null){
                        aux = aux+bfRead+"\r\n";
                    }
                    texto = aux;

                    //SE ELIMINA EL NÚMERO DEL AUTORIZADO 

                    if(texto.contains("\r\n"+tfTeléfonoAut1.getText()+"\r\n")) texto = texto.replace("\r\n"+tfTeléfonoAut1.getText()+"\r\n","\r\n");
                    else if(texto.contains(tfTeléfonoAut1.getText()+"\r\n")) texto = texto.replace(tfTeléfonoAut1.getText()+"\r\n","");
                    else texto.replace(tfTeléfonoAut1.getText(), "");

                    //Si el archivo se queda vacío, entonces se elimina
                    if(texto.isBlank() || texto.equals("\r\n") || (!texto.contains("0") && !texto.contains("1") && 
                       !texto.contains("2") && !texto.contains("3") && !texto.contains("4") && !texto.contains("5") && 
                       !texto.contains("6") && !texto.contains("7") && !texto.contains("8") && !texto.contains("9") ) ){
                        bf.close();
                        bw.close();
                        File archivo = new File("Niños/TutoresDe"+ID+".txt");
                    }
                    else{
                        FileWriter fw = new FileWriter(archAutorizados);
                        bw.write(texto);
                        bf.close();
                        bw.close();
                        fw.close();
                    }

                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Error al eliminar el teléfono del autorizado1 en el archivo del niño."+ID);
                }

                //SE BORRA AL NIÑO DEL ARCHIVO DE NIÑOS DEL AUTORIZADO
                File archNiños = new File("Tutores/NiñosDe"+tfTeléfonoAut1.getText()+".txt");
                texto = "";
                aux = "";

                //Se lee el texto
                try{
                    BufferedWriter bw = new BufferedWriter(new FileWriter(archNiños, true));
                    BufferedReader bf = new BufferedReader(new FileReader(archNiños));

                    //Se copia el texto en una variable
                    while((bfRead = bf.readLine() )!= null){
                        aux = aux+bfRead+"\r\n";
                    }
                    texto = aux;

                    //SE ELIMINA EL ID DEL NIÑO 
                    if(texto.contains("\r\n"+ID+"\r\n")) texto = texto.replace("\r\n"+ID+"\r\n","\r\n");
                    else if(texto.contains(ID+"\r\n")) texto = texto.replace(ID+"\r\n","");
                    else texto.replace(ID, "");

                    //Si el archivo se queda vacío, entonces se elimina junto con el autorizado
                    if(texto.isBlank() || texto.equals("\r\n") || (!texto.contains("0") && !texto.contains("1") && 
                       !texto.contains("2") && !texto.contains("3") && !texto.contains("4") && !texto.contains("5") && 
                       !texto.contains("6") && !texto.contains("7") && !texto.contains("8") && !texto.contains("9") ) ){
                        bf.close();
                        bw.close();
                        File archivo = new File("Tutores/NiñosDe"+tfTeléfonoAut1.getText()+".txt");
                        
                        /*EN ESTE CASO TAMBIÉN SE ELIMINA LA FOTO DEL AUTORIZADO*/
                        
                        //SE ELIMINA LA FOTO DEL AUTORIZADO PORQUE SE QUEDA SIN NIÑOS Y YA NO NECESITA ESTAR EN LA BASE DE DATOS
                        File archFotoAutorizado = new File("Fotos Tutores/tutor"+tfTeléfonoAut1.getText()+".jpg");
                        archFotoAutorizado.delete();
                        
                        try{ //Se elimina al tutor
                            con = DriverManager.getConnection(linkbd, "root", "");
                            Statement stmt = con.createStatement();
                            stmt.executeUpdate("DELETE FROM Tutores WHERE Telefono = '"+tfTeléfonoAut1.getText()+"'");
                         }
                         catch(SQLException e){
                             Logger.getLogger(PantallaConsultarNiño.class.getName()).log(Level.SEVERE, null, e);
                         }

                    }
                    else{
                        FileWriter fw = new FileWriter(archNiños);
                        bw.write(texto);
                        bf.close();
                        bw.close();
                        fw.close();
                    }

                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Error al borrar el ID del niño en el archivo del autorizado1"+ID);
                }
                
                JOptionPane.showMessageDialog(null, "Se ha borrado exitosamente al autorizado 1");
            }
            
        }
        
        //AUTORIZADO 2
        //Si la opción de asignar tutor está seleccionada, entonces se procede.
        if(cbAsignarAut2.isSelected()){
            /*
                Se obtiene el Estatus del tutor para saber donde buscar la información; además, 
                este bloque sirve como bandera para saber si el tutor que queremos asociar realmente
                existe en la base de datos
            */
            try {
                con = DriverManager.getConnection(linkbd, "root", "");
                Statement stmt = con.createStatement();
                ResultSet rs;
                rs = stmt.executeQuery("SELECT Estatus FROM Tutores WHERE Telefono = '"+tfNombresAut2.getText()+"'");
                rs.first();
                Estatus = rs.getObject(1).toString(); //Bandera que, en caso de que el tutor no exista lanza una excepción
            } catch (SQLException ex) {
                Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "La persona con el teléfono ingresado no existe. Revise e inténte de nuevo","ERROR",1);
                error = true; //Si el tutor no existe se marca como error.

            }
            
            //Si no hay error de ningún tipo, entonces se procede a guardar al autorizado en el archivo de autorizados del niño
            if(!error && !datosIncompletos && confirmacion){
                
                ruta = "Niños/TutoresDe"+ID+".txt";
                /*
                    se procede a averiguar si el niño tiene un file de autorizados, si no, se crea y se guarda el autorizado.
                */
                File bandera = new File(ruta); 
                //Si el file no existe, entonces se crea
                if(!bandera.exists()){
                    
                    try {
                        bandera.createNewFile();
                    } catch (IOException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Error al tratar de crear un file de autorizados para el niño","ERROR",1);
                    }
                    
                    //Como el niño no tenía autorizados, entonces ahora debe enlazarse este file al niño en la base de datos
                    try {
                        con = DriverManager.getConnection(linkbd, "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("UPDATE Ninos SET Autorizados = '"+ruta+"' WHERE ID = "+ID);
                    } catch (SQLException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Error al tratar de enlazar el archivo de los autorizados con el niño","ERROR",1);
                        error = true; //Si el tutor no existe se marca como error.
                    }  
                }
                //Se agrega al tutor al archivo de los autorizados del niño.
                AgregarIDNiño(ruta, tfNombresAut2.getText()); 
                
                
                /* Se procede a averiguar si el autorizado tiene un archivo de niños, si no, se crea y se guarda al niño ahí*/
                ruta = "Tutores/NiñosDe"+tfNombresAut2.getText()+".txt";
                bandera = new File(ruta);
                
                //Si el file no existe, entonces se crea
                if(!bandera.exists()){
                    try {
                        bandera.createNewFile();
                    } catch (IOException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Error al tratar de crear un file de autorizados para el niño","ERROR",1);
                    }
                    
                    //Como el Tutor no tenía niños, entonces ahora debe enlazarse este file al autorizado en la base de datos
                    try {
                        con = DriverManager.getConnection(linkbd, "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("UPDATE Tutores SET Ninos = '"+ruta+"' WHERE Telefono = '"+tfNombresAut2.getText()+"'");
                    } catch (SQLException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Error al tratar de enlazar el archivo de los autorizados con el niño","ERROR",1);
                        error = true; //Si el tutor no existe se marca como error.
                    }   
                }
                //Se agrega el ID al archivo de niños del autoriza.
                AgregarIDNiño(ruta, ID);

                JOptionPane.showMessageDialog(null, "Se han guardado los cambios con éxito");
            }
        }
        
        if(cbEliminarAut2.isSelected()){
            if(!error && !datosIncompletos && confirmacion){
            
                //SE ELIMINA EL TELÉFONO DEL **AUTORIZADO** EN EL ARCHIVO DE TUTORES DEL NIÑO
                File archAutorizados = new File("Niños/TutoresDe"+ID+".txt");
                String texto = "";
                String aux = "";
                String bfRead;

                //Se lee el texto
                try{
                    BufferedWriter bw = new BufferedWriter(new FileWriter(archAutorizados, true));
                    BufferedReader bf = new BufferedReader(new FileReader(archAutorizados));

                    //Se copia el texto en una variable
                    while((bfRead = bf.readLine() )!= null){
                        aux = aux+bfRead+"\r\n";
                    }
                    texto = aux;

                    //SE ELIMINA EL NÚMERO DEL AUTORIZADO 

                    if(texto.contains("\r\n"+tfTeléfonoAut2.getText()+"\r\n")) texto = texto.replace("\r\n"+tfTeléfonoAut2.getText()+"\r\n","\r\n");
                    else if(texto.contains(tfTeléfonoAut2.getText()+"\r\n")) texto = texto.replace(tfTeléfonoAut2.getText()+"\r\n","");
                    else texto.replace(tfTeléfonoAut2.getText(), "");
                    //Si el archivo se queda vacío, entonces se elimina
                    if(texto.isBlank() || texto.equals("\r\n") || (!texto.contains("0") && !texto.contains("1") && 
                       !texto.contains("2") && !texto.contains("3") && !texto.contains("4") && !texto.contains("5") && 
                       !texto.contains("6") && !texto.contains("7") && !texto.contains("8") && !texto.contains("9") ) ){
                        bf.close();
                        bw.close();
                        File archivo = new File("Niños/TutoresDe"+ID+".txt");

                    }
                    else{
                        FileWriter fw = new FileWriter(archAutorizados);
                        bw.write(texto);
                        bw.close();
                        bf.close();
                        fw.close();
                    }

                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Error al eliminar el teléfono del autorizado1 en el archivo del niño."+ID);
                }

                //SE BORRA AL NIÑO DEL ARCHIVO DE NIÑOS DEL AUTORIZADO
                File archNiños = new File("Tutores/NiñosDe"+tfTeléfonoAut2.getText()+".txt");
                texto = "";
                aux = "";

                //Se lee el texto
                try{
                    BufferedWriter bw = new BufferedWriter(new FileWriter(archNiños, true));
                    BufferedReader bf = new BufferedReader(new FileReader(archNiños));

                    //Se copia el texto en una variable
                    while((bfRead = bf.readLine() )!= null){
                        aux = aux+bfRead+"\r\n";
                    }
                    texto = aux;

                    //SE ELIMINA EL ID DEL NIÑO 
                    if(texto.contains("\r\n"+ID+"\r\n")) texto = texto.replace("\r\n"+ID+"\r\n","\r\n");
                    else if(texto.contains(ID+"\r\n")) texto = texto.replace(ID+"\r\n","");
                    else texto.replace(ID, "");

                    //Si el archivo se queda vacío, entonces se elimina junto con el autorizado
                    if(texto.isBlank() || texto.equals("\r\n") || (!texto.contains("0") && !texto.contains("1") && 
                       !texto.contains("2") && !texto.contains("3") && !texto.contains("4") && !texto.contains("5") && 
                       !texto.contains("6") && !texto.contains("7") && !texto.contains("8") && !texto.contains("9") ) ){
                        bf.close();
                        bw.close();
                        File archivo = new File("Tutores/NiñosDe"+tfTeléfonoAut2.getText()+".txt");
                        
                        /*EN ESTE CASO TAMBIÉN SE ELIMINA LA FOTO DEL AUTORIZADO*/
                        
                        //SE ELIMINA LA FOTO DEL AUTORIZADO PORQUE SE QUEDA SIN NIÑOS Y YA NO NECESITA ESTAR EN LA BASE DE DATOS
                        File archFotoAutorizado = new File("Fotos Tutores/tutor"+tfTeléfonoAut2.getText()+".jpg");
                        archFotoAutorizado.delete();

                        try{ //Se elimina al tutor
                            con = DriverManager.getConnection(linkbd, "root", "");
                            Statement stmt = con.createStatement();
                            stmt.executeUpdate("DELETE FROM Tutores WHERE Telefono = '"+tfTeléfonoAut2.getText()+"'");
                         }
                         catch(SQLException e){
                             Logger.getLogger(PantallaConsultarNiño.class.getName()).log(Level.SEVERE, null, e);
                         }

                    }
                    else{
                        FileWriter fw = new FileWriter(archNiños);
                        bw.write(texto);
                        bw.close();
                        bf.close();
                        fw.close();
                    }

                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Error al borrar el ID del niño en el archivo del autorizado1"+ID);
                }
                
                JOptionPane.showMessageDialog(null, "Se ha borrado exitosamente al autorizado 2");
            }
            
        }
        
        //AUTORIZADO 3
        //Si la opción de asignar tutor está seleccionada, entonces se procede.
        if(cbAsignarAut3.isSelected()){
            /*
                Se obtiene el Estatus del tutor para saber donde buscar la información; además, 
                este bloque sirve como bandera para saber si el tutor que queremos asociar realmente
                existe en la base de datos
            */
            try {
                con = DriverManager.getConnection(linkbd, "root", "");
                Statement stmt = con.createStatement();
                ResultSet rs;
                rs = stmt.executeQuery("SELECT Estatus FROM Tutores WHERE Telefono = '"+tfNombresAut3.getText()+"'");
                rs.first();
                Estatus = rs.getObject(1).toString(); //Bandera que, en caso de que el tutor no exista lanza una excepción
            } catch (SQLException ex) {
                Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "La persona con el teléfono ingresado no existe. Revise e inténte de nuevo","ERROR",1);
                error = true; //Si el tutor no existe se marca como error.
            }
            
            //Si no hay error de ningún tipo, entonces se procede a guardar al autorizado en el archivo de autorizados del niño
            if(!error && !datosIncompletos && confirmacion){
                ruta = "Niños/TutoresDe"+ID+".txt";
                /*
                    se procede a averiguar si el niño tiene un file de autorizados, si no, se crea y se guarda el autorizado.
                */
                File bandera = new File(ruta); 
                //Si el file no existe, entonces se crea
                if(!bandera.exists()){
                    try {
                        bandera.createNewFile();
                    } catch (IOException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Error al tratar de crear un file de autorizados para el niño","ERROR",1);
                    }
                    
                    //Como el niño no tenía autorizados, entonces ahora debe enlazarse este file al niño en la base de datos
                    try {
                        con = DriverManager.getConnection(linkbd, "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("UPDATE Ninos SET Autorizados = '"+ruta+"' WHERE ID = "+ID);
                    } catch (SQLException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Error al tratar de enlazar el archivo de los autorizados con el niño","ERROR",1);
                        error = true; //Si el tutor no existe se marca como error.
                    }   
                }
                //Se agrega al tutor al archivo de los autorizados del niño.
                AgregarIDNiño(ruta, tfNombresAut3.getText());
                
                /* Se procede a averiguar si el autorizado tiene un archivo de niños, si no, se crea y se guarda al niño ahí*/
                ruta = "Tutores/NiñosDe"+tfNombresAut3.getText()+".txt";
                bandera = new File(ruta);
                
                //Si el file no existe, entonces se crea
                if(!bandera.exists()){
                    try {
                        bandera.createNewFile();
                    } catch (IOException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Error al tratar de crear un file de autorizados para el niño","ERROR",1);
                    }
                    
                    //Como el autorizado no tenía niños, entonces ahora debe enlazarse este file al autorizado en la base de datos
                    try {
                        con = DriverManager.getConnection(linkbd, "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("UPDATE Tutores SET Ninos = '"+ruta+"' WHERE Telefono = '"+tfNombresAut3.getText()+"'");
                    } catch (SQLException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Error al tratar de enlazar el archivo de los autorizados con el niño","ERROR",1);
                        error = true; //Si el tutor no existe se marca como error.
                    }   
                }
                //Se agrega el ID al archivo de niños del autoriza.
                AgregarIDNiño(ruta, ID);

                JOptionPane.showMessageDialog(null, "Se han guardado los cambios con éxito");
            }
           
        }
        
        if(cbEliminarAut3.isSelected()){
            if(!error && !datosIncompletos && confirmacion){
            
                //SE ELIMINA EL TELÉFONO DEL **AUTORIZADO** EN EL ARCHIVO DE TUTORES DEL NIÑO
                File archAutorizados = new File("Niños/TutoresDe"+ID+".txt");
                String texto = "";
                String aux = "";
                String bfRead;

                //Se lee el texto
                try{
                    BufferedWriter bw = new BufferedWriter(new FileWriter(archAutorizados, true));
                    BufferedReader bf = new BufferedReader(new FileReader(archAutorizados));

                    //Se copia el texto en una variable
                    while((bfRead = bf.readLine() )!= null){
                        aux = aux+bfRead+"\r\n";
                    }
                    texto = aux;

                    //SE ELIMINA EL NÚMERO DEL AUTORIZADO 

                    if(texto.contains("\r\n"+tfTeléfonoAut3.getText()+"\r\n")) texto = texto.replace("\r\n"+tfTeléfonoAut3.getText()+"\r\n","\r\n");
                    else if(texto.contains(tfTeléfonoAut3.getText()+"\r\n")) texto = texto.replace(tfTeléfonoAut3.getText()+"\r\n","");
                    else texto.replace(tfTeléfonoAut3.getText(), "");

                    //Si el archivo se queda vacío, entonces se elimina
                    if(texto.isBlank() || texto.equals("\r\n") || (!texto.contains("0") && !texto.contains("1") && 
                       !texto.contains("2") && !texto.contains("3") && !texto.contains("4") && !texto.contains("5") && 
                       !texto.contains("6") && !texto.contains("7") && !texto.contains("8") && !texto.contains("9") ) ){
                        bf.close();
                        bw.close();
                        File archivo = new File("Niños/TutoresDe"+ID+".txt");

                    }
                    else{
                        FileWriter fw = new FileWriter(archAutorizados);
                        bw.write(texto);
                        bf.close();
                        bw.close();
                        fw.close();
                    }

                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Error al eliminar el teléfono del autorizado1 en el archivo del niño."+ID);
                }

                //SE BORRA AL NIÑO DEL ARCHIVO DE NIÑOS DEL AUTORIZADO
                File archNiños = new File("Tutores/NiñosDe"+tfTeléfonoAut3.getText()+".txt");
                texto = "";
                aux = "";

                //Se lee el texto
                try{
                    BufferedWriter bw = new BufferedWriter(new FileWriter(archNiños, true));
                    BufferedReader bf = new BufferedReader(new FileReader(archNiños));

                    //Se copia el texto en una variable
                    while((bfRead = bf.readLine() )!= null){
                        aux = aux+bfRead+"\r\n";
                    }
                    texto = aux;

                    //SE ELIMINA EL ID DEL NIÑO 
                    if(texto.contains("\r\n"+ID+"\r\n")) texto = texto.replace("\r\n"+ID+"\r\n","\r\n");
                    else if(texto.contains(ID+"\r\n")) texto = texto.replace(ID+"\r\n","");
                    else texto.replace(ID, "");

                    //Si el archivo se queda vacío, entonces se elimina junto con el autorizado
                    if(texto.isBlank() || texto.equals("\r\n") || (!texto.contains("0") && !texto.contains("1") && 
                       !texto.contains("2") && !texto.contains("3") && !texto.contains("4") && !texto.contains("5") && 
                       !texto.contains("6") && !texto.contains("7") && !texto.contains("8") && !texto.contains("9") ) ){
                        bf.close();
                        bw.close();
                        File archivo = new File("Tutores/NiñosDe"+tfTeléfonoAut3.getText()+".txt");
                        
                        /*EN ESTE CASO TAMBIÉN SE ELIMINA LA FOTO DEL AUTORIZADO*/
                        
                        //SE ELIMINA LA FOTO DEL AUTORIZADO PORQUE SE QUEDA SIN NIÑOS Y YA NO NECESITA ESTAR EN LA BASE DE DATOS
                        File archFotoAutorizado = new File("Fotos Tutores/tutor"+tfTeléfonoAut3.getText()+".jpg");
                        archFotoAutorizado.delete();

                        try{ //Se elimina al tutor
                            con = DriverManager.getConnection(linkbd, "root", "");
                            Statement stmt = con.createStatement();
                            stmt.executeUpdate("DELETE FROM Tutores WHERE Telefono = '"+tfTeléfonoAut3.getText()+"'");
                         }
                         catch(SQLException e){
                             Logger.getLogger(PantallaConsultarNiño.class.getName()).log(Level.SEVERE, null, e);
                         }

                    }
                    else{
                        FileWriter fw = new FileWriter(archNiños);
                        bw.write(texto);
                        bw.close();
                        bf.close();
                        fw.close();
                    }

                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Error al borrar el ID del niño en el archivo del autorizado1"+ID);
                }
                
                JOptionPane.showMessageDialog(null, "Se ha borrado exitosamente al autorizado 3");
            }
            
        }
        
        //SI LA OPCIÓN DE EDITAR AL AUTORIZADO 1 ESTÁ SELECCIONADA, ENTONCES SE PROCEDE
        if(cbEditarAut1.isSelected()){
            if(!error && !datosIncompletos && confirmacion){ //Si no hay error se procede con la actualización del tutor
                //CASO CUANDO EL TELÉFONO NO CAMBIA
                if(teléfonosAutorizados[0].equals(tfTeléfonoAut1.getText())){
                    try {
                        con = DriverManager.getConnection(linkbd, "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("UPDATE Tutores SET Nombres = '"+tfNombresAut1.getText()+"', Apellido_paterno = '"+
                         tfApellidoPaternoAut1.getText()+"', Apellido_materno = '"+tfApellidoMaternoAut1.getText()+"', Foto = '"+
                          (nuevaFotoAut1 == null ? dirFotosAutorizados[0] : dirFotoAut1Nueva)+"' WHERE Telefono = '"+teléfonosAutorizados[0]+"'");
                    } catch (SQLException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }//CASO CUANDO EL TELÉFONO SÍ CAMBIA
                
                else{
                    
                    //SE LE CAMBIA EL NOMBRE AL ARCHIVO
                    File f1 = new File(archivoNiñosAut1); //Archivo original
                    ruta = "Tutores/NiñosDe"+tfTeléfonoAut1.getText()+".txt"; //Nombre del nuevo archivo
                    
                    File f2 = new File(ruta); //Archivo nuevo
                    f1.renameTo(f2); //Se renombra el archivo
                    
                    f1.delete(); //Se borra el archivo viejo
                    
                    
                    //SE DEBE ACTUALIZAR EL TELÉFONO NUEVO DEL AUTORIZADO EN LOS ARCHIVOS DE TUTORES DE TODOS SUS NIÑOS
                    
                    int numNiños = 0; //Tamaño del arreglo
                    String cadena;
                    String[] niños;
                    FileReader f;
                    
                    //Se cuentan los niños
                    try {
                        f = new FileReader(f2);
                        BufferedReader b = new BufferedReader(f);
                        while((cadena = b.readLine())!=null) {
                            numNiños++;
                        }
                        b.close();
                        f.close();
                        
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    //Se crea el arreglo de los niños.
                    niños = new String[numNiños];
                    
                    //Se guardan los niños.
                    numNiños = 0;
                    try {
                        f = new FileReader(f2);
                        BufferedReader b = new BufferedReader(f);
                        while((cadena = b.readLine())!=null) {
                            niños[numNiños] = cadena;
                            numNiños++;
                        }
                        b.close();
                        f.close();
                        
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    //SE ACTUALIZA EL NÚMERO NUEVO EN CADA ARCHIVO DE AUTORIZADOS DE CADA NIÑO DEL TUTOR
                    File archAutorizados;
                    for(String niño : niños){
                        archAutorizados = new File("Niños/TutoresDe"+niño+".txt");
                        String texto = "";
                        String aux = "";
                        String bfRead;
                        
                        //CHECAR SI EL AUTORIZADO CAMBIADO ES TUTOR DE ALGÚN NIÑO EN LA BASE DE DATOS
                        String tutor = null;
                        //Se extrae el tutor de cada niño que forma parte del archivo de los niños del autorizado
                        //y se compara con el teléfono viejo del autorizado, si coinciden, entonces hay que actualizarle
                        //el tutor a ese niño
                        
                        /*Extraes el tutor del niño*/
                        try {
                            con = DriverManager.getConnection(linkbd, "root", "");
                            Statement stmt = con.createStatement();
                            ResultSet r;
                            r = stmt.executeQuery("SELECT Tutor FROM Ninos WHERE ID = "+niño);
                            r.first();
                            tutor = r.getObject(1).toString();
                        } catch (SQLException ex) {
                            Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        /*Comparas el número del autorizado viejo con el tutor del niño*/
                        if(teléfonosAutorizados[0].equals(tutor)){ //Si coniciden, le cambias el tutor.
                            try {
                                con = DriverManager.getConnection(linkbd, "root", "");
                                Statement stmt = con.createStatement();
                                stmt.executeUpdate("UPDATE Ninos SET Tutor = '"+tfTeléfonoAut1.getText()+"' WHERE ID = "+niño);
                            } catch (SQLException ex) {
                                Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                        
                        //Se lee el texto
                        try{
                            BufferedWriter bw = new BufferedWriter(new FileWriter(archAutorizados, true));
                            BufferedReader bf = new BufferedReader(new FileReader(archAutorizados));

                            //Se copia el texto en una variable
                            while((bfRead = bf.readLine() )!= null){
                                aux = aux+bfRead+"\r\n";
                            }
                            texto = aux;

                            //Se borra el ID del archivo
                            texto = texto.replace(teléfonosAutorizados[0]+"\r\n",tfTeléfonoAut1.getText()+"\r\n");
                            FileWriter fw = new FileWriter(archAutorizados);
                            bw.write(texto);
                            bw.close();
                            bf.close();
                            fw.close();
                            
                        }
                        catch(Exception e){
                            JOptionPane.showMessageDialog(null, "Error al cambiar el teléfono del tutor en el archivo del niño."+niño);
                        }
                    }
                    
                    /*SE LE CAMBIA EL NOMBRE A LA FOTO DEL AUTORIZADO PORQUE CONTIENE SU TELÉFONO EN ESE NOMBRE (Si es que tiene fotito)*/
                    File original = new File("Fotos Tutores/tutor"+teléfonosAutorizados[0]+".jpg");
                    if(original.exists()){
                        File renombre = new File("Fotos Tutores/tutor"+tfTeléfonoAut1.getText()+".jpg");
                        original.renameTo(renombre);
                        dirFotosAutorizados[0] = "Fotos Tutores/tutor"+tfTeléfonoAut1.getText()+".jpg";
                    }
               
                    
                    //SE ACTUALIZA LA DIRECCIÓN DEL ARCHIVO DE LOS NIÑOS DEL TUTOR
                    try {
                        con = DriverManager.getConnection(linkbd, "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("UPDATE Tutores SET Telefono = '"+tfTeléfonoAut1.getText()+"', Nombres = '"+tfNombresAut1.getText()+"', Apellido_paterno = '"+
                         tfApellidoPaternoAut1.getText()+"', Apellido_materno = '"+tfApellidoMaternoAut1.getText()+"', Foto = '"+
                          (nuevaFotoAut1 == null ? dirFotosAutorizados[0] : dirFotoAut1Nueva )+"', Ninos = '"+"Tutores/"+f2.getName()+"' WHERE Telefono = '"+teléfonosAutorizados[0]+"'");
                    } catch (SQLException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                JOptionPane.showMessageDialog(null, "Se han guardado los cambios con éxito");
            }
        }
        
        
        //SI LA OPCIÓN DE EDITAR AL AUTORIZADO 2 ESTÁ SELECCIONADA, ENTONCES SE PROCEDE
        if(cbEditarAut2.isSelected()){
            if(!error && !datosIncompletos && confirmacion){ //Si no hay error se procede con la actualización del tutor
                //CASO CUANDO EL TELÉFONO NO CAMBIA
                if(teléfonosAutorizados[1].equals(tfTeléfonoAut2.getText())){
                    try {
                        con = DriverManager.getConnection(linkbd, "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("UPDATE Tutores SET Nombres = '"+tfNombresAut2.getText()+"', Apellido_paterno = '"+
                         tfApellidoPaternoAut2.getText()+"', Apellido_materno = '"+tfApellidoMaternoAut2.getText()+"', Foto = '"+
                          (nuevaFotoAut2 == null ? dirFotosAutorizados[1] : dirFotoAut2Nueva )+"' WHERE Telefono = '"+teléfonosAutorizados[1]+"'");
                    } catch (SQLException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }//CASO CUANDO EL TELÉFONO SÍ CAMBIA
                
                else{
                    
                    //SE LE CAMBIA EL NOMBRE AL ARCHIVO
                    File f1 = new File(archivoNiñosAut2); //Archivo original
                    ruta = "Tutores/NiñosDe"+tfTeléfonoAut2.getText()+".txt"; //Nombre del nuevo archivo
                    
                    File f2 = new File(ruta); //Archivo nuevo
                    f1.renameTo(f2); //Se renombra el archivo
                    
                    f1.delete(); //Se borra el archivo viejo
                    
                    
                    //SE DEBE ACTUALIZAR EL TELÉFONO NUEVO DEL AUTORIZADO EN LOS ARCHIVOS DE TUTORES DE TODOS SUS NIÑOS
                    
                    int numNiños = 0; //Tamaño del arreglo
                    String cadena;
                    String[] niños;
                    FileReader f;
                    
                    //Se cuentan los niños
                    try {
                        f = new FileReader(f2);
                        BufferedReader b = new BufferedReader(f);
                        while((cadena = b.readLine())!=null) {
                            numNiños++;
                        }
                        b.close();
                        f.close();
                        
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    //Se crea el arreglo de los niños.
                    niños = new String[numNiños];
                    
                    //Se guardan los niños.
                    numNiños = 0;
                    try {
                        f = new FileReader(f2);
                        BufferedReader b = new BufferedReader(f);
                        while((cadena = b.readLine())!=null) {
                            niños[numNiños] = cadena;
                            numNiños++;
                        }
                        b.close();
                        f.close();
                        
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    //SE ACTUALIZA EL NÚMERO NUEVO EN CADA ARCHIVO DE AUTORIZADOS DE CADA NIÑO DEL TUTOR
                    File archAutorizados;
                    for(String niño : niños){
                        archAutorizados = new File("Niños/TutoresDe"+niño+".txt");
                        String texto = "";
                        String aux = "";
                        String bfRead;
                        
                        //CHECAR SI EL AUTORIZADO CAMBIADO ES TUTOR DE ALGÚN NIÑO EN LA BASE DE DATOS
                        String tutor = null;
                        //Se extrae el tutor de cada niño que forma parte del archivo de los niños del autorizado
                        //y se compara con el teléfono viejo del autorizado, si coinciden, entonces hay que actualizarle
                        //el tutor a ese niño
                        
                        /*Extraes el tutor del niño*/
                        try {
                            con = DriverManager.getConnection(linkbd, "root", "");
                            Statement stmt = con.createStatement();
                            ResultSet r;
                            r = stmt.executeQuery("SELECT Tutor FROM Ninos WHERE ID = "+niño);
                            r.first();
                            tutor = r.getObject(1).toString();
                        } catch (SQLException ex) {
                            Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        /*Comparas el número del autorizado viejo con el tutor del niño*/
                        if(teléfonosAutorizados[1].equals(tutor)){ //Si coniciden, le cambias el tutor.
                            try {
                                con = DriverManager.getConnection(linkbd, "root", "");
                                Statement stmt = con.createStatement();
                                stmt.executeUpdate("UPDATE Ninos SET Tutor = '"+tfTeléfonoAut2.getText()+"' WHERE ID = "+niño);
                            } catch (SQLException ex) {
                                Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                        //Se lee el texto
                        try{
                            BufferedWriter bw = new BufferedWriter(new FileWriter(archAutorizados, true));
                            BufferedReader bf = new BufferedReader(new FileReader(archAutorizados));

                            //Se copia el texto en una variable
                            while((bfRead = bf.readLine() )!= null){
                                aux = aux+bfRead+"\r\n";
                            }
                            texto = aux;

                            //Se borra el ID del archivo
                            texto = texto.replace(teléfonosAutorizados[1]+"\r\n",tfTeléfonoAut2.getText()+"\r\n");
                            FileWriter fw = new FileWriter(archAutorizados);
                            bw.write(texto);
                            bw.close();
                            bf.close();
                            fw.close();
                            
                        }
                        catch(Exception e){
                            JOptionPane.showMessageDialog(null, "Error al cambiar el teléfono del tutor en el archivo del niño."+niño);
                        }
                    }
                    
                    /*SE LE CAMBIA EL NOMBRE A LA FOTO DEL AUTORIZADO PORQUE CONTIENE SU TELÉFONO EN ESE NOMBRE (Si es que tiene fotito)*/
                    File original = new File("Fotos Tutores/tutor"+teléfonosAutorizados[1]+".jpg");
                    if(original.exists()){
                        File renombre = new File("Fotos Tutores/tutor"+tfTeléfonoAut2.getText()+".jpg");
                        original.renameTo(renombre);
                        dirFotosAutorizados[1] = "Fotos Tutores/tutor"+tfTeléfonoAut2.getText()+".jpg";
                    }
               
                    
                    //SE ACTUALIZA LA DIRECCIÓN DEL ARCHIVO DE LOS NIÑOS DEL TUTOR
                    try {
                        con = DriverManager.getConnection(linkbd, "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("UPDATE Tutores SET Telefono = '"+tfTeléfonoAut2.getText()+"', Nombres = '"+tfNombresAut2.getText()+"', Apellido_paterno = '"+
                         tfApellidoPaternoAut2.getText()+"', Apellido_materno = '"+tfApellidoMaternoAut2.getText()+"', Foto = '"+
                          (nuevaFotoAut2 == null ? dirFotosAutorizados[1] : dirFotoAut2Nueva )+"', Ninos = '"+"Tutores/"+f2.getName()+"' WHERE Telefono = '"+teléfonosAutorizados[1]+"'");
                    } catch (SQLException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                JOptionPane.showMessageDialog(null, "Se han guardado los cambios con éxito");
            }
        }
        
        //SI LA OPCIÓN DE EDITAR AL AUTORIZADO 3 ESTÁ SELECCIONADA, ENTONCES SE PROCEDE
        if(cbEditarAut3.isSelected()){
            if(!error && !datosIncompletos && confirmacion){ //Si no hay error se procede con la actualización del tutor
                //CASO CUANDO EL TELÉFONO NO CAMBIA
                if(teléfonosAutorizados[2].equals(tfTeléfonoAut3.getText())){
                    try {
                        con = DriverManager.getConnection(linkbd, "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("UPDATE Tutores SET Nombres = '"+tfNombresAut3.getText()+"', Apellido_paterno = '"+
                         tfApellidoPaternoAut3.getText()+"', Apellido_materno = '"+tfApellidoMaternoAut3.getText()+"', Foto = '"+
                          (nuevaFotoAut3 == null ? dirFotosAutorizados[2] : dirFotoAut3Nueva)+"' WHERE Telefono = '"+teléfonosAutorizados[2]+"'");
                    } catch (SQLException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }//CASO CUANDO EL TELÉFONO SÍ CAMBIA
                
                else{
                    
                    //SE LE CAMBIA EL NOMBRE AL ARCHIVO
                    File f1 = new File(archivoNiñosAut3); //Archivo original
                    ruta = "Tutores/NiñosDe"+tfTeléfonoAut3.getText()+".txt"; //Nombre del nuevo archivo
                    
                    File f2 = new File(ruta); //Archivo nuevo
                    f1.renameTo(f2); //Se renombra el archivo
                    
                    f1.delete(); //Se borra el archivo viejo
                    
                    
                    //SE DEBE ACTUALIZAR EL TELÉFONO NUEVO DEL AUTORIZADO EN LOS ARCHIVOS DE TUTORES DE TODOS SUS NIÑOS
                    
                    int numNiños = 0; //Tamaño del arreglo
                    String cadena;
                    String[] niños;
                    FileReader f;
                    
                    //Se cuentan los niños
                    try {
                        f = new FileReader(f2);
                        BufferedReader b = new BufferedReader(f);
                        while((cadena = b.readLine())!=null) {
                            numNiños++;
                        }
                        b.close();
                        f.close();
                        
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    //Se crea el arreglo de los niños.
                    niños = new String[numNiños];
                    
                    //Se guardan los niños.
                    numNiños = 0;
                    try {
                        f = new FileReader(f2);
                        BufferedReader b = new BufferedReader(f);
                        while((cadena = b.readLine())!=null) {
                            niños[numNiños] = cadena;
                            numNiños++;
                        }
                        b.close();
                        f.close();
                        
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    //SE ACTUALIZA EL NÚMERO NUEVO EN CADA ARCHIVO DE AUTORIZADOS DE CADA NIÑO DEL TUTOR
                    File archAutorizados;
                    for(String niño : niños){
                        archAutorizados = new File("Niños/TutoresDe"+niño+".txt");
                        String texto = "";
                        String aux = "";
                        String bfRead;
                        
                        //CHECAR SI EL AUTORIZADO CAMBIADO ES TUTOR DE ALGÚN NIÑO EN LA BASE DE DATOS
                        String tutor = null;
                        //Se extrae el tutor de cada niño que forma parte del archivo de los niños del autorizado
                        //y se compara con el teléfono viejo del autorizado, si coinciden, entonces hay que actualizarle
                        //el tutor a ese niño
                        
                        /*Extraes el tutor del niño*/
                        try {
                            con = DriverManager.getConnection(linkbd, "root", "");
                            Statement stmt = con.createStatement();
                            ResultSet r;
                            r = stmt.executeQuery("SELECT Tutor FROM Ninos WHERE ID = "+niño);
                            r.first();
                            tutor = r.getObject(1).toString();
                        } catch (SQLException ex) {
                            Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        /*Comparas el número del autorizado viejo con el tutor del niño*/
                        if(teléfonosAutorizados[2].equals(tutor)){ //Si coniciden, le cambias el tutor.
                            try {
                                con = DriverManager.getConnection(linkbd, "root", "");
                                Statement stmt = con.createStatement();
                                stmt.executeUpdate("UPDATE Ninos SET Tutor = '"+tfTeléfonoAut3.getText()+"' WHERE ID = "+niño);
                            } catch (SQLException ex) {
                                Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                        //Se lee el texto
                        try{
                            BufferedWriter bw = new BufferedWriter(new FileWriter(archAutorizados, true));
                            BufferedReader bf = new BufferedReader(new FileReader(archAutorizados));

                            //Se copia el texto en una variable
                            while((bfRead = bf.readLine() )!= null){
                                aux = aux+bfRead+"\r\n";
                            }
                            texto = aux;

                            //Se borra el ID del archivo
                            texto = texto.replace(teléfonosAutorizados[2]+"\r\n",tfTeléfonoAut3.getText()+"\r\n");
                            FileWriter fw = new FileWriter(archAutorizados);
                            bw.write(texto);
                            bw.close();
                            bf.close();
                            fw.close();
                            
                        }
                        catch(Exception e){
                            JOptionPane.showMessageDialog(null, "Error al cambiar el teléfono del tutor en el archivo del niño."+niño);
                        }
                    }
                    /*SE LE CAMBIA EL NOMBRE A LA FOTO DEL AUTORIZADO PORQUE CONTIENE SU TELÉFONO EN ESE NOMBRE (Si es que tiene fotito)*/
                    File original = new File("Fotos Tutores/tutor"+teléfonosAutorizados[2]+".jpg");
                    if(original.exists()){
                        File renombre = new File("Fotos Tutores/tutor"+tfTeléfonoAut3.getText()+".jpg");
                        original.renameTo(renombre);
                        dirFotosAutorizados[2] = "Fotos Tutores/tutor"+tfTeléfonoAut3.getText()+".jpg";
                    }
               
                    
                    //SE ACTUALIZA LA DIRECCIÓN DEL ARCHIVO DE LOS NIÑOS DEL TUTOR
                    try {
                        con = DriverManager.getConnection(linkbd, "root", "");
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("UPDATE Tutores SET Telefono = '"+tfTeléfonoAut3.getText()+"', Nombres = '"+tfNombresAut3.getText()+"', Apellido_paterno = '"+
                         tfApellidoPaternoAut3.getText()+"', Apellido_materno = '"+tfApellidoMaternoAut3.getText()+"', Foto = '"+
                          (nuevaFotoAut3 == null ? dirFotosAutorizados[2] : dirFotoAut3Nueva )+"', Ninos = '"+"Tutores/"+f2.getName()+"' WHERE Telefono = '"+teléfonosAutorizados[2]+"'");
                    } catch (SQLException ex) {
                        Logger.getLogger(PantallaActualizarNiño.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                JOptionPane.showMessageDialog(null, "Se han guardado los cambios con éxito");
            }
        }
        
        MostrarPantalla(ID);
        
        
    }//GEN-LAST:event_bGuardarActionPerformed

    private void cbAsignarAut1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAsignarAut1ActionPerformed
        if(cbAsignarAut1.isSelected()){
            cbEditarAut1.setSelected(false);
            cbEliminarAut1.setSelected(false);
            DefaultAutorizado1();
            
            lblNombresAut1.setText("Teléfono: ");
            tfNombresAut1.setText("");
            tfNombresAut1.setForeground(Color.black);
            tfNombresAut1.setEditable(true);
            lblApellidoPaternoAut1.setVisible(false);
            lblApellidoMaternoAut1.setVisible(false);
            lblTeléfonoAut1.setVisible(false);
            
            tfApellidoPaternoAut1.setVisible(false);
            tfApellidoMaternoAut1.setVisible(false);
            tfTeléfonoAut1.setVisible(false);
        }
        else{
             DefaultAutorizado1();
        }
    }//GEN-LAST:event_cbAsignarAut1ActionPerformed

    private void cbEliminarAut1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEliminarAut1ActionPerformed
        if(cbEliminarAut1.isSelected()){
            if(cbEditarAut1.isSelected())cbEditarAut1.setSelected(false);
            if(cbAsignarAut1.isSelected())cbAsignarAut1.setSelected(false);
            DefaultAutorizado1();
            
            tfNombresAut1.setForeground(Color.lightGray);
            tfApellidoPaternoAut1.setForeground(Color.lightGray);
            tfApellidoMaternoAut1.setForeground(Color.lightGray);
            tfTeléfonoAut1.setForeground(Color.lightGray);
            
            tfNombresAut1.setEditable(false);
            tfApellidoPaternoAut1.setEditable(false);
            tfApellidoMaternoAut1.setEditable(false);
            tfTeléfonoAut1.setEditable(false);
            
            tfNombresAut1.setText(nombresAutorizados[0]);
            tfApellidoPaternoAut1.setText(apellidosPaternosAutorizados[0]);
            tfApellidoMaternoAut1.setText(apellidosMaternosAutorizados[0]);
            tfTeléfonoAut1.setText(teléfonosAutorizados[0]);
            
        }
        else{
             DefaultAutorizado1();
        }
    }//GEN-LAST:event_cbEliminarAut1ActionPerformed

    private void cbEliminarAut2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEliminarAut2ActionPerformed
        if(cbEliminarAut2.isSelected()){
            if(cbEditarAut2.isSelected())cbEditarAut2.setSelected(false);
            if(cbAsignarAut2.isSelected())cbAsignarAut2.setSelected(false);
            DefaultAutorizado2();
            
            tfNombresAut2.setForeground(Color.lightGray);
            tfApellidoPaternoAut2.setForeground(Color.lightGray);
            tfApellidoMaternoAut2.setForeground(Color.lightGray);
            tfTeléfonoAut2.setForeground(Color.lightGray);
            
            tfNombresAut2.setEditable(false);
            tfApellidoPaternoAut2.setEditable(false);
            tfApellidoMaternoAut2.setEditable(false);
            tfTeléfonoAut2.setEditable(false);
            
            tfNombresAut2.setText(nombresAutorizados[1]);
            tfApellidoPaternoAut2.setText(apellidosPaternosAutorizados[1]);
            tfApellidoMaternoAut2.setText(apellidosMaternosAutorizados[1]);
            tfTeléfonoAut2.setText(teléfonosAutorizados[1]);
            
        }
        else{
             DefaultAutorizado2();
        }
    }//GEN-LAST:event_cbEliminarAut2ActionPerformed

    private void cbAsignarAut2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAsignarAut2ActionPerformed
        if(cbAsignarAut2.isSelected()){
            cbEditarAut2.setSelected(false);
            cbEliminarAut2.setSelected(false);
            DefaultAutorizado2();
            
            lblNombresAut2.setText("Teléfono: ");
            tfNombresAut2.setText("");
            tfNombresAut2.setForeground(Color.black);
            tfNombresAut2.setEditable(true);
            lblApellidoPaternoAut2.setVisible(false);
            lblApellidoMaternoAut2.setVisible(false);
            lblTeléfonoAut2.setVisible(false);
            
            tfApellidoPaternoAut2.setVisible(false);
            tfApellidoMaternoAut2.setVisible(false);
            tfTeléfonoAut2.setVisible(false);
        }
        else{
             DefaultAutorizado2();
        }
    }//GEN-LAST:event_cbAsignarAut2ActionPerformed

    private void cbEliminarAut3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEliminarAut3ActionPerformed
        if(cbEliminarAut3.isSelected()){
            if(cbEditarAut3.isSelected())cbEditarAut3.setSelected(false);
            if(cbAsignarAut3.isSelected())cbAsignarAut3.setSelected(false);
            DefaultAutorizado3();
            
            tfNombresAut3.setForeground(Color.lightGray);
            tfApellidoPaternoAut3.setForeground(Color.lightGray);
            tfApellidoMaternoAut3.setForeground(Color.lightGray);
            tfTeléfonoAut3.setForeground(Color.lightGray);
            
            tfNombresAut3.setEditable(false);
            tfApellidoPaternoAut3.setEditable(false);
            tfApellidoMaternoAut3.setEditable(false);
            tfTeléfonoAut3.setEditable(false);
            
            tfNombresAut3.setText(nombresAutorizados[2]);
            tfApellidoPaternoAut3.setText(apellidosPaternosAutorizados[2]);
            tfApellidoMaternoAut3.setText(apellidosMaternosAutorizados[2]);
            tfTeléfonoAut3.setText(teléfonosAutorizados[2]);
            
        }
        else{
             DefaultAutorizado3();
        }
    }//GEN-LAST:event_cbEliminarAut3ActionPerformed

    private void cbAsignarAut3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAsignarAut3ActionPerformed
        if(cbAsignarAut3.isSelected()){
            cbEditarAut3.setSelected(false);
            cbEliminarAut3.setSelected(false);
            DefaultAutorizado3();
            
            lblNombresAut3.setText("Teléfono: ");
            tfNombresAut3.setText("");
            tfNombresAut3.setForeground(Color.black);
            tfNombresAut3.setEditable(true);
            lblApellidoPaternoAut3.setVisible(false);
            lblApellidoMaternoAut3.setVisible(false);
            lblTeléfonoAut3.setVisible(false);
            
            tfApellidoPaternoAut3.setVisible(false);
            tfApellidoMaternoAut3.setVisible(false);
            tfTeléfonoAut3.setVisible(false);
        }
        else{
             DefaultAutorizado3();
        }
    }//GEN-LAST:event_cbAsignarAut3ActionPerformed

    private void bTomarFotoNiñoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTomarFotoNiñoActionPerformed
        JOptionPane.showMessageDialog(null, "Asegúrese de tomar la foto correctamente(Intento único)\n"
                + "Guarde la imagen con el mismo nombre al niño", "ADVERTENCIA", 1);
        camara.IniciaCamaraPro("nino"+ID, "Fotos ninos");
        
        
    }//GEN-LAST:event_bTomarFotoNiñoActionPerformed

    private void bTomarFotoTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTomarFotoTutorActionPerformed
        JOptionPane.showMessageDialog(null, "Asegúrese de tomar la foto correctamente(Intento único)\n"
                + "Guarde la imagen con el mismo nombre y teléfono asignados al tutor", "ADVERTENCIA", 1);
        camara.IniciaCamaraPro("tutor"+tfTeléfonoTutor.getText(), "Fotos Tutores");
    }//GEN-LAST:event_bTomarFotoTutorActionPerformed

    private void cbCambiarTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCambiarTutorActionPerformed
        if(cbCambiarTutor.isSelected()){
            cbEditarTutor.setSelected(false);
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
    }//GEN-LAST:event_cbCambiarTutorActionPerformed

    private void bCrearTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCrearTutorActionPerformed
        crearTutor.setLocation(this.getLocationOnScreen());
        crearTutor.IniciarPantalla();
        crearTutor.setVisible(true);
        crearTutor.setTitle("CREAR TUTOR");
    }//GEN-LAST:event_bCrearTutorActionPerformed

    private void bTomarFotoAut1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTomarFotoAut1ActionPerformed
        JOptionPane.showMessageDialog(null, "Asegúrese de tomar la foto correctamente(Intento único)\n"
                + "Guarde la imagen con el mismo nombre y teléfono asignados al autorizado 1", "ADVERTENCIA", 1);
        camara.IniciaCamaraPro("tutor"+tfTeléfonoAut1.getText(), "Fotos Tutores");
    }//GEN-LAST:event_bTomarFotoAut1ActionPerformed

    private void bTomarFotoAut2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTomarFotoAut2ActionPerformed
        JOptionPane.showMessageDialog(null, "Asegúrese de tomar la foto correctamente(Intento único)\n"
                + "Guarde la imagen con el mismo nombre y teléfono asignados al autorizado 2", "ADVERTENCIA", 1);
        camara.IniciaCamaraPro("tutor"+tfTeléfonoAut2.getText(), "Fotos Tutores");
    }//GEN-LAST:event_bTomarFotoAut2ActionPerformed

    private void bInsertarFotoNiñoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bInsertarFotoNiñoActionPerformed
        nuevaFotoNiño = new File("Fotos ninos/nino"+ID+".jpg");
        dirFotoNiñoNueva = "Fotos ninos/"+nuevaFotoNiño.getName();
        Image foto = getToolkit().getImage(nuevaFotoNiño.getAbsolutePath());
        foto = foto.getScaledInstance(260, 260, 260);
        lblFotoNiño.setIcon(new ImageIcon(foto));
    }//GEN-LAST:event_bInsertarFotoNiñoActionPerformed

    private void bInsertarFotoTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bInsertarFotoTutorActionPerformed
        nuevaFotoTutor = new File("Fotos Tutores/tutor"+tfTeléfonoTutor.getText()+".jpg");
        dirFotoTutorNueva = "Fotos Tutores/"+nuevaFotoTutor.getName();
        Image foto = getToolkit().getImage(nuevaFotoTutor.getAbsolutePath());
        foto = foto.getScaledInstance(260, 260, 260);
        lblFotoTutor.setIcon(new ImageIcon(foto));
    }//GEN-LAST:event_bInsertarFotoTutorActionPerformed

    private void bInsertarFotoAut1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bInsertarFotoAut1ActionPerformed
        nuevaFotoAut1 = new File("Fotos Tutores/tutor"+tfTeléfonoAut1.getText()+".jpg");
        dirFotoAut1Nueva = "Fotos Tutores/"+nuevaFotoAut1.getName();
        Image foto = getToolkit().getImage(nuevaFotoAut1.getAbsolutePath());
        foto = foto.getScaledInstance(260, 260, 260);
        lblFotoAut1.setIcon(new ImageIcon(foto));
    }//GEN-LAST:event_bInsertarFotoAut1ActionPerformed

    private void bInsertarFotoAut2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bInsertarFotoAut2ActionPerformed
        nuevaFotoAut2 = new File("Fotos Tutores/tutor"+tfTeléfonoAut2.getText()+".jpg");
        dirFotoAut2Nueva = "Fotos Tutores/"+nuevaFotoAut2.getName();
        Image foto = getToolkit().getImage(nuevaFotoAut2.getAbsolutePath());
        foto = foto.getScaledInstance(260, 260, 260);
        lblFotoAut2.setIcon(new ImageIcon(foto));
    }//GEN-LAST:event_bInsertarFotoAut2ActionPerformed

    private void bInsertarFotoAut3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bInsertarFotoAut3ActionPerformed
        nuevaFotoAut3 = new File("Fotos Tutores/tutor"+tfTeléfonoAut3.getText()+".jpg");
        dirFotoAut3Nueva = "Fotos Tutores/"+nuevaFotoAut3.getName();
        Image foto = getToolkit().getImage(nuevaFotoAut3.getAbsolutePath());
        foto = foto.getScaledInstance(260, 260, 260);
        lblFotoAut3.setIcon(new ImageIcon(foto));
    }//GEN-LAST:event_bInsertarFotoAut3ActionPerformed

    private void bTomarFotoAut3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTomarFotoAut3ActionPerformed
        JOptionPane.showMessageDialog(null, "Asegúrese de tomar la foto correctamente(Intento único)\n"
                + "Guarde la imagen con el mismo nombre y teléfono asignados al autorizado 3", "ADVERTENCIA", 1);
        camara.IniciaCamaraPro("tutor"+tfTeléfonoAut3.getText(), "Fotos Tutores");
    }//GEN-LAST:event_bTomarFotoAut3ActionPerformed

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
    private javax.swing.JButton bCrearTutor;
    private javax.swing.JButton bGuardar;
    private javax.swing.JButton bInsertarFotoAut1;
    private javax.swing.JButton bInsertarFotoAut2;
    private javax.swing.JButton bInsertarFotoAut3;
    private javax.swing.JButton bInsertarFotoNiño;
    private javax.swing.JButton bInsertarFotoTutor;
    private javax.swing.JButton bTomarFotoAut1;
    private javax.swing.JButton bTomarFotoAut2;
    private javax.swing.JButton bTomarFotoAut3;
    private javax.swing.JButton bTomarFotoNiño;
    private javax.swing.JButton bTomarFotoTutor;
    private javax.swing.JCheckBox cbAsignarAut1;
    private javax.swing.JCheckBox cbAsignarAut2;
    private javax.swing.JCheckBox cbAsignarAut3;
    private javax.swing.JCheckBox cbAsignarTutor;
    private javax.swing.JCheckBox cbCambiarTutor;
    private javax.swing.JCheckBox cbEditarAut1;
    private javax.swing.JCheckBox cbEditarAut2;
    private javax.swing.JCheckBox cbEditarAut3;
    private javax.swing.JCheckBox cbEditarNiño;
    private javax.swing.JCheckBox cbEditarTutor;
    private javax.swing.JCheckBox cbEliminarAut1;
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
    private javax.swing.JLabel lblNombreTutor;
    private javax.swing.JLabel lblNombresAut1;
    private javax.swing.JLabel lblNombresAut2;
    private javax.swing.JLabel lblNombresAut3;
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
