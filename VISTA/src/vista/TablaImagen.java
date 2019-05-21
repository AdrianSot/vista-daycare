/*VERSION DE WINDOWS CRUDS COMPLETOS*/

package vista;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author pedrohdez
 */
public class TablaImagen extends DefaultTableCellRenderer{
    
    @Override
        public Component getTableCellRendererComponent(JTable table, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {
            setHorizontalAlignment(SwingConstants.CENTER);
            if(value instanceof JLabel){
                
                JLabel lbl = (JLabel)value;
                return lbl;
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
        }
    
}
