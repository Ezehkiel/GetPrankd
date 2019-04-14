import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * This class is responsible for retrieving the following configurations from the config files :
 * the SMTP server IP address and port, the number of groups, the mail addresses and the pranks.
 */
public class Configuration {

    private String host;
    private int port;
    private int nbOfGroups;
    private String username;
    private String password;
    private List<String> victims;
    private List<String> pranks;
    private boolean hasLogin = false;

    /**
     * Configuration constructor
     */
    public Configuration(String conf, String pranks, String victims) {

        this.victims = new ArrayList<>();
        this.pranks = new ArrayList<>();

        final String msgDelimiter = "---";
        File confFile = new File(conf);
        File mailFile = new File(victims);
        File prankFile = new File(pranks);

        /* Gets server configuration and number of groups */
        List<String> configs = retrieveConf(confFile);

        this.host = configs.get(0);
        this.port = Integer.parseInt(configs.get(1));
        this.nbOfGroups = Integer.parseInt(configs.get(2));

        if (this.hasLogin) {
            this.username = configs.get(3);
            this.password = configs.get(4);
        }

        /* Gets the list of victims */
        this.victims = retrieveVictims(mailFile);

        /* Gets the list of pranks */
        this.pranks = retrievePranks(prankFile, msgDelimiter);
    }


    String getHost() {
        return this.host;
    }

    int getPort() {
        return this.port;
    }

    int getNbOfGroups() {
        return this.nbOfGroups;
    }

    String getUsername() {
        return this.username;
    }

    String getPassword() {
        return this.password;
    }

    List<String> getPranks() {
        return this.pranks;
    }

    List<String> getVictims() {
        return this.victims;
    }

    List<String> retrieveConf(File file) {

        List<String> conf = new ArrayList<>();
        Properties prop = new Properties();
        boolean hasUsername = false, hasPassword = false;

        try {
            Reader confReader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            prop.load(confReader);
            conf.add(prop.getProperty("smtpServerIp"));
            conf.add(prop.getProperty("smtpServerPort"));
            conf.add(prop.getProperty("numberOfGroups"));

            if (prop.getProperty("username") != null) {
                conf.add(prop.getProperty("username"));
                hasUsername = true;
            }
            if (prop.getProperty("password") != null) {
                conf.add(prop.getProperty("password"));
                hasPassword = true;
            }

            if (hasUsername && hasPassword) {
                this.hasLogin = true;
            }

            confReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return conf;
    }

    List<String> retrieveVictims(File file) {

        List<String> victims = new ArrayList<>();

        try {
            BufferedReader mailsReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));

            String line;
            while ((line = mailsReader.readLine()) != null) {
                victims.add(line);
            }
            mailsReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return victims;
    }

    List<String> retrievePranks(File file, String delimiter) {

        List<String> pranks = new ArrayList<>();

        try {
            BufferedReader pranksReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));

            String tempMessage = "";
            String line;

            while ((line = pranksReader.readLine()) != null) {
                if (!line.equals(delimiter)) {
                    tempMessage += line + "\n";
                } else {
                    pranks.add(tempMessage);
                    tempMessage = "";
                }
            }
            pranksReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return pranks;
    }

    boolean hasLogin() {
        return this.hasLogin;
    }
}
