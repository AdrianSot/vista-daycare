package vista;



import java.awt.Window;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import Luxand.FSDK;
import Luxand.FSDKCam;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

public class MainWindow extends javax.swing.JFrame {
    
    Connection con;
    public String username;
    public String password;
    public enum UserStat {AdminFailPass, RecepFailPass, RecepLogged, AdminLogged};
    public static UserStat userStatus;
    public Timer timer;
    
    /* Administrador */
    PantallaRegistrarRecepcionista pantallaregistro;
    PantallaConsultarRecepcionista consultarecepcionista;
    PantallaActualizarRecepcionista actualizarecepcionista;
    PantallaEliminarRecepcionista eliminarecepcionista;
    
    /* Recepcionista */
    
    PantallaRegistrarNiño pantallaregistrarniño; 
    PantallaConsultarNiño pantallaconsultarniño;
    
    String DatosCara;
    
    public MainWindow() {
        initComponents();
        setTitle("VISTA");
        setSize(1000,800);    
        setLocation(200,100);    
        
        /* Administrador */
        pantallaregistro = new PantallaRegistrarRecepcionista();
        consultarecepcionista = new PantallaConsultarRecepcionista();
        actualizarecepcionista = new PantallaActualizarRecepcionista();
        eliminarecepcionista = new PantallaEliminarRecepcionista();
        
        add(consultarecepcionista);
        add(pantallaregistro);
        add(actualizarecepcionista);
        add(eliminarecepcionista);
      
        
        
        /* Recepcionista */
        pantallaregistrarniño = new PantallaRegistrarNiño();
        pantallaconsultarniño = new PantallaConsultarNiño();
        
        add(pantallaregistrarniño);
        add(pantallaconsultarniño);
        
        
        //------------------
        BasicInternalFrameUI camaraMain = (BasicInternalFrameUI)IframeMain.getUI();
        camaraMain.setNorthPane(null);
        BasicInternalFrameUI camara = (BasicInternalFrameUI)Iframe.getUI();
        camara.setNorthPane(null);

        try {
            int r = FSDK.ActivateLibrary("i2L9huu8yTGls4kwRaOxv/38OxJXXmBBVUDbRCul+nZJH3DO9JnnxzlD59sZ+mOV6H9OyRczxjg4Ev+Ft80mte93DBbYbCtuvh0WkBeIovIo9uAjgYNxCD6T+NBsWLadnUvOQ2jalhXUXgclCqYV//jWsRWDYAfBtF3CxwzJhGs=");
            if (r != FSDK.FSDKE_OK){
                JOptionPane.showMessageDialog(mainFrame, "Please run the License Key Wizard (Start - Luxand - FaceSDK - License Key Wizard)", "Error activating FaceSDK", JOptionPane.ERROR_MESSAGE); 
                System.exit(r);
            }
        } 
        catch(java.lang.UnsatisfiedLinkError e) {
            JOptionPane.showMessageDialog(mainFrame, e.toString(), "Link Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }    
            
        FSDK.Initialize();
           
        // creating a Tracker
        if (FSDK.FSDKE_OK != FSDK.LoadTrackerMemoryFromFile(tracker, TrackerMemoryFile)) // try to load saved tracker state
            FSDK.CreateTracker(tracker); // if could not be loaded, create a new tracker

        // set realtime face detection parameters
        int err[] = new int[1];
        err[0] = 0;
        FSDK.SetTrackerMultipleParameters(tracker, "HandleArbitraryRotations=false; DetermineFaceRotationAngle=false; InternalResizeWidth=100; FaceDetectionThreshold=5;", err);
        
        FSDKCam.InitializeCapturing();
                
        FSDKCam.TCameras cameraList = new FSDKCam.TCameras();
        int count[] = new int[1];
        FSDKCam.GetCameraList(cameraList, count);
        if (count[0] == 0){
            JOptionPane.showMessageDialog(mainFrame, "Please attach a camera"); 
            System.exit(1);
        }
        
        String cameraName = cameraList.cameras[0];
        
        FSDKCam.FSDK_VideoFormats formatList = new FSDKCam.FSDK_VideoFormats();
        FSDKCam.GetVideoFormatList(cameraName, formatList, count);
        formatList.formats[0].BPP = 16;
        formatList.formats[0].Width = 640;
        formatList.formats[0].Height = 480;
        FSDKCam.SetVideoFormat(cameraName, formatList.formats[0]);
        
        cameraHandle = new FSDKCam.HCamera();
        int r = FSDKCam.OpenVideoCamera(cameraName, cameraHandle);
        if (r != FSDK.FSDKE_OK){
            JOptionPane.showMessageDialog(mainFrame, "Error opening camera"); 
            System.exit(r);
        }
        
        
        // Timer to draw and process image from camera
        drawingTimer = new Timer(40, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FSDK.HImage imageHandle = new FSDK.HImage();
                if (FSDKCam.GrabFrame(cameraHandle, imageHandle) == FSDK.FSDKE_OK){
                    Image awtImage[] = new Image[1];
                    if (FSDK.SaveImageToAWTImage(imageHandle, awtImage, FSDK.FSDK_IMAGEMODE.FSDK_IMAGE_COLOR_24BIT) == FSDK.FSDKE_OK){
                        
                        BufferedImage bufImage = null;
                        FSDK.TFacePosition.ByReference facePosition = new FSDK.TFacePosition.ByReference();
                        
                        long[] IDs = new long[256]; // maximum of 256 faces detected
                        long[] faceCount = new long[1];
                        
                        FSDK.FeedFrame(tracker, 0, imageHandle, faceCount, IDs); 
                        for (int i=0; i<faceCount[0]; ++i) {
                            FSDK.GetTrackerFacePosition(tracker, 0, IDs[i], facePosition);
                            
                            int left = facePosition.xc - (int)(facePosition.w * 0.6);
                            int top = facePosition.yc - (int)(facePosition.w * 0.5);
                            int w = (int)(facePosition.w * 1.2);
                            
                            bufImage = new BufferedImage(awtImage[0].getWidth(null), awtImage[0].getHeight(null), BufferedImage.TYPE_INT_ARGB);
                            Graphics gr = bufImage.getGraphics(); 
                            gr.drawImage(awtImage[0], 0, 0, null);
                            gr.setColor(Color.green);
                            
    			    String [] name = new String[1];
			    int res = FSDK.GetAllNames(tracker, IDs[i], name, 65536); // maximum of 65536 characters
    
			    if (FSDK.FSDKE_OK == res && name[0].length() > 0) { // draw name
                                DatosCara = name[0];
                                String[] words=name[0].split("-");
                                gr.setFont(new Font("Arial", Font.BOLD, 16));
                                FontMetrics fm = gr.getFontMetrics();
                                java.awt.geom.Rectangle2D textRect = fm.getStringBounds(words[0], gr);
                                gr.drawString(words[0], (int)(facePosition.xc - textRect.getWidth()/2), (int)(top + w + textRect.getHeight()));
                            }

                            if (mouseX >= left && mouseX <= left + w && mouseY >= top && mouseY <= top + w){
                                gr.setColor(Color.blue);
                                
                                if (programStateRemember == programState) {
                                    if (FSDK.FSDKE_OK == FSDK.LockID(tracker, IDs[i]))
                                    {
                                        // get the user name
                                        userName = (String)JOptionPane.showInputDialog(mainFrame, "Nombre:", "Ingresa el nombre y telefono", JOptionPane.QUESTION_MESSAGE, null, null, "Ej. Juan Lopez - 6621345678");
                                        FSDK.SetName(tracker, IDs[i], userName);
                                        if (userName == null || userName.length() <= 0) {
                                            FSDK.PurgeID(tracker, IDs[i]);
                                        }
                                        FSDK.UnlockID(tracker, IDs[i]);
                                    }
                                }
                            }
                            programState = programStateRecognize;
                            saveTracker();
                            gr.drawRect(left, top, w, w); // draw face rectangle
                        }
                        
                        // display current frame
                        mainFrame.getRootPane().getGraphics().drawImage((bufImage != null) ? bufImage : awtImage[0], 0, 0, null);
                        
                    }
                    FSDK.FreeImage(imageHandle); // delete the FaceSDK image handle
                }
            }
        });
        
        //------------------
        
        
        
        
        
        
        
        
        /*
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch(ClassNotFoundException e){
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE,null, e);
        }
        */
        userWindow();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        IframeMain = new javax.swing.JInternalFrame();
        Iframe = new javax.swing.JInternalFrame();
        mainFrame = new javax.swing.JPanel();
        btCapturar = new javax.swing.JButton();
        lbFotoTutor = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tfTutorNombre = new javax.swing.JTextField();
        tfTutorTel = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        miRegistrar = new javax.swing.JMenuItem();
        miConsultar = new javax.swing.JMenuItem();
        miActualizar = new javax.swing.JMenuItem();
        miEliminar = new javax.swing.JMenuItem();
        jMSalir = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 153, 153));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        IframeMain.setBorder(null);
        IframeMain.setVisible(true);

        Iframe.setVisible(true);
        Iframe.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                IframeMouseMoved(evt);
            }
        });
        Iframe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                IframeMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                IframeMouseExited(evt);
            }
        });

        javax.swing.GroupLayout mainFrameLayout = new javax.swing.GroupLayout(mainFrame);
        mainFrame.setLayout(mainFrameLayout);
        mainFrameLayout.setHorizontalGroup(
            mainFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 577, Short.MAX_VALUE)
        );
        mainFrameLayout.setVerticalGroup(
            mainFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 417, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout IframeLayout = new javax.swing.GroupLayout(Iframe.getContentPane());
        Iframe.getContentPane().setLayout(IframeLayout);
        IframeLayout.setHorizontalGroup(
            IframeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IframeLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(mainFrame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        IframeLayout.setVerticalGroup(
            IframeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IframeLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(mainFrame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        btCapturar.setText("Capturar");
        btCapturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCapturarActionPerformed(evt);
            }
        });

        jLabel1.setText("Nombre");

        jLabel2.setText("Teléfono");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Foto", "Nombre"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout IframeMainLayout = new javax.swing.GroupLayout(IframeMain.getContentPane());
        IframeMain.getContentPane().setLayout(IframeMainLayout);
        IframeMainLayout.setHorizontalGroup(
            IframeMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IframeMainLayout.createSequentialGroup()
                .addGroup(IframeMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(IframeMainLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Iframe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(IframeMainLayout.createSequentialGroup()
                        .addGap(263, 263, 263)
                        .addComponent(btCapturar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addGroup(IframeMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(IframeMainLayout.createSequentialGroup()
                        .addGroup(IframeMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(IframeMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfTutorTel)
                            .addComponent(tfTutorNombre)
                            .addComponent(lbFotoTutor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(44, 44, 44))
        );
        IframeMainLayout.setVerticalGroup(
            IframeMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IframeMainLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(IframeMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(IframeMainLayout.createSequentialGroup()
                        .addComponent(lbFotoTutor, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(IframeMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(tfTutorNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(IframeMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfTutorTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(31, 31, 31)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(IframeMainLayout.createSequentialGroup()
                        .addComponent(Iframe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btCapturar)))
                .addContainerGap(97, Short.MAX_VALUE))
        );

        jMenu1.setText("Archivo");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Niños");

        miRegistrar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        miRegistrar.setText("Registrar");
        miRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miRegistrarActionPerformed(evt);
            }
        });
        jMenu2.add(miRegistrar);

        miConsultar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        miConsultar.setText("Consultar");
        miConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miConsultarActionPerformed(evt);
            }
        });
        jMenu2.add(miConsultar);

        miActualizar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        miActualizar.setText("Actualizar");
        miActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miActualizarActionPerformed(evt);
            }
        });
        jMenu2.add(miActualizar);

        miEliminar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        miEliminar.setText("Eliminar");
        miEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEliminarActionPerformed(evt);
            }
        });
        jMenu2.add(miEliminar);

        jMenuBar1.add(jMenu2);

        jMSalir.setText("Salir");
        jMSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMSalirMouseClicked(evt);
            }
        });
        jMSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMSalirActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMSalir);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(IframeMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(IframeMain)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void Salir(){
        Main.lw = new LoginWindow();
        Main.lw.setVisible(true);
        userStatus = UserStat.AdminFailPass;
        this.dispose();
    }
    /*************************************************************************/
    
    public void userWindow(){
        
        if(userStatus == UserStat.AdminLogged){
            setTitle("VISTA para Administrador");
            jMenu2.setText("Recepcionistas");
        }else{
            miEliminar.setVisible(false);
            miActualizar.setVisible(false);
        }
        jMenuBar1.setVisible(true);
    }

    /*************************************************************************/
     


    private void miRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miRegistrarActionPerformed
        if(userStatus == UserStat.AdminLogged){
            pantallaregistro.IniciarVentana();
            pantallaregistro.setVisible(true);
            eliminarecepcionista.setVisible(false);
            actualizarecepcionista.setVisible(false);
            consultarecepcionista.setVisible(false);
            consultarecepcionista.borraTabla();
            IframeMain.setVisible(false);
        }else{
            pantallaconsultarniño.setVisible(false);
            pantallaregistrarniño.setVisible(true);
            pantallaregistrarniño.IniciarVentana();
            IframeMain.setVisible(false);
        }
    }//GEN-LAST:event_miRegistrarActionPerformed

    private void miEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEliminarActionPerformed
        if(userStatus == UserStat.AdminLogged){
            eliminarecepcionista.IniciarVentana();
            eliminarecepcionista.setVisible(true);
            IframeMain.setVisible(false);
            pantallaregistro.setVisible(false);
            actualizarecepcionista.setVisible(false);
            consultarecepcionista.setVisible(false);
            
            
        }
    }//GEN-LAST:event_miEliminarActionPerformed

    private void miActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miActualizarActionPerformed
        if(userStatus == UserStat.AdminLogged){
            actualizarecepcionista.IniciarVentana();
        IframeMain.setVisible(false);
        eliminarecepcionista.setVisible(false);
        pantallaregistro.setVisible(false);
        actualizarecepcionista.setVisible(true);
        consultarecepcionista.setVisible(false);
        }
    }//GEN-LAST:event_miActualizarActionPerformed

    private void miConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miConsultarActionPerformed
        if(userStatus == UserStat.AdminLogged){
            consultarecepcionista.setVisible(true);
            eliminarecepcionista.setVisible(false);
            actualizarecepcionista.setVisible(false);
            pantallaregistro.setVisible(false);
            consultarecepcionista.borraTabla();
            consultarecepcionista.IniciarVentana();
            
            IframeMain.setVisible(false);
        }else{
             pantallaregistrarniño.setVisible(false);
             pantallaconsultarniño.setVisible(true);
             pantallaconsultarniño.IniciarVentana();
        
             IframeMain.setVisible(false);
        }
        
    }//GEN-LAST:event_miConsultarActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
    }//GEN-LAST:event_formMouseClicked

    private void jMSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMSalirActionPerformed

    }//GEN-LAST:event_jMSalirActionPerformed

    private void jMSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMSalirMouseClicked
        Salir();
    }//GEN-LAST:event_jMSalirMouseClicked

    private void IframeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IframeMouseExited
        mouseX = 0;
        mouseY = 0;
    }//GEN-LAST:event_IframeMouseExited

    private void IframeMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IframeMouseMoved
        mouseX = evt.getX();
        mouseY = evt.getY();
    }//GEN-LAST:event_IframeMouseMoved

    private void IframeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IframeMouseClicked
        programState = programStateRemember;
    }//GEN-LAST:event_IframeMouseClicked

    private void btCapturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCapturarActionPerformed
        String s1=DatosCara;
        String[] words=s1.split("-");
        tfTutorTel.setText(words[1]);
        
        String nombre = "";
        Connection con;
        String tel = words[1];
        tel = tel.replaceAll("\\s","");
        try {
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");             
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vista", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT * FROM Tutores WHERE Telefono = '"+tel+"'"); //Se extrae la info de cada autorizado
            rs.first();
            nombre = rs.getObject(2).toString() +" "+rs.getObject(3).toString() +" "+ rs.getObject(4).toString() ;
            
            
            Image foto = getToolkit().getImage(rs.getObject(5).toString());
            foto = foto.getScaledInstance(210, 210, 210);
            lbFotoTutor.setIcon(new ImageIcon(foto));
            tfTutorNombre.setText(nombre);
            
            
            File file = new File(rs.getObject(6).toString()); 
  
            BufferedReader br = null; 
            try {
                br = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
            
            DefaultTableModel tabla = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) {
                   //all cells false
                   return false;
                }
                public Class getColumnClass(int column)
                {
                return getValueAt(0, column).getClass();
                }
            };
            String st;
            tabla.addColumn("Foto");
            tabla.addColumn("Nombres");
            
            try {
                while ((st = br.readLine()) != null){
                    
                    Object[] fila = new Object[2];
                
                    //Se crea la foto para mostrarla en la tabla
                    
                    ResultSet rs1 = stmt.executeQuery("SELECT * FROM Ninos WHERE ID = '"+st+"'"); //Se extrae la info de cada autorizado
                    rs1.first();
                    System.out.println(rs1.getObject(2).toString());
                    
                    
                    foto = getToolkit().getImage(rs1.getObject(5).toString());
                    foto = foto.getScaledInstance(260, 260, 260);
                    ImageIcon icono = new ImageIcon(foto);
                    Image conversion = icono.getImage();
                    Image tamaño = conversion.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
                    Icon fin = new ImageIcon(tamaño);


                    fila[0] = fin;
                    fila[1] = rs1.getObject(2);



                    //Se introduce una fila auxiliar para separar los datos
                    tabla.addRow(fila);
                }
                jTable1.setModel(tabla);
                jTable1.setRowHeight(90);
                jTable1.getColumnModel().getColumn(0).setPreferredWidth(100);
                jTable1.getColumnModel().getColumn(1).setPreferredWidth(90);
                
            } catch (IOException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error.");
        }
    }//GEN-LAST:event_btCapturarActionPerformed
 
    /*************************************************************************/
    
    public void saveTracker(){
        FSDK.SaveTrackerMemoryToFile(tracker, TrackerMemoryFile);
    }
    
    public void closeCamera(){
        FSDKCam.CloseVideoCamera(cameraHandle);
        FSDKCam.FinalizeCapturing();
        FSDK.Finalize();
    }
    
    
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
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JInternalFrame Iframe;
    private javax.swing.JInternalFrame IframeMain;
    private javax.swing.JButton btCapturar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMSalir;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbFotoTutor;
    private javax.swing.JPanel mainFrame;
    private javax.swing.JMenuItem miActualizar;
    private javax.swing.JMenuItem miConsultar;
    private javax.swing.JMenuItem miEliminar;
    private javax.swing.JMenuItem miRegistrar;
    private javax.swing.JTextField tfTutorNombre;
    private javax.swing.JTextField tfTutorTel;
    // End of variables declaration//GEN-END:variables
    
    public final Timer drawingTimer;
    private FSDKCam.HCamera cameraHandle;
    private String userName;
    
    private List<FSDK.FSDK_FaceTemplate.ByReference> faceTemplates; // set of face templates (we store 10)
    
    // program states: waiting for the user to click a face
    // and recognizing user's face
    final int programStateRemember = 1;
    final int programStateRecognize = 2;
    private int programState = programStateRecognize;
    
    private String TrackerMemoryFile = "tracker70.dat";
    private int mouseX = 0;
    private int mouseY = 0;
    
    FSDK.HTracker tracker = new FSDK.HTracker();
}
