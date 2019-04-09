import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Configuration {

    /* These constants define where the configuration files will be stored. The path is relative
     * to where the Java application is invoked. */
    private static String ROOT = "./conf/";
    private static String CONF = ROOT + "conf.txt";

    private String host;
    private int port;
    private int nbOfGroups;

    /**
     * Configuration constructor
     */
    public Configuration() {

        final String confDelimiter = "=";
        final String msgDelimiter = "---";
        File confFile = new File(CONF);
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


        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

}
