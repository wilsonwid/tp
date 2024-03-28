package scm.address.logic.parser;

import static scm.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static scm.address.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static scm.address.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static scm.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.time.LocalDateTime;
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

    /**
     * Parses the given {@code String} of arguments in the context of the AddScheduleCommand
     * and returns an AddScheduleCommand object for execution.
     *
     * @param args The input arguments to be parsed.
     * @return The constructed AddScheduleCommand.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    @Override
    public AddScheduleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE,
                        PREFIX_DESCRIPTION, PREFIX_START_DATETIME, PREFIX_END_DATETIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE,
                PREFIX_DESCRIPTION, PREFIX_START_DATETIME, PREFIX_END_DATETIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        LocalDateTime startDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_START_DATETIME).get());
        LocalDateTime endDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_END_DATETIME).get());
        Schedule schedule = new Schedule(title, description, startDateTime, endDateTime);

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
