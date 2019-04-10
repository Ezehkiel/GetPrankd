import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Configuration {

    /* These constants define where the configuration files will be stored. The path is relative
     * to where the Java application is invoked. */
    private static String ROOT = "./conf/";
    private static String CONF = ROOT + "conf.txt";
    private static String VICTIMS = ROOT + "victims.txt";
    private static String PRANKS = ROOT + "pranks.txt";

    private String host;
    private int port;
    private int nbOfGroups;
    private List<String> victims;
    private List<String> pranks;

    /**
     * Configuration constructor
     */
    public Configuration() {

        this.victims = new ArrayList<String>();
        this.pranks = new ArrayList<String>();

        final String confDelimiter = "=";
        final String msgDelimiter = "---";
        File confFile = new File(CONF);
        File mailFile = new File(VICTIMS);
        File prankFile = new File(PRANKS);

        /* Gets server configuration and number of groups */
        List<String> conf = retrieveConf(confFile, confDelimiter);

        this.host = conf.get(0);
        this.port = Integer.parseInt(conf.get(1));
        this.nbOfGroups = Integer.parseInt(conf.get(2));

        /* Gets the list of victims */
        this.victims = retrieveVictims(mailFile);

        /* Gets the list of pranks */
        this.pranks = retrievePranks(prankFile, msgDelimiter);
    }


    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public int getNbOfGroups() {
        return this.nbOfGroups;
    }

    public List<String> getPranks() {
        return this.pranks;
    }

    public List<String> getVictims() {
        return this.victims;
    }

    public List<String> retrieveConf(File file, String delimiter) {

        List<String> conf = new ArrayList<String>();

        try {
            BufferedReader confReader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = confReader.readLine()) != null) {
                String[] values = line.split(delimiter);
                conf.add(values[1]);
            }
            confReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return conf;
    }

    public List<String> retrieveVictims(File file) {

        List<String> victims = new ArrayList<String>();

        try {
            BufferedReader mailsReader = new BufferedReader(new FileReader(file));

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

    public List<String> retrievePranks(File file, String delimiter) {

        List<String> pranks = new ArrayList<String>();

        try {
            BufferedReader pranksReader = new BufferedReader(new FileReader(file));

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

}
