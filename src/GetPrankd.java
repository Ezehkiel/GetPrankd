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
    }
}
