package scm.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
