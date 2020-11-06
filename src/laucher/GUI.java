package laucher;

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

        // adding components
        frame.add(mainPanel);

        // show frame
        frame.setVisible(true);
    }

    @Override
    public void run() {
        initMainFrame();
        setActionListeners();
        addConfigurationComponents();
    }

    private void addConfigurationComponents() {
        for (Configuration configuration : Configuration.configurationList) {
            //todo implement
        }
    }


    private void setActionListeners() {
        addConfigurationButton.addActionListener(e -> {
            try {
                Data.addConfiguration(aliasField.getText());
            } catch (IOException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to create new profile configuration.\n"
                        + e1.getMessage(), "Data write failure", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
