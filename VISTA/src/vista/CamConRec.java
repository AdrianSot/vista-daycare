/*VERSION DE WINDOWS CRUDS COMPLETOS*/

package vista;

/**
 *
 * @author Adrian
 */
public class CamConRec {
    String nombre, direccion;
    public void main(String[] args) {
        
        IniciaCamaraPro(nombre, direccion);
    }
    
    public void IniciaCamaraPro(String NombreFoto, String dir){
        System.loadLibrary("facesdk");
        
        MainCamara mainWindow;
        mainWindow = new MainCamara();
        mainWindow.nombreFoto = NombreFoto;
        mainWindow.direccion = dir;
        mainWindow.setTitle("Vista 5.0");
        mainWindow.setSize(650,510);
        mainWindow.setLocation(150, 150);
        mainWindow.setVisible(true);
        mainWindow.drawingTimer.start();
    }
}
