package laucher.data;

import javax.swing.table.AbstractTableModel;

public class ConfigurationsTableModel extends AbstractTableModel {
    @Override
    public int getRowCount() {
        return Configuration.configurationList.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Configuration conf = Configuration.configurationList.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> conf.alias;
            case 1 -> conf.proxyAddress;
            case 2 -> conf.proxyPort;
            case 3 -> conf.proxyUser;
            case 4 -> conf.proxyPass;
            case 5 -> conf.proxyCountry;
            default -> "N/A";
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Alias";
            case 1 -> "Proxy address";
            case 2 -> "Proxy port";
            case 3 -> "Proxy user";
            case 4 -> "Proxy password";
            case 5 -> "Proxy country";
            default -> "Invalid column";
        };
    }
}
