
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author fernandadominguez
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LoginWindow login = new LoginWindow();
        
        login.setSize(600,500);
        login.setLocation(300, 100);
        login.setTitle("VISTA");
        login.setVisible(true);
        if(login.tipoUsuario() == 1){
            login.setVisible(false);
            SesionAdmin sesionAdmin = new SesionAdmin();
            sesionAdmin.setSize(600,500);
            sesionAdmin.setLocation(300,100);
            sesionAdmin.setTitle("VISTA para Administrador");
            sesionAdmin.setVisible(true);
        }else if(login.tipoUsuario() == 0){
            login.setVisible(false);
            SesionRecepcionista sesionRecep = new SesionRecepcionista();
            sesionRecep.setSize(600,500);
            sesionRecep.setLocation(300,100);
            sesionRecep.setTitle("VISTA para Usuario");
            sesionRecep.setVisible(true);
        }
            
    }
    
}
