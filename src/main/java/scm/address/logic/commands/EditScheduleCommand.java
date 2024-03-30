package scm.address.logic.commands;

import static scm.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static scm.address.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static scm.address.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static scm.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.time.LocalDateTime;
import scm.address.commons.core.index.Index;
import scm.address.commons.util.CollectionUtil;
import scm.address.logic.commands.descriptors.EditScheduleDescriptor;
import scm.address.model.Model;
import scm.address.model.schedule.Description;
import scm.address.model.schedule.Title;

/**
 * A command to edit a schedule in the contact manager.
 *
 * This command allows users to edit a schedule with a title,
 * description, start datetime, and end datetime.
 */
public class EditScheduleCommand {
    public static final String COMMAND_WORD = "edit_schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the specified schedule "
            + "through the index number used in the displayed list of schedules. "
            + "Parameters: INDEX (must be a positive integer) "
            + "[" +

    private final Index index;
    private final EditScheduleDescriptor editScheduleDescriptor;

    public EditScheduleCommand(Index index, EditScheduleDescriptor editScheduleDescriptor) {
        this.index = index;
        this.editScheduleDescriptor = editScheduleDescriptor;
    }

    @Override
    public CommandResult execute(Model model) {

    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other instanceof EditScheduleCommand) {
            EditScheduleCommand otherCommand = (EditScheduleCommand) other;
            return schedule.equals(otherCommand.schedule) &&
        }
    }


}
