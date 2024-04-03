package scm.address.logic.parser;

import java.time.format.DateTimeFormatter;

public abstract class ScheduleDateTimeFormatter {
    public static final DateTimeFormatter FORMATTER = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
}
