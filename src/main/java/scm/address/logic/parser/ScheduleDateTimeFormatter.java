package scm.address.logic.parser;

import java.time.format.DateTimeFormatter;

/**
 * Contains the DateTimeFormatter for parsing and formatting of dates and times.
 */
public abstract class ScheduleDateTimeFormatter {
    public static final DateTimeFormatter FORMATTER = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
}
