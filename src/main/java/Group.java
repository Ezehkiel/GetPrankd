import java.util.ArrayList;
import java.util.List;

public class Group {
    private Victim sender;
    private List<Victim> recipients = new ArrayList<Victim>();

    public Group() {}

    public void addSender(Victim sender){
        this.sender = sender;
    }

    public void addRecipient(Victim recipient){
        this.recipients.add(recipient);
    }

    public Victim getSender() {
        return this.sender;
    }

    public List<Victim> getRecipients() {
        return this.recipients;
    }
}