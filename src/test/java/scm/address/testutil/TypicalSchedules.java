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
            .withStartDateTime("2024-03-11 20:00")
            .withEndDateTime("2024-03-11 21:00")
            .build();

    public static final Schedule DOCTOR_APPOINTMENT = new ScheduleBuilder()
            .withTitle("Doctor Appointment")
            .withDescription("Routine checkup")
            .withStartDateTime("2024-03-12 09:00")
            .withEndDateTime("2024-03-12 10:00")
            .build();

    public static final Schedule STUDY_SESSION = new ScheduleBuilder()
            .withTitle("Study Session")
            .withDescription("Preparing for exams")
            .withStartDateTime("2024-03-13 14:00")
            .withEndDateTime("2024-03-13 16:00")
            .build();

    public static final Schedule TEAM_MEETING = new ScheduleBuilder()
            .withTitle("Team Meeting")
            .withDescription("Discuss project progress")
            .withStartDateTime("2024-03-14 10:00")
            .withEndDateTime("2024-03-14 11:00")
            .build();

    // Add more schedules as needed

    public static final EditScheduleDescriptor MEETING_DESCRIPTOR =
            new EditScheduleDescriptorBuilder()
            .withTitle("Meeting")
            .withDescription("Meeting with supervisor")
            .withStartDateTime("2024-03-10 16:00")
            .withEndDateTime("2024-03-10 17:00")
            .build();

    public static final EditScheduleDescriptor EXERCISE_DESCRIPTOR =
            new EditScheduleDescriptorBuilder()
            .withTitle("Exercise")
            .withDescription("With 2 friends")
            .withStartDateTime("2024-03-10 16:00")
            .withEndDateTime("2024-03-10 18:00")
            .build();

    public static final EditScheduleDescriptor DOCTOR_APPOINTMENT_DESCRIPTOR =
            new EditScheduleDescriptorBuilder()
            .withTitle("Doctor Appointment")
            .withDescription("Routine checkup")
            .withStartDateTime("2024-03-12 09:00")
            .withEndDateTime("2024-03-12 10:00")
            .build();

    public static final EditScheduleDescriptor STUDY_SESSION_DESCRIPTOR =
            new EditScheduleDescriptorBuilder()
            .withTitle("Study Session")
            .withDescription("Preparing for exams")
            .withStartDateTime("2024-03-13 14:00")
            .withEndDateTime("2024-03-13 16:00")
            .build();

    public static final EditScheduleDescriptor TEAM_MEETING_DESCRIPTOR =
            new EditScheduleDescriptorBuilder()
            .withTitle("Team Meeting")
            .withDescription("Discuss project progress")
            .withStartDateTime("2024-03-14 10:00")
            .withEndDateTime("2024-03-14 11:00")
            .build();

    // Add more descriptors as needed

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
        return new ArrayList<>(Arrays.asList(MEETING, EXERCISE, DOCTOR_APPOINTMENT, STUDY_SESSION, TEAM_MEETING));
    }
}
