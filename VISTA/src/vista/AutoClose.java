package vista;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class AutoClose {
    public static void main(String[] args) {
        new AutoClose();
    }

    private Timer timer;
    public static MainWindow w = new MainWindow();
    
    AutoClose() {
        w.setVisible(true);
        w.drawingTimer.start();
        
        
        
        
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }
                w.setDefaultCloseOperation(MainWindow.EXIT_ON_CLOSE);
                Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
                    @Override
                    public void eventDispatched(AWTEvent event) {
                        Object source = event.getSource();
                        if (source instanceof Component) {
                            Component comp = (Component) source;
                            Window win = null;
                            if (comp instanceof Window) {
                                win = (Window) comp;
                            } else {
                                win = SwingUtilities.windowForComponent(comp);
                            }
                            if (win == w) {
                                timer.restart();
                            }
                        }
                    }
                }, AWTEvent.KEY_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.MOUSE_WHEEL_EVENT_MASK);

                timer = new Timer(1800000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        w.Salir();
                        //w.dispose();
                    }
                });
                try{
                    if(Main.lw.isVisible() && w.isVisible()){
                        Main.lw.setVisible(false);
                    }
                }
                    catch(Exception e){   
                }
                
                   WindowListener fw = new WindowListener() {
                    @Override
                    public void windowOpened(WindowEvent e) {
                       if(e.getWindow() == w){
                            timer.start();
                        }else if(e.getWindow() == Main.lw){
                            w.dispose();
                            timer.stop();
                        }
                        
                    }

                    @Override
                    public void windowClosing(WindowEvent e) {
                     
                    }

                    @Override
                    public void windowClosed(WindowEvent e) {
                        
                    }

                    @Override
                    public void windowIconified(WindowEvent e) {

                    }

                    @Override
                    public void windowDeiconified(WindowEvent e) {

                    }

                    @Override
                    public void windowActivated(WindowEvent e) {
                       if(e.getWindow() == w){
                            timer.restart();
                        }else{
                           timer.stop();
                        }
                    }

                    @Override
                    public void windowDeactivated(WindowEvent e) {
                     
                    }
                };
            }
        });
    }

}