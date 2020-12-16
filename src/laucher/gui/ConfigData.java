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

        // show login data
        usernameField.setText(configuration.getProxyUser());
        passField.setText(configuration.getProxyPass());

        // exit on window close
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // add action listener
        doneButton.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();
        });

        frame.setSize(300,200);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);
    }
}
