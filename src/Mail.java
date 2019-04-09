import java.util.List;

/**
 * This class represents the DATA part of a fake mail
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

    public Victim getFrom() {
        return this.from;
    }

    public List<Victim> getTo() {
        return this.to;
    }

    public Prank getMessage() {
        return this.message;
    }
}
