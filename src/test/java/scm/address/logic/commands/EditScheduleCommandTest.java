package scm.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static scm.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static scm.address.testutil.Assert.assertThrows;
import static scm.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import scm.address.commons.core.index.Index;
import scm.address.logic.Messages;
import scm.address.logic.commands.descriptors.EditScheduleDescriptor;
import scm.address.model.AddressBook;
import scm.address.model.Model;
import scm.address.model.ModelManager;
import scm.address.model.ScheduleList;
import scm.address.model.UserPrefs;
import scm.address.model.schedule.Schedule;
import scm.address.testutil.EditScheduleDescriptorBuilder;
import scm.address.testutil.ScheduleBuilder;


public class EditScheduleCommandTest {
    private static final Index VALID_INDEX = Index.fromZeroBased(0);
    private static final EditScheduleDescriptor VALID_DESCRIPTOR = new EditScheduleDescriptor();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new ScheduleList());

    @Test
    public void constructor_nullTest_failure() {
        assertThrows(NullPointerException.class, () -> new EditScheduleCommand(null, VALID_DESCRIPTOR));

        assertThrows(NullPointerException.class, () -> new EditCommand(VALID_INDEX, null));
    }

    @Test
    public void equals_testEquality_success() {
        EditScheduleCommand command = new EditScheduleCommand(VALID_INDEX, VALID_DESCRIPTOR);
        assertTrue(command.equals(command));
    }

    @Test
    public void execute_allFieldsSpecified_success() {
        Schedule editedSchedule = new ScheduleBuilder().build();
        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder(editedSchedule).build();
        EditScheduleCommand command = new EditScheduleCommand(VALID_INDEX, VALID_DESCRIPTOR);

        String expectedMessage = String
                .format(EditScheduleCommand.MESSAGE_EDIT_SCHEDULE_SUCCESS, Messages.format(editedSchedule));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs(), new ScheduleList());
        expectedModel.setSchedule(model.getFilteredScheduleList().get(0), editedSchedule);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }
}
