package laucher;

import laucher.data.Configuration;
import laucher.data.DataReaderWriter;
import laucher.gui.GUI;
import laucher.gui.Login;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // READ CONFIG DATA
        try {
            DataReaderWriter.init();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Failed to start program, please check console logs","Error",JOptionPane.ERROR_MESSAGE);
        }

        // SHOW LOGIN
        Login login = new Login(DataReaderWriter.getPassword());
        Thread loginThread = new Thread(login);
        loginThread.start();
        while (loginThread.isAlive())
            //noinspection BusyWait
            Thread.sleep(200);

        // in case that login thread failed but user did not authorize - exit
        if(!login.isAuthorized())
            System.exit(1);


        // START MAIN GUI
        SwingUtilities.invokeLater(new GUI());
        System.out.println(Configuration.configurationList);
    }
}
