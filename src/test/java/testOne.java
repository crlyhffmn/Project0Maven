import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.sql.Connection;

public class testOne {
    @Test
    public void test() {
        User testUser = new User(0, "a", "b", "c", "d");
        assertTrue(testUser.getFirstName().equals("a"));
    }

}
