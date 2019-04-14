import org.apache.commons.cli.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class uses the data gathered from Configuration to create Victim, Group and Prank objects. Once everything is
 * ready, Mail objects are created and passed to the SMTPClient.
 */
public class GetPrankd {

    private static final int MIN_GROUP_SIZE = 3;

    private Configuration config;
    private List<Victim> victims;
    private List<Prank> pranks;
    private List<Group> groups;
    private List<Mail> mails;
    private int nbOfGroups;
    private String username;
    private String password;

    /**
     *
     */
    public GetPrankd(Configuration configuration) {

        this.config = configuration;

        /* If there is a username and a password, gets and converts them in base64 */
        if (config.hasLogin()) {
            //this.username = Base64.getEncoder().encodeToString((config.getUsername()).getBytes());
            //this.password = Base64.getEncoder().encodeToString((config.getPassword()).getBytes());
            this.username = config.getUsername();
            this.password = config.getPassword();
        }

        /* Gets number of groups */
        this.nbOfGroups = this.config.getNbOfGroups();

        /* Checks if there is enough victims for the number of groups */
        if (!enoughVictimsPerGroup()) {
            System.exit(1);
        }

        /* Creates Victim objects from their mail address */
        this.victims = new ArrayList<>();
        List<String> addresses = config.getVictims();
        for (String address : addresses) {
            this.victims.add(new Victim(address));
        }

        /* Creates Prank objects from the given pranks file */
        this.pranks = new ArrayList<>();
        for (String message : config.getPranks()) {
            this.pranks.add(new Prank(message));
        }

        /* Creates Group objects */
        this.groups = makeGroups();

        /* Chooses a random prank among all given pranks */
        int index = (int) (Math.random() * this.pranks.size());

        /* Creates Mail objects */
        this.mails = new ArrayList<>();
        for (Group group : this.groups) {
            this.mails.add(new Mail(group.getSender(), group.getRecipients(), this.pranks.get(index)));
        }

        /* Once the mails are ready, they are passed to the SMTP client, which is responsible for sending them */
        for (Mail mail : this.mails) {
            new SMTPClient(this.config.getHost(), this.config.getPort(), mail, this.username, this.password);
        }
    }

    /**
     * Performs simple checks to determine whether there is enough victims per group or not.
     *
     * @return true if enough groups of MIN_GROUP_SIZE can be made, false otherwise
     */
    private boolean enoughVictimsPerGroup() {

        if ((config.getVictims()).size() < this.nbOfGroups * MIN_GROUP_SIZE) {
            System.out.println("Not enough victims.");
            return false;
        }

        return true;
    }

    /**
     * Produces groups using the class's list of victims.
     *
     * @return groups
     */
    private List<Group> makeGroups() {

        int nbVictimsPerGroup = this.victims.size() / this.nbOfGroups;
        int remainder = this.victims.size() - (this.nbOfGroups * (nbVictimsPerGroup));
        List<Group> groups = new ArrayList<>();
        int i;


        /* Pushes an equal number of victims in each group. The first one is the sender */
        for (i = 0; i < this.nbOfGroups; ++i) {
            groups.add(new Group());
            for (int j = 0; j < nbVictimsPerGroup; ++j) {
                if (j == 0) {
                    groups.get(i).addSender(this.victims.get((i * nbVictimsPerGroup) + j));
                } else {
                    groups.get(i).addRecipient(this.victims.get((i * nbVictimsPerGroup) + j));
                }
            }
        }

        /* Treats remainder if there is some */
        int j = 0;
        for (int k = 0; k < remainder; k++) {
            groups.get(k).addRecipient(this.victims.get((i * nbVictimsPerGroup) + j + k));
        }
        return groups;
    }


    /**
     * Main application
     */
    public static void main(String[] args) {

        /* Parses arguments */
        Options options = new Options();

        Option confFile = new Option("c", "configfile", true, "config file");
        confFile.setRequired(true);
        options.addOption(confFile);

        Option pranksFile = new Option("p", "pranksfile", true, "pranks file");
        pranksFile.setRequired(true);
        options.addOption(pranksFile);

        Option victimsFile = new Option("v", "victimsfile", true, "victims file");
        victimsFile.setRequired(true);
        options.addOption(victimsFile);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
            String config = cmd.getOptionValue("configfile");
            String pranks = cmd.getOptionValue("pranksfile");
            String victims = cmd.getOptionValue("victimsfile");

            Configuration configuration = new Configuration(config, pranks, victims);
            new GetPrankd(configuration);

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }
    }
}
