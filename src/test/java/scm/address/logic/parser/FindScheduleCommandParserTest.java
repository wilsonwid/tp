package scm.address.logic.parser;

import static scm.address.logic.Messages.MESSAGE_ALL_INPUT_VALUES_EMPTY;
import static scm.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static scm.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static scm.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import scm.address.logic.commands.FindScheduleCommand;
import scm.address.model.schedule.AfterDateTimePredicate;
import scm.address.model.schedule.BeforeDateTimePredicate;
import scm.address.model.schedule.DescriptionContainsKeywordsPredicate;
import scm.address.model.schedule.DuringDateTimePredicate;
import scm.address.model.schedule.TitleContainsKeywordsPredicate;



public class FindScheduleCommandParserTest {
    private final FindScheduleCommandParser parser = new FindScheduleCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyValues_throwsParseException() {
        assertParseFailure(parser, " title/ d/ before/ after/", MESSAGE_ALL_INPUT_VALUES_EMPTY);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "before/2024-05-07 00:00 2024-04-30 12:00",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "after/2024-04-19 00:00:09",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "t/meeting d/project meeting",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindScheduleCommand() {
        // no leading and trailing whitespaces
        FindScheduleCommand expectedFindScheduleCommand =
                new FindScheduleCommand(new TitleContainsKeywordsPredicate(Arrays.asList("meeting", "lesson")),
                        new DescriptionContainsKeywordsPredicate(Collections.emptyList()),
                        new BeforeDateTimePredicate(Optional.of(LocalDateTime.of(2024, 5, 7, 0, 0))),
                        new AfterDateTimePredicate(Optional.of(LocalDateTime.of(2024, 4, 30, 12, 0))),
                        new DuringDateTimePredicate(Optional.empty()));
        assertParseSuccess(parser, " title/meeting lesson before/2024-05-07 00:00 after/2024-04-30 12:00",
                expectedFindScheduleCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser,
                " \n title/meeting \t lesson \n before/2024-05-07 \n 00:00 \t after/2024-04-30 \t 12:00",
                expectedFindScheduleCommand);
    }
}
