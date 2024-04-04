package scm.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import scm.address.commons.core.GuiSettings;
import scm.address.model.person.Person;
import scm.address.model.schedule.Schedule;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluates to true. **/
    Predicate<Schedule> PREDICATE_SHOW_ALL_SCHEDULES = x -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' contact manager file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' contact manager file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces contact manager data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the contact manager.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the contact manager.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the contact manager.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the contact manager.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the
     * contact manager.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Replaces schedule list data with the data in {@code scheduleList}.
     */
    void setScheduleList(ReadOnlyScheduleList scheduleList);

    /** Returns an unmodifiable view of available schedules. **/
    ReadOnlyScheduleList getScheduleList();

    /**
     * Adds the given schedule.
     *
     * @param schedule Schedule to be added.
     */
    void addSchedule(Schedule schedule);

    /**
     * Sets the scheduleToEdit to editedSchedule.
     *
     * @param scheduleToEdit Schedule to be edited.
     * @param editedSchedule Schedule that has been edited.
     */
    void setSchedule(Schedule scheduleToEdit, Schedule editedSchedule);

    /**
     * Updates the filter of the filtered schedule list to filter by the
     * given {@code predicate}
     *
     * @param predicate The predicate to be used as a filter.
     */
    void updateFilteredScheduleList(Predicate<Schedule> predicate);

    /**
     * Deletes the given schedule.
     *
     * @param schedule Schedule to be deleted.
     */
    void removeSchedule(Schedule schedule);

    /**
     * Returns an unmodifiable view of the filtered person list
     *
     * @return An ObservableList of Schedules.
     */
    ObservableList<Schedule> getFilteredScheduleList();
}
