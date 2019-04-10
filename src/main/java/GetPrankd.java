import java.util.ArrayList;
import java.util.List;

public class GetPrankd {

    private static final int MIN_GROUP_SIZE = 3;

    private Configuration config;
    private List<Victim> victims;
    private List<Prank> pranks;
    private List<Group> groups;
    private List<Mail> mails;
    private int nbOfGroups;

    /**
     *
     */
    public GetPrankd() {

        this.config = new Configuration();

        /* Gets number of groups */
        this.nbOfGroups = this.config.getNbOfGroups();

        /* Checks if there is enough victims for the number of groups */
        if (!enoughVictimsPerGroup()) {
            System.exit(1);
        }

        /* Creates Victim objects from their mail address */
        this.victims = new ArrayList<Victim>();
        List<String> addresses = config.getVictims();
        for (String address : addresses) {
            this.victims.add(new Victim(address));

        }

        /* Creates Prank objects from the given pranks file */
        this.pranks = new ArrayList<Prank>();
        for (String message : config.getPranks()) {
            this.pranks.add(new Prank(message));
        }


        /* Creates Group objects */
        this.groups = makeGroups();

        /* Chooses a random prank among all given pranks */
        int index = (int) (Math.random() * this.pranks.size());

        /* Creates Mail objects */
        this.mails = new ArrayList<Mail>();
        for (Group group : this.groups) {
            this.mails.add(new Mail(group.getSender(), group.getRecipients(), this.pranks.get(index)));
        }

        /* Once the mails are ready, they are passed to the SMTP client, which is responsible for sending them */
        for (Mail mail : this.mails) {
            new SMTPClient(this.config.getHost(), this.config.getPort(), mail);
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
        List<Group> groups = new ArrayList<Group>();
        int i = 0;


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

        GetPrankd application = new GetPrankd();
    }
}
