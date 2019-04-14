import java.util.List;

/**
 * This class represents the DATA part of a mail. It contains a sender, a list of recipients and a prank message.
 */
public class Mail {
    private Victim from;
    private List<Victim> to;
    private Prank message;

    public Mail(Victim from, List<Victim> to, Prank message) {
        this.from = from;
        this.to = to;
        this.message = message;
    }

    Victim getFrom() {
        return this.from;
    }

    List<Victim> getTo() {
        return this.to;
    }

    Prank getMessage() {
        return this.message;
    }
}
