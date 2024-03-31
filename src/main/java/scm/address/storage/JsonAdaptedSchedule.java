package scm.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import scm.address.commons.exceptions.IllegalValueException;
import scm.address.model.schedule.Description;
import scm.address.model.schedule.Schedule;
import scm.address.model.schedule.Title;

/**
 * Jackson-friendly version of {@link Schedule}.
 */
public class JsonAdaptedSchedule {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Field %s is missing in the schedule!";
    public static final String START_TIME_FIELD_NAME = "startDateTime";
    public static final String END_TIME_FIELD_NAME = "endDateTime";


    private final String title;
    private final String description;
    private final String startDateTime;
    private final String endDateTime;

    /**
     * Constructs a {@code JsonAdaptedSchedule} with the given details.
     *
     * @param title Title of the Schedule.
     * @param description Description of the Schedule.
     * @param startDateTime Starting datetime of the Schedule.
     * @param endDateTime Ending datetime of the Schedule.
     */
    @JsonCreator
    public JsonAdaptedSchedule(@JsonProperty("title") String title, @JsonProperty("description") String description,
            @JsonProperty("startDateTime") String startDateTime,
            @JsonProperty("endDateTime") String endDateTime) {
        this.title = title;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * Converts {@code schedule} into this class for Jackson use.
     *
     * @param schedule Schedule to be converted.
     */
    public JsonAdaptedSchedule(Schedule schedule) {
        this.title = schedule.getTitle().toString();
        this.description = schedule.getDescription().toString();
        this.startDateTime = schedule.getStartDateTime().format(Schedule.DATE_TIME_FORMATTER);
        this.endDateTime = schedule.getEndDateTime().format(Schedule.DATE_TIME_FORMATTER);
    }

    /**
     * Converts this Jackson-friendly adapted schedule object into the model's
     * {@code Schedule} object.
     *
     * @return A Schedule.
     * @throws IllegalValueException If there are any data constraints violated in the adapted schedule.
     */
    public Schedule toModelType() throws IllegalValueException {
        if (this.title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(this.title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }
        final Title modelTitle = new Title(this.title);

        if (this.description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(this.description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(this.description);

        if (this.startDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, START_TIME_FIELD_NAME));
        }
        try {
            LocalDateTime.parse(this.startDateTime, Schedule.DATE_TIME_FORMATTER);
        } catch (DateTimeParseException dtpe) {
            throw new IllegalValueException(Schedule.DATE_TIME_CONSTRAINTS);
        }
        final LocalDateTime modelStartDateTime = LocalDateTime.parse(this.startDateTime,
                Schedule.DATE_TIME_FORMATTER);

        if (this.endDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    END_TIME_FIELD_NAME));
        }
        try {
            LocalDateTime.parse(this.endDateTime, Schedule.DATE_TIME_FORMATTER);
        } catch (DateTimeParseException dtpe) {
            throw new IllegalValueException(Schedule.DATE_TIME_CONSTRAINTS);
        }
        final LocalDateTime modelEndDateTime = LocalDateTime.parse(this.endDateTime,
                Schedule.DATE_TIME_FORMATTER);
        return new Schedule(modelTitle, modelDescription, modelStartDateTime,
                modelEndDateTime);
    }
}
