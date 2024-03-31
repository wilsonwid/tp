package scm.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import scm.address.commons.core.index.Index;
import scm.address.logic.Messages;
import scm.address.logic.commands.exceptions.CommandException;
import scm.address.model.Model;
import scm.address.model.schedule.Schedule;

/**
 * Represents the command to delete a schedule.
 */
public class DeleteScheduleCommand extends Command {
    public static final String COMMAND_WORD = "delete_schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the schedule identified by the index number used in the displayed schedule list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_SCHEDULE_SUCCESS = "Deleted schedule: %1$s";

    private final Index targetIndex;

    public DeleteScheduleCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Schedule> lastShownList = model.getFilteredScheduleList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SCHEDULE_DISPLAYED_INDEX);
        }

        Schedule scheduleToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.removeSchedule(scheduleToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_SCHEDULE_SUCCESS, Messages.format(scheduleToDelete)));
    }
}
