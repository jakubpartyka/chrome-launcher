package laucher.data;

import java.util.ArrayList;
import java.util.List;

public class Configuration {
    public static List<Configuration> configurationList = new ArrayList<>();

    String alias;
    String proxyAddress;
    String proxyPort;
    String proxyUser;
    String proxyPass;

    public Configuration(String alias, String proxyAddress, String proxyPort, String proxyUser, String proxyPass) {
        this.alias = alias;
        this.proxyAddress = proxyAddress;
        this.proxyPort = proxyPort;
        this.proxyUser = proxyUser;
        this.proxyPass = proxyPass;
    }

    @Override
    public String toString() {
        return alias;
    }
}
