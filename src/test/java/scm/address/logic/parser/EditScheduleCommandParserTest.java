package scm.address.logic.parser;

import static scm.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_MEETING;
import static scm.address.logic.commands.CommandTestUtil.END_DATE_TIME_DESC_MEETING;
import static scm.address.logic.commands.CommandTestUtil.START_DATE_TIME_DESC_MEETING;
import static scm.address.logic.commands.CommandTestUtil.TITLE_DESC_MEETING;
import static scm.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_MEETING;
import static scm.address.logic.commands.CommandTestUtil.VALID_END_DATE_TIME_MEETING;
import static scm.address.logic.commands.CommandTestUtil.VALID_START_DATE_TIME_MEETING;
import static scm.address.logic.commands.CommandTestUtil.VALID_TITLE_MEETING;
import static scm.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static scm.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static scm.address.logic.parser.EditScheduleCommandParser.MESSAGE_INVALID_COMMAND_FORMAT;
import static scm.address.logic.parser.EditScheduleCommandParser.MESSAGE_USAGE;

import org.junit.jupiter.api.Test;

import scm.address.commons.core.index.Index;
import scm.address.logic.commands.EditScheduleCommand;
import scm.address.logic.commands.descriptors.EditScheduleDescriptor;
import scm.address.testutil.EditScheduleDescriptorBuilder;

public class EditScheduleCommandParserTest {
    private static final Index VALID_INDEX = Index.fromZeroBased(0);
    private EditScheduleCommandParser parser = new EditScheduleCommandParser();

    @Test
    public void parse_missingParts_failure() {
        assertParseFailure(parser, "1", EditScheduleCommand.MESSAGE_NOT_EDITED);

        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        assertParseFailure(parser, "-5" + VALID_TITLE_MEETING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        assertParseFailure(parser, "1 i/ string", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = VALID_INDEX;
        String userInput = targetIndex.getOneBased() + TITLE_DESC_MEETING + DESCRIPTION_DESC_MEETING
                + START_DATE_TIME_DESC_MEETING
                + END_DATE_TIME_DESC_MEETING;
        System.out.println(userInput);
        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder()
                .withTitle(VALID_TITLE_MEETING)
                .withDescription(VALID_DESCRIPTION_MEETING)
                .withStartDateTime(VALID_START_DATE_TIME_MEETING)
                .withEndDateTime(VALID_END_DATE_TIME_MEETING)
                .build();
        EditScheduleCommand expectedCommand = new EditScheduleCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
