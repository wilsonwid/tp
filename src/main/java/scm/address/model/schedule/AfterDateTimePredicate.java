package scm.address.model.schedule;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Predicate;

import scm.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Schedule}'s {@code startDateTime} is on or after the given date and time.
 */
public class AfterDateTimePredicate implements Predicate<Schedule> {
    private final Optional<LocalDateTime> dateTime;

    public AfterDateTimePredicate(Optional<LocalDateTime> dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean test(Schedule schedule) {
        if (dateTime.isEmpty()) {
            return true;
        } else {
            LocalDateTime startDateTime = schedule.getStartDateTime();
            return startDateTime.compareTo(dateTime.get()) >= 0;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AfterDateTimePredicate)) {
            return false;
        }

        AfterDateTimePredicate otherAfterDateTimePredicate = (AfterDateTimePredicate) other;
        return dateTime.equals(otherAfterDateTimePredicate.dateTime);
    }

    public Optional<LocalDateTime> getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("dateTime", dateTime).toString();
    }
}
