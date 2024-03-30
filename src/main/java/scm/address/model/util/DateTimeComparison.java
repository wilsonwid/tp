package scm.address.model.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeComparison {
    public DateTimeComparison() {

    }
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
