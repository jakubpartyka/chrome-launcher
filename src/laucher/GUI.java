package laucher;

import javax.swing.*;
import java.awt.*;

public class GUI implements Runnable {
    JPanel mainPanel;
    JTabbedPane tabbedPane;
    private JTextField textField1;
    private JButton addConfigurationButton;
    JFrame frame = new JFrame();

    private void initMainFrame() {
        // set frame properties
        frame.setSize(800,600);
        frame.setMinimumSize(new Dimension(300,300));
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
    }
}
