package laucher.data;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

@SuppressWarnings("FieldCanBeLocal")
public class DataReaderWriter {
    private static FileReader reader;
    private static Properties p;
    private static int config_count;

    public static void init() throws IOException {
        reader = new FileReader(".cl.cfg");
        p=new Properties();
        p.load(reader);
        
        config_count = Integer.parseInt(p.getProperty("config_count"));
        
        readConfigurations();
    }

    public static void addConfiguration(String alias) throws IOException {
        config_count++;
        p.setProperty("config_count", String.valueOf(config_count));
        p.setProperty("alias_"+config_count,alias);
        p.store(new FileWriter(".cl.cfg"),"Configuration File");
    }

    private static void readConfigurations() {
        for (int i = 1; i <= config_count; i++) {
            String alias = p.getProperty("alias_" + i);
            Configuration configuration = new Configuration(alias);
            Configuration.configurationList.add(configuration);
        }
    }

}
