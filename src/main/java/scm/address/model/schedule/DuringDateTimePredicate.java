package scm.address.model.schedule;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Predicate;

import scm.address.commons.util.ToStringBuilder;

/**
 * Tests that the given date and time lies between the {@code Schedule}'s {@code startDateTime}
 * and {@code endDateTime} inclusive.
 */
public class DuringDateTimePredicate implements Predicate<Schedule> {
    private final Optional<LocalDateTime> dateTime;

    public DuringDateTimePredicate(Optional<LocalDateTime> dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean test(Schedule schedule) {
        if (dateTime.isEmpty()) {
            return true;
        } else {
            LocalDateTime startDateTime = schedule.getStartDateTime();
            LocalDateTime endDateTime = schedule.getEndDateTime();
            return startDateTime.compareTo(dateTime.get()) <= 0 && endDateTime.compareTo(dateTime.get()) >= 0;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DuringDateTimePredicate)) {
            return false;
        }

        DuringDateTimePredicate otherDuringDateTimePredicate = (DuringDateTimePredicate) other;
        return dateTime.equals(otherDuringDateTimePredicate.dateTime);
    }

    public Optional<LocalDateTime> getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("dateTime", dateTime).toString();
    }
}
