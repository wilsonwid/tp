package scm.address.model.schedule;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static scm.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import scm.address.testutil.ScheduleBuilder;

public class ScheduleTest {
    @Test
    public void constructor_createSchedule_success() {
        Title title = new Title("Meeting");
        Description description = new Description("Project meeting");
        LocalDateTime startDateTime = LocalDateTime.parse("2024-03-10 16:00", Schedule.DATE_TIME_FORMATTER);
        LocalDateTime endDateTime = LocalDateTime.parse("2024-03-10 18:00", Schedule.DATE_TIME_FORMATTER);
        Schedule schedule = new Schedule(title, description, startDateTime, endDateTime);

        assertTrue(schedule instanceof Schedule);
    }

    @Test
    public void constructor_createScheduleFromString_success() {
        Title title = new Title("Meeting");
        Description description = new Description("Project meeting");
        String startDateTime = "2024-03-10 16:00";
        String endDateTime = "2024-03-10 18:00";
        Schedule schedule = new Schedule(title, description, startDateTime, endDateTime);

        assertTrue(schedule instanceof Schedule);
    }

    @Test
    public void equals() {
        Schedule schedule = new ScheduleBuilder()
                .withTitle("Meeting")
                .withDescription("Project meeting")
                .withStartDateTime("2024-03-10 16:00")
                .withEndDateTime("2024-03-10 18:00")
                .build();

        Schedule otherSchedule = new ScheduleBuilder(schedule).build();

        assertTrue(schedule.equals(otherSchedule));

        otherSchedule = new ScheduleBuilder(schedule)
                .withTitle("Project meeting")
                .build();

        assertFalse(schedule.equals(otherSchedule));

        otherSchedule = new ScheduleBuilder(schedule)
                .withDescription("Another project meeting")
                .build();

        assertFalse(schedule.equals(otherSchedule));

        otherSchedule = new ScheduleBuilder(schedule)
                .withStartDateTime("2024-03-11 16:00")
                .build();

        assertFalse(schedule.equals(otherSchedule));

        otherSchedule = new ScheduleBuilder(schedule)
                .withEndDateTime("2024-03-11 18:00")
                .build();

        assertFalse(schedule.equals(otherSchedule));
    }

    @Test
    public void constructor_nullField_failure() {
        assertThrows(NullPointerException.class, () -> new Schedule(null, null, null, (LocalDateTime) null));

        assertThrows(NullPointerException.class, () -> new Schedule(null, null, null, (String) null));
    }
}
