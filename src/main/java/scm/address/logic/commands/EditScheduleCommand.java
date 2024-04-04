package scm.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static scm.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static scm.address.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static scm.address.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static scm.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static scm.address.model.Model.PREDICATE_SHOW_ALL_SCHEDULES;

import java.time.LocalDateTime;
import java.util.List;

import scm.address.commons.core.index.Index;
import scm.address.commons.util.ToStringBuilder;
import scm.address.logic.Messages;
import scm.address.logic.commands.descriptors.EditScheduleDescriptor;
import scm.address.logic.commands.exceptions.CommandException;
import scm.address.model.Model;
import scm.address.model.schedule.Description;
import scm.address.model.schedule.Schedule;
import scm.address.model.schedule.Title;

/**
 * A command to edit a schedule in the contact manager.
 *
 * This command allows users to edit a schedule with a title,
 * description, start datetime, and end datetime.
 */
public class EditScheduleCommand extends Command {
    public static final String COMMAND_WORD = "edit_schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the specified schedule "
            + "through the index number used in the displayed list of schedules. "
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TITLE + "TITLE]"
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION]"
            + "[" + PREFIX_START_DATETIME + "START_DATETIME]"
            + "[" + PREFIX_END_DATETIME + "END_DATETIME]\n"
            + "Note: All dates and times must be in the format of yyyy-MM-dd HH:mm.\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TITLE + "Meeting "
            + PREFIX_DESCRIPTION + "Discuss project details "
            + PREFIX_START_DATETIME + "2024-03-01 22:00 "
            + PREFIX_END_DATETIME + "2024-03-02 00:00";

    public static final String MESSAGE_EDIT_SCHEDULE_SUCCESS = "Edited schedule: %s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided in the command.";

    private final Index index;
    private final EditScheduleDescriptor editScheduleDescriptor;

    /**
     * Constructs a new EditScheduleCommand instance.
     *
     * @param index Index of the Schedule to be edited.
     * @param editScheduleDescriptor The descriptor to edit with.
     */
    public EditScheduleCommand(Index index, EditScheduleDescriptor editScheduleDescriptor) {
        requireNonNull(index);
        requireNonNull(editScheduleDescriptor);

        this.index = index;
        this.editScheduleDescriptor = editScheduleDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Schedule> scheduleList = model.getScheduleList().getScheduleList();

        if (index.getZeroBased() >= scheduleList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SCHEDULE_DISPLAYED_INDEX);
        }
        Schedule scheduleToEdit = scheduleList.get(this.index.getZeroBased());
        Schedule editedSchedule = createEditedSchedule(scheduleToEdit, this.editScheduleDescriptor);

        model.setSchedule(scheduleToEdit, editedSchedule);
        model.updateFilteredScheduleList(PREDICATE_SHOW_ALL_SCHEDULES);
        return new CommandResult(String.format(MESSAGE_EDIT_SCHEDULE_SUCCESS, Messages.format(editedSchedule)));
    }

    private static Schedule createEditedSchedule(Schedule scheduleToEdit, EditScheduleDescriptor
            editScheduleDescriptor) {
        requireNonNull(scheduleToEdit);
        requireNonNull(editScheduleDescriptor);

        Title updatedTitle = editScheduleDescriptor.getTitle().orElse(scheduleToEdit.getTitle());
        Description updatedDescription = editScheduleDescriptor.getDescription()
                .orElse(scheduleToEdit.getDescription());
        LocalDateTime updatedStartDateTime = editScheduleDescriptor.getStartDateTime()
                .orElse(scheduleToEdit.getStartDateTime());
        LocalDateTime updatedEndDateTime = editScheduleDescriptor.getEndDateTime()
                .orElse(scheduleToEdit.getEndDateTime());

        return new Schedule(updatedTitle, updatedDescription,
                updatedStartDateTime, updatedEndDateTime);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other instanceof EditScheduleCommand) {
            EditScheduleCommand otherCommand = (EditScheduleCommand) other;
            return this.index.equals(otherCommand.index)
                    && this.editScheduleDescriptor.equals(otherCommand.editScheduleDescriptor);
        }

        return false;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", this.index)
                .add("editScheduleDescriptor", this.editScheduleDescriptor)
                .toString();
    }
}
