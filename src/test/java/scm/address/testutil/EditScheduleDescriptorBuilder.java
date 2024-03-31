package scm.address.testutil;

import java.time.LocalDateTime;

import scm.address.logic.commands.descriptors.EditScheduleDescriptor;
import scm.address.model.schedule.Description;
import scm.address.model.schedule.Schedule;
import scm.address.model.schedule.Title;

/**
 * A utility class to help with building EditScheduleDescriptor objects.
 */
public class EditScheduleDescriptorBuilder {
    private EditScheduleDescriptor descriptor;

    public EditScheduleDescriptorBuilder() {
        this.descriptor = new EditScheduleDescriptor();
    }

    public EditScheduleDescriptorBuilder(EditScheduleDescriptor descriptor) {
        this.descriptor = new EditScheduleDescriptor();
    }

    /**
     * Constructs an EditScheduleDescriptorBuilder given the {@code schedule}.
     *
     * @param schedule Source Schedule.
     */
    public EditScheduleDescriptorBuilder(Schedule schedule) {
        this.descriptor = new EditScheduleDescriptor();
        this.descriptor.setTitle(schedule.getTitle());
        this.descriptor.setDescription(schedule.getDescription());
        this.descriptor.setStartDateTime(schedule.getStartDateTime());
        this.descriptor.setEndDateTime(schedule.getEndDateTime());
    }

    /**
     * Sets the {@code Title} of the {@code EditScheduleDescriptor} we are building.
     *
     * @param title Title to be used.
     * @return An {@link EditScheduleDescriptorBuilder}.
     */
    public EditScheduleDescriptorBuilder withTitle(String title) {
        this.descriptor.setTitle(new Title(title));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditScheduleDescriptor} we are building.
     *
     * @param description Description to be used.
     * @return An {@link EditScheduleDescriptorBuilder}.
     */
    public EditScheduleDescriptorBuilder withDescription(String description) {
        this.descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code startDateTime} of the {@code EditScheduleDescriptor} we are building.
     *
     * @param startStringTime  Starting time (in String) to be used.
     * @return An {@link EditScheduleDescriptorBuilder}.
     */
    public EditScheduleDescriptorBuilder withStartDateTime(String startStringTime) {
        this.descriptor.setStartDateTime(LocalDateTime
                .parse(startStringTime, Schedule.DATE_TIME_FORMATTER));
        return this;
    }

    /**
     * Sets the {@code endDateTime} of the {@code EditScheduleDescriptor} we are building.
     *
     * @param endDateTime Ending time (in String) to be used.
     * @return An {@link EditScheduleDescriptorBuilder}.
     */
    public EditScheduleDescriptorBuilder withEndDateTime(String endDateTime) {
        this.descriptor.setEndDateTime(LocalDateTime
                .parse(endDateTime, Schedule.DATE_TIME_FORMATTER));
        return this;
    }

    /**
     * Builds the {@code EditScheduleDescriptor}.
     *
     * @return An {@link EditScheduleDescriptor}.
     */
    public EditScheduleDescriptor build() {
        return this.descriptor;
    }
}
