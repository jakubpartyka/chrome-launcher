package laucher.data;

import javax.swing.table.AbstractTableModel;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
            case 1 -> conf.getProxyInfo();
            case 2 -> conf.getProfilePath();
            case 3 -> conf.vpnRequired;
            case 4 -> conf.disableExtensions;
            case 5 -> conf.isPasswordProtected();
            case 6 -> conf.getUserAgentInfo();
            default -> "N/D";
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Alias";
            case 1 -> "Proxy";
            case 2 -> "Custom profile";
            case 3 -> "VPN required";
            case 4 -> "Ext. disabled";
            case 5 -> "Password protected";
            case 6 -> "User agent";
            default -> "Invalid column";
        };
    }

    public void delete(int[] selectedRows) {
        List<Configuration> toRemove = Arrays.stream(selectedRows).mapToObj(row -> Configuration.configurationList.get(row)).collect(Collectors.toList());
        toRemove.forEach(DataReaderWriter::removeConfiguration);
        Configuration.configurationList.removeAll(toRemove);
        this.fireTableDataChanged();
    }

    public void delete(Configuration conf){
        Configuration.configurationList.remove(conf);
        this.fireTableDataChanged();
    }
}
