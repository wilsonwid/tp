package scm.address.logic.parser;

import static scm.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static scm.address.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static scm.address.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static scm.address.logic.parser.CliSyntax.PREFIX_TITLE;

import scm.address.commons.core.index.Index;
import scm.address.logic.commands.EditScheduleCommand;
import scm.address.logic.commands.descriptors.EditScheduleDescriptor;
import scm.address.logic.parser.exceptions.ParseException;

/**
 * Parser for EditScheduleCommand.
 */
public class EditScheduleCommandParser implements Parser<EditScheduleCommand> {
    public static final String MESSAGE_USAGE = "edit_schedule: Edits a schedule in the schedule list. "
            + "Parameters: INDEX [title/TITLE] [d/DESCRIPTION]\n"
            + "[start/START_DATETIME] [end/END_DATETIME]\n"
            + "Example: edit_schedule 1 title/Meeting";

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";

    /**
     * Parses the given {@code String} of arguments in the context of the EditScheduleCommand
     * and returns an EditScheduleCommand object for execution.
     *
     * @param userInput The user input.
     * @return An EditScheduleCommand.
     * @throws ParseException If the user input does not follow given specifications.
     */
    @Override
    public EditScheduleCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_TITLE, PREFIX_DESCRIPTION,
                PREFIX_START_DATETIME, PREFIX_END_DATETIME);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TITLE, PREFIX_DESCRIPTION,
                PREFIX_START_DATETIME, PREFIX_END_DATETIME);

        EditScheduleDescriptor descriptor = new EditScheduleDescriptor();

        if (argMultimap.getValue(PREFIX_TITLE).isPresent()) {
            descriptor.setTitle(ParserUtil.parseTitle(
                    argMultimap.getValue(PREFIX_TITLE).get()));
        }

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            descriptor.setDescription(ParserUtil.parseDescription(
                    argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }

        if (argMultimap.getValue(PREFIX_START_DATETIME).isPresent()) {
            descriptor.setStartDateTime(ParserUtil.parseDateTime(
                    argMultimap.getValue(PREFIX_START_DATETIME).get()));
        }

        if (argMultimap.getValue(PREFIX_END_DATETIME).isPresent()) {
            descriptor.setEndDateTime(ParserUtil.parseDateTime(
                    argMultimap.getValue(PREFIX_END_DATETIME).get()));
        }

        if (!descriptor.isAnyFieldEdited()) {
            throw new ParseException(EditScheduleCommand.MESSAGE_NOT_EDITED);
        }

        return new EditScheduleCommand(index, descriptor);
    }
}
