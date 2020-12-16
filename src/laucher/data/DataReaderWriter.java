package laucher.data;

import javax.swing.*;
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
    private static String password;

    public static void init() throws IOException {
        reader = new FileReader(".cl.cfg");
        p = new Properties();
        p.load(reader);

        // load basic properties
        config_count = Integer.parseInt(p.getProperty("config_count"));
        next_id = Integer.parseInt(p.getProperty("next_id"));
        password = p.getProperty("password");

        // check if password configured
        if(password==null){
            System.out.println("no password in config file");
            JOptionPane.showMessageDialog(null,"No password found in configuration file!","Configuration error",JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // load chrome browser configurations
        readConfigurations();
    }

    private static void readConfigurations() {
        for (int i = 1; i < next_id; i++) {
            String alias = p.getProperty("alias_" + i);

            if(alias == null || alias.matches("^\\s*$"))
                continue;

            // read data from file
            String proxyAddress = p.getProperty("proxy_address_" + i,null);
            String proxyPort = p.getProperty("proxy_port_" + i,null);
            String proxyUser = p.getProperty("proxy_user_" + i,null);
            String proxyPass = p.getProperty("proxy_pass_" + i,null);
            String proxyCountry = p.getProperty("proxy_country_" + i,null);
            String userAgent = p.getProperty("user_agent_" + i,null);
            String userAgentAlias = p.getProperty("user_agent_alias_" + i,null);
            boolean vpnRequired = Boolean.parseBoolean(p.getProperty("vpn_required_" + i,null));
            String customProfileDirectory = p.getProperty("custom+profile_dir_" + i,null);
            String accessPassword  = p.getProperty("access_password_" + i,null);

            // create new Configuration object
            Configuration configuration = new Configuration(alias,proxyAddress,proxyPort,proxyUser,
                    proxyPass, proxyCountry, userAgent,userAgentAlias,vpnRequired,customProfileDirectory,accessPassword);

            configuration.id = i;
            Configuration.configurationList.add(configuration);
        }
    }

    public static void addConfiguration(Configuration conf) throws IOException {
        // set new configuration ID
        conf.id = next_id;

        // increment counters
        config_count++;
        next_id++;

        // increment counters in property file
        p.setProperty("config_count", String.valueOf(config_count));
        p.setProperty("next_id", String.valueOf(next_id));

        // set config values
        p.setProperty("alias_" + conf.id,conf.alias);
        p.setProperty("proxy_address_" + conf.id,conf.proxyAddress);
        p.setProperty("proxy_port_" + conf.id,conf.proxyPort);
        p.setProperty("proxy_user_" + conf.id,conf.proxyUser);
        p.setProperty("proxy_pass_" + conf.id,conf.proxyPass);
        p.setProperty("proxy_country_" + conf.id,conf.proxyCountry);
        p.setProperty("user_agent_" + conf.id,conf.userAgent);

        // write config to file
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
        p.remove("user_agent_" + id);

        try {
            p.store(new FileWriter(".cl.cfg"),"Configuration File");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPassword() {
        return password;
    }
}
