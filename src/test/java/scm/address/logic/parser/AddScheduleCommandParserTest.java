package scm.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import scm.address.logic.commands.AddScheduleCommand;
import scm.address.logic.parser.exceptions.ParseException;
import scm.address.model.schedule.Description;
import scm.address.model.schedule.Schedule;
import scm.address.model.schedule.Title;

public class AddScheduleCommandParserTest {
    private AddScheduleCommandParser parser = new AddScheduleCommandParser();

    @Test
    public void parse_validArgs_returnsAddScheduleCommand() throws ParseException {
        LocalDateTime startDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = startDateTime.plusHours(1);
        Schedule schedule = new Schedule(new Title("Meeting"), new Description("Discuss project"), startDateTime, endDateTime);
        AddScheduleCommand command = parser.parse("t/Meeting d/Discuss project sd/" + startDateTime.toString() + " ed/" + endDateTime.toString());

        assertEquals(new AddScheduleCommand(schedule), command);
    }
}
