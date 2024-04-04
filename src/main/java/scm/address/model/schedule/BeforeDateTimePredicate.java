package scm.address.model.schedule;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Predicate;

import scm.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Schedule}'s {@code endDateTime} is on or before the given date and time.
 */
public class BeforeDateTimePredicate implements Predicate<Schedule> {
    private final Optional<LocalDateTime> dateTime;

    public BeforeDateTimePredicate(Optional<LocalDateTime> dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean test(Schedule schedule) {
        if (dateTime.isEmpty()) {
            return true;
        } else {
            LocalDateTime endDateTime = schedule.getEndDateTime();
            return endDateTime.compareTo(dateTime.get()) <= 0;
        }
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

    public Optional<LocalDateTime> getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("dateTime", dateTime).toString();
    }
}
