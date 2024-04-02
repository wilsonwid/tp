package scm.address.model.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class DescriptionTest {
    private static final String VALID_DESCRIPTION_STRING = "Project meeting";
    @Test
    public void equals() {
        Description description = new Description(VALID_DESCRIPTION_STRING);
        Description otherDescription = new Description(VALID_DESCRIPTION_STRING);
        assertEquals(description, otherDescription);

        Title title = new Title(VALID_DESCRIPTION_STRING);
        assertFalse(description.equals(title));
    }
}
