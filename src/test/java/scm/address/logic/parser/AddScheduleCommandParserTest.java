package scm.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import scm.address.logic.commands.AddScheduleCommand;
import scm.address.logic.parser.exceptions.ParseException;
import scm.address.model.schedule.Description;
import scm.address.model.schedule.Schedule;
import scm.address.model.schedule.Title;

public class AddScheduleCommandParserTest {
    private AddScheduleCommandParser parser = new AddScheduleCommandParser();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Returns true if all the prefixes contain non-empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return java.util.stream.Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    @Test
    public void parse_validArgs_returnsAddScheduleCommand() {
        LocalDateTime startDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = startDateTime.plusHours(1);
        String formattedStart = startDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String formattedEnd = endDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        String input = String.format("title/Meeting d/Discuss project start/%s end/%s",
                formattedStart, formattedEnd);

        AddScheduleCommand expectedCommand = new AddScheduleCommand(
                new Schedule(new Title("Meeting"), new Description("Discuss project"),
                        startDateTime.format(formatter), endDateTime.format(formatter))
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
}
