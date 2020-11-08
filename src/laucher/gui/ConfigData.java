package laucher.gui;

import laucher.data.Configuration;

import javax.swing.*;

public class ConfigData implements Runnable{

    private JPanel mainPanel;
    private JTextField usernameField;
    private JTextField passField;
    private JButton doneButton;
    Configuration configuration;

    public ConfigData(Configuration configuration){
        this.configuration = configuration;
    }

    @Override
    public void run() {
        JFrame frame = new JFrame();
        frame.add(mainPanel);

        usernameField.setText(configuration.getProxyUser());
        passField.setText(configuration.getProxyPass());


        frame.setSize(300,300);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);
    }
}
