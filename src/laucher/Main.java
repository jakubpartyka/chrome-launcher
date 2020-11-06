package laucher;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Data.init();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Failed to start program, please check console logs","Error",JOptionPane.ERROR_MESSAGE);
        }
        SwingUtilities.invokeLater(new GUI());
        System.out.println(Configuration.configurationList);
    }

}
