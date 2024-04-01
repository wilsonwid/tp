package scm.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static scm.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static scm.address.testutil.TypicalPersons.getTypicalAddressBook;
import static scm.address.testutil.TypicalSchedules.getTypicalScheduleList;

import org.junit.jupiter.api.Test;

import scm.address.commons.core.index.Index;
import scm.address.logic.Messages;
import scm.address.logic.commands.exceptions.CommandException;
import scm.address.model.Model;
import scm.address.model.ModelManager;
import scm.address.model.UserPrefs;
import scm.address.model.schedule.Schedule;

public class DeleteScheduleCommandTest {
    private static final Index VALID_INDEX = Index.fromZeroBased(0);

    private static final Index INVALID_INDEX = Index.fromZeroBased(9999);
    private static final Model model = new ModelManager(getTypicalAddressBook(),
            new UserPrefs(), getTypicalScheduleList());

    @Test
    public void constructor_nullInput_failure() {
        assertThrows(NullPointerException.class, () -> new DeleteScheduleCommand(null));
    }

    @Test
    public void execute_testIndex_success() {
        DeleteScheduleCommand command = new DeleteScheduleCommand(VALID_INDEX);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalScheduleList());
        Schedule scheduleToDelete = expectedModel.getScheduleList().getScheduleList().get(0);
        expectedModel.removeSchedule(scheduleToDelete);
        String expectedMessage = String.format(DeleteScheduleCommand.MESSAGE_DELETE_SCHEDULE_SUCCESS,
                Messages.format(scheduleToDelete));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_outsideBounds_failure() {
        DeleteScheduleCommand command = new DeleteScheduleCommand(INVALID_INDEX);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void equals() {
        DeleteScheduleCommand command = new DeleteScheduleCommand(VALID_INDEX);
        DeleteScheduleCommand otherCommand = new DeleteScheduleCommand(VALID_INDEX);

        assertTrue(command.equals(otherCommand));

        assertTrue(command.equals(command));

        DeleteScheduleCommand invalidCommand = new DeleteScheduleCommand(INVALID_INDEX);

        assertFalse(command.equals(invalidCommand));

        ListScheduleCommand listCommand = new ListScheduleCommand();
        assertFalse(command.equals(listCommand));
    }

    @Test
    public void testToString() {
        DeleteScheduleCommand command = new DeleteScheduleCommand(VALID_INDEX);
        String expectedMessage = DeleteScheduleCommand.class.getCanonicalName()
                + "{targetIndex=" + VALID_INDEX + "}";
        assertEquals(command.toString(), expectedMessage);
    }
}
