package scm.address.model.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for comparing date and time values.
 */
public class DateTimeComparison {
    public DateTimeComparison() {

    }

    /**
     * Checks if the first date and time is before the second date and time.
     *
     * @param datetimeStr1 A {@code String} representing the first date and time.
     * @param datetimeStr2 A {@code String} representing the second date and time.
     * @return {@code true} if the first date and time is before the second date and time, {@code false} otherwise.
     */
    public boolean isFirstDateTimeBeforeSecond(String datetimeStr1, String datetimeStr2) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        try {
            LocalDateTime dateTime1 = LocalDateTime.parse(datetimeStr1, formatter);
            LocalDateTime dateTime2 = LocalDateTime.parse(datetimeStr2, formatter);

            return dateTime1.isBefore(dateTime2);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}
