import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrankTest {

    @Test
    public void aPrankShouldHaveTheRightMessage() {
        String testPrank = "Hello darkness my old friend";
        Prank prank = new Prank(testPrank);

        assertEquals(prank.getMessage(), testPrank);
    }

}
