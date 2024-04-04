package scm.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static scm.address.logic.Messages.MESSAGE_SCHEDULES_FILTERED_OVERVIEW;
import static scm.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static scm.address.testutil.TypicalPersons.getTypicalAddressBook;
import static scm.address.testutil.TypicalSchedules.DOCTOR_APPOINTMENT;
import static scm.address.testutil.TypicalSchedules.EXERCISE;
import static scm.address.testutil.TypicalSchedules.MEETING;
import static scm.address.testutil.TypicalSchedules.STUDY_SESSION;
import static scm.address.testutil.TypicalSchedules.TEAM_MEETING;
import static scm.address.testutil.TypicalSchedules.getTypicalScheduleList;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import scm.address.model.Model;
import scm.address.model.ModelManager;
import scm.address.model.UserPrefs;
import scm.address.model.schedule.AfterDateTimePredicate;
import scm.address.model.schedule.BeforeDateTimePredicate;
import scm.address.model.schedule.DescriptionContainsKeywordsPredicate;
import scm.address.model.schedule.DuringDateTimePredicate;
import scm.address.model.schedule.TitleContainsKeywordsPredicate;

public class FindScheduleCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalScheduleList());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalScheduleList());

    @Test
    public void equals() {
        TitleContainsKeywordsPredicate firstTitlePredicate =
                new TitleContainsKeywordsPredicate(Collections.singletonList("meeting"));
        TitleContainsKeywordsPredicate secondTitlePredicate =
                new TitleContainsKeywordsPredicate(Collections.singletonList("planning"));
        DescriptionContainsKeywordsPredicate firstDescriptionPredicate =
                new DescriptionContainsKeywordsPredicate(Collections.singletonList("planning"));
        DescriptionContainsKeywordsPredicate secondDescriptionPredicate =
                new DescriptionContainsKeywordsPredicate(Collections.singletonList("meeting"));
        BeforeDateTimePredicate firstBeforePredicate =
                new BeforeDateTimePredicate(Optional.of(LocalDateTime.of(2024, 5, 1, 0, 0)));
        BeforeDateTimePredicate secondBeforePredicate =
                new BeforeDateTimePredicate(Optional.of(LocalDateTime.of(2024, 4, 30, 23, 59)));
        AfterDateTimePredicate firstAfterPredicate =
                new AfterDateTimePredicate(Optional.of(LocalDateTime.of(2024, 4, 1, 0, 0)));
        AfterDateTimePredicate secondAfterPredicate =
                new AfterDateTimePredicate(Optional.of(LocalDateTime.of(2024, 4, 1, 0, 1)));
        DuringDateTimePredicate firstDuringPredicate =
                new DuringDateTimePredicate(Optional.of(LocalDateTime.of(2024, 4, 10, 10, 30)));
        DuringDateTimePredicate secondDuringPredicate =
                new DuringDateTimePredicate(Optional.of(LocalDateTime.of(2024, 4, 15, 19, 45)));

        FindScheduleCommand findFirstCommand = new FindScheduleCommand(firstTitlePredicate,
                firstDescriptionPredicate, firstBeforePredicate, firstAfterPredicate, firstDuringPredicate);
        FindScheduleCommand findSecondCommand = new FindScheduleCommand(secondTitlePredicate,
                secondDescriptionPredicate, secondBeforePredicate, secondAfterPredicate, secondDuringPredicate);

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        FindScheduleCommand findFirstCommandCopy = new FindScheduleCommand(firstTitlePredicate,
                firstDescriptionPredicate, firstBeforePredicate, firstAfterPredicate, firstDuringPredicate);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        assertFalse(findFirstCommand.equals(420691337));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different schedule -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroCriteria_allSchedulesFound() {
        String expectedMessage = String.format(MESSAGE_SCHEDULES_FILTERED_OVERVIEW, 5, "");
        TitleContainsKeywordsPredicate titlePredicate = prepareTitlePredicate(" ");
        DescriptionContainsKeywordsPredicate descriptionPredicate = prepareDescriptionPredicate(" ");
        BeforeDateTimePredicate beforePredicate = prepareBeforePredicate(Optional.empty());
        AfterDateTimePredicate afterPredicate = prepareAfterPredicate(Optional.empty());
        DuringDateTimePredicate duringPredicate = prepareDuringPredicate(Optional.empty());

        FindScheduleCommand command = new FindScheduleCommand(titlePredicate, descriptionPredicate,
                beforePredicate, afterPredicate, duringPredicate);
        expectedModel.updateFilteredScheduleList(titlePredicate.and(descriptionPredicate.and(beforePredicate
                .and(afterPredicate.and(duringPredicate)))));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleCriteria_multipleSchedulesFound() {
        String expectedMessage = String.format(MESSAGE_SCHEDULES_FILTERED_OVERVIEW, 3,
                "\nTitle: Study Meeting\nBefore: 2024-04-01 00:00\nAfter: 2024-03-01 00:00");
        TitleContainsKeywordsPredicate titlePredicate = prepareTitlePredicate("Study Meeting");
        DescriptionContainsKeywordsPredicate descriptionPredicate = prepareDescriptionPredicate(" ");
        BeforeDateTimePredicate beforePredicate =
                prepareBeforePredicate(Optional.of(LocalDateTime.of(2024, 4, 1, 0, 0)));
        AfterDateTimePredicate afterPredicate =
                prepareAfterPredicate(Optional.of(LocalDateTime.of(2024, 3, 1, 0, 0)));
        DuringDateTimePredicate duringPredicate = prepareDuringPredicate(Optional.empty());

        FindScheduleCommand command = new FindScheduleCommand(titlePredicate, descriptionPredicate,
                beforePredicate, afterPredicate, duringPredicate);
        expectedModel.updateFilteredScheduleList(titlePredicate.and(descriptionPredicate.and(beforePredicate
                .and(afterPredicate.and(duringPredicate)))));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(MEETING, STUDY_SESSION, TEAM_MEETING), model.getFilteredScheduleList());

        expectedMessage = String.format(MESSAGE_SCHEDULES_FILTERED_OVERVIEW, 2,
                "\nDescription: with");
        titlePredicate = prepareTitlePredicate(" ");
        descriptionPredicate = prepareDescriptionPredicate("with");
        beforePredicate = prepareBeforePredicate(Optional.empty());
        afterPredicate = prepareAfterPredicate(Optional.empty());

        command = new FindScheduleCommand(titlePredicate, descriptionPredicate,
                beforePredicate, afterPredicate, duringPredicate);
        expectedModel.updateFilteredScheduleList(titlePredicate.and(descriptionPredicate.and(beforePredicate
                .and(afterPredicate.and(duringPredicate)))));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(MEETING, EXERCISE), model.getFilteredScheduleList());

        expectedMessage = String.format(MESSAGE_SCHEDULES_FILTERED_OVERVIEW, 1,
                "\nDuring: 2024-03-12 09:30");
        descriptionPredicate = prepareDescriptionPredicate(" ");
        duringPredicate = prepareDuringPredicate(Optional.of(LocalDateTime.of(2024, 3, 12, 9, 30)));
        command = new FindScheduleCommand(titlePredicate, descriptionPredicate,
                beforePredicate, afterPredicate, duringPredicate);
        expectedModel.updateFilteredScheduleList(titlePredicate.and(descriptionPredicate.and(beforePredicate
                .and(afterPredicate.and(duringPredicate)))));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DOCTOR_APPOINTMENT), model.getFilteredScheduleList());
    }

    @Test
    public void toStringMethod() {
        TitleContainsKeywordsPredicate titlePredicate = new TitleContainsKeywordsPredicate(Arrays.asList("keyword"));
        DescriptionContainsKeywordsPredicate descriptionPredicate = new DescriptionContainsKeywordsPredicate(Arrays
                .asList("keyword"));
        BeforeDateTimePredicate beforePredicate =
                new BeforeDateTimePredicate(Optional.of(LocalDateTime.of(2024, 4, 1, 0, 0)));
        AfterDateTimePredicate afterPredicate =
                new AfterDateTimePredicate(Optional.of(LocalDateTime.of(2024, 4, 11, 1, 1)));
        DuringDateTimePredicate duringPredicate =
                new DuringDateTimePredicate(Optional.of(LocalDateTime.of(2024, 4, 10, 10, 30)));

        FindScheduleCommand findScheduleCommand = new FindScheduleCommand(titlePredicate, descriptionPredicate,
                beforePredicate, afterPredicate, duringPredicate);
        String expected = FindScheduleCommand.class.getCanonicalName() + "{titlePredicate=" + titlePredicate + ", "
                + "descriptionPredicate=" + descriptionPredicate + ", "
                + "beforePredicate=" + beforePredicate + ", "
                + "afterPredicate=" + afterPredicate + ", "
                + "duringPredicate=" + duringPredicate + "}";
        assertEquals(expected, findScheduleCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code TitleContainsKeywordsPredicate}.
     */
    private TitleContainsKeywordsPredicate prepareTitlePredicate(String userInput) {
        return new TitleContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code DescriptionContainsKeywordsPredicate}.
     */
    private DescriptionContainsKeywordsPredicate prepareDescriptionPredicate(String userInput) {
        return new DescriptionContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code BeforeDateTimePredicate}.
     */
    private BeforeDateTimePredicate prepareBeforePredicate(Optional<LocalDateTime> dateTime) {
        return new BeforeDateTimePredicate(dateTime);
    }

    /**
     * Parses {@code userInput} into a {@code AfterDateTimePredicate}.
     */
    private AfterDateTimePredicate prepareAfterPredicate(Optional<LocalDateTime> dateTime) {
        return new AfterDateTimePredicate(dateTime);
    }

    /**
     * Parses {@code userInput} into a {@code DuringDateTimePredicate}.
     */
    private DuringDateTimePredicate prepareDuringPredicate(Optional<LocalDateTime> dateTime) {
        return new DuringDateTimePredicate(dateTime);
    }
}
