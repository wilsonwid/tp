package scm.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import scm.address.logic.commands.descriptors.EditScheduleDescriptor;
import scm.address.model.ScheduleList;
import scm.address.model.schedule.Schedule;

/**
 * A utility class containing a list of {@code Schedule} objects to be used in tests.
 */
public class TypicalSchedules {
    public static final Schedule MEETING = new ScheduleBuilder()
            .withTitle("Meeting")
            .withDescription("Meeting with supervisor")
            .withStartDateTime("2024-03-10 16:00")
            .withEndDateTime("2024-03-10 17:00")
            .build();
    public static final Schedule EXERCISE = new ScheduleBuilder()
            .withTitle("Exercise")
            .withDescription("With 2 friends")
            .withStartDateTime("2024-03-10 16:00")
            .withEndDateTime("2024-03-10 18:00")
            .build();

    public static final EditScheduleDescriptor MEETING_DESCRIPTOR
            = new EditScheduleDescriptorBuilder()
            .withTitle("Meeting")
            .withDescription("Meeting with supervisor")
            .withStartDateTime("2024-03-10 16:00")
            .withEndDateTime("2024-03-10 17:00")
            .build();

    public static final EditScheduleDescriptor EXERCISE_DESCRIPTOR
            = new EditScheduleDescriptorBuilder()
            .withTitle("Exercise")
            .withDescription("With 2 friends")
            .withStartDateTime("2024-03-10 16:00")
            .withEndDateTime("2024-03-10 18:00")
            .build();

    private TypicalSchedules() {}

    /**
     * Returns a {@code ScheduleList} with all typical Schedules.
     *
     * @return A ScheduleList.
     */
    public static ScheduleList getTypicalScheduleList() {
        ScheduleList sl = new ScheduleList();
        for (Schedule s : getTypicalSchedules()) {
            sl.addSchedule(s);
        }
        return sl;
    }

    /**
     * Returns a typical list of Schedules.
     *
     * @return A List of Schedules.
     */
    public static List<Schedule> getTypicalSchedules() {
        return new ArrayList<>(Arrays.asList(MEETING, EXERCISE));
    }
}