package scm.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static scm.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static scm.address.logic.parser.CliSyntax.PREFIX_NAME;
import static scm.address.logic.parser.CliSyntax.PREFIX_TAG;

import scm.address.commons.util.ToStringBuilder;
import scm.address.logic.Messages;
import scm.address.model.Model;
import scm.address.model.person.AddressContainsKeywordsPredicate;
import scm.address.model.person.NameContainsKeywordsPredicate;
import scm.address.model.person.TagsContainKeywordsPredicate;

/**
 * Finds and lists all persons in contact manager whose name, address, and any
 * of its tags contain any of the specified argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose attributes match any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME KEYWORDS] "
            + "[" + PREFIX_ADDRESS + "ADDRESS KEYWORDS] "
            + "[" + PREFIX_TAG + "TAG KEYWORDS]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "alice bob charlie "
            + PREFIX_ADDRESS + "Clementi "
            + PREFIX_TAG + "friends";

    private final NameContainsKeywordsPredicate namePredicate;
    private final AddressContainsKeywordsPredicate addressPredicate;
    private final TagsContainKeywordsPredicate tagsPredicate;

    /**
     * Creates a FindCommand to find all {@code Person} that
     * contains any keyword in its name, address, and any of its tags.
     */
    public FindCommand(NameContainsKeywordsPredicate namePredicate,
                       AddressContainsKeywordsPredicate addressPredicate,
                       TagsContainKeywordsPredicate tagsPredicate) {
        this.namePredicate = namePredicate;
        this.addressPredicate = addressPredicate;
        this.tagsPredicate = tagsPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(namePredicate.and(addressPredicate.and(tagsPredicate)));

        String nameMessage = namePredicate.getKeywords().isEmpty()
                ? "" : "\nName: " + String.join(" ", namePredicate.getKeywords());
        String addressMessage = addressPredicate.getKeywords().isEmpty()
                ? "" : "\nAddress: " + String.join(" ", addressPredicate.getKeywords());
        String tagsMessage = tagsPredicate.getKeywords().isEmpty()
                ? "" : "\nTags: " + String.join(" ", tagsPredicate.getKeywords());
        String filterMessage = nameMessage + addressMessage + tagsMessage;

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_FILTERED_OVERVIEW,
                        model.getFilteredPersonList().size(), filterMessage));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return namePredicate.equals(otherFindCommand.namePredicate)
                && addressPredicate.equals(otherFindCommand.addressPredicate)
                && tagsPredicate.equals(otherFindCommand.tagsPredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("namePredicate", namePredicate)
                .add("addressPredicate", addressPredicate)
                .add("tagsPredicate", tagsPredicate)
                .toString();
    }
}
