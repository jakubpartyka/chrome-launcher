package launcher.data;

import launcher.gui.ConfigData;
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
    public String alias;
    public String proxyAddress;
    public String proxyPort;
    public String proxyUser;
    public String proxyPass;
    public String proxyCountry;
    public String userAgent, userAgentAlias;
    public String startPage;

    public boolean vpnRequired, disableExtensions;
    public String  customProfileDirectory;
    /**
     * Additional access-control layer. Password to be provided to launch or access config data.
     */
    public String  accessPassword;

    public Configuration(String alias, String proxyAddress, String proxyPort, String proxyUser,
                         String proxyPass, String proxyCountry, String userAgent,
                         String userAgentAlias, boolean vpnRequired, String customProfileDirectory,
                         String accessPassword, boolean disableExtensions, String startPage) {
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
        this.disableExtensions = disableExtensions;
        this.startPage = startPage;
    }


    public static boolean start(Configuration conf) {
        // check if password needed
        if(!conf.accessPassword.isBlank()){
            boolean userVerified = showPasswordDialog(conf);
            if(!userVerified) {
                JOptionPane.showMessageDialog(null,"Authorization failed","Access denied",JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }

        // vpn prompt if necessary
        if(conf.vpnRequired){
            int option = JOptionPane.showConfirmDialog(null,"VPN connection is required to use this configuration.\n" +
                    "Please make sure your connection is secure before proceeding.","Reminder", JOptionPane.OK_CANCEL_OPTION);
            if(option != 0)
                return false;
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
                return false;
            }
            else
                options.addArguments("--user-data-dir=" + dir.getAbsolutePath());
        }

        //disable extensions if needed
        if(conf.disableExtensions)
            options.addArguments("--disable-extensions");


        // start driver and navigate to myip.com API address
        try {
            WebDriver driver = new ChromeDriver(options);

            String address = "https://api.myip.com";

            if(conf.startPage != null && !conf.startPage.isBlank())
                address = conf.startPage;

            if(!address.startsWith("https://"))
                address = "https://" + address;
            driver.get(address);

            // show proxy credentials field if present
            if(!conf.proxyPass.isBlank() && !conf.proxyUser.isBlank())
                SwingUtilities.invokeLater(new ConfigData(conf));
        } catch (Exception e){
            String errorMessage = e.getMessage();
            //check if it's error name not resolved exception
            if (e.getMessage().contains("ERR_NAME_NOT_RESOLVED"))
                errorMessage = "Incorrect URL specified";

            JOptionPane.showMessageDialog(null,"Failed to start browser instance. Error message:\n" + errorMessage,
                    "Launch failed",JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
        }

    /**
     * Shows password dialog.
     * @param conf configuration object to which access is being verified
     * @return true if provided password matches conf accessPassword
     */
    public static boolean showPasswordDialog(Configuration conf) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Password:");
        JPasswordField pass = new JPasswordField(10);
        panel.add(label);
        panel.add(pass);
        panel.setPreferredSize(new Dimension(200,100));
        String[] options = new String[]{"OK", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "Password required",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        if(option == 0) // pressing OK button
        {
            String provided = new String(pass.getPassword());
            Encryptor.setLastPassword(provided);
            return Encryptor.hashMatch(provided,conf.accessPassword);
        }
        else    // password verification cancelled
            return false;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "id=" + id +
                ", alias='" + alias + '\'' +
                ", proxyAddress='" + proxyAddress + '\'' +
                ", proxyPort='" + proxyPort + '\'' +
                ", proxyUser='" + proxyUser + '\'' +
                ", proxyPass='" + proxyPass + '\'' +
                ", proxyCountry='" + proxyCountry + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", userAgentAlias='" + userAgentAlias + '\'' +
                ", vpnRequired=" + vpnRequired +
                ", disableExtensions=" + disableExtensions +
                ", customProfileDirectory='" + customProfileDirectory + '\'' +
                ", accessPassword='" + accessPassword + '\'' +
                '}';
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

    public Object getStartPage() {
        if(startPage == null || startPage.isBlank())
            return "not set";
        if(startPage.startsWith("https://"))
            return startPage.substring(8);
        else
            return startPage;
    }
}
