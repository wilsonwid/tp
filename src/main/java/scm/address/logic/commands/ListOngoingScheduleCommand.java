package scm.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Optional;

import scm.address.model.Model;
import scm.address.model.schedule.DuringDateTimePredicate;

/**
 * Represents the command to list all ongoing schedules.
 */
public class ListOngoingScheduleCommand extends Command {
    public static final String COMMAND_WORD = "list_ongoing_schedule";

    public static final String MESSAGE_SUCCESS = "Listed all ongoing schedules!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredScheduleList(new DuringDateTimePredicate(Optional.of(LocalDateTime.now())));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
