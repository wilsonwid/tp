package scm.address.logic.parser;

import static scm.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static scm.address.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static scm.address.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static scm.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.lang.reflect.InvocationTargetException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import scm.address.logic.commands.AddScheduleCommand;
import scm.address.logic.parser.exceptions.ParseException;
import scm.address.model.schedule.Description;
import scm.address.model.schedule.Schedule;
import scm.address.model.schedule.Title;
import scm.address.model.util.DateTimeComparison;

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
    private final DateTimeComparison comparator = new DateTimeComparison();

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
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_TITLE,
                        PREFIX_DESCRIPTION, PREFIX_START_DATETIME, PREFIX_END_DATETIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE,
                PREFIX_DESCRIPTION, PREFIX_START_DATETIME, PREFIX_END_DATETIME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_USAGE));
        }

        Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        String startDateTime = argMultimap.getValue(PREFIX_START_DATETIME).get();
        String startYear = startDateTime.substring(0, 4);
        String startMonth = startDateTime.substring(5, 7);
        String startDay = startDateTime.substring(8, 10);
        String startHour = startDateTime.substring(11, 13);
        String startMinute = startDateTime.substring(14, 16);
        String endDateTime = argMultimap.getValue(PREFIX_END_DATETIME).get();
        String endYear = endDateTime.substring(0, 4);
        String endMonth = endDateTime.substring(5, 7);
        String endDay = endDateTime.substring(8, 10);
        String endHour = endDateTime.substring(11, 13);
        String endMinute = endDateTime.substring(14, 16);
        String start = startYear + "-" + startMonth + "-" + startDay + "T" + startHour + ":" + startMinute;
        String end = endYear + "-" + endMonth + "-" + endDay + "T" + endHour + ":" + endMinute;

        try {
            LocalDateTime dateTime1 = LocalDateTime.of(Integer.parseInt(startYear), Integer.parseInt(startMonth),
                    Integer.parseInt(startDay), Integer.parseInt(startHour), Integer.parseInt(startMinute));
            LocalDateTime dateTime2 = LocalDateTime.of(Integer.parseInt(endYear), Integer.parseInt(endMonth),
                    Integer.parseInt(endDay), Integer.parseInt(endHour), Integer.parseInt(endMinute));
        } catch (DateTimeException e) {
            throw new ParseException("Date or time are out of range.", e);
        }

        if (!comparator.isFirstDateTimeBeforeSecond(start, end)) {
            throw new ParseException("Before date and/or time is after the after date and/or time");
        }

        Schedule schedule = new Schedule(title, description,
                LocalDateTime.of(Integer.parseInt(startYear), Integer.parseInt(startMonth),
                        Integer.parseInt(startDay), Integer.parseInt(startHour), Integer.parseInt(startMinute)),
                LocalDateTime.of(Integer.parseInt(endYear), Integer.parseInt(endMonth),
                        Integer.parseInt(endDay), Integer.parseInt(endHour), Integer.parseInt(endMinute)));

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

    public DateTimeComparison createDtc() {
        return new DateTimeComparison();
    }
}
