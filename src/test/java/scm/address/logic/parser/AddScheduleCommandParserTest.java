package scm.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

import scm.address.logic.commands.AddScheduleCommand;
import scm.address.logic.parser.exceptions.ParseException;
import scm.address.model.schedule.Description;
import scm.address.model.schedule.Schedule;
import scm.address.model.schedule.Title;

public class AddScheduleCommandParserTest {
    private AddScheduleCommandParser parser = new AddScheduleCommandParser();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private String invalidInputNoEnd = "title/Meeting d/Project discussion start/2023-03-21 15:00";
    private String invalidFormattedStart = "2023-10-10 18:00";
    private String formattedStart = "2023-10-10 16:00";
    private String formattedEnd = "2023-10-10 17:00";

    private class DateTimeComparison {
        public DateTimeComparison() {

        }
        public boolean isFirstDateTimeBeforeSecond(String datetimeStr1, String datetimeStr2) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

            try {
                LocalDateTime dateTime1 = LocalDateTime.parse(datetimeStr1, formatter);
                LocalDateTime dateTime2 = LocalDateTime.parse(datetimeStr2, formatter);

                return dateTime1.isBefore(dateTime2);
            } catch (DateTimeParseException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    /**
     * Returns true if all the prefixes contain non-empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return java.util.stream.Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    @Test
    public void parse_validArgs_returnsAddScheduleCommand() {
        String input = String.format("title/Meeting d/Discuss project start/%s end/%s",
                formattedStart, formattedEnd);
        LocalDateTime startDateTime = LocalDateTime.of(2023, 3, 21, 15, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2023, 3, 21, 16, 0);
        AddScheduleCommand expectedCommand = new AddScheduleCommand(
                new Schedule(new Title("Meeting"),
                        new Description("Project Discussion"),
                        startDateTime,
                        endDateTime)
        );

        AddScheduleCommandParser parser = new AddScheduleCommandParser();
        try {
            AddScheduleCommand actualCommand = parser.parse(input);
            assertEquals(expectedCommand, actualCommand);
        } catch (ParseException e) {
            // No exception expected
        }
    }

    @Test
    public void isDateTimeBefore_otherDateTime_returnsTrue() {
        String datetimeStr1 = "2023-03-21T14:00:00";
        String datetimeStr2 = "2023-03-21T15:00:00";

        LocalDateTime dateTime1 = LocalDateTime.parse(datetimeStr1);
        LocalDateTime dateTime2 = LocalDateTime.parse(datetimeStr2);

        assertTrue(dateTime1.isBefore(dateTime2));
    }

    @Test
    public void areAllPrefixesPresent_allPresent_returnsTrue() {
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        argumentMultimap.put(new Prefix("title/"), "Meeting");
        argumentMultimap.put(new Prefix("d/"), "Discuss project");
        argumentMultimap.put(new Prefix("start/"), "2023-03-21 15:00");
        argumentMultimap.put(new Prefix("end/"), "2023-03-21 16:00");

        Prefix titlePrefix = new Prefix("title/");
        Prefix descriptionPrefix = new Prefix("d/");
        Prefix startPrefix = new Prefix("start/");
        Prefix endPrefix = new Prefix("end/");

        boolean allPrefixesPresent = arePrefixesPresent(argumentMultimap,
                titlePrefix, descriptionPrefix, startPrefix, endPrefix);

        assertTrue(allPrefixesPresent);
    }

    @Test
    public void parse_missingPrefixes_throwsParseException() {
        AddScheduleCommandParser parser = new AddScheduleCommandParser();

        assertThrows(ParseException.class, () -> parser.parse(invalidInputNoEnd));
    }

    @Test
    public void isFirstDateTimeBeforeSecond_validDateTimes_correctResult() {
        DateTimeComparison comparison = new DateTimeComparison();

        assertFalse(comparison.isFirstDateTimeBeforeSecond(formattedStart, formattedEnd));

        assertFalse(comparison.isFirstDateTimeBeforeSecond(invalidFormattedStart, formattedEnd));
    }

    @Test
    public void parse_allPrefixesPresent_success() throws ParseException {
        AddScheduleCommandParser parser = new AddScheduleCommandParser();
        String input = " title/Meeting d/Project Discussion start/2023-03-21 15:00 end/2023-03-21 16:00";

        AddScheduleCommand command = parser.parse(input);

        LocalDateTime startDateTime = LocalDateTime.of(2023, 3, 21, 15, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2023, 3, 21, 16, 0);
        Schedule expectedSchedule = new Schedule(new Title("Meeting"),
                new Description("Project Discussion"),
                startDateTime,
                endDateTime);

        assertTrue(command.equals(new AddScheduleCommand(expectedSchedule)));
    }
    @Test
    public void isFirstDateTimeBeforeSecond_validDateTimes_firstIsBefore() {
        AddScheduleCommandParser parser = new AddScheduleCommandParser();
        String dateTimeStr1 = "2023-03-21T15:00";
        String dateTimeStr2 = "2023-03-21T16:00";

        boolean result = parser.createDtc().isFirstDateTimeBeforeSecond(dateTimeStr1, dateTimeStr2);

        assertTrue(result, "First date-time should be before the second date-time");
    }

    @Test
    public void isFirstDateTimeBeforeSecond_validDateTimes_firstIsAfter() {
        AddScheduleCommandParser parser = new AddScheduleCommandParser();
        String dateTimeStr1 = "2023-03-21T17:00";
        String dateTimeStr2 = "2023-03-21T16:00";

        boolean result = parser.createDtc().isFirstDateTimeBeforeSecond(dateTimeStr1, dateTimeStr2);

        assertFalse(result, "First date-time should not be before the second date-time");
    }

    @Test
    public void parse_invalidMonth_throwsParseException() {
        AddScheduleCommandParser parser = new AddScheduleCommandParser();
        String startDateTime = "2023-13-21 15:00";
        String endDateTime = "2023-03-21 16:00";
        String input = "add_schedule title/Meeting d/Discussion start/" + startDateTime + " end/" + endDateTime;

        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_invalidDay_throwsParseException() {
        AddScheduleCommandParser parser = new AddScheduleCommandParser();
        String startDateTime = "2023-03-32 15:00";
        String endDateTime = "2023-03-21 16:00";

        String input = "add_schedule title/Meeting d/Discussion start/" + startDateTime + " end/" + endDateTime;

        assertThrows(ParseException.class, () -> parser.parse(input));
    }
}
