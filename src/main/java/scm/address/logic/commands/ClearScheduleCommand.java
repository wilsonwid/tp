package scm.address.logic.commands;

import static java.util.Objects.requireNonNull;

import scm.address.model.Model;
import scm.address.model.ScheduleList;

/**
 * Clears the schedule list.
 */
public class ClearScheduleCommand extends Command {
    public static final String COMMAND_WORD = "clear_schedule";
    public static final String MESSAGE_SUCCESS = "Schedule list has been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setScheduleList(new ScheduleList());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
