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
    String userAgent;

    boolean proxyRequired;
    String  customProfileDirectory;
    /**
     * Additional access-control layer. Password to be provided to launch or access config data.
     */
    String  accessPassword;

    public Configuration(String alias, String proxyAddress, String proxyPort, String proxyUser, String proxyPass, String proxyCountry, String userAgent) {
        this.alias = alias;
        this.proxyAddress = proxyAddress;
        this.proxyPort = proxyPort;
        this.proxyUser = proxyUser;
        this.proxyPass = proxyPass;
        this.proxyCountry = proxyCountry;
        this.userAgent = userAgent;
    }

    public static void start(Configuration conf) {
        Proxy proxy = new Proxy();

        // set proxy
        proxy.setHttpProxy(conf.proxyAddress + ":" + conf.proxyPort);
        proxy.setSslProxy(conf.proxyAddress + ":" + conf.proxyPort);
        ChromeOptions options = new ChromeOptions();
        options.setCapability("proxy", proxy);

        // set user agent
        if(conf.userAgent != null && !conf.userAgent.equals(""))
            options.addArguments("--user-agent=\"" + conf.userAgent + "\"");

        // start driver and navigate to myip.com API address
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://api.myip.com");

        // show proxy credentials field if present
        if(conf.proxyPass != null && !conf.proxyPass.matches("^\\s*$") && conf.proxyUser != null && !conf.proxyUser.matches("^\\s*$"))
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
