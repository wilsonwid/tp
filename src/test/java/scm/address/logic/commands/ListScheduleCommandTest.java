package scm.address.logic.commands;

import static scm.address.logic.Messages.MESSAGE_SCHEDULES_LISTED_OVERVIEW;
import static scm.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static scm.address.testutil.TypicalPersons.getTypicalAddressBook;
import static scm.address.testutil.TypicalSchedules.getTypicalScheduleList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import scm.address.model.Model;
import scm.address.model.ModelManager;
import scm.address.model.UserPrefs;
import scm.address.model.schedule.Schedule;

/**
 * Contains tests for ListScheduleCommand.
 */
public class ListScheduleCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(),
                new UserPrefs(), getTypicalScheduleList());
        expectedModel = new ModelManager(model.getAddressBook(),
                new UserPrefs(), model.getScheduleList());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListScheduleCommand(), model,
                String.format(MESSAGE_SCHEDULES_LISTED_OVERVIEW, 5), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        Schedule schedule = model.getFilteredScheduleList().get(0);
        model.updateFilteredScheduleList((x) -> x.getTitle().equals(schedule.getTitle()));
        assertCommandSuccess(new ListScheduleCommand(), model,
                String.format(MESSAGE_SCHEDULES_LISTED_OVERVIEW, 5), expectedModel);
    }
}
