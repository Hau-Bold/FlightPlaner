package render;

import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import gps_coordinates.GpsCoordinate;

@SuppressWarnings("serial")
public class CityRenderer extends DefaultTableCellRenderer {

	private List<GpsCoordinate> receiving;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		String city = (String) value;
		for (GpsCoordinate gps : receiving) {
			if (gps.getCity().equals(city)) {
				if (gps.getLongitude() > 0) {
					setForeground(Color.BLUE);
				} else {
					setForeground(Color.RED);
				}
			}
		}

		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}

	public void setData(List<GpsCoordinate> receiving) {
		this.receiving = receiving;
	}

}
