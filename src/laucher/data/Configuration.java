package laucher.data;

import java.util.ArrayList;
import java.util.List;

public class Configuration {
    public static List<Configuration> configurationList = new ArrayList<>();

    int id;
    String alias;
    String proxyAddress;
    String proxyPort;
    String proxyUser;
    String proxyPass;
    String proxyCountry;

    public Configuration(String alias, String proxyAddress, String proxyPort, String proxyUser, String proxyPass, String proxyCountry) {
        this.alias = alias;
        this.proxyAddress = proxyAddress;
        this.proxyPort = proxyPort;
        this.proxyUser = proxyUser;
        this.proxyPass = proxyPass;
        this.proxyCountry = proxyCountry;
    }

    public static Configuration getConfiguration(int id){
        for (Configuration configuration : configurationList)
            if (configuration.id == id)
                return configuration;
        return null;
    }

    @Override
    public String toString() {
        return alias;
    }
}
