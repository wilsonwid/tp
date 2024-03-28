package scm.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import scm.address.model.Model;
import scm.address.model.ModelManager;
import scm.address.model.schedule.Description;
import scm.address.model.schedule.Schedule;
import scm.address.model.schedule.Title;

public class AddScheduleCommandTest {

    @Test
    public void execute_scheduleConstructed_success() {
        Title title = new Title("Meeting");
        Description description = new Description("Discuss project");
        LocalDateTime startDateTime = LocalDateTime.parse("2024-03-20T10:00");
        LocalDateTime endDateTime = LocalDateTime.parse("2024-03-20T11:00");
        Model model = new ModelManager();

        Schedule schedule = new Schedule(title, description, startDateTime, endDateTime);
        AddScheduleCommand command = new AddScheduleCommand(schedule);

        CommandResult result = command.execute(model);

        assertTrue(command.scheduleExists());
    }
}
