package laucher.data;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationsTableModel extends AbstractTableModel {
    @Override
    public int getRowCount() {
        return Configuration.configurationList.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
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
            case 6 -> conf.userAgent;
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
            case 6 -> "User agent";
            default -> "Invalid column";
        };
    }

    public void delete(int[] selectedRows) {
        List<Configuration> toRemove = new ArrayList<>();
        for (int row : selectedRows)
            toRemove.add(Configuration.configurationList.get(row));
        toRemove.forEach(DataReaderWriter::removeConfiguration);
        Configuration.configurationList.removeAll(toRemove);
        this.fireTableDataChanged();
    }
}
