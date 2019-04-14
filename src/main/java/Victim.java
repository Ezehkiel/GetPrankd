/**
 * This class represents a victim of the prank campaign.
 */
public class Victim {

    private String mailAddress;

    public Victim(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    String getMailAddress() {
        return this.mailAddress;
    }
}