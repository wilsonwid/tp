package scm.address.logic.parser;

import static scm.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static scm.address.logic.parser.CliSyntax.PREFIX_AFTER_DATETIME;
import static scm.address.logic.parser.CliSyntax.PREFIX_BEFORE_DATETIME;
import static scm.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static scm.address.logic.parser.CliSyntax.PREFIX_DURING_DATETIME;
import static scm.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static scm.address.logic.parser.ScheduleDateTimeFormatter.FORMATTER;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import scm.address.logic.commands.FindScheduleCommand;
import scm.address.logic.parser.exceptions.ParseException;
import scm.address.model.schedule.AfterDateTimePredicate;
import scm.address.model.schedule.BeforeDateTimePredicate;
import scm.address.model.schedule.DescriptionContainsKeywordsPredicate;
import scm.address.model.schedule.DuringDateTimePredicate;
import scm.address.model.schedule.TitleContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindScheduleCommand object
 */
public class FindScheduleCommandParser implements Parser<FindScheduleCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindScheduleCommand
     * and returns a FindScheduleCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FindScheduleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_DESCRIPTION, PREFIX_BEFORE_DATETIME,
                        PREFIX_AFTER_DATETIME, PREFIX_DURING_DATETIME);

        if (!areAnyPrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_DESCRIPTION, PREFIX_BEFORE_DATETIME,
                PREFIX_AFTER_DATETIME, PREFIX_DURING_DATETIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindScheduleCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TITLE, PREFIX_DESCRIPTION, PREFIX_BEFORE_DATETIME,
                PREFIX_AFTER_DATETIME, PREFIX_DURING_DATETIME);

        argMultimap.verifyNotAllValuesEmpty(PREFIX_TITLE, PREFIX_DESCRIPTION, PREFIX_BEFORE_DATETIME,
                PREFIX_AFTER_DATETIME, PREFIX_DURING_DATETIME);

        List<String> titleKeywords = getKeywords(argMultimap, PREFIX_TITLE);
        List<String> descriptionKeywords = getKeywords(argMultimap, PREFIX_DESCRIPTION);
        Optional<LocalDateTime> beforePredicateDateTime = getDateTime(argMultimap, PREFIX_BEFORE_DATETIME);
        Optional<LocalDateTime> afterPredicateDateTime = getDateTime(argMultimap, PREFIX_AFTER_DATETIME);
        Optional<LocalDateTime> duringPredicateDateTime = getDateTime(argMultimap, PREFIX_DURING_DATETIME);

        TitleContainsKeywordsPredicate titlePredicate = new TitleContainsKeywordsPredicate(titleKeywords);
        DescriptionContainsKeywordsPredicate descriptionPredicate =
                new DescriptionContainsKeywordsPredicate(descriptionKeywords);
        BeforeDateTimePredicate beforePredicate = new BeforeDateTimePredicate(beforePredicateDateTime);
        AfterDateTimePredicate afterPredicate = new AfterDateTimePredicate(afterPredicateDateTime);
        DuringDateTimePredicate duringPredicate = new DuringDateTimePredicate(duringPredicateDateTime);

        return new FindScheduleCommand(titlePredicate, descriptionPredicate, beforePredicate, afterPredicate,
                duringPredicate);
    }

    /**
     * Returns true if at least one of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns all keywords between the given prefix and the next prefix (if any)
     * in the given ArgumentMultimap.
     */
    private List<String> getKeywords(ArgumentMultimap argMultimap, Prefix prefix) {
        if (argMultimap.getValue(prefix).isEmpty()) {
            return Collections.emptyList();
        } else {
            return Arrays.asList(argMultimap.getValue(prefix).get().split("\\s+"));
        }
    }

    /**
     * Returns the date and time immediately after the given prefix in the given ArgumentMultimap.
     */
    private Optional<LocalDateTime> getDateTime(ArgumentMultimap argMultimap, Prefix prefix) throws ParseException {
        if (argMultimap.getValue(prefix).isEmpty()) {
            return Optional.empty();
        } else if (getKeywords(argMultimap, prefix).size() != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindScheduleCommand.MESSAGE_USAGE));
        } else {
            String date = getKeywords(argMultimap, prefix).get(0);
            String time = getKeywords(argMultimap, prefix).get(1);

            try {
                return Optional.of(LocalDateTime.parse(date + " " + time, FORMATTER));
            } catch (DateTimeParseException e) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindScheduleCommand.MESSAGE_USAGE));
            }
        }
    }
}
