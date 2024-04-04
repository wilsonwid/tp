package scm.address.model.schedule;

import static scm.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import scm.address.commons.util.ToStringBuilder;

/**
 * Represents a Schedule in the address book.
 * <p>
 * Guarantees: details are present and not null, field values are validated.
 */
public class Schedule {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final String DATE_TIME_CONSTRAINTS = "Datetimes should be in yyyy-MM-dd HH:mm format.";

    private final Title title;
    private final Description description;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;

    /**
     * Constructs a {@code Schedule} with the specified title, description, start and end datetime.
     *
     * @param title       The schedule's title.
     * @param description The schedule's description.
     * @param startDateTime The schedule's start datetime.
     * @param endDateTime The schedule's end datetime.
     */
    public Schedule(Title title, Description description, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        requireAllNonNull(title, description, startDateTime, endDateTime);

        this.title = title;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * Constructs a {@code Schedule} with the specified title, description, start, and end datetime.
     *
     * @param title Title to be used.
     * @param description Description to be used.
     * @param startStringTime {@code startDateTime} in String to be used.
     * @param endStringTime {@code endDateTime} in String to be used.
     */
    public Schedule(Title title, Description description, String startStringTime, String endStringTime) {
        requireAllNonNull(title, description, startStringTime, endStringTime);

        this.title = title;
        this.description = description;
        this.startDateTime = LocalDateTime.parse(startStringTime, DATE_TIME_FORMATTER);
        this.endDateTime = LocalDateTime.parse(endStringTime, DATE_TIME_FORMATTER);
    }

    public Description getDescription() {
        return description;
    }

    public Title getTitle() {
        return title;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * Expresses the schedule's components in string form.
     */
    public String toStringCalendar() {
        return title.toString();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("title", this.title)
                .add("description", this.description)
                .add("startDateTime", this.startDateTime.format(DATE_TIME_FORMATTER))
                .add("endDateTime", this.endDateTime.format(DATE_TIME_FORMATTER))
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Schedule) {
            Schedule otherSchedule = (Schedule) other;
            return title.equals(otherSchedule.title)
                    && description.equals(otherSchedule.description)
                    && startDateTime
                    .format(DATE_TIME_FORMATTER)
                    .equals(otherSchedule.startDateTime.format(DATE_TIME_FORMATTER))
                    && endDateTime
                    .format(DATE_TIME_FORMATTER)
                    .equals(otherSchedule.endDateTime.format(DATE_TIME_FORMATTER));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, startDateTime, endDateTime);
    }

}
