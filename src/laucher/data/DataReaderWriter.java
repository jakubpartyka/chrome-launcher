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

    public static void addConfiguration(String alias, String proxyAddress, String proxyPort, String proxyUser, String proxyPass) throws IOException {
        config_count++;
        p.setProperty("config_count", String.valueOf(config_count));
        p.setProperty("alias_"+config_count,alias);
        p.setProperty("proxy_address_"+config_count,proxyAddress);
        p.setProperty("proxy_port_"+config_count,proxyPort);
        p.setProperty("proxy_user_"+config_count,proxyUser);
        p.setProperty("proxy_pass_"+config_count,proxyPass);
        p.store(new FileWriter(".cl.cfg"),"Configuration File");
    }

    private static void readConfigurations() {
        for (int i = 1; i <= config_count; i++) {
            String alias = p.getProperty("alias_" + i);
            String proxyAddress = p.getProperty("proxy_address_" + 1,null);
            String proxyPort = p.getProperty("proxy_port_" + 1,null);
            String proxyUser = p.getProperty("proxy_user_" + 1,null);
            String proxyPass = p.getProperty("proxy_pass_" + 1,null);
            Configuration configuration = new Configuration(alias,proxyAddress,proxyPort,proxyUser,proxyPass);
            Configuration.configurationList.add(configuration);
        }
    }

}
