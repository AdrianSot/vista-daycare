
package cam;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author adrian
 */
public class Cam {

   
    public static JFrame window;
    
    public static void main(String[] args) {
        
                CapturaFoto("adrian1");
    }
    
    public static void CapturaFoto(String nombre){
                
                window = new JFrame();
        
                Webcam webcam = Webcam.getDefault();
		webcam.setViewSize(WebcamResolution.VGA.getSize());

		WebcamPanel panel = new WebcamPanel(webcam);
		panel.setMirrored(true);
                
                window.setTitle("WebCam");
                window.setSize(400,300);
                window.setLocation(150, 150);
		window.add(panel,BorderLayout.CENTER);
                
                CamaraControl camara = new CamaraControl();
                camara.nombreFoto = nombre;
                window.add(camara,BorderLayout.SOUTH);
                
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setResizable(true);
		window.pack();
                window.setVisible(true);
        
    }
    
}
