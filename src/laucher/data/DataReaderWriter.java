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
    private static int next_id;

    public static void init() throws IOException {
        reader = new FileReader(".cl.cfg");
        p=new Properties();
        p.load(reader);
        
        config_count = Integer.parseInt(p.getProperty("config_count"));
        next_id = Integer.parseInt(p.getProperty("next_id"));
        
        readConfigurations();
    }

    private static void readConfigurations() {
        for (int i = 1; i < next_id; i++) {
            String alias = p.getProperty("alias_" + i);

            if(alias == null || alias.matches("(\\s+)"))
                continue;

            String proxyAddress = p.getProperty("proxy_address_" + i,null);
            String proxyPort = p.getProperty("proxy_port_" + i,null);
            String proxyUser = p.getProperty("proxy_user_" + i,null);
            String proxyPass = p.getProperty("proxy_pass_" + i,null);
            String proxyCountry = p.getProperty("proxy_country_" + i,null);
            Configuration configuration = new Configuration(alias,proxyAddress,proxyPort,proxyUser,proxyPass, proxyCountry);
            configuration.id = i;
            Configuration.configurationList.add(configuration);
        }
    }

    public static void addConfiguration(Configuration conf) throws IOException {
        config_count++;
        next_id++;
        conf.id = Integer.parseInt((String) p.get("next_id"));

        p.setProperty("config_count", String.valueOf(config_count));
        p.setProperty("next_id", String.valueOf(next_id));

        p.setProperty("alias_" + conf.id,conf.alias);
        p.setProperty("proxy_address_" + conf.id,conf.proxyAddress);
        p.setProperty("proxy_port_" + conf.id,conf.proxyPort);
        p.setProperty("proxy_user_" + conf.id,conf.proxyUser);
        p.setProperty("proxy_pass_" + conf.id,conf.proxyPass);
        p.setProperty("proxy_country_" + conf.id,conf.proxyCountry);

        p.store(new FileWriter(".cl.cfg"),"Configuration File");
    }

    public static void removeConfiguration(Configuration configuration){
        int id = configuration.id;
        config_count--;
        p.setProperty("config_count", String.valueOf(config_count));

        // remove properties
        p.remove("alias_" + id);
        p.remove("proxy_address_" + id);
        p.remove("proxy_port_" + id);
        p.remove("proxy_user_" + id);
        p.remove("proxy_pass_" + id);
        p.remove("proxy_country_" + id);

        try {
            p.store(new FileWriter(".cl.cfg"),"Configuration File");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
