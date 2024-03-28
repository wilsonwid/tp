package scm.address.model.filename.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import scm.address.model.schedule.Description;

public class DescriptionTest {
    @Test
    public void constructor_validDescription_createsDescription() {
        String validDescription = "Discuss project milestones";
        Description description = new Description(validDescription);
        assertEquals(validDescription, description.toString());
    }
}
