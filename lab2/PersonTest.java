import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

public class PersonTest {

    @Test
    public void testEqualsAndHashCode() {
        EqualsVerifier.simple().forClass(Person.class).verify();
    }
}
