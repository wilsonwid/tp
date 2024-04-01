package scm.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static scm.address.model.Model.PREDICATE_SHOW_ALL_SCHEDULES;

import scm.address.model.Model;

/**
 * Represents the command to list all available schedules.
 */
public class ListScheduleCommand extends Command {
    public static final String COMMAND_WORD = "list_schedule";

    public static final String MESSAGE_SUCCESS = "Listed all schedules!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredScheduleList(PREDICATE_SHOW_ALL_SCHEDULES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
