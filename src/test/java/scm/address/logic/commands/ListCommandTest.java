package scm.address.logic.commands;

import static scm.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static scm.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static scm.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static scm.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static scm.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import scm.address.model.Model;
import scm.address.model.ModelManager;
import scm.address.model.ScheduleList;
import scm.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new ScheduleList());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new ScheduleList());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model,
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model,
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7), expectedModel);
    }
}
