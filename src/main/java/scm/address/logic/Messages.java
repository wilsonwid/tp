package scm.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import scm.address.logic.parser.Prefix;
import scm.address.model.person.Person;
import scm.address.model.schedule.Schedule;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "All %1$d persons listed!";
    public static final String MESSAGE_PERSONS_FILTERED_OVERVIEW =
                "%1$d persons listed matching the following attributes: %2$s";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_SCHEDULES_LISTED_OVERVIEW = "All %1$d schedules listed!";
    public static final String MESSAGE_SCHEDULES_FILTERED_OVERVIEW =
                "%1$d schedules listed matching the following attributes: %2$s";

    public static final String MESSAGE_INVALID_SCHEDULE_DISPLAYED_INDEX = "The schedule index provided is invalid.";
    public static final String MESSAGE_INVALID_SCHEDULE_DATETIME_RANGE =
                "The end date time must be after the start date time.";
    public static final String MESSAGE_INVALID_SCHEDULE_DATETIME_FORMAT =
                "The date time format is invalid. Please follow the format: yyyy-MM-dd HH:mm";
    public static final String MESSAGE_ALL_INPUT_VALUES_EMPTY = "All input values are empty.";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code Schedule} for display to the user.
     */
    public static String format(Schedule schedule) {
        final StringBuilder builder = new StringBuilder();
        builder.append(schedule.getTitle())
                .append("; Description: ")
                .append(schedule.getDescription())
                .append("; Start Time: ")
                .append(schedule.getStartDateTime().format(Schedule.DATE_TIME_FORMATTER))
                .append("; End Time: ")
                .append(schedule.getEndDateTime().format(Schedule.DATE_TIME_FORMATTER));
        return builder.toString();
    }
}
