package scm.address.logic.commands;

import static scm.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import scm.address.model.Model;
import scm.address.model.ModelManager;
import scm.address.model.UserPrefs;
import scm.address.model.schedule.Description;
import scm.address.model.schedule.Schedule;
import scm.address.model.schedule.Title;

/**
 * Contains tests for ListScheduleCommand.
 */
public class ListOngoingScheduleCommandTest {

    @Test
    public void execute_listHasOngoingSchedule_showsOngoingSchedule() {
        Model model = new ModelManager();
        Title title = new Title("Meeting");
        Description description = new Description("Project discussion");
        LocalDateTime startDateTime = LocalDateTime.now().minusDays(1);
        LocalDateTime endDateTime = LocalDateTime.now().plusHours(1);
        Schedule scheduleToAdd = new Schedule(title, description,
                startDateTime, endDateTime);
        AddScheduleCommand addCommand = new AddScheduleCommand(scheduleToAdd);
        addCommand.execute(model);
        Model expectedModel = new ModelManager(model.getAddressBook(),
                new UserPrefs(), model.getScheduleList());
        assertCommandSuccess(new ListOngoingScheduleCommand(), model,
                ListOngoingScheduleCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listHasNoOngoingSchedule_showsNothing() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager(model.getAddressBook(),
                new UserPrefs(), model.getScheduleList());
        Title title = new Title("Meeting");
        Description description = new Description("Project discussion");
        LocalDateTime startDateTime = LocalDateTime.now().minusHours(1);
        LocalDateTime endDateTime = LocalDateTime.now().minusMinutes(30);
        Schedule scheduleToAdd = new Schedule(title, description,
                startDateTime, endDateTime);
        AddScheduleCommand addCommand = new AddScheduleCommand(scheduleToAdd);
        addCommand.execute(model);
        assertCommandSuccess(new ListOngoingScheduleCommand(), model,
                ListOngoingScheduleCommand.MESSAGE_SUCCESS, expectedModel);
    }
}

