package laucher.gui;

import javax.swing.*;

@SuppressWarnings("BusyWait")
public class Login implements Runnable {
    private String password;

    // GUI components
    private JFrame frame;
    private boolean authorized = false;
    private JButton loginButton;
    private JPanel loginPanel;

    public Login(String password){
        this.password = password;
    }

    @Override
    public void run() {
        initFrame();

        loginButton.addActionListener(e -> {
            this.authorized = true;
            frame.dispose();
        });

        while (frame.isVisible()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void initFrame() {
        frame = new JFrame();
        frame.setSize(300,300);
        frame.setResizable(false);
        frame.add(loginPanel);
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public boolean isAuthorized() {
        return authorized;
    }
}
