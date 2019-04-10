import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MailTest {

    @Test
    public void aMailFromShouldReturnTheCorrectSender() {
        Victim sender = new Victim("batman@gotham.ct");
        Victim luke = new Victim("lukeskywalker@rebel.ta");
        Victim leia = new Victim("princess@alderaan.ga");
        List<Victim> victims = new ArrayList<Victim>();
        victims.add(luke);
        victims.add(leia);
        Prank prank = new Prank("I am your father.");

        Mail mail = new Mail(sender, victims, prank);

        assertEquals(sender, mail.getFrom());
    }

    @Test
    public void aMailToShouldReturnTheCorrectRecipients() {
        Victim sender = new Victim("batman@gotham.ct");
        Victim luke = new Victim("lukeskywalker@rebel.ta");
        Victim leia = new Victim("princess@alderaan.ga");
        List<Victim> victims = new ArrayList<Victim>();
        victims.add(luke);
        victims.add(leia);
        Prank prank = new Prank("I am your father.");

        Mail mail = new Mail(sender, victims, prank);

        assertEquals(victims, mail.getTo());
    }

    @Test
    public void aMailMessageShouldReturnTheCorrectPrank() {
        Victim sender = new Victim("batman@gotham.ct");
        Victim luke = new Victim("lukeskywalker@rebel.ta");
        Victim leia = new Victim("princess@alderaan.ga");
        List<Victim> recipients = new ArrayList<Victim>();
        recipients.add(luke);
        recipients.add(leia);
        Prank prank = new Prank("I am your father.");

        Mail mail = new Mail(sender, recipients, prank);

        assertEquals(prank, mail.getMessage());
    }

}
