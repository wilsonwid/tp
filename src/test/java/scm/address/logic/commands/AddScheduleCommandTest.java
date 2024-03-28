package scm.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import scm.address.model.Model;
import scm.address.model.ModelManager;
import scm.address.model.schedule.Description;
import scm.address.model.schedule.Schedule;
import scm.address.model.schedule.Title;

public class AddScheduleCommandTest {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Test
    public void execute_scheduleConstructed_success() {
        Title title = new Title("Meeting");
        Description description = new Description("Discuss project");
        LocalDateTime startDateTime = LocalDateTime.parse("2024-03-20T10:00");
        LocalDateTime endDateTime = LocalDateTime.parse("2024-03-20T11:00");
        Model model = new ModelManager();

        Schedule schedule = new Schedule(title, description,
                startDateTime.format(formatter), endDateTime.format(formatter));
        AddScheduleCommand command = new AddScheduleCommand(schedule);

        CommandResult result = command.execute(model);

        assertTrue(command.scheduleExists());
    }

    @Test
    public void execute_addSchedule_successful() {
        Model model = new ModelManager();
        Title title = new Title("Meeting");
        Description description = new Description("Project discussion");
        LocalDateTime startDateTime = LocalDateTime.of(2023, 3, 21, 15, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2023, 3, 21, 16, 0);
        Schedule scheduleToAdd = new Schedule(title, description,
                startDateTime.format(formatter), endDateTime.format(formatter));

        CommandResult expectedCommandResult = new CommandResult("Added schedule: " + scheduleToAdd);
        AddScheduleCommand command = new AddScheduleCommand(scheduleToAdd);

        assertEquals(expectedCommandResult, command.execute(model));
    }
}
