package laucher;

import jdk.dynalink.linker.GuardedInvocation;

import javax.swing.*;

public class GUI implements Runnable {
    JPanel mainPanel;
    JTabbedPane tabbedPane;
    JFrame frame = new JFrame();

    private void initMainFrame() {
        // set frame properties
        frame.setSize(800,600);
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
