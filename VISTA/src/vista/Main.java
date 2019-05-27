package vista;

public class Main {

    public static MainWindow w;
    public static LoginWindow lw;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LoginWindow lw = new LoginWindow();
        lw.setVisible(true);
        System.loadLibrary("facesdk");
        
    }
    
}
