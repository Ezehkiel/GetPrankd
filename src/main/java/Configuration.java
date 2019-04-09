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
        List<String> conf = new ArrayList<String>();

        try {

            /* Gets server configuration and groups number */
            BufferedReader confReader = new BufferedReader(new FileReader(confFile));
            String line;

            while ((line = confReader.readLine()) != null) {
                String[] values = line.split(confDelimiter);
                conf.add(values[1]);
            }
            confReader.close();

            this.host = conf.get(0);
            this.port = Integer.parseInt(conf.get(1));
            this.nbOfGroups = Integer.parseInt(conf.get(2));

            /* Gets the list of victims */
            BufferedReader mailsReader = new BufferedReader(new FileReader(mailFile));

            while ((line = mailsReader.readLine()) != null) {
                this.victims.add(line);
            }
            mailsReader.close();

            /* Gets the list of pranks */
            BufferedReader pranksReader = new BufferedReader(new FileReader(prankFile));

            String tempMessage = "";

            while ((line = pranksReader.readLine()) != null) {
                if (!line.equals(msgDelimiter)) {
                    tempMessage += line + "\n";
                } else {
                    this.pranks.add(tempMessage);
                    tempMessage = "";
                }
            }

            pranksReader.close();

        }  catch (IOException e) {
            e.printStackTrace();
        }
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

    public List<String> getpranks() {
        return this.pranks;
    }

    public List<String> getVictims() {
        return this.victims;
    }


}
