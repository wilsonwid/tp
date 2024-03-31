package scm.address.model.schedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

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
        if (title == null || description == null || startDateTime == null || endDateTime == null) {
            throw new NullPointerException("None of the fields should be null.");
        }

        this.title = title;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
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

    @Override
    public String toString() {
        return title.toString() + description.toString() + startDateTime.toString() + endDateTime.toString();
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
