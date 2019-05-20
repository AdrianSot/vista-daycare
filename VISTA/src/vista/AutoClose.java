package vista;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class AutoClose {
    long t = 0;
    public static void main(String[] args) {
        new AutoClose();
    }

    private Timer timer;
    private MainWindow w = new MainWindow();
    
    AutoClose() {
        w.setVisible(true);
        t = 0;
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                t++;
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

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
                                try{
                                    if(Main.lw.isDisplayable()){
                                        if(t < timer.getDelay())
                                           Main.lw.setVisible(false);
                                        else{
                                            w.Salir();
                                        }
                                     }
                                }catch(NullPointerException n){
                                }
                                //t = 0;
                            }
                        }
                    }
                }, AWTEvent.KEY_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.MOUSE_WHEEL_EVENT_MASK);

                timer = new Timer(5000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        t = 0;
                        w.Salir();
                    }
                });
                timer.start();
                
                   WindowListener fw = new WindowListener() {
                    @Override
                    public void windowOpened(WindowEvent e) {
                       /* if(e.getWindow() == w){
                            timer.start();
                        }else{
                            try {
                                timer.wait();
                            } catch (InterruptedException ex) {
                            }
                        }*/
                        
                    }

                    @Override
                    public void windowClosing(WindowEvent e) {
                        /*if(e.getWindow() == w){
                            Main.lw.setVisible(true);
                        }*/
                    }

                    @Override
                    public void windowClosed(WindowEvent e) {
                        /*if(e.getWindow() == w){
                             Main.lw.setVisible(true);
                        }*/
                    }

                    @Override
                    public void windowIconified(WindowEvent e) {

                    }

                    @Override
                    public void windowDeiconified(WindowEvent e) {

                    }

                    @Override
                    public void windowActivated(WindowEvent e) {
                       /*if(e.getWindow() == w){
                            timer.start();
                        }else{
                            try {
                                timer.wait();
                            } catch (InterruptedException ex) {
                            }
                        }*/
                    }

                    @Override
                    public void windowDeactivated(WindowEvent e) {
                       /* if(e.getWindow() == w)
                            Main.lw.setVisible(true);
                        else
                            timer.start();*/
                    }
                };
            }
        });
    }

}