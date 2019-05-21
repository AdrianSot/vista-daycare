/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebasvista;

/**
 *
 * @author Adrian
 */
public class CamConRec {

    
    public static void main(String[] args) {
        IniciaCamaraPro("adrian1");
    }
    
    public static void IniciaCamaraPro(String NombreFoto){
        System.loadLibrary("libfacesdk");
        
        MainCamara mainWindow;
        mainWindow = new MainCamara();
        mainWindow.nombreFoto = NombreFoto;
        mainWindow.setTitle("Vista 5.0");
        mainWindow.setSize(650,510);
        mainWindow.setLocation(150, 150);
        mainWindow.setVisible(true);
        mainWindow.drawingTimer.start();
    }
}
