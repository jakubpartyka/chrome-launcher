package laucher.data;

import javax.swing.table.AbstractTableModel;

public class ConfigurationsTable extends AbstractTableModel {
    @Override
    public int getRowCount() {
        return Configuration.configurationList.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Configuration conf = Configuration.configurationList.get(rowIndex);
        return switch (columnIndex) {
            case 1 -> conf.alias;
            case 2 -> conf.proxyAddress;
            case 3 -> conf.proxyPort;
            case 4 -> conf.proxyUser;
            case 5 -> conf.proxyPass;
            default -> "N/A";
        };
    }
}
