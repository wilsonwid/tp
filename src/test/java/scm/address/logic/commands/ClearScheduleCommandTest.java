package scm.address.logic.commands;

import static scm.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static scm.address.testutil.TypicalSchedules.getTypicalScheduleList;

import org.junit.jupiter.api.Test;

import scm.address.model.AddressBook;
import scm.address.model.Model;
import scm.address.model.ModelManager;
import scm.address.model.ScheduleList;
import scm.address.model.UserPrefs;

public class ClearScheduleCommandTest {

    @Test
    public void execute_emptyScheduleList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearScheduleCommand(), model, ClearScheduleCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyScheduleList_success() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs(), getTypicalScheduleList());
        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs(), getTypicalScheduleList());
        expectedModel.setScheduleList(new ScheduleList());

        assertCommandSuccess(new ClearScheduleCommand(), model, ClearScheduleCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
