package scm.address.model.filename.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import scm.address.model.schedule.Title;

public class TitleTest {
    @Test
    public void constructor_validTitle_createsTitle() {
        String validTitle = "Meeting with Team";
        Title title = new Title(validTitle);
        assertEquals(validTitle, title.toString());
    }
}
