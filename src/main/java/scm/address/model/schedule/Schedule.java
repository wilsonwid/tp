package scm.address.model.schedule;

import java.util.Objects;

/**
 * Represents a Schedule in the address book.
 * <p>
 * Guarantees: details are present and not null, field values are validated.
 */
public class Schedule {
    private final Title title;
    private final Description description;
    private final String startDateTime;
    private final String endDateTime;

    /**
     * Constructs a {@code Schedule} with the specified title, description, start and end datetime.
     *
     * @param title       The schedule's title.
     * @param description The schedule's description.
     * @param startDateTime The schedule's start datetime.
     * @param endDateTime The schedule's end datetime.
     */
    public Schedule(Title title, Description description, String startDateTime, String endDateTime) {
        assert title != null : "Title cannot be null";
        assert description != null : "Description cannot be null";
        assert startDateTime != null : "Start DateTime cannot be null";
        assert endDateTime != null : "End DateTime cannot be null";

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

    @Override
    public String toString() {
        return String.format("%s at %s", description, startDateTime);
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
                    && startDateTime.equals(otherSchedule.startDateTime)
                    && endDateTime.equals(otherSchedule.endDateTime);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, startDateTime, endDateTime);
    }

}
