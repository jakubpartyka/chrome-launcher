package laucher.data;

import laucher.gui.ConfigData;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.swing.*;
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

    public static void start(Configuration conf) {
        Proxy proxy = new Proxy();
        proxy.setHttpProxy("<" + conf.proxyAddress + ":" + conf.proxyPort + ">");
        ChromeOptions options = new ChromeOptions();
        options.setCapability("proxy", proxy);
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://api.myip.com");
        SwingUtilities.invokeLater(new ConfigData(conf));
    }

    public String getProxyUser() {
        return proxyUser;
    }

    public String getProxyPass() {
        return proxyPass;
    }

    @Override
    public String toString() {
        return alias;
    }
}
