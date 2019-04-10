import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GroupTest {

    @Test
    public void theSenderShouldBeTheRightOne() {
        Victim sender = new Victim("batman@gotham.ct");
        Group group = new Group();
        group.addSender(sender);

        assertEquals(sender, group.getSender());
    }

    @Test
    public void VictimsShouldBeTheRightOnes() {
        Victim luke = new Victim("lukeskywalker@rebel.ta");
        Victim leia = new Victim("princess@alderaan.ga");
        List<Victim> victims = new ArrayList<Victim>();
        victims.add(luke);
        victims.add(leia);
        Group group = new Group();
        group.addRecipient(luke);
        group.addRecipient(leia);

        assertArrayEquals(group.getRecipients().toArray(), victims.toArray());
    }
}
