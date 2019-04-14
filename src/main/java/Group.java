import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a group of victims, made of a sender and recipients.
 */
public class Group {

    private Victim sender;
    private List<Victim> recipients = new ArrayList<>();

    public Group() {}

    void addSender(Victim sender){
        this.sender = sender;
    }

    void addRecipient(Victim recipient){
        this.recipients.add(recipient);
    }

    Victim getSender() {
        return this.sender;
    }

    List<Victim> getRecipients() {
        return this.recipients;
    }
}
