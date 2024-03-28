package scm.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        LocalDateTime startDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = startDateTime.plusHours(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String startDateTimeStr = startDateTime.format(formatter);
        String endDateTimeStr = endDateTime.format(formatter);

        String input = String.format("title/Meeting d/Project discussion start/%s end/%s",
                startDateTimeStr,
                endDateTimeStr);

        AddScheduleCommandParser parser = new AddScheduleCommandParser();
        AddScheduleCommand command = parser.parse(input);

        Schedule expectedSchedule = new Schedule(new Title("Meeting"),
                new Description("Project discussion"),
                startDateTime.format(formatter),
                endDateTime.format(formatter));

        AddScheduleCommand expectedCommand = new AddScheduleCommand(expectedSchedule);

        assertFalse(expectedCommand.equals(command));
    }

    @Test
    public void parse_invalidDateTime_failure() {
        String invalidInput = "title/Meeting d/Project discussion start/2023-25-40 15:00 end/2023-03-21 16:00";
        ParseException thrownException = null;

        try {
            parser.parse(invalidInput);
        } catch (ParseException e) {
            thrownException = e;
            assertNotNull(thrownException, "A ParseException should have been thrown.");
            assertEquals("Invalid date time format. Correct format: yyyy-MM-dd HH:mm",
                    thrownException.getMessage());
        }
    }

    @Test
    public void parse_missingParts_failure() {
        String missingPartsInput = "title/Meeting d/Project discussion start/2023-03-21 15:00";
        ParseException thrownException = null;

        try {
            parser.parse(missingPartsInput);
        } catch (ParseException e) {
            thrownException = e;
            assertNotNull(thrownException, "A ParseException should have been thrown.");
            assertEquals("Invalid command format! \n"
                    + AddScheduleCommandParser.MESSAGE_USAGE, thrownException.getMessage());
        }
    }

    @Test
    public void parse_missingTitle_throwsParseException() {
        String expectedMessage = String.format("Invalid command format! \n%1$s",
                AddScheduleCommandParser.MESSAGE_USAGE);
        assertThrows(ParseException.class, () -> parser.parse(
                "d/Description start/2023-03-21 15:00 end/2023-03-21 16:00"), expectedMessage);
    }
}
