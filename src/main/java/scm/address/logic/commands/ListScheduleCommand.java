package scm.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static scm.address.logic.Messages.MESSAGE_SCHEDULES_LISTED_OVERVIEW;
import static scm.address.model.Model.PREDICATE_SHOW_ALL_SCHEDULES;

import scm.address.model.Model;

/**
 * Represents the command to list all available schedules.
 */
public class ListScheduleCommand extends Command {
    public static final String COMMAND_WORD = "list_schedule";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredScheduleList(PREDICATE_SHOW_ALL_SCHEDULES);
        return new CommandResult(
                String.format(MESSAGE_SCHEDULES_LISTED_OVERVIEW, model.getFilteredScheduleList().size()));
    }
}
