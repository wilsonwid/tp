package scm.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scm.address.commons.util.ToStringBuilder;
import scm.address.model.schedule.Schedule;

public class ScheduleList implements Iterable<Schedule>, ReadOnlyScheduleList {
    private final ObservableList<Schedule> schedules = FXCollections.observableArrayList();
    private final ObservableList<Schedule> internalUnmodifiableList = FXCollections.unmodifiableObservableList(schedules);

    public ScheduleList() {}

    public ScheduleList(ReadOnlyScheduleList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    public void setSchedules(List<Schedule> schedules) {
        requireNonNull(schedules);
        this.schedules.addAll(schedules);
    }

    public void resetData(ReadOnlyScheduleList newData) {
        requireNonNull(newData);
        setSchedules(schedules);
    }

    public boolean hasSchedule(Schedule schedule) {
        requireNonNull(schedule);
        return this.schedules.contains(schedule);
    }

    public void addSchedule(Schedule schedule) {
        requireNonNull(schedule);
        this.schedules.add(schedule);
    }

    public void setSchedule(Schedule target, Schedule editedSchedule) {
        requireNonNull(target);
        requireNonNull(editedSchedule);
        int idx = schedules.indexOf(target);
        schedules.set(idx, editedSchedule);
    }

    public void removeSchedule(Schedule schedule) {
        requireNonNull(schedule);
        schedules.remove(schedule);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("schedules", this.schedules)
                .toString();
    }

    @Override
    public Iterator<Schedule> iterator() {
        return this.schedules.iterator();
    }

    @Override
    public ObservableList<Schedule> getScheduleList() {
        return this.internalUnmodifiableList;
    }

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

    @Override
    public int hashCode() {
        return this.schedules.hashCode();
    }
}
