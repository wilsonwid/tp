package scm.address.model.filename.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import scm.address.model.schedule.Description;
import scm.address.model.schedule.Title;

public class TitleTest {
    @Test
    public void constructor_validTitle_createsTitle() {
        String validTitle = "Meeting with Team";
        Title title = new Title(validTitle);
        assertEquals(validTitle, title.toString());
    }
    @Test
    public void equals() {
        Title title1 = new Title("Title");
        Title title2 = new Title("Title");
        Title title3 = new Title("Different Title");

        assertEquals(title1, title2);
        assertNotEquals(title1, title3);
    }

    @Test
    public void equals_variousDescriptions_correctResult() {
        Description description1 = new Description("description");
        Description description2 = new Description("description");
        Description description3 = new Description("other");
        assertEquals(description1, description2);
        assertNotEquals(description1, description3);
    }

    @Test
    public void equals_identicalObjects_true() {
        Title title1 = new Title("Meeting");
        Title title2 = new Title("Meeting");
        assertEquals(title1, title2);
    }

    @Test
    public void equals_null_false() {
        Title title = new Title("Meeting");
        assertNotEquals(null, title);
    }

    @Test
    public void equals_differentType_false() {
        Title title = new Title("Meeting");
        Object other = new Object();
        assertNotEquals(other, title);
    }
}
