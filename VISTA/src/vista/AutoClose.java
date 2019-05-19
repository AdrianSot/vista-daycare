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
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class AutoClose {

    public static void main(String[] args) {
        new AutoClose();
    }

    private Timer timer;
    private MainWindow w = new MainWindow();

    AutoClose() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }
                w.setVisible(true);

                Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {

                    private int count;

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
                                if(Main.lw.isDisplayable())
                                    Main.lw.setVisible(false);
                            }
                        }
                    }
                }, AWTEvent.KEY_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.MOUSE_WHEEL_EVENT_MASK);

                timer = new Timer(5000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        w.dispose();
                        Main.lw = new LoginWindow();
                        Main.lw.setVisible(true);
                    }
                });
                    WindowListener fw = new WindowListener() {
                    @Override
                    public void windowOpened(WindowEvent e) {
                        if(e.getWindow() == w){
                            timer.start();
                            Main.lw.setVisible(false);
                        }
                            
                        
                    }

                    @Override
                    public void windowClosing(WindowEvent e) {
                        if(e.getWindow() == w){
                            Main.lw.setVisible(true);
                        }
                    }

                    @Override
                    public void windowClosed(WindowEvent e) {
                        if(e.getWindow() == w)
                            Main.lw.setVisible(true);
                    }

                    @Override
                    public void windowIconified(WindowEvent e) {

                    }

                    @Override
                    public void windowDeiconified(WindowEvent e) {

                    }

                    @Override
                    public void windowActivated(WindowEvent e) {
                        if(e.getWindow() == w)
                            timer.start();
                        else
                            w.setVisible(false);
                    }

                    @Override
                    public void windowDeactivated(WindowEvent e) {
                        if(e.getWindow() == w)
                            Main.lw.setVisible(true);
                        else
                            timer.start();
                    }
                };
            }
        });
    }

}