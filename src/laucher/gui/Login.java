package laucher.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("BusyWait")
public class Login implements Runnable {
    private final String password;

    // GUI components
    private JFrame frame;
    private boolean authorized = false;
    private JButton loginButton;
    private JPanel loginPanel;
    private JPasswordField passwordField;
    private JLabel infoLabel;

    public Login(String password){
        this.password = password;
    }

    @Override
    public void run() {
        initFrame();

        loginButton.addActionListener(e -> {
            // get password from password field
            StringBuilder userPassword = new StringBuilder();
            for (char c : passwordField.getPassword()) {
                userPassword.append(c);
            }

            // verify password
            if(userPassword.toString().equals(password)) {
                // password correct ; user authorized
                this.authorized = true;
                frame.dispose();
            }
            else {
                // password incorrect
                infoLabel.setText("Incorrect password!");
                infoLabel.setForeground(Color.RED);
                passwordField.setText("");
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getExtendedKeyCode() == 10){
                    loginButton.doClick();
                    e.consume();
                    return;
                }
                super.keyReleased(e);
            }
        });

        while (frame.isVisible())
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    private void initFrame() {
        frame = new JFrame();
        frame.setSize(400,300);
        frame.setTitle("Chrome Launcher Login");
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
