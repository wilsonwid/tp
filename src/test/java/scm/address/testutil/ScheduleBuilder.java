package scm.address.testutil;

import java.time.LocalDateTime;

import scm.address.model.schedule.Description;
import scm.address.model.schedule.Schedule;
import scm.address.model.schedule.Title;

/**
 * A utility class to help with building Schedule objects.
 */
public class ScheduleBuilder {
    /** Default title String. **/
    public static final String DEFAULT_TITLE = "Studying";

    /** Default description String. **/
    public static final String DEFAULT_DESCRIPTION = "Something";

    /** Default startDateTIme String. **/
    public static final String DEFAULT_START_STRING_TIME = "2024-03-15 16:00";

    /** Default endDateTime String. **/
    public static final String DEFAULT_END_STRING_TIME = "2024-03-17 18:00";

    private Title title;
    private Description description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    /**
     * Constructs a {@link ScheduleBuilder} with default details.
     */
    public ScheduleBuilder() {
        this.title = new Title(DEFAULT_TITLE);
        this.description = new Description(DEFAULT_DESCRIPTION);
        this.startDateTime = LocalDateTime.parse(DEFAULT_START_STRING_TIME, Schedule.DATE_TIME_FORMATTER);
        this.endDateTime = LocalDateTime.parse(DEFAULT_END_STRING_TIME, Schedule.DATE_TIME_FORMATTER);
    }

    /**
     * Constructs a new {@link ScheduleBuilder} from a Schedule.
     *
     * @param schedule Schedule to be copied.
     */
    public ScheduleBuilder(Schedule schedule) {
        this.title = new Title(schedule.getTitle().toString());
        this.description = new Description(schedule.getDescription().toString());
        this.startDateTime = LocalDateTime.parse(schedule
                        .getStartDateTime()
                        .format(Schedule.DATE_TIME_FORMATTER),
                Schedule.DATE_TIME_FORMATTER);
        this.endDateTime = LocalDateTime.parse(schedule
                        .getEndDateTime()
                        .format(Schedule.DATE_TIME_FORMATTER),
                Schedule.DATE_TIME_FORMATTER);
    }

    /**
     * Sets the {@code Title} of the {@code Schedule} that we are building.
     *
     * @param title Title to be changed to.
     * @return A modified ScheduleBuilder.
     */
    public ScheduleBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Schedule} that we are building.
     *
     * @param description Description to be changed to.
     * @return A modified ScheduleBuilder.
     */
    public ScheduleBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code startDateTime} of the {@code Schedule} we are building.
     *
     * @param startStringTime startDateTime in String we want to use.
     * @return A modified ScheduleBuilder.
     */
    public ScheduleBuilder withStartDateTime(String startStringTime) {
        this.startDateTime = LocalDateTime.parse(startStringTime, Schedule.DATE_TIME_FORMATTER);
        return this;
    }

    /**
     * Sets the {@code endDateTime} of the {@code Schedule} we are building.
     *
     * @param endStringTime endDateTime in String we want to use.
     * @return A modfiied ScheduleBuilder.
     */
    public ScheduleBuilder withEndDateTime(String endStringTime) {
        this.endDateTime = LocalDateTime.parse(endStringTime, Schedule.DATE_TIME_FORMATTER);
        return this;
    }

    /**
     * Builds the {@link ScheduleBuilder} into a concrete {@link Schedule}.
     *
     * @return A Schedule.
     */
    public Schedule build() {
        return new Schedule(this.title, this.description,
                this.startDateTime, this.endDateTime);
    }
}
