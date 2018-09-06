package tablemodel;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class CommonModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private String[] nameofColumns = null;

	private Object[][] dataEntries = null;

	/**
	 * Constructor.
	 * 
	 * @param nameofCOlumns
	 *            - the column names
	 */
	public CommonModel(String[] nameofCOlumns) {
		this.nameofColumns = nameofCOlumns;
	}

	@Override
	public int getColumnCount() {
		return nameofColumns.length;
	}

	@Override
	public int getRowCount() {
		if (dataEntries != null) {
			return dataEntries.length;
		}
		return 0;
	}

	@Override
	public Object getValueAt(int row, int column) {
		return dataEntries[row][column];
	}

	/** to receive name of a column */
	public String getColumnName(int column) {
		return nameofColumns[column];
	}

	/**
	 * to add a row
	 * 
	 * @param row
	 *            - the row
	 */
	public void addDataRow(Vector<String> vector) {
		Object[][] newData = null;

		if (dataEntries == null) {
			dataEntries = new Object[1][getColumnCount()];
			newData = new Object[1][getColumnCount()];
		}

		else {
			newData = new Object[getRowCount() + 1][getColumnCount()];
			/** copy data */
			for (int row = 0; row < dataEntries.length; row++) {
				for (int column = 0; column < getColumnCount(); column++) {
					newData[row][column] = dataEntries[row][column];
				}
			}

		}
		/** append the vector */
		for (int i = 0; i < vector.size(); i++) {
			newData[newData.length - 1][i] = vector.get(i);
		}

		dataEntries = newData;
		this.fireTableDataChanged();
	}

	/**
	 * to append an empty row
	 */
	public void appendEmptyRow() {
		Object[][] newData;
		if (dataEntries.equals(null)) {
			newData = new Object[1][nameofColumns.length];
		} else {
			newData = new Object[dataEntries.length + 1][nameofColumns.length];

			for (int row = 0; row < dataEntries.length; row++) {
				for (int column = 0; column < nameofColumns.length; column++) {
					newData[row][column] = dataEntries[row][column];
				}
			}

		}

		dataEntries = newData;
		this.fireTableDataChanged();
	}

	/**
	 * to delete selected rows.
	 * 
	 * @param arrayOfSelectedRows
	 *            - the array of selected rows
	 */
	public void deleteRow(int[] arrayOfSelectedRows) {
		if (dataEntries != null) {
			Object[][] newData = new Object[dataEntries.length - arrayOfSelectedRows.length][nameofColumns.length];

			/** copy upper part */
			for (int row = 0; row < arrayOfSelectedRows[0]; row++) {
				for (int column = 0; column < nameofColumns.length; column++) {
					newData[row][column] = dataEntries[row][column];
				}
			}

			/** copy lower part */
			for (int row = arrayOfSelectedRows[arrayOfSelectedRows.length - 1] + 1; row < dataEntries.length; row++) {
				for (int column = 0; column < nameofColumns.length; column++) {
					newData[row - arrayOfSelectedRows.length][column] = dataEntries[row][column];
				}
			}

			dataEntries = newData;
			this.fireTableDataChanged();
		}

		else {
			return;
		}
	}

	/**
	 * 
	 * yields the state
	 */
	public Boolean isEmpty() {
		return dataEntries == null;
	}

	/**
	 * to clear the model
	 */
	public void clear() {
		if (dataEntries == null) {
			return;
		} else {
			Object[][] newData = null;
			dataEntries = newData;
		}
		this.fireTableDataChanged();

	}

	/**
	 * reloads the id column.
	 */
	public void revalidate() {
		if (dataEntries != null) {
			Object[][] newData = new Object[dataEntries.length][nameofColumns.length];

			for (int row = 0; row < dataEntries.length; row++) {
				for (int column = 0; column < nameofColumns.length; column++) {
					if (column == 0) {
						newData[row][column] = row + 1;
					} else {
						newData[row][column] = dataEntries[row][column];
					}
				}
			}

			dataEntries = newData;
			this.fireTableDataChanged();
		}

		else {
			return;
		}

	}
}
