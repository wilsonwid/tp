package scm.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static scm.address.logic.parser.CliSyntax.PREFIX_AFTER_DATETIME;
import static scm.address.logic.parser.CliSyntax.PREFIX_BEFORE_DATETIME;
import static scm.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static scm.address.logic.parser.CliSyntax.PREFIX_DURING_DATETIME;
import static scm.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static scm.address.logic.parser.ScheduleDateTimeFormatter.FORMATTER;

import scm.address.commons.util.ToStringBuilder;
import scm.address.logic.Messages;
import scm.address.model.Model;
import scm.address.model.schedule.AfterDateTimePredicate;
import scm.address.model.schedule.BeforeDateTimePredicate;
import scm.address.model.schedule.DescriptionContainsKeywordsPredicate;
import scm.address.model.schedule.DuringDateTimePredicate;
import scm.address.model.schedule.TitleContainsKeywordsPredicate;

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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all schedules whose attributes match any of "
            + "the specified keywords (case-insensitive) and date/time constraints, "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: "
            + "[" + PREFIX_TITLE + "TITLE KEYWORDS] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION KEYWORDS] "
            + "[" + PREFIX_BEFORE_DATETIME + "BEFORE DATE TIME] "
            + "[" + PREFIX_AFTER_DATETIME + "AFTER DATE TIME] "
            + "[" + PREFIX_DURING_DATETIME + "DURING DATE TIME]\n"
            + "Note: All dates and times must be in the format of yyyy-MM-dd HH:mm.\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "meeting "
            + PREFIX_DESCRIPTION + "project "
            + PREFIX_BEFORE_DATETIME + "2023-12-31 23:59 "
            + PREFIX_AFTER_DATETIME + "2025-01-01 00:00 "
            + PREFIX_DURING_DATETIME + "2024-06-01 00:00";

    private final TitleContainsKeywordsPredicate titlePredicate;
    private final DescriptionContainsKeywordsPredicate descriptionPredicate;
    private final BeforeDateTimePredicate beforePredicate;
    private final AfterDateTimePredicate afterPredicate;
    private final DuringDateTimePredicate duringPredicate;

    /**
     * Creates a FindScheduleCommand to find all {@code Schedule} that
     * contains any keyword in its title or description, starts after a certain date,
     * ends before a certain date, or would be occurring at a certain date.
     */
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

        String titleMessage = titlePredicate.getKeywords().isEmpty()
                ? "" : "\nTitle: " + String.join(" ", titlePredicate.getKeywords());
        String descriptionMessage = descriptionPredicate.getKeywords().isEmpty()
                ? "" : "\nDescription: " + String.join(" ", descriptionPredicate.getKeywords());
        String beforeMessage = beforePredicate.getDateTime().isEmpty()
                ? "" : "\nBefore: " + beforePredicate.getDateTime().get().format(FORMATTER);
        String afterMessage = afterPredicate.getDateTime().isEmpty()
                ? "" : "\nAfter: " + afterPredicate.getDateTime().get().format(FORMATTER);
        String duringMessage = duringPredicate.getDateTime().isEmpty()
                ? "" : "\nDuring: " + duringPredicate.getDateTime().get().format(FORMATTER);
        String filterMessage = titleMessage + descriptionMessage + beforeMessage + afterMessage + duringMessage;

        return new CommandResult(
                String.format(Messages.MESSAGE_SCHEDULES_FILTERED_OVERVIEW,
                        model.getFilteredScheduleList().size(), filterMessage));
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
