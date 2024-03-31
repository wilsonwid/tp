package scm.address.testutil;

import scm.address.logic.commands.descriptors.EditScheduleDescriptor;
import scm.address.model.schedule.Description;
import scm.address.model.schedule.Schedule;
import scm.address.model.schedule.Title;

import java.time.LocalDateTime;

public class EditScheduleDescriptorBuilder {
    private EditScheduleDescriptor descriptor;

    public EditScheduleDescriptorBuilder() {
        this.descriptor = new EditScheduleDescriptor();
    }

    public EditScheduleDescriptorBuilder(EditScheduleDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    public EditScheduleDescriptorBuilder(Schedule schedule) {
        this.descriptor = new EditScheduleDescriptor();
        this.descriptor.setTitle(schedule.getTitle());
        this.descriptor.setDescription(schedule.getDescription());
        this.descriptor.setStartDateTime(schedule.getStartDateTime());
        this.descriptor.setEndDateTime(schedule.getEndDateTime());
    }

    public EditScheduleDescriptorBuilder withTitle(String title) {
        this.descriptor.setTitle(new Title(title));
        return this;
    }

    public EditScheduleDescriptorBuilder withDescription(String description) {
        this.descriptor.setDescription(new Description(description));
        return this;
    }

    public EditScheduleDescriptorBuilder withStartDateTime(String startStringTime) {
        this.descriptor.setStartDateTime(LocalDateTime
                .parse(startStringTime, Schedule.DATE_TIME_FORMATTER));
        return this;
    }

    public EditScheduleDescriptorBuilder withEndDateTime(String endDateTime) {
        this.descriptor.setEndDateTime(LocalDateTime
                .parse(endDateTime, Schedule.DATE_TIME_FORMATTER));
        return this;
    }

    public EditScheduleDescriptor build() {
        return this.descriptor;
    }
}
