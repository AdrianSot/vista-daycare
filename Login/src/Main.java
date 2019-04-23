
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
    }
    
}
