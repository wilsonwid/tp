package scm.address.logic.commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import scm.address.model.Model;
import scm.address.model.ModelManager;
import scm.address.model.schedule.Description;
import scm.address.model.schedule.Schedule;
import scm.address.model.schedule.Title;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddScheduleCommandTest {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Test
    public void execute_scheduleConstructed_success() {
        Title title = new Title("Meeting");
        Description description = new Description("Discuss project");
        LocalDateTime startDateTime = LocalDateTime.parse("2024-03-20T10:00");
        LocalDateTime endDateTime = LocalDateTime.parse("2024-03-20T11:00");
        Model model = new ModelManager();

        Schedule schedule = new Schedule(title, description,
                startDateTime, endDateTime);
        AddScheduleCommand command = new AddScheduleCommand(schedule);

        CommandResult result = command.execute(model);

        assertTrue(command != null);
    }

    @Test
    public void execute_addSchedule_successful() {
        Model model = new ModelManager();
        Title title = new Title("Meeting");
        Description description = new Description("Project discussion");
        LocalDateTime startDateTime = LocalDateTime.of(2023, 3, 21, 15, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2023, 3, 21, 16, 0);
        Schedule scheduleToAdd = new Schedule(title, description,
                startDateTime, endDateTime);

        CommandResult expectedCommandResult = new CommandResult("Added schedule: " + scheduleToAdd);
        AddScheduleCommand command = new AddScheduleCommand(scheduleToAdd);

        assertEquals(expectedCommandResult, command.execute(model));
    }

    @Test
    public void equals_sameObject_true() {
        LocalDateTime startDateTime = LocalDateTime.of(2023, 3, 21, 15, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2023, 3, 21, 16, 0);
        Schedule schedule = new Schedule(new Title("Meeting"),
                new Description("Project Discussion"),
                startDateTime,
                endDateTime);
        AddScheduleCommand command = new AddScheduleCommand(schedule);

        assertTrue(command.equals(command), "A command should equal itself.");
    }

    @Test
    public void equals_null_false() {
        LocalDateTime startDateTime = LocalDateTime.of(2023, 3, 21, 15, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2023, 3, 21, 16, 0);
        Schedule schedule = new Schedule(new Title("Meeting"),
                new Description("Project Discussion"),
                startDateTime,
                endDateTime);
        AddScheduleCommand command = new AddScheduleCommand(schedule);

        assertFalse(command.equals(null), "A command should not equal null.");
    }

    @Test
    public void equals_differentType_false() {
        LocalDateTime startDateTime = LocalDateTime.of(2023, 3, 21, 15, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2023, 3, 21, 16, 0);
        Schedule schedule = new Schedule(new Title("Meeting"),
                new Description("Project Discussion"),
                startDateTime,
                endDateTime);
        AddScheduleCommand command = new AddScheduleCommand(schedule);
        Object other = new Object();

        assertFalse(command.equals(other), "A command should not equal an object of a different type.");
    }

    @Test
    public void equals_differentValues_false() {
        LocalDateTime startDateTime = LocalDateTime.of(2023, 3, 21, 15, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2023, 3, 21, 16, 0);
        LocalDateTime endDateTime1 = LocalDateTime.of(2023, 3, 21, 17, 0);
        Schedule schedule1 = new Schedule(new Title("Meeting"),
                new Description("Project Discussion"),
                startDateTime,
                endDateTime);
        Schedule schedule2 = new Schedule(new Title("Meeting"),
                new Description("Project Discussion"),
                startDateTime,
                endDateTime1);
        AddScheduleCommand command1 = new AddScheduleCommand(schedule1);
        AddScheduleCommand command2 = new AddScheduleCommand(schedule2);

        assertFalse(command1.equals(command2), "Commands with different schedules should not be equal.");
    }

    @Test
    public void hashCode_consistency_check() {
        LocalDateTime startDateTime = LocalDateTime.of(2023, 3, 21, 15, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2023, 3, 21, 16, 0);
        Schedule schedule = new Schedule(new Title("Meeting"),
                new Description("Project Discussion"),
                startDateTime,
                endDateTime);
        AddScheduleCommand command = new AddScheduleCommand(schedule);

        int expectedHashCode = schedule.hashCode();
        assertEquals(expectedHashCode, command.hashCode(),
                "Hash code should be consistent and equal to the schedule's hash code.");
    }

    @Test
    public void testConstructor() {
        LocalDateTime startDateTime = LocalDateTime.of(2023, 3, 21, 15, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2023, 3, 21, 16, 0);
        Schedule schedule = new Schedule(new Title("Meeting"),
                new Description("Project Discussion"),
                startDateTime,
                endDateTime);
        assertDoesNotThrow(() -> new AddScheduleCommand(schedule));
        assertThrows(AssertionError.class, () -> new AddScheduleCommand(null));
    }

    @Test
    public void toStringAccurate() {
        LocalDateTime startDateTime = LocalDateTime.of(2023, 3, 21, 15, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2023, 3, 21, 16, 0);
        Schedule schedule1 = new Schedule(new Title("Meeting"),
                new Description("Project Discussion"),
                startDateTime,
                endDateTime);
        AddScheduleCommand command1 = new AddScheduleCommand(schedule1);

        assertTrue(command1.toString().equals("MeetingProject Discussion2023-03-21T15:002023-03-21T16:00"));
    }
}
