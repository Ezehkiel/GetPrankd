import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VictimTest {

    @Test
    public void aVictimShouldHaveTheRightMailAddress() {
        String chuckNorris = "chucknorris@chucknorris.cn";
        Victim norrisChuck = new Victim(chuckNorris);

        assertEquals(norrisChuck.getMailAddress(), chuckNorris);
    }

}
