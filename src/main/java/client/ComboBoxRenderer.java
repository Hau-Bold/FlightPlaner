package client;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import Routenplaner.Colors;

@SuppressWarnings("serial")
public class ComboBoxRenderer extends BasicComboBoxRenderer {

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
