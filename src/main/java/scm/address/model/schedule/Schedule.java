package scm.address.model.schedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Schedule {
    private final Title title;
    private final Description description;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;

    public Schedule(Title title, Description description, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        assert title != null : "Title cannot be null";
        assert description != null : "Description cannot be null";
        assert startDateTime != null : "Start DateTime cannot be null";
        assert endDateTime != null : "End DateTime cannot be null";
        assert startDateTime.isBefore(endDateTime) : "Start DateTime must be before End DateTime";

        this.title = title;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("%s at %s", description, startDateTime.format(formatter));
    }
}
