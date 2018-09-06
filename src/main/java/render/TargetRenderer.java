package render;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TargetRenderer extends DefaultTableCellRenderer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4184483296177981045L;
	private Double content;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if(value != null)
		{
		 content =Double.valueOf((String) value);
		}
		
			if(content > 0)
			{
			setForeground(Color.BLUE);
			}
			else
			{
				setForeground(Color.RED);
			}
		
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}

}
