package scm.address.logic.commands;

import scm.address.commons.util.ToStringBuilder;
import scm.address.logic.Messages;
import scm.address.model.Model;
import scm.address.model.schedule.AfterDateTimePredicate;
import scm.address.model.schedule.BeforeDateTimePredicate;
import scm.address.model.schedule.DescriptionContainsKeywordsPredicate;
import scm.address.model.schedule.DuringDateTimePredicate;
import scm.address.model.schedule.TitleContainsKeywordsPredicate;

import static java.util.Objects.requireNonNull;

/**
 * A command to find certain schedules in the address book
 * given certain restrictions.
 * <p>
 * This command allows users to find schedules by title, description,
 * whether it starts after a certain date, ends before a certain date,
 * or would be occurring at a certain date.
 */
public class FindScheduleCommand extends Command {

    public static final String COMMAND_WORD = "find_schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all schedules whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final TitleContainsKeywordsPredicate titlePredicate;
    private final DescriptionContainsKeywordsPredicate descriptionPredicate;
    private final BeforeDateTimePredicate beforePredicate;
    private final AfterDateTimePredicate afterPredicate;
    private final DuringDateTimePredicate duringPredicate;

    public FindScheduleCommand(TitleContainsKeywordsPredicate titlePredicate,
                               DescriptionContainsKeywordsPredicate descriptionPredicate,
                               BeforeDateTimePredicate beforePredicate,
                               AfterDateTimePredicate afterPredicate,
                               DuringDateTimePredicate duringPredicate) {
        this.titlePredicate = titlePredicate;
        this.descriptionPredicate = descriptionPredicate;
        this.beforePredicate = beforePredicate;
        this.afterPredicate = afterPredicate;
        this.duringPredicate = duringPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredScheduleList(titlePredicate.and(descriptionPredicate
                .and(beforePredicate.and(afterPredicate.and(duringPredicate)))));

        return new CommandResult(
                String.format(Messages.MESSAGE_SCHEDULES_LISTED_OVERVIEW, model.getFilteredScheduleList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindScheduleCommand)) {
            return false;
        }

        FindScheduleCommand otherFindScheduleCommand = (FindScheduleCommand) other;
        return titlePredicate.equals(otherFindScheduleCommand.titlePredicate)
                && descriptionPredicate.equals(otherFindScheduleCommand.descriptionPredicate)
                && beforePredicate.equals(otherFindScheduleCommand.beforePredicate)
                && afterPredicate.equals(otherFindScheduleCommand.afterPredicate)
                && duringPredicate.equals(otherFindScheduleCommand.duringPredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("titlePredicate", titlePredicate)
                .add("descriptionPredicate", descriptionPredicate)
                .add("beforePredicate", beforePredicate)
                .add("afterPredicate", afterPredicate)
                .add("duringPredicate", duringPredicate)
                .toString();
    }
}
