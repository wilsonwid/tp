package scm.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scm.address.commons.util.ToStringBuilder;
import scm.address.model.schedule.Schedule;

/**
 * Encapsulates the list of schedules.
 */
public class ScheduleList implements Iterable<Schedule>, ReadOnlyScheduleList {
    private final ObservableList<Schedule> schedules = FXCollections.observableArrayList();
    private final ObservableList<Schedule> internalUnmodifiableList = FXCollections
            .unmodifiableObservableList(schedules);

    /**
     * Constructor to create a new ScheduleList.
     */
    public ScheduleList() {}

    /**
     * Constructor to create a new ScheduleList from an existing {@code ReadOnlyScheduleList}.
     *
     * @param toBeCopied ReadOnlyScheduleList to be copied.
     */
    public ScheduleList(ReadOnlyScheduleList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Sets the schedules to {@code schedules}.
     * Requires {@code schedules} to be non-null.
     *
     * @param schedules Schedule to be set to.
     */
    public void setSchedules(List<Schedule> schedules) {
        requireNonNull(schedules);
        this.schedules.setAll(schedules);
    }

    /**
     * Resets the data to new data {@code newData}.
     * Requires {@code newData} to be non-null.
     *
     * @param newData New data to set the schedules to.
     */
    public void resetData(ReadOnlyScheduleList newData) {
        requireNonNull(newData);
        setSchedules(newData.getScheduleList());
    }

    /**
     * Adds {@code schedule} to the list of schedules.
     * Requires {@code schedule} to be non-null.
     *
     * @param schedule Schedule to be added.
     */
    public void addSchedule(Schedule schedule) {
        requireNonNull(schedule);
        this.schedules.add(schedule);
    }

    /**
     * Sets {@code target} to the edited schedule {@code editedSchedule}.
     *
     * @param target The schedule to be edited.
     * @param editedSchedule The edited schedule.
     */
    public void setSchedule(Schedule target, Schedule editedSchedule) {
        requireNonNull(target);
        requireNonNull(editedSchedule);
        int idx = schedules.indexOf(target);
        schedules.set(idx, editedSchedule);
    }

    /**
     * Removes {@code schedule} from the list of schedules.
     *
     * @param schedule Schedule to be removed.
     */
    public void removeSchedule(Schedule schedule) {
        requireNonNull(schedule);
        schedules.remove(schedule);
    }

    /**
     * Returns a String representation of the schedule list.
     *
     * @return A String.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("schedules", this.schedules)
                .toString();
    }

    /**
     * Returns an iterator to the list of schedules.
     *
     * @return An Iterator.
     */
    @Override
    public Iterator<Schedule> iterator() {
        return this.schedules.iterator();
    }

    /**
     * Returns an unmodifiable view of the current list of schedules.
     *
     * @return An ObservableList of Schedules.
     */
    @Override
    public ObservableList<Schedule> getScheduleList() {
        return this.internalUnmodifiableList;
    }

    /**
     * Returns whether this instance is equal to another instance of ScheduleList.
     * Returns true only if the list of schedules are equal.
     *
     * @param other The other Object to be compared to.
     * @return A boolean value.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ScheduleList)) {
            return false;
        }

        ScheduleList otherScheduleList = (ScheduleList) other;
        return this.schedules.equals(otherScheduleList.schedules);
    }

    /**
     * Returns a hashcode of this instance using the hashcode of
     * the list of schedules.
     *
     * @return An integer hashcode.
     */
    @Override
    public int hashCode() {
        return this.schedules.hashCode();
    }
}
