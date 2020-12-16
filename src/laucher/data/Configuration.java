package laucher.data;

import laucher.gui.ConfigData;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import javax.swing.*;
import java.awt.*;
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
    String userAgent, userAgentAlias;

    boolean vpnRequired;
    String  customProfileDirectory;
    /**
     * Additional access-control layer. Password to be provided to launch or access config data.
     */
    String  accessPassword;

    public Configuration(String alias, String proxyAddress, String proxyPort, String proxyUser,
                         String proxyPass, String proxyCountry, String userAgent,
                         String userAgentAlias, boolean vpnRequired, String customProfileDirectory,
                         String accessPassword) {
        this.alias = alias;
        this.proxyAddress = proxyAddress;
        this.proxyPort = proxyPort;
        this.proxyUser = proxyUser;
        this.proxyPass = proxyPass;
        this.proxyCountry = proxyCountry;
        this.userAgent = userAgent;
        this.userAgentAlias = userAgentAlias;
        this.vpnRequired = vpnRequired;
        this.customProfileDirectory = customProfileDirectory;
        this.accessPassword = accessPassword;
    }


    public static void start(Configuration conf) {
        if(!conf.accessPassword.isBlank()){
            boolean userVerified = showPasswordDialog(conf);
            if(!userVerified)
                return;
        }


        Proxy proxy = new Proxy();

        // set proxy
        proxy.setHttpProxy(conf.proxyAddress + ":" + conf.proxyPort);
        proxy.setSslProxy(conf.proxyAddress + ":" + conf.proxyPort);
        ChromeOptions options = new ChromeOptions();
        options.setCapability("proxy", proxy);

        // SET USER AGENT
        if(!conf.userAgent.isBlank())
            options.addArguments("--user-agent=\"" + conf.userAgent + "\"");

        // start driver and navigate to myip.com API address
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://api.myip.com");

        // show proxy credentials field if present
        if(conf.proxyPass != null && !conf.proxyPass.matches("^\\s*$") && conf.proxyUser != null && !conf.proxyUser.matches("^\\s*$"))
            SwingUtilities.invokeLater(new ConfigData(conf));
    }

    /**
     * Shows password dialog.
     * @param conf configuration object to which access is being verified
     * @return true if provided password matches conf accessPassword
     */
    private static boolean showPasswordDialog(Configuration conf) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Provide password:");
        JPasswordField pass = new JPasswordField(10);
        panel.add(label);
        panel.add(pass);
        panel.setPreferredSize(new Dimension(200,100));
        String[] options = new String[]{"OK", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "Password required",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[1]);
        if(option == 0) // pressing OK button
        {
            char[] password = pass.getPassword();
            String provided = new String(password);
            return provided.equals(conf.accessPassword);
        }
        else    // password verification cancelled
            return false;
    }


    @Override
    public String toString() {
        return alias;
    }


    // GETTERS

    public String getProxyUser() {
        return proxyUser;
    }

    public String getProxyInfo() {
        if(proxyUser.isBlank())
            return "none";
        return proxyCountry + " - " + proxyAddress;
    }

    public String getProfilePath() {
        if(customProfileDirectory.isBlank())
            return "not set";
        return customProfileDirectory;
    }

    public String isPasswordProtected() {
        if(accessPassword.isBlank())
            return "not set";
        return "yes";
    }

    public String getProxyPass() {
        return proxyPass;
    }

    public Object getUserAgentInfo() {
        if(userAgent.isBlank())
            return "not set";
        if(userAgentAlias.isBlank())
            return "set, no alias";
        return userAgentAlias;
    }
}
