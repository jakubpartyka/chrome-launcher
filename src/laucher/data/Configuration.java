package laucher.data;

import laucher.gui.ConfigData;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import javax.swing.*;
import java.awt.*;
import java.io.File;
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
        // check if password needed
        if(!conf.accessPassword.isBlank()){
            boolean userVerified = showPasswordDialog(conf);
            if(!userVerified) {
                JOptionPane.showMessageDialog(null,"Authorization failed","Access denied",JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        // vpn prompt if necessary
        if(conf.vpnRequired){
            int option = JOptionPane.showConfirmDialog(null,"VPN connection is required to use this configuration.\n" +
                    "Please make sure your connection is secure before proceeding.","Reminder", JOptionPane.OK_CANCEL_OPTION);
            if(option != 0)
                return;
        }

        ChromeOptions options = new ChromeOptions();

        // INIT PROXY IF SET
        if(!conf.proxyAddress.isBlank() && !conf.proxyPort.isBlank()) {
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(conf.proxyAddress + ":" + conf.proxyPort);
            proxy.setSslProxy(conf.proxyAddress + ":" + conf.proxyPort);
            options.setCapability("proxy", proxy);
        }

        // INIT USER AGENT IF SET
        if(!conf.userAgent.isBlank())
            options.addArguments("--user-agent=\"" + conf.userAgent + "\"");

        // CHOOSE PROFILE DIR IF SET
        if(!conf.customProfileDirectory.isBlank()){
            File dir = new File(conf.customProfileDirectory);
            //check if custom profile directory is set correctly
            if(!dir.exists() || !dir.isDirectory()){
                JOptionPane.showMessageDialog(null,"Failed to set up profile directory." +
                        "\nExists: " + dir.exists() +
                        "\nIs Directory: " + dir.isDirectory());
            }
            else
                options.addArguments("--user-data-dir=" + dir.getAbsolutePath());
        }

        // start driver and navigate to myip.com API address
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://api.myip.com");

        // show proxy credentials field if present
        if(!conf.proxyPass.isBlank() && !conf.proxyUser.isBlank())
            SwingUtilities.invokeLater(new ConfigData(conf));
    }

    /**
     * Shows password dialog.
     * @param conf configuration object to which access is being verified
     * @return true if provided password matches conf accessPassword
     */
    private static boolean showPasswordDialog(Configuration conf) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Password:");
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
