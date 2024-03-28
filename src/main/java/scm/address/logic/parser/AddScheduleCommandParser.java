package scm.address.logic.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import scm.address.logic.commands.AddScheduleCommand;
import scm.address.logic.parser.exceptions.ParseException;
import scm.address.model.schedule.Description;
import scm.address.model.schedule.Schedule;
import scm.address.model.schedule.Title;

/**
 * Parser for {@code AddScheduleCommand}.
 * <p>
 * Parses input arguments and creates a new AddScheduleCommand object.
 */
public class AddScheduleCommandParser implements Parser<AddScheduleCommand> {

    public static final String MESSAGE_USAGE = "add_schedule: Adds a schedule to the address book. "
            + "Parameters: title/TITLE d/DESCRIPTION start/START_DATETIME end/END_DATETIME\n"
            + "Example: add_schedule title/Meeting d/Project discussion start/2023-03-21 15:00 end/2023-03-21 16:00";

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Parses the given {@code String} of arguments in the context of the AddScheduleCommand
     * and returns an AddScheduleCommand object for execution.
     *
     * @param userInput The input arguments to be parsed.
     * @return The constructed AddScheduleCommand.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    @Override
    public AddScheduleCommand parse(String userInput) throws ParseException {
        String[] parts = userInput.trim().split("\\s+", 4); // Assuming format: date time description
        if (parts.length < 4) {
            throw new ParseException("Invalid number of arguments. Expected format: yyyy-MM-dd HH:mm description");
        }

        String title = parts[0];
        String description = parts[1];
        String datetime = parts[2] + " " + parts[3];

        Schedule schedule = new Schedule(new Title(title), new Description(description), parts[2], parts[3]);
        return new AddScheduleCommand(schedule);
    }

    /**
     * Checks if all the given prefixes contain non-empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     *
     * @param argumentMultimap The map of arguments to check.
     * @param prefixes         The prefixes to check for presence.
     * @return True if all prefixes are present, otherwise false.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes)
                .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
