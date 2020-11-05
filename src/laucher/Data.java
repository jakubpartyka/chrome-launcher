package laucher;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

@SuppressWarnings("FieldCanBeLocal")
public class Data {

    private static FileReader reader;
    private static Properties p;

    public static void init() throws IOException {
        reader = new FileReader(".cl.cfg");
        p=new Properties();
        p.load(reader);
    }

    public static void addConfiguration(String alias) throws IOException {
        p.setProperty("alias",alias);
        p.store(new FileWriter(".cl.cfg"),"Configuration File");
    }

}
