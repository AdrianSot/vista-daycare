package vista;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                                //label.setText("Interrupted..." + (++count));
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
                if(Main.lw.userStatus == Main.lw.userStatus.AdminLogged || Main.lw.userStatus == Main.lw.userStatus.RecepLogged)
                    timer.start();
            }
        });
    }

}