package scm.address.model.filename.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import scm.address.model.schedule.Description;

public class DescriptionTest {
    @Test
    public void constructor_validDescription_createsDescription() {
        String validDescription = "Discuss project milestones";
        Description description = new Description(validDescription);
        assertEquals(validDescription, description.toString());
    }
    @Test
    public void equals() {
        Description description1 = new Description("Description");
        Description description2 = new Description("Description");
        Description description3 = new Description("Different");

        assertEquals(description1, description2);
        assertNotEquals(description1, description3);
    }
}
