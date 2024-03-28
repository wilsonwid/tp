package scm.address.model.filename.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

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
}
