/*VERSION DE WINDOWS CRUDS COMPLETOS*/


package vista;

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
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 *
 * @author Adrian
 */
public class MainCamara extends javax.swing.JFrame {

    /**
     * Creates new form MainCamara
     */
    String nombreFoto;
    String direccion;
    
    public MainCamara() {
        initComponents();
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
            JOptionPane.showMessageDialog(mainFrame, "Noy hay cámara conectada."); 
            System.exit(1);
        }
        
        String cameraName = cameraList.cameras[0];
        
        FSDKCam.FSDK_VideoFormats formatList = new FSDKCam.FSDK_VideoFormats();
        FSDKCam.GetVideoFormatList(cameraName, formatList, count);
        FSDKCam.SetVideoFormat(cameraName, formatList.formats[0]);
        
        cameraHandle = new FSDKCam.HCamera();
        int r = FSDKCam.OpenVideoCamera(cameraName, cameraHandle);
        if (r != FSDK.FSDKE_OK){
            JOptionPane.showMessageDialog(mainFrame, "Error al abrir la camara"); 
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
                                
                                gr.setFont(new Font("Arial", Font.BOLD, 16));
                                FontMetrics fm = gr.getFontMetrics();
                                java.awt.geom.Rectangle2D textRect = fm.getStringBounds(name[0], gr);
                                gr.drawString(name[0], (int)(facePosition.xc - textRect.getWidth()/2), (int)(top + w + textRect.getHeight()));
                            }

                            if (mouseX >= left && mouseX <= left + w && mouseY >= top && mouseY <= top + w){
                                gr.setColor(Color.blue);
                                
                                if (programStateRemember == programState) {
                                    if (FSDK.FSDKE_OK == FSDK.LockID(tracker, IDs[i]))
                                    {
                                        // get the user name
                                        userName = (String)JOptionPane.showInputDialog(mainFrame, "Nombre:", "Introduce el nombre y teléfono", JOptionPane.QUESTION_MESSAGE, null, null, "Ej. Juan Lopez - 6621345678");
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
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Iframe = new javax.swing.JInternalFrame();
        mainFrame = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        Iframe.setBorder(null);
        Iframe.setClosable(true);
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
            .addGap(0, 650, Short.MAX_VALUE)
        );
        mainFrameLayout.setVerticalGroup(
            mainFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 307, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout IframeLayout = new javax.swing.GroupLayout(Iframe.getContentPane());
        Iframe.getContentPane().setLayout(IframeLayout);
        IframeLayout.setHorizontalGroup(
            IframeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, IframeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(mainFrame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        IframeLayout.setVerticalGroup(
            IframeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IframeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainFrame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(167, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Iframe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Iframe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void IframeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IframeMouseExited
        mouseX = 0;
        mouseY = 0;
    }//GEN-LAST:event_IframeMouseExited

    private void IframeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IframeMouseClicked
        programState = programStateRemember;
    }//GEN-LAST:event_IframeMouseClicked

    private void IframeMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IframeMouseMoved
        mouseX = evt.getX();
        mouseY = evt.getY();
    }//GEN-LAST:event_IframeMouseMoved

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
            java.util.logging.Logger.getLogger(MainCamara.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainCamara.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainCamara.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainCamara.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainCamara().setVisible(true);
            }
        });
    }
    
    

    public void saveTracker(){
        FSDK.SaveTrackerMemoryToFile(tracker, TrackerMemoryFile);
    }
    
    public void closeCamera(){
        FSDKCam.CloseVideoCamera(cameraHandle);
        FSDKCam.FinalizeCapturing();
        FSDK.Finalize();
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JInternalFrame Iframe;
    private javax.swing.JPanel mainFrame;
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
