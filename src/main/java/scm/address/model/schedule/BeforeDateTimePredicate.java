package scm.address.model.schedule;

import scm.address.commons.util.ToStringBuilder;

import java.time.LocalDateTime;
import java.util.function.Predicate;

/**
 * Tests that a {@code Schedule}'s {@code endDateTime} is on or before the given date and time.
 */
public class BeforeDateTimePredicate implements Predicate<Schedule> {
    private final LocalDateTime dateTime;

    public BeforeDateTimePredicate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean test(Schedule schedule) {
        LocalDateTime endDateTime = schedule.getEndDateTime();
        return endDateTime.compareTo(dateTime) <= 0;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BeforeDateTimePredicate)) {
            return false;
        }

        BeforeDateTimePredicate otherBeforeDateTimePredicate = (BeforeDateTimePredicate) other;
        return dateTime.equals(otherBeforeDateTimePredicate.dateTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("dateTime", dateTime).toString();
    }
}
