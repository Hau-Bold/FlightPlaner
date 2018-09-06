package client;

import java.awt.Component;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import Routenplaner.Colors;

public class ComboBoxRenderer extends BasicComboBoxRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7824668586097836987L;

	private ListSelectionModel disabledItems;

	ComboBoxRenderer(ListSelectionModel dmode) {
		this.disabledItems = dmode;
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {

		Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

		if (!disabledItems.isSelectedIndex(index)) {
			if (isSelected) {
				component.setBackground(super.getBackground());
				component.setForeground(super.getForeground());
			} else {
				component.setBackground(super.getBackground());
				component.setForeground(super.getForeground());
			}
		} else {
			if (disabledItems.isSelectedIndex(index)) {
					component.setBackground(Colors.colorRenderGray);
					component.setForeground(super.getForeground());
			}
		}
		return component;
	}
}
