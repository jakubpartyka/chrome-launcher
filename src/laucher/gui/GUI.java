package laucher.gui;

import laucher.data.Configuration;
import laucher.data.ConfigurationsTableModel;
import laucher.data.DataReaderWriter;

import javax.swing.*;
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
    JFrame frame;

    private void initMainFrame() {
        frame = new JFrame();
        // set frame properties
        frame.setSize(800, 600);
        frame.setMinimumSize(new Dimension(300, 300));
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Chrome Launcher");

        // init table model
        dataTable.setModel(new ConfigurationsTableModel());

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
                // create new configuration object
                Configuration configuration = new Configuration(
                        aliasField.getText(),
                        proxyAddressField.getText(),
                        proxyPortField.getText(),
                        proxyUserField.getText(),
                        proxyPasswordField.getText(),
                        proxyCountryField.getText()
                );

                // write configuration to file
                DataReaderWriter.addConfiguration(configuration);

                // add configuration to gui
                Configuration.configurationList.add(configuration);
                ((ConfigurationsTableModel)dataTable.getModel()).fireTableDataChanged();

                // show info message
                JOptionPane.showMessageDialog(null,"Configuration added successfully","Configuration added",JOptionPane.INFORMATION_MESSAGE);
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
            if(dataTable.getSelectedRows().length != 1){
                JOptionPane.showMessageDialog(null,"Please select exactly one configuration to launch","Incorrect number of configurations selected",JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Configuration configuration = Configuration.configurationList.get(dataTable.getSelectedRow());
            Configuration.start(configuration);
        });

    }

}
