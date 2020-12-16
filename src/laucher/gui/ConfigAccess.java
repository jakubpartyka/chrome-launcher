package laucher.gui;

import javax.swing.*;

public class ConfigAccess implements Runnable {
    private final String password;

    private JFrame frame;

    public ConfigAccess(String password){
        this.password = password;
    }


    @Override
    public void run() {
        initFrame();
    }


    @SuppressWarnings("DuplicatedCode")
    public void initFrame() {
        frame = new JFrame();
        frame.setSize(400,300);
        frame.setTitle("Chrome Launcher Login");
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
