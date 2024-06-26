package scm.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import scm.address.commons.core.GuiSettings;
import scm.address.logic.commands.CommandResult;
import scm.address.logic.commands.exceptions.CommandException;
import scm.address.logic.parser.exceptions.ParseException;
import scm.address.model.ReadOnlyAddressBook;
import scm.address.model.person.Person;
import scm.address.model.schedule.Schedule;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see scm.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns the user prefs' contact manager file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /** Returns an unmodifiable view of the list of schedules. **/
    ObservableList<Schedule> getFilteredScheduleList();
}
