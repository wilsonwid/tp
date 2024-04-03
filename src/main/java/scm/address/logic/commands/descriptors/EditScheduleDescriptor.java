package scm.address.logic.commands.descriptors;

import java.time.LocalDateTime;
import java.util.Optional;

import scm.address.commons.util.CollectionUtil;
import scm.address.commons.util.ToStringBuilder;
import scm.address.model.schedule.Description;
import scm.address.model.schedule.Title;

/**
 * Descriptor for EditScheduleCommand.
 */
public class EditScheduleDescriptor {
    private Title title;
    private Description description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    /**
     * Constructs a default EditScheduleDescriptor.
     */
    public EditScheduleDescriptor() {}

    /**
     * Constructs a new EditScheduleDescriptor.
     *
     * @param toCopy The EditScheduleDescriptor to copy from.
     */
    public EditScheduleDescriptor(EditScheduleDescriptor toCopy) {
        setTitle(toCopy.title);
        setDescription(toCopy.description);
        setStartDateTime(toCopy.startDateTime);
        setEndDateTime(toCopy.endDateTime);
    }

    /**
     * Returns true if at least one field is edited.
     *
     * @return A boolean value.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(title, description, startDateTime, endDateTime);
    }

    /**
     * Sets a new title for the EditScheduleDescriptor.
     *
     * @param title New title to be set.
     */
    public void setTitle(Title title) {
        this.title = title;
    }

    /**
     * Returns the current title of the EditScheduleDescriptor.
     *
     * @return The current title.
     */
    public Optional<Title> getTitle() {
        return Optional.ofNullable(this.title);
    }

    /**
     * Sets a new description for the EditScheduleDescriptor.
     *
     * @param description New title to be set.
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * Returns the current title of the EditScheduleDescriptor.
     *
     * @return The current description.
     */
    public Optional<Description> getDescription() {
        return Optional.ofNullable(this.description);
    }

    /**
     * Sets a new startDateTime for the EditScheduleDescriptor.
     *
     * @param startDateTime New startDateTime to be set.
     */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * Returns the current startDateTime of the EditScheduleDescriptor.
     *
     * @return The current startDateTime.
     */
    public Optional<LocalDateTime> getStartDateTime() {
        return Optional.ofNullable(this.startDateTime);
    }

    /**
     * Sets a new endDateTime for the EditScheduleDescriptor.
     *
     * @param endDateTime New endDateTime to be set.
     */
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * Returns the current endDateTime of the EditScheduleDescriptor.
     *
     * @return The current endDateTime.
     */
    public Optional<LocalDateTime> getEndDateTime() {
        return Optional.ofNullable(this.endDateTime);
    }

    /**
     * Returns whether this instance is equal to another Object.
     * Returns true only if all attributes return true or if this instance
     * is the same as the other instance.
     *
     * @param other The other Object to be compared against.
     * @return A boolean value.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other instanceof EditScheduleDescriptor) {
            EditScheduleDescriptor otherDescriptor = (EditScheduleDescriptor) other;

            return this.title.equals(otherDescriptor.title)
                    && this.description.equals(otherDescriptor.description)
                    && this.startDateTime.equals(otherDescriptor.startDateTime)
                    && this.endDateTime.equals(otherDescriptor.endDateTime);
        }

        return false;
    }

    /**
     * Returns the String representation of this instance.
     *
     * @return A String representation.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("title", this.title)
                .add("description", this.description)
                .add("startDateTime", this.startDateTime)
                .add("endDateTime", this.endDateTime)
                .toString();
    }
}
