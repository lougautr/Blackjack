import test.*;
import org.junit.Test;


public class LauchAllTest {
    @Test
    public void all() {
        System.out.println("\t\t\t[START TESTING]\n");

        // Call to main test class
        CardTest.allTest();
        DeckTest.allTest();
        DeckFactoryTest.allTest();
        System.out.println("\n\t\t\t[END TESTING]");
    }
}
