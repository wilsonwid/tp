package scm.address.model.filename.schedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import scm.address.logic.parser.AddScheduleCommandParser;
import scm.address.logic.parser.exceptions.ParseException;
import scm.address.model.schedule.Description;
import scm.address.model.schedule.Schedule;
import scm.address.model.schedule.Title;

import static org.junit.jupiter.api.Assertions.*;

public class ScheduleTest {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    @Test
    public void constructor_validArgs_createsSchedule() {
        LocalDateTime startDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = startDateTime.plusHours(1);
        Schedule schedule = new Schedule(new Title("Meeting"),
                new Description("Discuss project"),
                startDateTime.format(formatter), endDateTime.format(formatter));
        assertNotNull(schedule);
    }

    @Test
    public void constructor_nullTitle_throwsNullPointerException() {
        Description validDescription = new Description("Valid Description");
        LocalDateTime validStartDateTime = LocalDateTime.of(2023, 3, 21, 15, 0);
        LocalDateTime validEndDateTime = LocalDateTime.of(2023, 3, 21, 16, 0);
        assertThrows(AssertionError.class, () -> new Schedule(null,
                validDescription, validStartDateTime.format(formatter), validEndDateTime.format(formatter)));
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
}
