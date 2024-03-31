package scm.address.logic.parser;

import scm.address.commons.core.index.Index;
import scm.address.logic.commands.EditScheduleCommand;
import scm.address.logic.commands.descriptors.EditScheduleDescriptor;
import scm.address.logic.parser.exceptions.ParseException;

import static scm.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static scm.address.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static scm.address.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static scm.address.logic.parser.CliSyntax.PREFIX_TITLE;

public class EditScheduleCommandParser implements Parser<EditScheduleCommand> {
    public static final String MESSAGE_USAGE = "edit_schedule: Edits a schedule in the schedule list. "
            + "Parameters: INDEX [title/TITLE] [d/DESCRIPTION]\n"
            + "[start/START_DATETIME] [end/END_DATETIME]\n"
            + "Example: edit_schedule 1 title/Meeting";

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";

    @Override
    public EditScheduleCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_TITLE, PREFIX_DESCRIPTION,
                PREFIX_START_DATETIME,  PREFIX_END_DATETIME);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditScheduleCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TITLE, PREFIX_DESCRIPTION, PREFIX_START_DATETIME, PREFIX_END_DATETIME);

        EditScheduleDescriptor descriptor = new EditScheduleDescriptor();

        if (argMultimap.getValue(PREFIX_TITLE).isPresent()) {
            descriptor.setTitle(ParserUtil.parseTitle(
                    argMultimap.getValue(PREFIX_TITLE).get()));
        }

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            descriptor.setTitle(ParserUtil.parseTitle(
                    argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }

        if (argMultimap.getValue(PREFIX_START_DATETIME).isPresent()) {
            descriptor.setTitle(ParserUtil.parseTitle(
                    argMultimap.getValue(PREFIX_START_DATETIME).get()));
        }

        if (argMultimap.getValue(PREFIX_END_DATETIME).isPresent()) {
            descriptor.setTitle(ParserUtil.parseTitle(
                    argMultimap.getValue(PREFIX_END_DATETIME).get()));
        }

        if (!descriptor.isAnyFieldEdited()) {
            throw new ParseException(EditScheduleCommand.MESSAGE_NOT_EDITED);
        }

        return new EditScheduleCommand(index, descriptor);
    }
}
