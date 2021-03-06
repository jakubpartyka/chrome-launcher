package launcher;

import launcher.data.Configuration;
import launcher.data.DataReaderWriter;
import launcher.gui.GUI;
import launcher.gui.Login;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("BusyWait")
public class Main {
    public static void main(String[] args) throws InterruptedException {
        // READ CONFIG DATA
        try {
            DataReaderWriter.init();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Failed to start program, please check console logs\n" + e.getMessage() + "\nWorking directory: " + (new File("./").getAbsolutePath()),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // SHOW LOGIN
        Login login = new Login(DataReaderWriter.getPassword());
        if(!DataReaderWriter.getPassword().isBlank()) {
            Thread loginThread = new Thread(login);
            loginThread.start();
            while (loginThread.isAlive())
                Thread.sleep(200);

            // in case that login thread failed but user did not authorize - exit
            if (!login.isAuthorized()) {
                JOptionPane.showMessageDialog(null, "User unauthorized", "Access denied", JOptionPane.WARNING_MESSAGE);
                System.exit(1);
            }
        }

        // START MAIN GUI
        SwingUtilities.invokeLater(new GUI());
        System.out.println(Configuration.configurationList);
    }
}