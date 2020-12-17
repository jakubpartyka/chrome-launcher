package laucher.gui;

import laucher.data.Configuration;
import laucher.data.ConfigurationsTableModel;
import laucher.data.DataReaderWriter;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.io.IOException;

public class GUI implements Runnable {
    JPanel mainPanel;
    JTabbedPane tabbedPane;
    private JTextField aliasField;
    private JButton addConfigurationButton;
    private JButton startBrowserButton;
    private JCheckBox exitLauncherOnceBrowserCheckBox;
    private JTable dataTable;
    private JTextField proxyAddressField;
    private JTextField proxyPortField;
    private JTextField proxyUserField;
    private JTextField proxyPasswordField;
    private JTextField proxyCountryField;
    private JButton deleteSelectedButton;
    private JTextField userAgentField;
    private JCheckBox vpnRequiredCheckBox;
    private JTextField accessPasswordField;
    private JTextField customProfilePath;
    private JTextField userAgentAliasField;
    private JCheckBox disableExtensionsCheckbox;
    private JButton addButton;
    private JButton cancelButton;
    private JTextField editAlias;
    private JTextField editProxyAlias;
    private JTextField editProxyPort;
    private JTextField editProxyUser;
    private JTextField editProxyPassword;
    private JTextField editProxyCountry;
    private JTextField editUserAgent;
    private JTextField editUserAgentAlias;
    private JTextField editProfilePath;
    private JTextField editAccessPassword;
    private JCheckBox editVpnRequired;
    private JCheckBox editDisableExtensions;
    private JButton updateConfiguration;
    private JButton cancelButton2;
    private JButton editButton;
    private JFrame frame;

    private void initMainFrame() {
        frame = new JFrame();
        // set frame properties
        frame.setSize(800, 600);
        frame.setMinimumSize(new Dimension(300, 300));
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Chrome Launcher");

        // init data table
        dataTable.setModel(new ConfigurationsTableModel());
        dataTable.setForeground(Color.WHITE);
        resizeColumnWidth(dataTable);
        dataTable.getTableHeader().setFont(new Font("Monospaced", Font.BOLD, 12));
        dataTable.setRowHeight(dataTable.getRowHeight()+10);

        tabbedPane.setUI(new javax.swing.plaf.metal.MetalTabbedPaneUI(){
            @Override
            protected int calculateTabAreaHeight(int tabPlacement, int horizRunCount, int maxTabHeight) {
                return 0;
            }
            protected void paintTabArea(Graphics g,int tabPlacement,int selectedIndex){}
        });

        // adding components
        frame.add(mainPanel);

        // show frame
        frame.setVisible(true);
    }

    @Override
    public void run() {
        initMainFrame();
        setActionListeners();
    }

    private void setActionListeners() {
        addConfigurationButton.addActionListener(e -> {
            try {
                createNewConfiguration();
                tabbedPane.setSelectedIndex(0);
                } catch (IOException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to create new profile configuration.\n"
                        + e1.getMessage(), "Data write failure", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteSelectedButton.addActionListener(e -> {
            int [] selectedRows = dataTable.getSelectedRows();
            if(selectedRows.length == 0)
                return;
            int choice = JOptionPane.showConfirmDialog(null,"Delete " + selectedRows.length + " entries?","Confirm action",JOptionPane.OK_CANCEL_OPTION);
            if(choice == 0)
                ((ConfigurationsTableModel)dataTable.getModel()).delete(selectedRows);
        });

        startBrowserButton.addActionListener(e -> {
            // allow only one browser instance to be started at time
            if (dataTable.getSelectedRows().length != 1) {
                JOptionPane.showMessageDialog(null, "Please select exactly one configuration to launch", "Incorrect number of configurations selected", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Configuration configuration = Configuration.configurationList.get(dataTable.getSelectedRow());
            Configuration.start(configuration);

            if (exitLauncherOnceBrowserCheckBox.isSelected()){
                frame.setVisible(false);
                frame.dispose();
            }
        });

        cancelButton.addActionListener(e -> tabbedPane.setSelectedIndex(0));

        cancelButton2.addActionListener(e -> tabbedPane.setSelectedIndex(0));

        editButton.addActionListener(e -> {
            tabbedPane.setSelectedIndex(2);
        });

        addButton.addActionListener(e -> tabbedPane.setSelectedIndex(1));

    }

    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0 ; column < table.getColumnCount() - 1 ; column++) {
            int width = 15;     // min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1 , width);
            }
            if(width > 300)
                width = 300;    // max width
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void createNewConfiguration() throws IOException {
        // read data from GUI
        String alias = aliasField.getText();
        String proxyAddress = proxyAddressField.getText();
        String proxyPort = proxyPortField.getText();
        String proxyUser = proxyUserField.getText();
        String proxyPassword = proxyPasswordField.getText();
        String proxyCountry = proxyCountryField.getText();
        String userAgent = userAgentField.getText();
        String userAgentAlias = userAgentAliasField.getText();
        boolean vpnRequired = vpnRequiredCheckBox.isSelected();
        String customProfileDirectory = customProfilePath.getText();
        String accessPassword = accessPasswordField.getText();
        boolean disableExtensions = disableExtensionsCheckbox.isSelected();

        // validate data
        if(alias.isBlank()){
            JOptionPane.showMessageDialog(null,"Alias can't be null","Incorrect alias",JOptionPane.ERROR_MESSAGE);
            return;
        }

        // create new configuration object
        Configuration configuration = new Configuration(
                alias,proxyAddress,proxyPort,proxyUser,proxyPassword,proxyCountry,
                userAgent,userAgentAlias,vpnRequired,customProfileDirectory,accessPassword,disableExtensions
        );

        // write configuration to file
        DataReaderWriter.addConfiguration(configuration);

        // add configuration to gui
        Configuration.configurationList.add(configuration);
        ((ConfigurationsTableModel)dataTable.getModel()).fireTableDataChanged();

        // show info message
        JOptionPane.showMessageDialog(null,"Configuration added successfully","Configuration added",JOptionPane.INFORMATION_MESSAGE);

        //clear GUI fields
        aliasField.setText("");
        proxyAddressField.setText("");
        proxyPortField.setText("");
        proxyUserField.setText("");
        proxyPasswordField.setText("");
        userAgentField.setText("");
        userAgentAliasField.setText("");
        customProfilePath.setText("");
        accessPasswordField.setText("");
    }

}
