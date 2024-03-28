package scm.address.model.filename.schedule;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

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
                startDateTime.format(formatter), endDateTime.format(formatter));
        assertNotNull(schedule);
    }
}
