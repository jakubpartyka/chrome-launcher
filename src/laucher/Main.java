package laucher;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
      SwingUtilities.invokeLater(new GUI());
//        FileReader reader = new FileReader(".cl.cfg");
//
//        Properties p=new Properties();
//        p.load(reader);
//
//        p.setProperty("a","3");
//        p.store(new FileWriter(".cl.cfg"),"test");
    }
}
