package view.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RenderTable extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	private DecimalFormat numberFormat = new DecimalFormat("###.##");
	private Component javaComponent;
	
	public RenderTable() {}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		
		javaComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		setHorizontalAlignment(0);
		setBorder(null);
		setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, new Color(58,159,161)));
		setFont(new Font("Verdana", Font.BOLD, 12));
		
		if(isSelected) {
			javaComponent.setForeground(table.getSelectionForeground());
			javaComponent.setBackground(table.getSelectionBackground());
		}
		else {
			javaComponent.setForeground(table.getSelectionForeground());
			javaComponent.setBackground(row % 2 == 0 ?
					new Color(240,240,255)
					: table.getBackground());
		}
		
		if(value instanceof Double) {
			Number numero = (Number) value;
			String texto = numberFormat.format(numero);
			setText(texto);
			setForeground(numero.doubleValue()< 0 ? Color.RED : Color.BLACK);
			setHorizontalAlignment(CENTER);
		}
		
		if(value instanceof Integer) {
			setHorizontalAlignment(CENTER);
		}
		
		if(value != null && value instanceof Date) {
			Date date = (Date) value;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String textoData = sdf.format(date);
			setText(textoData);
			setHorizontalAlignment(CENTER);
			
		}
		
		return javaComponent;
	}
	
}
