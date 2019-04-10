import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationTest {

    @Test
    public void configurationShouldBeTheRightOnes() {
        File testConfFile = new File("./conf/conf.txt");
        Configuration configuration = new Configuration();
        List<String> confTest = new ArrayList<String>();

        confTest = configuration.retrieveConf(testConfFile, "=");

        String testHost = confTest.get(0);
        int testPort = Integer.parseInt(confTest.get(1));
        int testNbOfGroups = Integer.parseInt(confTest.get(2));

        String host = configuration.getHost();
        int port = configuration.getPort();
        int nbOfGroups = configuration.getNbOfGroups();

        assertEquals(host, testHost);
        assertEquals(port, testPort);
        assertEquals(nbOfGroups, testNbOfGroups);
    }

    @Test
    public void victimsShouldBeTheRightOnes() {
        File mailFile = new File("./conf/victims.txt");
        Configuration config = new Configuration();

        List<String> victimsTest = config.retrieveVictims(mailFile);
        List<String> victims = config.getVictims();

        assertArrayEquals(victims.toArray(), victimsTest.toArray());
    }

    @Test
    public void pranksShouldBeTheRightOnes() {
        File prankFile = new File("./conf/pranks.txt");
        Configuration config = new Configuration();

        List<String> pranksTest = config.retrievePranks(prankFile, "---");
        List<String> pranks = config.getPranks();

        assertArrayEquals(pranks.toArray(), pranksTest.toArray());
    }

}
