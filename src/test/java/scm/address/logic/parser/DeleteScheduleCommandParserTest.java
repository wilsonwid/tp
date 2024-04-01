package scm.address.logic.parser;

import static scm.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static scm.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static scm.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import scm.address.commons.core.index.Index;
import scm.address.logic.commands.DeleteScheduleCommand;

/**
 * Testing class for DeleteScheduleCommandParser.
 */
public class DeleteScheduleCommandParserTest {
    private static final Index INDEX_FIRST_SCHEDULE = Index.fromZeroBased(0);
    private DeleteScheduleCommandParser parser = new DeleteScheduleCommandParser();

    @Test
    public void parse_validArgs_returnsCommand() {
        assertParseSuccess(parser, "1", new DeleteScheduleCommand(INDEX_FIRST_SCHEDULE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteScheduleCommand.MESSAGE_USAGE));
    }
}
