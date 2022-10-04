package view.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

public class RenderHeaderTable implements TableCellRenderer{

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		
		JComponent component = null;
		if(value instanceof String) {
			component = new JLabel((String)""+value);
			((JLabel)component).setHorizontalAlignment(SwingConstants.CENTER);
			((JLabel)component).setSize(30, component.getWidth());
			((JLabel)component).setPreferredSize(new Dimension(3, component.getWidth()));
			
		}
		
		table.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 12));
		table.getTableHeader().setPreferredSize(new Dimension(45,45));
		component.setEnabled(true);
		component.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, new Color(255,255,255)));
		component.setOpaque(true);
		component.setBackground(new Color(51,102,153));
		component.setForeground(Color.white);
		component.setFont(new Font("Verdana", Font.BOLD, 11));
		
		
		return component;
	}
	
}
