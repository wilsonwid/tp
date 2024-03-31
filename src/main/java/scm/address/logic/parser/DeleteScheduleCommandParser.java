package scm.address.logic.parser;

import static scm.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import scm.address.commons.core.index.Index;
import scm.address.logic.commands.DeleteScheduleCommand;
import scm.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteScheduleCommand object.
 */
public class DeleteScheduleCommandParser implements Parser<DeleteScheduleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteScheduleCommand
     * and returns a DeleteScheduleCommand object for execution.
     *
     * @param args Arguments to be parsed.
     * @return A DeleteScheduleCommand.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public DeleteScheduleCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteScheduleCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            DeleteScheduleCommand.MESSAGE_USAGE), pe);
        }
    }
}
