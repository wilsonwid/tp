package scm.address.model.filename.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import scm.address.logic.parser.AddScheduleCommandParser;
import scm.address.logic.parser.exceptions.ParseException;
import scm.address.model.schedule.Description;
import scm.address.model.schedule.Schedule;
import scm.address.model.schedule.Title;

public class ScheduleTest {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    @Test
    public void constructor_validArgs_createsSchedule() {
        LocalDateTime startDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = startDateTime.plusHours(1);
        Schedule schedule = new Schedule(new Title("Meeting"),
                new Description("Discuss project"),
                startDateTime, endDateTime);
        assertNotNull(schedule);
    }

    @Test
    public void constructor_nullTitle_throwsNullPointerException() {
        Description validDescription = new Description("Valid Description");
        LocalDateTime validStartDateTime = LocalDateTime.of(2023, 3, 21, 15, 0);
        LocalDateTime validEndDateTime = LocalDateTime.of(2023, 3, 21, 16, 0);
        assertThrows(NullPointerException.class, () -> new Schedule(null,
                validDescription, validStartDateTime, validEndDateTime));
    }

    @Test
    public void constructor_invalidStartEndTime_throwsIllegalArgumentException() {
        Title validTitle = new Title("Valid Title");
        Description validDescription = new Description("Valid Description");
        LocalDateTime invalidStartDateTime = LocalDateTime.of(2023, 3, 22, 16, 0);
        LocalDateTime invalidEndDateTime = LocalDateTime.of(2023, 3, 21, 15, 0);
        assertThrows(ParseException.class, () -> new AddScheduleCommandParser().parse("title/"
                        + validTitle.toString() + " d/" + validDescription.toString()
                        + " start/" + invalidStartDateTime.format(formatter)
                        + " end/" + invalidEndDateTime.format(formatter)));
    }

    @Test
    public void createScheduleFromParts_validInputs_success() {
        String title = "Meeting";
        String description = "Project discussion";
        String date = "2023-03-21";
        String time1 = "15:00";
        String time2 = "16:00";

        Schedule schedule = new Schedule(
                new Title("Meeting"),
                new Description("Project discussion"),
                LocalDateTime.of(2023, 3, 21, 15, 0),
                LocalDateTime.of(2023, 3, 21, 16, 0)
        );

        assertNotNull(schedule);
        assertEquals(title, schedule.getTitle().toString());
        assertEquals(description, schedule.getDescription().toString());
    }

    @Test
    public void sameSchedule() {
        String title = "Meeting";
        String description = "Project discussion";
        String date = "2023-03-21";
        String time1 = "15:00";
        String time2 = "16:00";

        Schedule schedule = new Schedule(
                new Title("Meeting"),
                new Description("Project discussion"),
                LocalDateTime.of(2023, 3, 21, 15, 0),
                LocalDateTime.of(2023, 3, 21, 16, 0)
        );
        assertTrue(schedule.equals(schedule));
    }

    @Test
    public void equals() {
        String title = "Meeting";
        String description = "Project discussion";
        String date = "2023-03-21";
        String time1 = "15:00";
        String time2 = "16:00";

        Schedule schedule = new Schedule(
                new Title("Meeting"),
                new Description("Project discussion"),
                LocalDateTime.of(2023, 3, 21, 15, 0),
                LocalDateTime.of(2023, 3, 21, 16, 0)
        );
        assertTrue(schedule.equals(schedule));
        Schedule scheduleCopy = new Schedule(
                new Title("Meeting"),
                new Description("Project discussion"),
                LocalDateTime.of(2023, 3, 21, 15, 0),
                LocalDateTime.of(2023, 3, 21, 16, 0)
        );
        assertTrue(schedule.equals(scheduleCopy));

        assertFalse(schedule.equals(1));

        assertFalse(schedule.equals(null));

        Schedule differentSchedule = new Schedule(
                new Title("Meeting"),
                new Description("Project discussion"),
                LocalDateTime.of(2023, 3, 21, 15, 0),
                LocalDateTime.of(2023, 3, 21, 17, 0)
        );
        assertFalse(schedule.equals(differentSchedule));
    }
}
