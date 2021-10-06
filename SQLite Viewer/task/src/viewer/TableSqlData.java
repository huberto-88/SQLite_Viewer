package viewer;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class TableSqlData extends AbstractTableModel {
    String[] columns;
    Object[][] data;

    public TableSqlData(ArrayList<String> columns, ArrayList<ArrayList<String>> data) {
        this.columns = columns.toArray(String[]::new);
        this.data = new String[data.size()][data.get(0).size()];
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(0).size(); j++) {
                this.data[i][j] = data.get(i).get(j);
            }
        }
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        data[rowIndex][columnIndex] = value;
        fireTableCellUpdated(rowIndex, columnIndex);

    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns[columnIndex];
    }
}
