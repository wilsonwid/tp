package scm.address.model.schedule;

import java.util.List;
import java.util.function.Predicate;

import scm.address.commons.util.StringUtil;
import scm.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Schedule}'s {@code Title} matches any of the keywords given.
 */
public class TitleContainsKeywordsPredicate implements Predicate<Schedule> {
    private final List<String> keywords;

    public TitleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Schedule schedule) {
        if (keywords.isEmpty()) {
            return true;
        }

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(schedule.getTitle().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TitleContainsKeywordsPredicate)) {
            return false;
        }

        TitleContainsKeywordsPredicate otherTitleContainsKeywordsPredicate = (TitleContainsKeywordsPredicate) other;
        return keywords.equals(otherTitleContainsKeywordsPredicate.keywords);
    }

    public List<String> getKeywords() {
        return keywords;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

