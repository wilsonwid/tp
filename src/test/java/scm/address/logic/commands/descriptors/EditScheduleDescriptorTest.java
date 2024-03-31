package scm.address.logic.commands.descriptors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static scm.address.testutil.TypicalSchedules.EXERCISE_DESCRIPTOR;
import static scm.address.testutil.TypicalSchedules.MEETING_DESCRIPTOR;

import org.junit.jupiter.api.Test;

import scm.address.model.schedule.Schedule;
import scm.address.testutil.EditScheduleDescriptorBuilder;


public class EditScheduleDescriptorTest {

    @Test
    public void equals() {
        EditScheduleDescriptor descriptorWithSameValues = new EditScheduleDescriptor(MEETING_DESCRIPTOR);
        assertTrue(MEETING_DESCRIPTOR.equals(descriptorWithSameValues));

        assertTrue(MEETING_DESCRIPTOR.equals(MEETING_DESCRIPTOR));

        assertFalse(MEETING_DESCRIPTOR.equals(null));

        assertFalse(MEETING_DESCRIPTOR.equals(5));

        assertFalse(MEETING_DESCRIPTOR.equals(EXERCISE_DESCRIPTOR));

        EditScheduleDescriptor editedDescriptor = new EditScheduleDescriptorBuilder(MEETING_DESCRIPTOR)
                .withTitle(EXERCISE_DESCRIPTOR
                        .getTitle()
                        .get()
                        .toString())
                .build();

        assertFalse(MEETING_DESCRIPTOR.equals(editedDescriptor));

        editedDescriptor = new EditScheduleDescriptorBuilder(MEETING_DESCRIPTOR)
                .withDescription(EXERCISE_DESCRIPTOR
                        .getDescription()
                        .get()
                        .toString())
                .build();

        assertFalse(MEETING_DESCRIPTOR.equals(editedDescriptor));

        editedDescriptor = new EditScheduleDescriptorBuilder(MEETING_DESCRIPTOR)
                .withStartDateTime(EXERCISE_DESCRIPTOR.getStartDateTime()
                        .get()
                        .format(Schedule.DATE_TIME_FORMATTER))
                .build();

        assertFalse(MEETING_DESCRIPTOR.equals(editedDescriptor));

        editedDescriptor = new EditScheduleDescriptorBuilder(MEETING_DESCRIPTOR)
                .withEndDateTime(EXERCISE_DESCRIPTOR.getEndDateTime()
                        .get()
                        .format(Schedule.DATE_TIME_FORMATTER))
                .build();

        assertFalse(MEETING_DESCRIPTOR.equals(editedDescriptor));
    }

    @Test
    public void toStringMethod() {
        EditScheduleDescriptor descriptor = new EditScheduleDescriptor(MEETING_DESCRIPTOR);
        String expected = EditScheduleDescriptor.class.getCanonicalName() + "{title="
                + descriptor.getTitle().orElse(null) + ", description="
                + descriptor.getDescription().orElse(null) + ", startDateTime="
                + descriptor.getStartDateTime().orElse(null) + ", endDateTime="
                + descriptor.getEndDateTime().orElse(null) + "}";
        assertEquals(expected, descriptor.toString());
    }
}
